package com.eokwingster.hollowcraft.client.gui.hud;

import com.eokwingster.hollowcraft.HCConfig;
import com.eokwingster.hollowcraft.skills.soul.Soul;
import com.eokwingster.hollowcraft.world.attachmentdata.HCAttachmentTypes;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.LayeredDraw;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

import java.util.function.Function;

import static com.eokwingster.hollowcraft.HollowCraft.MODID;

@OnlyIn(Dist.CLIENT)
public class SoulMeterAndVessels implements LayeredDraw.Layer {
    private static final ResourceLocation SOUL_METER_SPRITE = ResourceLocation.fromNamespaceAndPath(MODID, "hud/soul_meter_full");
    private static final ResourceLocation SOUL_VESSEL_SPRITE = ResourceLocation.fromNamespaceAndPath(MODID, "hud/soul_vessel");

    private static final int soulMeterSpriteWidth = 130;
    private static final int soulMeterSpriteHeight = 125;
    private static final int soulVesselSpriteWidth = 45;
    private static final int soulVesselSpriteHeight = 44;

    @Override
    public void render(GuiGraphics pGuiGraphics, DeltaTracker pDeltaTracker) {
        Minecraft mc = Minecraft.getInstance();
        LocalPlayer player = mc.player;
        Soul soulAttach = player.getData(HCAttachmentTypes.SOUL);

        //calculate soul values
        int soul = soulAttach.getSoul();
        int soulMeterCap = soulAttach.getSoulMeterCapacity();
        int meterSoul = Math.min(soul, soulMeterCap);
        int vesselSoul = soul - meterSoul;
        int soulVesselCap = soulAttach.getSoulVesselCapacity();
        int vessel = soulAttach.getVessel();
        int fullVessel = vesselSoul / soulVesselCap;
        int lastVesselSoul = vesselSoul % soulVesselCap;

        //set sprites' sizes and positions
        int i = 9 - HCConfig.soulMeterScale;
        int soulMeterWidth = soulMeterSpriteWidth / i;
        int soulMeterHeight = soulMeterSpriteHeight / i;
        int soulVesselWidth = soulVesselSpriteWidth / i;
        int soulVesselHeight = soulVesselSpriteHeight / i;

        //set sprites' positions
        int guiWidth = pGuiGraphics.guiWidth();
        int guiHeight = pGuiGraphics.guiHeight();
        int wholeSpriteX = Math.min(HCConfig.soulMeterX, guiWidth - soulMeterWidth - soulVesselWidth);
        int wholeSpriteY = Math.min(HCConfig.soulMeterY, guiHeight - soulMeterHeight - soulVesselHeight);
        int soulMeterX = wholeSpriteX + soulVesselWidth;
        int soulMeterY = wholeSpriteY;

        //vesselX and vesselY functions
        int soulMeterCenterX = soulMeterX + soulMeterWidth / 2;
        int soulMeterCenterY = soulMeterY + soulMeterHeight / 2;
        float distance = (float) (soulMeterWidth + soulVesselWidth) / 2;
        double separateAngle = Math.toRadians(30);
        Function<Integer, Integer> vesselX = (n) -> (int) (soulMeterCenterX - distance * Math.cos(n * separateAngle) - (double) soulVesselWidth / 2);
        Function<Integer, Integer> vesselY = (n) -> (int) (soulMeterCenterY + distance * Math.sin(n * separateAngle) - (double) soulVesselHeight / 2);

        //render background
        RenderSystem.enableBlend();
        pGuiGraphics.setColor(0.2F, 0.2F, 0.2F, 0.7F);
        pGuiGraphics.blitSprite(SOUL_METER_SPRITE, soulMeterX, soulMeterY, soulMeterWidth, soulMeterHeight);
        for (int n = 0; n < vessel; n++) {
            pGuiGraphics.blitSprite(SOUL_VESSEL_SPRITE, vesselX.apply(n), vesselY.apply(n), soulVesselWidth, soulVesselHeight);
        }
        pGuiGraphics.setColor(1F, 1F, 1F, 1F);

        //render soul meter and vessels
        int meterVH = soulMeterHeight * meterSoul / soulMeterCap;
        int meterVP = soulMeterHeight - meterVH;
        pGuiGraphics.blitSprite(SOUL_METER_SPRITE, soulMeterWidth, soulMeterHeight, 0, meterVP, soulMeterX, soulMeterY + meterVP, soulMeterWidth, meterVH);
        for (int n = 0; n < vessel; n++) {
            int vesselVH = n < fullVessel ? soulVesselHeight : soulVesselHeight * lastVesselSoul / soulVesselCap;
            int vesselVP = soulVesselHeight - vesselVH;
            pGuiGraphics.blitSprite(SOUL_VESSEL_SPRITE, soulVesselWidth, soulVesselHeight, 0, vesselVP, vesselX.apply(n), vesselY.apply(n) + vesselVP, soulVesselWidth, vesselVH);
            if (n == fullVessel) {
                break;
            }
        }

        RenderSystem.disableBlend();
    }
}
