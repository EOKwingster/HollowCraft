package com.eokwingster.hollowcraft.world.item;

import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.phys.Vec3;

public class NailItem extends SwordItem {
    public static boolean slashKnockback = false;
    public static int knockbackDelay = 0;

    public static final float knockbackXRot = 30F;
    public static final double knockbackStrength = 0.333D;
    public static final int knockbackMaxDelay = 8;

    public NailItem(Tier pTier, Properties pProperties) {
        super(pTier, pProperties.setNoRepair().fireResistant().rarity(Rarity.EPIC));
    }

    @Override
    public void postHurtEnemy(ItemStack pStack, LivingEntity pTarget, LivingEntity pAttacker) {
        float xRot = pAttacker.getXRot();
        slashKnockback = xRot > 90F - knockbackXRot;
    }

    public static void tickSlashKnockback(LocalPlayer localPlayer) {
        if (knockbackDelay > 0) {
            knockbackDelay--;
        }
        if (slashKnockback && knockbackDelay == 0) {
            Vec3 vec3 = localPlayer.getDeltaMovement();
            localPlayer.setDeltaMovement(vec3.x, knockbackStrength, vec3.z);
            if (localPlayer.isSprinting()) {
                float YRotR = localPlayer.getYRot() * (float) (Math.PI / 180.0);
                localPlayer.addDeltaMovement(new Vec3((double)(-Mth.sin(YRotR)) * 0.2, 0.0, (double)Mth.cos(YRotR) * 0.2));
            }
            localPlayer.hasImpulse = true;
            knockbackDelay = knockbackMaxDelay;
            localPlayer.resetFallDistance();

            double x = localPlayer.getX();
            double y = localPlayer.getY();
            double z = localPlayer.getZ();
            localPlayer.level().addParticle(ParticleTypes.SWEEP_ATTACK, x, y - 1D, z, 0D, 0D, 0D);
        }
        slashKnockback = false;
    }
}
