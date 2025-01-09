package dev.definedentity.astralgenerators.blocks.ports

import java.util.Locale

enum class PortType {
    FLUID,
    ITEM,
    ENERGY;

    override fun toString(): String {
        return super.toString().lowercase(Locale.getDefault())
    }
}