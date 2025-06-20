package com.eokwingster.hollowcraft.world.entity;

import com.eokwingster.hollowcraft.client.sounds.HCSoundEvents;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageSource;

public interface IHCMob {
    default SoundEvent getHollowHurtSound(DamageSource pDamageSource) {
        return HCSoundEvents.ENEMY_DAMAGE.get();
    }
}
