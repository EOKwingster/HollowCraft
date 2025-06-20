package com.eokwingster.hollowcraft.world.item;

import com.eokwingster.hollowcraft.HCConfig;
import com.eokwingster.hollowcraft.client.gui.hud.LookingDirectionIndicator;
import com.eokwingster.hollowcraft.world.attachmentdata.HCAttachmentTypes;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;

import static com.eokwingster.hollowcraft.HollowCraft.MODID;

public class NailItem extends SwordItem {
    public static final String TRANSLATABLE_ID_OLD_NAIL = Util.makeDescriptionId("item", ResourceLocation.fromNamespaceAndPath(MODID, "old_nail"));
    public static final String TRANSLATABLE_ID_SHARPENED_NAIL = Util.makeDescriptionId("item", ResourceLocation.fromNamespaceAndPath(MODID, "sharpened_nail"));
    public static final String TRANSLATABLE_ID_CHANNELLED_NAIL = Util.makeDescriptionId("item", ResourceLocation.fromNamespaceAndPath(MODID, "channelled_nail"));
    public static final String TRANSLATABLE_ID_COILED_NAIL = Util.makeDescriptionId("item", ResourceLocation.fromNamespaceAndPath(MODID, "coiled_nail"));
    public static final String TRANSLATABLE_ID_PURE_NAIL = Util.makeDescriptionId("item", ResourceLocation.fromNamespaceAndPath(MODID, "pure_nail"));

    private static boolean bounce = false;

    public NailItem(Tier pTier, Properties pProperties) {
        super(pTier, pProperties.setNoRepair().fireResistant().rarity(Rarity.EPIC));
    }

    @Override
    public void postHurtEnemy(ItemStack pStack, LivingEntity pTarget, LivingEntity pAttacker) {
        bounce = LookingDirectionIndicator.lookingDirection == LookingDirectionIndicator.LookingDirection.DOWNWARD;
    }

    public static ItemAttributeModifiers createAttributes() {
        return SwordItem.createAttributes(HCTiers.NAIL, 0, HCConfig.nailAttackSpeed);
    }

    public static float nailStateFunction(ItemStack pStack, @Nullable ClientLevel pLevel, @Nullable LivingEntity pEntity, int pSeed) {
        if (pEntity instanceof LocalPlayer player) {
            return player.getData(HCAttachmentTypes.NAIL_LEVEL).getLevel() / 10.0F;
        }
        return 0.0F;
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

    @Override
    public Component getName(ItemStack pStack) {
        return switch (Minecraft.getInstance().player.getData(HCAttachmentTypes.NAIL_LEVEL).getLevel()) {
            case 0 -> Component.translatable(TRANSLATABLE_ID_OLD_NAIL);
            case 1 -> Component.translatable(TRANSLATABLE_ID_SHARPENED_NAIL);
            case 2 -> Component.translatable(TRANSLATABLE_ID_CHANNELLED_NAIL);
            case 3 -> Component.translatable(TRANSLATABLE_ID_COILED_NAIL);
            case 4 -> Component.translatable(TRANSLATABLE_ID_PURE_NAIL);
            default -> super.getName(pStack);
        };
    }
}
