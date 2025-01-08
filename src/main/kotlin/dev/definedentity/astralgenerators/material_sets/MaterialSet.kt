package dev.definedentity.astralgenerators.material_sets

import com.tterrag.registrate.providers.RegistrateRecipeProvider.has
import com.tterrag.registrate.util.entry.BlockEntry
import com.tterrag.registrate.util.entry.ItemEntry
import dev.definedentity.astralgenerators.AstralGenerators.REGISTRATE
import dev.definedentity.astralgenerators.utils.CIdentifier
import dev.definedentity.astralgenerators.utils.TextFormatting
import me.alphamode.forgetags.Tags
import net.minecraft.client.color.block.BlockColor
import net.minecraft.client.color.item.ItemColor
import net.minecraft.client.renderer.RenderType
import net.minecraft.core.Registry
import net.minecraft.data.recipes.ShapedRecipeBuilder
import net.minecraft.data.recipes.ShapelessRecipeBuilder
import net.minecraft.resources.ResourceLocation
import net.minecraft.tags.BlockTags
import net.minecraft.tags.TagKey
import net.minecraft.world.item.Item
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.SoundType
import net.minecraft.world.level.block.state.BlockBehaviour
import net.minecraft.world.level.material.Material
import java.util.function.Supplier

class MaterialSet(
    private val name: String,
    private val color: Int
) {
    val INGOT_TAG = TagKey.create(Registry.ITEM_REGISTRY, CIdentifier("ingots/${name}"))
    val NUGGET_TAG = TagKey.create(Registry.ITEM_REGISTRY, CIdentifier("nuggets/${name}"))
    val SHEET_TAG = TagKey.create(Registry.ITEM_REGISTRY, CIdentifier("sheets/${name}"))
    val PLATE_TAG = TagKey.create(Registry.ITEM_REGISTRY, CIdentifier("plates/${name}"))
    val ROD_TAG = TagKey.create(Registry.ITEM_REGISTRY, CIdentifier("rods/${name}"))
    val DUST_TAG = TagKey.create(Registry.ITEM_REGISTRY, CIdentifier("dusts/${name}"))

    val BLOCK_TAG = TagKey.create(Registry.BLOCK_REGISTRY, CIdentifier("storage_blocks/${name}"))
    val FRAME_TAG = TagKey.create(Registry.ITEM_REGISTRY, CIdentifier("frames/${name}"))

    private val itemColorSupplier: Supplier<ItemColor> = Supplier {
        ItemColor { _, _ -> color }
    }

    private val blockColorSupplier: Supplier<BlockColor> = Supplier {
        BlockColor { _, _, _, _ -> color }
    }

    val NUGGET: ItemEntry<Item> = createNugget()
    val INGOT: ItemEntry<Item> = createIngot()
    val SHEET: ItemEntry<Item> = createSheet()
    val ROD: ItemEntry<Item> = createRod()
    val DUST: ItemEntry<Item> = createDust()

    val BLOCK: BlockEntry<Block> = createBlock()
    val FRAME: BlockEntry<Block> = createFrame()

    private fun createIngot(): ItemEntry<Item> {
        val fullName = "${name}_ingot"
        return REGISTRATE.item(fullName, ::Item)
            .lang(TextFormatting.toEnglishName(fullName))
            .model { ctx, prov ->
                prov.generated(
                    { ctx.get() },
                    prov.modLoc("item/material_sets/ingot"),
                    prov.modLoc("item/material_sets/ingot_overlay"),
                    prov.modLoc("item/material_sets/ingot_secondary")
                )
            }
            .color { itemColorSupplier }.tag(INGOT_TAG, Tags.Items.INGOTS).properties { p ->
                p.stacksTo(64)
            }.recipe { ctx, prov ->
                ShapedRecipeBuilder.shaped(ctx.entry, 1).pattern("xxx").pattern("xxx").pattern("xxx")
                    .define("x"[0], NUGGET_TAG).unlockedBy("has_nugget", has(NUGGET_TAG)).save(prov)
                ShapelessRecipeBuilder.shapeless(ctx.entry, 9).requires(BLOCK.get())
                    .unlockedBy("has_block", has(BLOCK.get())).save(prov, "${name}_block_to_${name}_ingot")
            }
            .register()
    }

    private fun createNugget(): ItemEntry<Item> {
        val fullName = "${name}_nugget"
        return REGISTRATE.item(fullName, ::Item)
            .lang(TextFormatting.toEnglishName(fullName))
            .model { ctx, prov ->
                prov.generated(
                    { ctx.get() },
                    prov.modLoc("item/material_sets/nugget"),
                    prov.modLoc("item/material_sets/nugget_overlay"),
                    prov.modLoc("item/material_sets/nugget_secondary")
                )
            }
            .color { itemColorSupplier }.tag(NUGGET_TAG, Tags.Items.NUGGETS).properties { p ->
                p.stacksTo(64)
            }.recipe { ctx, prov ->
                ShapelessRecipeBuilder.shapeless(ctx.entry, 9).requires(INGOT_TAG)
                    .unlockedBy("has_ingot", has(INGOT_TAG)).save(prov)
            }
            .register()
    }

    private fun createSheet(): ItemEntry<Item> {
        val fullName = "${name}_sheet"
        return REGISTRATE.item(fullName, ::Item)
            .lang(TextFormatting.toEnglishName(fullName))
            .model { ctx, prov ->
                prov.generated(
                    { ctx.get() },
                    prov.modLoc("item/material_sets/sheet"),
                    prov.modLoc("item/material_sets/sheet_overlay"),
                    prov.modLoc("item/material_sets/sheet_secondary")
                )
            }
            .color { itemColorSupplier }.tag(SHEET_TAG, PLATE_TAG).properties { p ->
                p.stacksTo(64)
            }.register()
    }

    private fun createRod(): ItemEntry<Item> {
        val fullName = "${name}_rod"
        return REGISTRATE.item(fullName, ::Item)
            .lang(TextFormatting.toEnglishName(fullName))
            .model { ctx, prov ->
                prov.generated(
                    { ctx.get() },
                    prov.modLoc("item/material_sets/rod"),
                    prov.modLoc("item/material_sets/rod_secondary")
                )
            }
            .color { itemColorSupplier }.tag(ROD_TAG, Tags.Items.RODS).properties { p ->
                p.stacksTo(64)
            }.register()
    }

    private fun createDust(): ItemEntry<Item> {
        val fullName = "${name}_dust"
        return REGISTRATE.item(fullName, ::Item)
            .lang(TextFormatting.toEnglishName(fullName))
            .model { ctx, prov ->
                prov.generated(
                    { ctx.get() },
                    prov.modLoc("item/material_sets/dust"),
                    prov.modLoc("item/material_sets/dust_overlay"),
                    prov.modLoc("item/material_sets/dust_secondary")
                )
            }
            .color { itemColorSupplier }.tag(DUST_TAG, Tags.Items.DUSTS).properties { p ->
                p.stacksTo(64)
            }.register()
    }

    private fun createBlock(): BlockEntry<Block> {
        val fullName = "${name}_block"
        return REGISTRATE.block(fullName, ::Block)
            .lang(TextFormatting.toEnglishName(fullName))
            .item()
            .color { itemColorSupplier }
            .build()
            .properties {
                BlockBehaviour.Properties.of(Material.METAL).strength(5f, 6f).sound(SoundType.METAL)
                    .requiresCorrectToolForDrops()

            }.tag(
                BlockTags.MINEABLE_WITH_PICKAXE,
                BlockTags.NEEDS_IRON_TOOL,
                BlockTags.BEACON_BASE_BLOCKS,
                BLOCK_TAG
            )
            .blockstate { ctx, prov ->
                prov.simpleBlock(
                    ctx.entry,
                    prov.models()
                        .withExistingParent(ctx.name, "block/block")
                        .texture("all", prov.modLoc("block/material_sets/block").path)
                        .texture("particle", "#all")
                        .element()
                        .allFaces { _, faceBuilder ->
                            faceBuilder.tintindex(0)
                            faceBuilder.texture("#all")
                        }
                        .end()
                )
            }
            .color { blockColorSupplier }.recipe { ctx, prov ->
                ShapedRecipeBuilder.shaped(ctx.entry, 1).pattern("xxx").pattern("xxx").pattern("xxx")
                    .define("x"[0], INGOT_TAG).unlockedBy("has_ingot", has(INGOT_TAG)).save(prov)
            }
            .register()
    }

    private fun createFrame(): BlockEntry<Block> {
        val fullName = "${name}_frame"
        return REGISTRATE.block(fullName, ::Block)
            .lang(TextFormatting.toEnglishName(fullName))
            .item()
            .color { itemColorSupplier }
            .build()
            .properties {
                BlockBehaviour.Properties.of(Material.METAL).strength(5f, 6f).sound(SoundType.METAL)
                    .requiresCorrectToolForDrops()

            }.tag(BlockTags.MINEABLE_WITH_PICKAXE, BlockTags.NEEDS_IRON_TOOL)
            .blockstate { ctx, prov ->
                prov.simpleBlock(
                    ctx.entry,
                    prov.models()
                        .withExistingParent(ctx.name, "block/block")
                        .texture("all", prov.modLoc("block/material_sets/frame").path)
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
