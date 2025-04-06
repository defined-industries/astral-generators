package dev.toby0207.astralgenerators.data

import dev.toby0207.astralgenerators.AstralGenerators
import dev.toby0207.astralgenerators.AstralGenerators.Companion.MOD_ID
import dev.toby0207.astralgenerators.data.items.AGItems
import net.minecraft.core.registries.Registries
import net.minecraft.network.chat.Component
import net.minecraft.world.item.CreativeModeTab
import net.neoforged.bus.api.IEventBus
import net.neoforged.neoforge.registries.DeferredRegister
import java.util.function.Supplier

object AGCreativeModeTabs {
    val CREATIVE_MODE_TABS: DeferredRegister<CreativeModeTab> =
        DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MOD_ID)

    val GENERAL_TAB: Supplier<CreativeModeTab> = CREATIVE_MODE_TABS.register("general", Supplier {
        CreativeModeTab.builder().title(Component.translatable("itemGroup.${AstralGenerators.id("general")}"))
            .icon { AGItems.ASTRALNOMICON.asItem().defaultInstance }.displayItems { _, o ->
                run {
                    AGContent.ITEMS.entries.stream().forEach { i ->
                        o.accept(i.get())
                    }

                    AGContent.BLOCKS.entries.stream().forEach { b ->
                        o.accept(b.get())
                    }
                }
            }.build()
    })

    fun init(modBus: IEventBus) {
        CREATIVE_MODE_TABS.register(modBus)
    }
}