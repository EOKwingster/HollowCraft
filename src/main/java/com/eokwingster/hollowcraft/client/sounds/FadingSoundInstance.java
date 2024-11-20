package com.eokwingster.hollowcraft.client.sounds;

import net.minecraft.client.resources.sounds.AbstractTickableSoundInstance;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;

public class FadingSoundInstance extends AbstractTickableSoundInstance {
    private final float fadingSpeed;
    private boolean autoStop;
    private boolean isFadingIn;
    private boolean isFadingOut;

    public FadingSoundInstance(SoundEvent soundEvent, SoundSource soundSource, RandomSource randomSource, float fadingSpeed, boolean autoStop) {
        super(soundEvent, soundSource, randomSource);
        this.fadingSpeed = fadingSpeed;
        this.autoStop = autoStop;
    }

    @Override
    public void tick() {
        if (isFadingIn) {
            volume = Math.clamp(volume + fadingSpeed, 0.0F, 2.0F);
        } else if (isFadingOut) {
            volume = Math.clamp(volume - fadingSpeed, 0.0F, 2.0F);
            if (autoStop && isLooping() && volume == 0.0F) {
                stop();
            }
        }
    }

    public void fadeIn() {
        stopFadeOut();
        isFadingIn = true;
    }

    public void stopFadeIn() {
        isFadingIn = false;
    }

    public void fadeOut() {
        stopFadeIn();
        isFadingOut = true;
    }

    public void stopFadeOut() {
        isFadingOut = false;
    }
}
