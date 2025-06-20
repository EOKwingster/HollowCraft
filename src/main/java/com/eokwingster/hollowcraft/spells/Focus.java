package com.eokwingster.hollowcraft.spells;

import com.eokwingster.hollowcraft.client.sounds.FadingSoundInstance;
import com.eokwingster.hollowcraft.client.sounds.HCSoundEvents;
import com.eokwingster.hollowcraft.network.SoulData;
import com.eokwingster.hollowcraft.world.attachmentdata.HCAttachmentTypes;
import com.eokwingster.hollowcraft.world.attachmentdata.data.Soul;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.sounds.SoundManager;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.network.PacketDistributor;

public class Focus implements ISpell {
    public static final Focus INSTANCE = new Focus();

    private static final int ONCE_TIME = 18;
    private static final int ONCE_SOUL_COST = 3;
    // server side
    private boolean continuous = true;
    // client side
    private FadingSoundInstance focusChargingSound = null;

    private Focus() {}

    @Override
    public void release(Player player, int keyDownTime) {
        int focusTime = (keyDownTime - 5) % ONCE_TIME; // from 1 to 17 then 0, where 0 is actually frame 18 when hearing happens
        if (player instanceof ServerPlayer serverPlayer) {
            releaseChargingAndHeal(serverPlayer, focusTime);
        } else if (player instanceof LocalPlayer localPlayer) {
            releaseSounds(localPlayer, focusTime);
        }
    }

    private void releaseChargingAndHeal(ServerPlayer player, int focusTime) {
        Soul soulAttach = player.getData(HCAttachmentTypes.SOUL);
        if (focusTime > 6) {
            if (soulAttach.getSoul() >= ONCE_SOUL_COST) {
                soulAttach.subSoul(ONCE_SOUL_COST);
            } else {
                continuous = false;
            }
            PacketDistributor.sendToPlayer(player, new SoulData(soulAttach.writeNBT()));
        }
        if (focusTime == 1 && !continuous) {
            continuous = true;
        }
        if (focusTime == 0 && continuous) {
            player.heal(2);
        }
    }

    private void releaseSounds(LocalPlayer player, int focusTime) {
        SoundManager soundManager = Minecraft.getInstance().getSoundManager();
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
        if (focusTime == 0 && continuous) {
            player.level().playLocalSound(player, HCSoundEvents.FOCUS_HEAL.get(), SoundSource.PLAYERS, 1F, 1F);
        }
        if (focusTime < 0 && focusChargingSound != null) {
            focusChargingSound.fadeOut();
            focusChargingSound = null;
        }
    }
}
