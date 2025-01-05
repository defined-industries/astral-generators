package dev.definedentity.astralgenerators.multiblock

import com.google.common.collect.ImmutableList
import com.google.common.collect.ImmutableMap
import com.google.common.collect.Lists
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonElement
import definedentity.xenon.math.MathHelper.torad
import definedentity.xenon.vec.Quat
import definedentity.xenon.vec.Rotation
import dev.definedentity.astralgenerators.AstralGenerators
import dev.definedentity.astralgenerators.network.ChannelList
import net.fabricmc.api.EnvType
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking
import net.fabricmc.fabric.api.resource.IdentifiableResourceReloadListener
import net.fabricmc.fabric.api.resource.ResourceManagerHelper
import net.fabricmc.loader.api.FabricLoader
import net.minecraft.core.BlockPos
import net.minecraft.resources.ResourceLocation
import net.minecraft.server.level.ServerLevel
import net.minecraft.server.level.ServerPlayer
import net.minecraft.server.packs.PackType
import net.minecraft.server.packs.resources.ResourceManager
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener
import net.minecraft.util.profiling.ProfilerFiller
import net.minecraft.world.level.block.Block
import net.minecraft.world.phys.Vec3
import org.joml.Quaternionf

class MultiBlockManager :
    SimpleJsonResourceReloadListener(GSON, "multiblocks"), IdentifiableResourceReloadListener {
    companion object {
        val GSON: Gson = GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create()

        val LOCAL_DEFINITIONS = hashMapOf<ResourceLocation, MultiBlockDefinition>()
        val SERVER_DEFINITIONS = hashMapOf<ResourceLocation, MultiBlockDefinition>()

        fun init() {
            ResourceManagerHelper.get(PackType.SERVER_DATA)
                .registerReloadListener(MultiBlockManager())

            ServerLifecycleEvents.SYNC_DATA_PACK_CONTENTS.register(::syncDatapack)

            if (FabricLoader.getInstance().environmentType == EnvType.CLIENT) {
                ClientPlayConnectionEvents.DISCONNECT.register { _, _ ->
                    SERVER_DEFINITIONS.clear()
                }
            }
        }

        private fun syncDatapack(player: ServerPlayer, isJoined: Boolean) {
            if (!isJoined) return

            sendMultiBlockDefinition(player, getDefinitions())
        }

        private fun sendMultiBlockDefinition(
            player: ServerPlayer,
            multiBlockMap: Map<ResourceLocation, MultiBlockDefinition>
        ) {
            val packet = PacketByteBufs.create()
            packet.writeVarInt(multiBlockMap.size)

            for ((key, value) in multiBlockMap) {
                packet.writeResourceLocation(key)
                packet.writeUtf(GSON.toJson(value.json))
            }

            ServerPlayNetworking.send(player, ChannelList.MULTIBLOCK_CHANNEL, packet)
        }

        fun getDefinitions(): ImmutableMap<ResourceLocation, MultiBlockDefinition> {
            if (SERVER_DEFINITIONS.isEmpty()) {
                return ImmutableMap.copyOf(LOCAL_DEFINITIONS)
            }
            return ImmutableMap.copyOf(SERVER_DEFINITIONS)
        }

        fun getRegisteredIds(): ImmutableList<ResourceLocation> {
            return ImmutableList.copyOf(LOCAL_DEFINITIONS.keys)
        }

        fun getDefinition(id: ResourceLocation): MultiBlockDefinition? {
            return LOCAL_DEFINITIONS[id]
        }

        fun placeCommand(
            level: ServerLevel,
            placePos: BlockPos,
            id: ResourceLocation?,
            rotation: Vec3
        ): Int {
            val definition = getDefinition(id!!)

            if (definition != null) {
                for ((offset, value) in
                definition.getBlocks(
                    Rotation(
                        Quat(
                            Quaternionf()
                                .rotationXYZ(
                                    rotation.x.toFloat() * torad.toFloat(),
                                    rotation.y.toFloat() * torad.toFloat(),
                                    rotation.z.toFloat() * torad.toFloat()
                                )
                        )
                    )
                )) {
                    val blocks: List<Block> = Lists.newArrayList(value!!.validBlocks())
                    if (blocks.isNotEmpty()) {
                        val pos = placePos.offset(offset!!.x, offset.y, offset.z)
                        level.setBlockAndUpdate(pos, blocks[0].defaultBlockState())
                    }
                }

                return 0
            }

            return 1
        }
    }

    override fun apply(
        `object`: MutableMap<ResourceLocation, JsonElement>,
        resourceManager: ResourceManager,
        profiler: ProfilerFiller
    ) {
        LOCAL_DEFINITIONS.clear()

        for ((key, value) in `object`) {
            try {
                LOCAL_DEFINITIONS[key] = MultiBlockDefinition(key, value)
            } catch (e: Exception) {
                AstralGenerators.LOGGER.error(e.message)
            }
        }
    }

    override fun getFabricId(): ResourceLocation {
        return ResourceLocation(AstralGenerators.MOD_ID, "multiblocks")
    }
}