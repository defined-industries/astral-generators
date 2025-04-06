package dev.toby0207.astralgenerators

import com.mojang.logging.LogUtils
import dev.toby0207.astralgenerators.data.AGContent
import dev.toby0207.astralgenerators.data.AGCreativeModeTabs
import dev.toby0207.astralgenerators.data.datagen.AGDatagen
import dev.toby0207.astralgenerators.data.items.AGItems
import net.minecraft.core.registries.Registries
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.item.CreativeModeTab
import net.neoforged.bus.api.IEventBus
import net.neoforged.bus.api.SubscribeEvent
import net.neoforged.fml.ModContainer
import net.neoforged.fml.common.Mod
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent
import net.neoforged.neoforge.common.NeoForge
import net.neoforged.neoforge.event.server.ServerStartingEvent
import net.neoforged.neoforge.registries.DeferredRegister

@Mod(AstralGenerators.MOD_ID)
class AstralGenerators(modEventBus: IEventBus, modContainer: ModContainer) {
    companion object {
        const val MOD_ID = "astralgenerators"
        const val MOD_NAME = "Astral Generators"

        val LOGGER = LogUtils.getLogger()

        fun id(path: String): ResourceLocation {
            return ResourceLocation.fromNamespaceAndPath(MOD_ID, path)
        }
    }

    init {
        modEventBus.addListener(::commonSetup)

        AGContent.init(modEventBus)
        AGCreativeModeTabs.init(modEventBus)

        modEventBus.addListener(AGDatagen::gatherData)

    }

    private fun commonSetup(event: FMLCommonSetupEvent) {
        LOGGER.info("HELLO FROM COMMON SETUP")
    }
}