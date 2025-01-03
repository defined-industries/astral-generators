package dev.definedentity.astralgenerators.materials

import com.google.common.base.Supplier
import com.tterrag.registrate.util.entry.BlockEntry
import com.tterrag.registrate.util.entry.ItemEntry
import dev.definedentity.astralgenerators.AstralGenerators.REGISTRATE
import dev.definedentity.astralgenerators.utils.TextFormatting
import net.minecraft.client.color.block.BlockColor
import net.minecraft.client.color.item.ItemColor
import net.minecraft.world.item.Item
import net.minecraft.world.level.block.Block
import net.minecraftforge.client.model.generators.ModelFile

class MetalSet(
    private val name: String, private val color: Int
) {
    private val itemColor: Supplier<ItemColor> = Supplier {
        ItemColor { _, _ -> color }
    }
    private val blockColor: Supplier<BlockColor> = Supplier {
        BlockColor { _, _, _, _ -> color }
    }

    val NUGGET: ItemEntry<Item> = createItem("nugget")
    val INGOT: ItemEntry<Item> = createItem("ingot")
    val SHEET: ItemEntry<Item> = createItem("sheet")

    val BLOCK: BlockEntry<Block> =
        REGISTRATE.block("${name}_block", ::Block).lang(TextFormatting.toEnglishName("${name}_block")).item()
            .color { Supplier { ItemColor { _, _ -> color } } }.build().properties { p ->
                p.requiresCorrectToolForDrops().strength(1.0f, 6.0f)
            }.blockstate { ctx, prov ->
                prov.simpleBlock(
                    ctx.entry,
                    prov.models()
                        .withExistingParent(ctx.name, "block/block")
                        .texture("all", prov.modLoc("block/material_sets/dull/block").path)
                        .texture("particle", "#all")
                        .element()
                        .allFaces { direction, faceBuilder ->
                            faceBuilder.tintindex(0)
                            faceBuilder.texture(
                                "#all"
                            )
                        }.end()
                )
            }.color { blockColor }.register()

    private fun createItem(suffix: String): ItemEntry<Item> {
        val fullName = "${name}_$suffix"

        return REGISTRATE.item(fullName, ::Item).lang(TextFormatting.toEnglishName(fullName)).model { ctx, prov ->
            prov.generated(
                { ctx.get() },
                prov.modLoc("item/material_sets/bright/$suffix"),
                prov.modLoc("item/material_sets/bright/${suffix}_overlay"),
                prov.modLoc("item/material_sets/bright/${suffix}_secondary")
            )
        }.color { itemColor }.register()
    }
}
