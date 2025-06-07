package com.eokwingster.hollowcraft.event;

import com.eokwingster.hollowcraft.client.gui.hud.LookingDirectionIndicator;
import com.eokwingster.hollowcraft.client.keymapping.HCKeyMappings;
import com.eokwingster.hollowcraft.client.sounds.FadingSoundInstance;
import com.eokwingster.hollowcraft.client.sounds.HCSoundEvents;
import com.eokwingster.hollowcraft.network.SpellData;
import com.eokwingster.hollowcraft.skills.soul.Soul;
import com.eokwingster.hollowcraft.skills.spells.Focus;
import com.eokwingster.hollowcraft.tags.HCItemTags;
import com.eokwingster.hollowcraft.world.attachmentdata.HCAttachmentTypes;
import com.eokwingster.hollowcraft.world.item.NailItem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.sounds.SoundManager;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.Level;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.client.event.InputEvent;
import net.neoforged.neoforge.network.PacketDistributor;

import static com.eokwingster.hollowcraft.HollowCraft.MODID;

@EventBusSubscriber(modid = MODID, value = Dist.CLIENT)
public class GameEventBusClientHandler {
    private static int spellKeyDownTime;

    //make nails can not attack while attack strength is not full
    @SubscribeEvent
    private static void onInteractionKeyTriggered(InputEvent.InteractionKeyMappingTriggered event) {
        if (event.isAttack()) {
            LocalPlayer player = Minecraft.getInstance().player;
            if (player.getItemInHand(event.getHand()).is(HCItemTags.NAIL) && player.getAttackStrengthScale(0F) < 1) {
                event.setSwingHand(false);
                event.setCanceled(true);
            }
        }
    }

    private static FadingSoundInstance focusChargingSound = null;
    @SubscribeEvent
    private static void postClientTick(ClientTickEvent.Post event) {
        Minecraft mc = Minecraft.getInstance();
        SoundManager soundManager = mc.getSoundManager();
        Level level = mc.level;
        LocalPlayer player = mc.player;

        // handle nail bounce
        NailItem.tickNailBounce(player);

        // spell key logic
        if (HCKeyMappings.KEY_SPELL.isDown()) {
            if (spellKeyDownTime > 5) {
                //trigger focus effect
                PacketDistributor.sendToServer(SpellData.make(LookingDirectionIndicator.lookingDirection, spellKeyDownTime));

                // handle focus charging sound
                Soul soulAttach = player.getData(HCAttachmentTypes.SOUL);
                if (focusChargingSound == null && soulAttach.getSoul() != 0) {
                    focusChargingSound = new FadingSoundInstance(HCSoundEvents.FOCUS_CHARGING.get(), SoundSource.AMBIENT, 1.0F, 1.0F, player.getRandom(), player.blockPosition());
                    soundManager.play(focusChargingSound);
                } else if (focusChargingSound != null && soulAttach.getSoul() == 0) {
                    focusChargingSound.fadeOut();
                    focusChargingSound = null;
                }

                // handle focus complete sound
                int focusTime = (spellKeyDownTime - 5) % Focus.ONCE_TIME;
                if (focusTime == 0) {
                    level.playLocalSound(player, HCSoundEvents.FOCUS_HEAL.get(), SoundSource.PLAYERS, 1F, 1F);
                }
            }

            spellKeyDownTime++;
        } else {
            // trigger instant spells' effect
            if (spellKeyDownTime > 0 && spellKeyDownTime <= 5) {
                PacketDistributor.sendToServer(SpellData.make(LookingDirectionIndicator.lookingDirection, spellKeyDownTime));

            // focus stop sound fade out
            } else if (focusChargingSound != null) {
                focusChargingSound.fadeOut();
                focusChargingSound = null;
            }

            spellKeyDownTime = 0;
        }
    }
}
