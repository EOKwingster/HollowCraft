package com.eokwingster.hollowcraft.client.sounds;

import net.minecraft.client.resources.sounds.AbstractTickableSoundInstance;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;

public class FadingSoundInstance extends AbstractTickableSoundInstance {
    private float fadingVelocity;
    private float minVolume;
    private float maxVolume = 1.0F;

    public FadingSoundInstance(SoundEvent soundEvent, SoundSource source, float volume, float pitch, RandomSource random, BlockPos blockPos) {
        super(soundEvent, source, random);
        this.volume = volume;
        this.pitch = pitch;
        this.x = blockPos.getX();
        this.y = blockPos.getY();
        this.z = blockPos.getZ();
    }

    @Override
    public void tick() {
        volume = Math.clamp(volume + fadingVelocity, minVolume, maxVolume);
        if (volume == minVolume || volume == maxVolume) {
            fadingVelocity = 0.0F;
            minVolume = 0.0F;
            maxVolume = 1.0F;
        }
        if (volume == 0.0F) {
            stop();
        }
    }

    public void fadeOut() {
        fadeOutTo(0.0F, 0.05F);
    }

    public void fadeOutTo(float minVolume, float fadingSpeed) {
        this.minVolume = Math.clamp(minVolume, 0.0F, volume);
        fadingVelocity = -fadingSpeed;
    }

    public void fadeInTo(float maxVolume, float fadingSpeed) {
        this.maxVolume = Math.clamp(maxVolume, volume, 1.0F);
        this.fadingVelocity = fadingSpeed;
    }
}
