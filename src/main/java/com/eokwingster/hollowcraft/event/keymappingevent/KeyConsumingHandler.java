package com.eokwingster.hollowcraft.event.keymappingevent;

import com.eokwingster.hollowcraft.client.gui.hud.LookingDirectionIndicator;
import com.eokwingster.hollowcraft.client.keymapping.HCKeyMappings;
import com.eokwingster.hollowcraft.client.sounds.FadingSoundInstance;
import com.eokwingster.hollowcraft.client.sounds.HCSoundEvents;
import com.eokwingster.hollowcraft.network.SpellData;
import com.eokwingster.hollowcraft.skills.soul.Soul;
import com.eokwingster.hollowcraft.skills.spells.Focus;
import com.eokwingster.hollowcraft.world.attachmentdata.HCAttachmentTypes;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.client.sounds.SoundManager;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.Level;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.network.PacketDistributor;

import static com.eokwingster.hollowcraft.HollowCraft.MODID;

@EventBusSubscriber(modid = MODID, value = Dist.CLIENT)
public class KeyConsumingHandler {
    private static int spellKeyDownTime;
    private static FadingSoundInstance focusChargingSound = null;

    @SubscribeEvent
    private static void clientTickPost(ClientTickEvent.Post event) {
        Minecraft mc = Minecraft.getInstance();
        SoundManager soundManager = mc.getSoundManager();
        Level level = mc.level;
        LocalPlayer player = mc.player;

        //spell key logic
        if (HCKeyMappings.KEY_SPELL.isDown()) {
            //focus logic
            if (spellKeyDownTime > 5) {
                PacketDistributor.sendToServer(SpellData.make(LookingDirectionIndicator.lookingDirection, spellKeyDownTime));

                Soul soulAttach = player.getData(HCAttachmentTypes.SOUL);

                // control focus charging sound
                if (focusChargingSound == null && soulAttach.getSoul() != 0) {
                    focusChargingSound = new FadingSoundInstance(
                            HCSoundEvents.FOCUS_CHARGING.get(),
                            SoundSource.AMBIENT,
                            SoundInstance.createUnseededRandom(),
                            0.05F,
                            true);;
                    soundManager.play(focusChargingSound);
                } else if (focusChargingSound != null && soulAttach.getSoul() == 0) {
                    focusChargingSound.fadeOut();
                    focusChargingSound = null;
                }

                // play focus complete sound
                int focusTime = (spellKeyDownTime - 5) % Focus.ONCE_TIME;
                if (focusTime == 0) {
                    level.playLocalSound(player, HCSoundEvents.FOCUS_HEAL.get(), SoundSource.PLAYERS, 1F, 1F);
                }
            }

            spellKeyDownTime++;
        } else {
            //instant spells logic
            if (spellKeyDownTime > 0 && spellKeyDownTime <= 5) {
                PacketDistributor.sendToServer(SpellData.make(LookingDirectionIndicator.lookingDirection, spellKeyDownTime));
            } else if (focusChargingSound != null) {
                focusChargingSound.fadeOut();
                focusChargingSound = null;
            }

            spellKeyDownTime = 0;
        }
    }
}
