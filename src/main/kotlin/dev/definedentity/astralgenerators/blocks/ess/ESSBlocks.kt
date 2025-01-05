package dev.definedentity.astralgenerators.blocks.ess

import com.tterrag.registrate.util.entry.BlockEntityEntry
import com.tterrag.registrate.util.entry.BlockEntry
import dev.definedentity.astralgenerators.blocks.ess.blockentities.BatteryTier1Entity
import dev.definedentity.astralgenerators.blocks.ess.blockentities.BatteryTier2Entity
import dev.definedentity.astralgenerators.blocks.ess.blockentities.BatteryTier3Entity
import dev.definedentity.astralgenerators.blocks.ess.blockentities.ESSCasingEntity
import dev.definedentity.astralgenerators.blocks.ess.blockentities.ESSGlassEntity
import dev.definedentity.astralgenerators.blocks.ess.blockentities.ESSInputHatchEntity
import dev.definedentity.astralgenerators.blocks.ess.blockentities.ESSOutputHatchEntity
import dev.definedentity.astralgenerators.registry.BatteryBlockRegistry
import dev.definedentity.astralgenerators.registry.CasingBlockRegistry
import dev.definedentity.astralgenerators.registry.GlassBlockRegistry
import dev.definedentity.astralgenerators.registry.PortBlockRegistry
import net.minecraft.core.Registry
import net.minecraft.world.level.block.RotatedPillarBlock

object ESSBlocks {
    val CASING: BlockEntry<ESSCasing> = CasingBlockRegistry.block("ess_casing", "ESS Casing", ::ESSCasing)
        .blockEntity { type, pos, state -> ESSCasingEntity(type, pos, state) }.build().register()
    val GLASS: BlockEntry<ESSGlass> = GlassBlockRegistry.block("ess_glass", "ESS Glass", ::ESSGlass)
        .blockEntity { type, pos, state -> ESSGlassEntity(type, pos, state) }.build().register()
    val BATTERY_TIER_1: BlockEntry<RotatedPillarBlock> =
        BatteryBlockRegistry.block("1", ::BatteryTier1)
            .blockEntity { type, pos, state -> BatteryTier1Entity(type, pos, state) }.build()
            .register()
    val BATTERY_TIER_2: BlockEntry<RotatedPillarBlock> =
        BatteryBlockRegistry.block("2", ::BatteryTier2)
            .blockEntity { type, pos, state -> BatteryTier2Entity(type, pos, state) }.build()
            .register()
    val BATTERY_TIER_3: BlockEntry<RotatedPillarBlock> =
        BatteryBlockRegistry.block("3", ::BatteryTier3)
            .blockEntity { type, pos, state -> BatteryTier3Entity(type, pos, state) }.build()
            .register()
    val INPUT_HATCH: BlockEntry<ESSInputHatch> =
        PortBlockRegistry.block("ess_input_hatch", "ESS Input Hatch", ::ESSInputHatch)
            .blockEntity { type, pos, state -> ESSInputHatchEntity(type, pos, state) }.build().register()
    val OUTPUT_HATCH: BlockEntry<ESSOutputHatch> =
        PortBlockRegistry.block("ess_output_hatch", "ESS Output Hatch", ::ESSOutputHatch)
            .blockEntity { type, pos, state -> ESSOutputHatchEntity(type, pos, state) }.build().register()

    val BATTERY_TIER_1_ENTITY: BlockEntityEntry<BatteryTier1Entity> =
        BlockEntityEntry.cast<BatteryTier1Entity>(
            BATTERY_TIER_1.getSibling(Registry.BLOCK_ENTITY_TYPE_REGISTRY)
        )
    val BATTERY_TIER_2_ENTITY: BlockEntityEntry<BatteryTier2Entity> =
        BlockEntityEntry.cast<BatteryTier2Entity>(
            BATTERY_TIER_2.getSibling(Registry.BLOCK_ENTITY_TYPE_REGISTRY)
        )
    val BATTERY_TIER_3_ENTITY: BlockEntityEntry<BatteryTier3Entity> =
        BlockEntityEntry.cast<BatteryTier3Entity>(
            BATTERY_TIER_3.getSibling(Registry.BLOCK_ENTITY_TYPE_REGISTRY)
        )

    val CASING_ENTITY: BlockEntityEntry<ESSCasingEntity> =
        BlockEntityEntry.cast<ESSCasingEntity>(
            CASING.getSibling(Registry.BLOCK_ENTITY_TYPE_REGISTRY)
        )
    val GLASS_ENTITY: BlockEntityEntry<ESSGlassEntity> =
        BlockEntityEntry.cast<ESSGlassEntity>(
            GLASS.getSibling(Registry.BLOCK_ENTITY_TYPE_REGISTRY)
        )

    val INPUT_HATCH_ENTITY: BlockEntityEntry<ESSInputHatchEntity> =
        BlockEntityEntry.cast<ESSInputHatchEntity>(
            INPUT_HATCH.getSibling(Registry.BLOCK_ENTITY_TYPE_REGISTRY)
        )

    val OUTPUT_HATCH_ENTITY: BlockEntityEntry<ESSOutputHatchEntity> =
        BlockEntityEntry.cast<ESSOutputHatchEntity>(
            OUTPUT_HATCH.getSibling(Registry.BLOCK_ENTITY_TYPE_REGISTRY)
        )

    fun init() {}
}