package dev.definedentity.astralgenerators.sounds

import dev.definedentity.astralgenerators.utils.AGIdentifier
import net.minecraft.core.Registry
import net.minecraft.sounds.SoundEvent

object AGSounds {
    val ASSEMBLER_ID = AGIdentifier("assembler")
    val ASSEMBLER = SoundEvent(ASSEMBLER_ID)

    fun init() {
        Registry.register(Registry.SOUND_EVENT, ASSEMBLER_ID, ASSEMBLER)
    }
}