package com.eokwingster.hollowcraft.skills.spells;

import com.eokwingster.hollowcraft.client.sounds.HCSoundEvents;
import com.eokwingster.hollowcraft.network.SoulData;
import com.eokwingster.hollowcraft.skills.soul.Soul;
import com.eokwingster.hollowcraft.world.attachmentdata.HCAttachmentTypes;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.network.PacketDistributor;

public class Focus implements ISpell {
    public static final Focus INSTANCE = new Focus();

    public static final int ONCE_TIME = 18;

    private Focus() {}

    @Override
    public void release(ServerPlayer player, int keyDownTime) {
        Soul soulAttach = player.getData(HCAttachmentTypes.SOUL);

        int focusTime = (keyDownTime - 5) % ONCE_TIME;
        if (focusTime > 6) {
            soulAttach.subSoul(3);
            PacketDistributor.sendToPlayer(player, new SoulData(soulAttach.writeNBT()));
        }
        if (focusTime == 0) {
            player.heal(2);
        }
    }
}
