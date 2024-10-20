package com.eokwingster.hollowcraft.skills.spells;

import net.minecraft.server.level.ServerPlayer;

public interface ISpell {
    void release(ServerPlayer player, int keyDownTime);
}
