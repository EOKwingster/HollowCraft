package com.eokwingster.hollowcraft.client.sounds;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;

public class EntityBoundFadingSoundInstance extends FadingSoundInstance {
    private final Entity entity;

    public EntityBoundFadingSoundInstance(SoundEvent soundEvent, SoundSource source, float volume, float pitch, Entity entity) {
        super(soundEvent, source, volume, pitch, entity.getRandom(), entity.blockPosition());
        this.looping = true;
        this.entity = entity;
    }

    @Override
    public void tick() {
        if (!entity.isRemoved()) {
            this.x = entity.getX();
            this.y = entity.getY();
            this.z = entity.getZ();
        } else {
            this.stop();
        }
    }
}
