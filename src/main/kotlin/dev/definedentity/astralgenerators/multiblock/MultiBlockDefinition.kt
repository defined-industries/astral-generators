package dev.definedentity.astralgenerators.multiblock

import com.google.common.collect.ImmutableMap
import com.google.gson.JsonElement
import definedentity.xenon.vec.Rotation
import definedentity.xenon.vec.Vector3
import net.minecraft.core.BlockPos
import net.minecraft.core.Registry
import net.minecraft.resources.ResourceLocation
import net.minecraft.tags.TagKey
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.Blocks

class MultiBlockDefinition(val id: ResourceLocation, val json: JsonElement) {

    private var origin = BlockPos.ZERO
    private val blockMap: HashMap<BlockPos, MultiBlockPart> = HashMap()

    init {
        loadFromJson()
    }

    fun getBlocks(): ImmutableMap<BlockPos, MultiBlockPart> {
        return ImmutableMap.copyOf(blockMap)
    }

    fun getBlockAt(worldOrigin: BlockPos): ImmutableMap<BlockPos, MultiBlockPart> {
        val translated = HashMap<BlockPos, MultiBlockPart>()

        blockMap.forEach { (pos, part) ->
            {
                translated[worldOrigin.offset(pos)] = part
            }
        }
        return ImmutableMap.copyOf(translated)
    }

    fun getBlockAt(worldOrigin: BlockPos, rotation: Rotation): ImmutableMap<BlockPos, MultiBlockPart> {
        val translated = HashMap<BlockPos, MultiBlockPart>()
        val transform = rotation.at(Vector3.CENTER).inverse()

        blockMap.forEach { (pos, part) ->
            {
                val vec = Vector3.fromBlockPosCenter(pos)
                vec.apply(transform)
                translated[worldOrigin.offset(vec.pos())] = part
            }
        }
        return ImmutableMap.copyOf(translated)
    }

    fun getBlocks(rotation: Rotation): ImmutableMap<BlockPos?, MultiBlockPart?> {
        val transformed = HashMap<BlockPos, MultiBlockPart>()
        val transform = rotation.at(Vector3.CENTER).inverse()

        blockMap.forEach { (pos, part) ->
            {
                val vec = Vector3.fromBlockPosCenter(pos)
                vec.apply(transform)
                transformed.put(vec.pos(), part)
            }

        }
        return ImmutableMap.copyOf(transformed)
    }

    fun test(level: Level, originPos: BlockPos): ArrayList<InvalidPart> {
        val result = ArrayList<InvalidPart>()

        getBlockAt(originPos).forEach { pos, part ->
            {
                if (!part.isMatch(level, pos)) {
                    result.add(InvalidPart(pos, part))
                }
            }
        }
        return result
    }

    fun test(level: Level, originPos: BlockPos, rotation: Rotation): ArrayList<InvalidPart> {
        val result = ArrayList<InvalidPart>()

        getBlockAt(originPos, rotation).forEach { pos, part ->
            {
                if (!part.isMatch(level, pos)) {
                    result.add(InvalidPart(pos, part))
                }
            }
        }
        return result
    }

    private fun loadFromJson() {
        val obj = json.asJsonObject

        if (obj.has("origin")) {
            val origin = obj.getAsJsonObject("origin")

            val x = origin.get("x").asInt
            val y = origin.get("y").asInt
            val z = origin.get("z").asInt

            this.origin = BlockPos(x, y, z)
        }

        val keysObj = obj.getAsJsonObject("keys")
        val keyMap = HashMap<Char, MultiBlockPart>()

        for (entry in keysObj.entrySet()) {
            val keyStr = entry.key

            if (keyStr.length != 1) {
                throw IllegalArgumentException("Key must be a single character")
            }

            val keyChar = keyStr[0]

            if (keyMap.containsKey(keyChar)) {
                throw IllegalArgumentException("Duplicate key: $keyChar")
            }

            val value = entry.value.asJsonObject
            var part: MultiBlockPart? = null

            if (value.has("tag")) {
                val tagString = value.get("tag").asString
                val res = ResourceLocation(tagString)
                val tagKey = TagKey.create(Registry.BLOCK_REGISTRY, res)
                part = TagPart(tagKey)
            } else if (value.has("block")) {
                val blockString = value.get("block").asString
                val res = ResourceLocation(blockString)
                val block = Registry.BLOCK.getOptional(res).orElseThrow {
                    IllegalArgumentException("Specified block could not be found: $blockString")
                }

                part = if (Registry.BLOCK.getKey(Blocks.AIR) == res) {
                    EmptyPart()
                } else {
                    BlockPart(res)
                }
            } else {
                throw IllegalArgumentException("Invalid block key detected!: $value, $id");
            }

            keyMap[keyChar] = part
        }

        val structure = obj.getAsJsonArray("structure")
        val layers = structure.size()

        for (layer in 0 until layers) {
            val layerArray = structure.get(layer).asJsonArray
            val rows = layerArray.size()

            for (row in 0 until rows) {
                val rowString = layerArray.get(row).asString
                val length = rowString.length

                for (i in 0 until length) {
                    val key = rowString[i]

                    if (key == ' ') {
                        continue
                    }

                    val part = keyMap[key]

                    if (part == null) {
                        throw IllegalArgumentException("Undefined key in multiblock definition: $id, Key: $key");
                    }

                    val pos = BlockPos(i, layer, row).subtract(origin)

                    if (blockMap.putIfAbsent(pos, part) != null) {
                        throw IllegalStateException("Duplicate Position Detected at: $pos");
                    }
                }
            }
        }
    }
}