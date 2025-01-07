package dev.definedentity.astralgenerators.material_sets

import com.tterrag.registrate.util.entry.BlockEntry
import com.tterrag.registrate.util.entry.ItemEntry
import dev.definedentity.astralgenerators.AstralGenerators.REGISTRATE
import dev.definedentity.astralgenerators.utils.TextFormatting
import net.minecraft.client.color.block.BlockColor
import net.minecraft.client.color.item.ItemColor
import net.minecraft.client.renderer.RenderType
import net.minecraft.world.item.Item
import net.minecraft.world.level.block.Block
import java.util.function.Supplier

class MaterialSet(
    private val name: String,
    private val color: Int
) {
    private val itemColorSupplier: Supplier<ItemColor> = Supplier {
        ItemColor { _, _ -> color }
    }

    private val blockColorSupplier: Supplier<BlockColor> = Supplier {
        BlockColor { _, _, _, _ -> color }
    }

    val NUGGET: ItemEntry<Item> = createItemWithOverlay("nugget")
    val INGOT: ItemEntry<Item> = createItemWithOverlay("ingot")
    val SHEET: ItemEntry<Item> = createItemWithOverlay("sheet")
    val ROD: ItemEntry<Item> = createItemNoOverlay("rod")
    val DUST: ItemEntry<Item> = createItemNoOverlay("dust")

    val BLOCK: BlockEntry<Block> = createBlock("block")
    val FRAME: BlockEntry<Block> = createTranslucentBlock("frame")

    private fun createItemWithOverlay(id: String): ItemEntry<Item> {
        val fullName = "${name}_$id"
        return REGISTRATE.item(fullName, ::Item)
            .lang(TextFormatting.toEnglishName(fullName))
            .model { ctx, prov ->
                prov.generated(
                    { ctx.get() },
                    prov.modLoc("item/material_sets/$id"),
                    prov.modLoc("item/material_sets/${id}_overlay"),
                    prov.modLoc("item/material_sets/${id}_secondary")
                )
            }
            .color { itemColorSupplier }
            .register()
    }

    private fun createItemNoOverlay(id: String): ItemEntry<Item> {
        val fullName = "${name}_$id"
        return REGISTRATE.item(fullName, ::Item)
            .lang(TextFormatting.toEnglishName(fullName))
            .model { ctx, prov ->
                prov.generated(
                    { ctx.get() },
                    prov.modLoc("item/material_sets/$id"),
                    prov.modLoc("item/material_sets/${id}_secondary")
                )
            }
            .color { itemColorSupplier }
            .register()
    }

    private fun createBlock(id: String): BlockEntry<Block> {
        val fullName = "${name}_$id"
        return REGISTRATE.block(fullName, ::Block)
            .lang(TextFormatting.toEnglishName(fullName))
            .item()
            .color { itemColorSupplier }
            .build()
            .properties { p ->
                p.requiresCorrectToolForDrops().strength(1.0f, 6.0f)
            }
            .blockstate { ctx, prov ->
                prov.simpleBlock(
                    ctx.entry,
                    prov.models()
                        .withExistingParent(ctx.name, "block/block")
                        .texture("all", prov.modLoc("block/material_sets/$id").path)
                        .texture("particle", "#all")
                        .element()
                        .allFaces { _, faceBuilder ->
                            faceBuilder.tintindex(0)
                            faceBuilder.texture("#all")
                        }
                        .end()
                )
            }
            .color { blockColorSupplier }
            .register()
    }

    private fun createTranslucentBlock(id: String): BlockEntry<Block> {
        val fullName = "${name}_$id"
        return REGISTRATE.block(fullName, ::Block)
            .lang(TextFormatting.toEnglishName(fullName))
            .item()
            .color { itemColorSupplier }
            .build()
            .properties { p ->
                p.requiresCorrectToolForDrops().strength(1.0f, 6.0f).noOcclusion()
            }
            .blockstate { ctx, prov ->
                prov.simpleBlock(
                    ctx.entry,
                    prov.models()
                        .withExistingParent(ctx.name, "block/block")
                        .texture("all", prov.modLoc("block/material_sets/$id").path)
                        .texture("particle", "#all")
                        .element()
                        .allFaces { _, faceBuilder ->
                            faceBuilder.tintindex(0)
                            faceBuilder.texture("#all")
                        }
                        .end()
                )
            }
            .color { blockColorSupplier }
            .addLayer { Supplier { RenderType.translucent() } }
            .register()
    }
}
