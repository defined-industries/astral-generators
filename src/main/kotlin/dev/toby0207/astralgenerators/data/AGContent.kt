package dev.toby0207.astralgenerators.data

import dev.toby0207.astralgenerators.AstralGenerators.Companion.MOD_ID
import dev.toby0207.astralgenerators.data.blocks.AGBlocks
import dev.toby0207.astralgenerators.data.items.AGItems
import net.neoforged.bus.api.IEventBus
import net.neoforged.neoforge.registries.DeferredRegister

object AGContent {
    val BLOCKS: DeferredRegister.Blocks = DeferredRegister.createBlocks(MOD_ID)
    val ITEMS: DeferredRegister.Items = DeferredRegister.createItems(MOD_ID)

    fun init(modBus: IEventBus) {
        BLOCKS.register(modBus)
        ITEMS.register(modBus)

        AGItems.init()
        AGBlocks.init()
    }
}