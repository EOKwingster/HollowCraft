package com.eokwingster.hollowcraft.skills.spells;

import com.eokwingster.hollowcraft.network.SoulData;
import com.eokwingster.hollowcraft.skills.soul.Soul;
import com.eokwingster.hollowcraft.world.attachmentdata.HCAttachmentTypes;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.network.PacketDistributor;

public class Focus implements ISpell {
    public static final Focus INSTANCE = new Focus();

    private Focus() {}

    @Override
    public void release(ServerPlayer player, int keyDownTime) {
        Soul soulAttach = player.getData(HCAttachmentTypes.SOUL);
        int onceTime = 18;
        int spellTime = (keyDownTime - 5) % onceTime + 1;

        if (spellTime > 7) {
            soulAttach.subSoul(3);
            PacketDistributor.sendToPlayer(player, new SoulData(soulAttach.writeNBT()));
        }
        if (spellTime == 18) {
            player.heal(2);
        }
    }
}
