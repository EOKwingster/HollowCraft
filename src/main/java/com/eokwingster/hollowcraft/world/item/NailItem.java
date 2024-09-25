package com.eokwingster.hollowcraft.world.item;

import com.eokwingster.hollowcraft.HCConfig;
import com.eokwingster.hollowcraft.client.gui.hud.LookingDirectionIndicator;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.phys.Vec3;

public class NailItem extends SwordItem {
    public static boolean bounce = false;

    public NailItem(Tier pTier, Properties pProperties) {
        super(pTier, pProperties.setNoRepair().fireResistant().rarity(Rarity.EPIC));
    }

    @Override
    public void postHurtEnemy(ItemStack pStack, LivingEntity pTarget, LivingEntity pAttacker) {
        bounce = LookingDirectionIndicator.lookingDirection == LookingDirectionIndicator.LookingDirection.DOWNWARD;
    }

    public static ItemAttributeModifiers createAttributes(float damage) {
        return SwordItem.createAttributes(HCTiers.NAIL, damage, HCConfig.nailAttackSpeed);
    }

    public static void tickNailBounce(LocalPlayer localPlayer) {
        if (bounce) {
            Vec3 vec3 = localPlayer.getDeltaMovement();
            localPlayer.setDeltaMovement(vec3.x, HCConfig.bounceStrength, vec3.z);
            if (localPlayer.isSprinting()) {
                float YRotR = localPlayer.getYRot() * (float) (Math.PI / 180.0);
                localPlayer.addDeltaMovement(new Vec3((double)(-Mth.sin(YRotR)) * 0.2, 0.0, (double)Mth.cos(YRotR) * 0.2));
            }
            localPlayer.hasImpulse = true;
            localPlayer.resetFallDistance();

            double x = localPlayer.getX();
            double y = localPlayer.getY();
            double z = localPlayer.getZ();
            localPlayer.level().addParticle(ParticleTypes.SWEEP_ATTACK, x, y - 1D, z, 0D, 0D, 0D);
        }
        bounce = false;
    }
}
