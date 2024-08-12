package com.eokwingster.hollowcraft.client.gui;

import com.eokwingster.hollowcraft.HCConfig;
import com.eokwingster.hollowcraft.tags.HCItemTags;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.AttackIndicatorStatus;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.Options;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.LayeredDraw;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.GameType;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

import static com.eokwingster.hollowcraft.HollowCraft.MODID;

@OnlyIn(Dist.CLIENT)
public class LookingDirectionIndicator implements LayeredDraw.Layer {
    private static final ResourceLocation LOOKING_DIRECTION_INDICATOR_UPWARD_SPRITE = ResourceLocation.fromNamespaceAndPath(MODID, "hud/looking_direction_indicator_upward");
    private static final ResourceLocation LOOKING_DIRECTION_INDICATOR_FORWARD_SPRITE = ResourceLocation.fromNamespaceAndPath(MODID, "hud/looking_direction_indicator_forward");
    private static final ResourceLocation LOOKING_DIRECTION_INDICATOR_DOWNWARD_SPRITE = ResourceLocation.fromNamespaceAndPath(MODID, "hud/looking_direction_indicator_downward");

    public static LookingDirection lookingDirection;

    private ResourceLocation lastSprite;

    public LookingDirectionIndicator() {
        lookingDirection = LookingDirection.VOID;
        lastSprite = LOOKING_DIRECTION_INDICATOR_FORWARD_SPRITE;
    }

    @Override
    public void render(GuiGraphics pGuiGraphics, DeltaTracker pDeltaTracker) {
        Minecraft mc = Minecraft.getInstance();
        LocalPlayer player = mc.player;
        ClientLevel level = mc.level;

        if (player != null) {
            if (level != null) {
                float viewXRot = player.getViewXRot(pDeltaTracker.getGameTimeDeltaPartialTick(level.tickRateManager().runsNormally()));
                if (viewXRot > 90 - HCConfig.lookDownwardAngle) {
                    lookingDirection = LookingDirection.DOWNWARD;
                } else if (viewXRot < HCConfig.lookForwardAngle && viewXRot > -HCConfig.lookForwardAngle) {
                    lookingDirection = LookingDirection.FORWARD;
                } else if (viewXRot < -90 + HCConfig.lookUpwardAngle) {
                    lookingDirection = LookingDirection.UPWARD;
                } else {
                    lookingDirection = LookingDirection.VOID;
                }
            }
        }

        Options options = mc.options;
        if (options.getCameraType().isFirstPerson()) {
            if (mc.gameMode != null && mc.gameMode.getPlayerMode() != GameType.SPECTATOR) {
                if (options.attackIndicator().get() == AttackIndicatorStatus.CROSSHAIR) {
                    if (player != null && player.getMainHandItem().is(HCItemTags.NAIL)) {
                        RenderSystem.enableBlend();

                        //set sprite's size
                        int i = 9 - HCConfig.lookingDirectionIndicatorScale;
                        int spriteWidth = 200 / i;
                        int spriteHeight = 360 / i;
                        //set offsets of sprites
                        int guiWidth = pGuiGraphics.guiWidth();
                        int guiHeight = pGuiGraphics.guiHeight();
                        int xOffset1 = HCConfig.lookingDirectionIndicatorXOffset;
                        int yOffset1 = HCConfig.lookingDirectionIndicatorYOffset;
                        int xOffset = Math.min(xOffset1, guiWidth - spriteWidth);
                        int yOffset = Math.min(yOffset1, guiHeight - spriteHeight);
                        //set sprite's position
                        int spriteX;
                        int spriteY;
                        POSITION indicatorPosition = HCConfig.lookingDirectionIndicatorPosition;
                        if (indicatorPosition.isLeft()) {
                            spriteX = xOffset;
                        } else {
                            spriteX = guiWidth - xOffset - spriteWidth;
                        }
                        if (indicatorPosition.isTop()) {
                            spriteY = yOffset;
                        } else {
                            spriteY = guiHeight - yOffset - spriteHeight;
                        }
                        //set the sprite
                        ResourceLocation sprite;
                        switch (lookingDirection) {
                            case UPWARD -> sprite = LOOKING_DIRECTION_INDICATOR_UPWARD_SPRITE;
                            case DOWNWARD -> sprite = LOOKING_DIRECTION_INDICATOR_DOWNWARD_SPRITE;
                            case FORWARD -> sprite = LOOKING_DIRECTION_INDICATOR_FORWARD_SPRITE;
                            default -> {
                                sprite = lastSprite;
                                pGuiGraphics.setColor(0.2F, 0.2F, 0.2F, 0.7F);
                            }
                        }
                        lastSprite = sprite;
                        //set sprite's direction;
                        int spriteCenterX = spriteX + spriteWidth / 2;
                        boolean inRightHalf = spriteCenterX > guiWidth / 2;
                        if (inRightHalf) {
                            //flip the sprite left and right
                            spriteX += spriteWidth;
                            spriteY += spriteHeight;
                            spriteWidth = -spriteWidth;
                            spriteHeight = -spriteHeight;
                            if (sprite == LOOKING_DIRECTION_INDICATOR_UPWARD_SPRITE) {
                                sprite = LOOKING_DIRECTION_INDICATOR_DOWNWARD_SPRITE;
                            } else if (sprite == LOOKING_DIRECTION_INDICATOR_DOWNWARD_SPRITE) {
                                sprite = LOOKING_DIRECTION_INDICATOR_UPWARD_SPRITE;
                            }
                        }

                        pGuiGraphics.blitSprite(sprite, spriteX, spriteY, spriteWidth, spriteHeight);

                        pGuiGraphics.setColor(1F, 1F, 1F, 1F);
                        RenderSystem.disableBlend();
                    }
                }
            }
        }
    }

    @OnlyIn(Dist.CLIENT)
    public enum LookingDirection {
        VOID, UPWARD, DOWNWARD, FORWARD
    }

    @OnlyIn(Dist.CLIENT)
    public enum POSITION {
        TOP_LEFT, TOP_RIGHT, BOTTOM_LEFT, BOTTOM_RIGHT;

        public boolean isLeft() {
            return this == TOP_LEFT || this == BOTTOM_LEFT;
        }

        public boolean isRight() {
            return this == BOTTOM_RIGHT || this == TOP_RIGHT;
        }

        public boolean isTop (){
            return this == TOP_LEFT || this == TOP_RIGHT;
        }

        public boolean isBottom() {
            return this == BOTTOM_LEFT || this == BOTTOM_RIGHT;
        }
    }
}
