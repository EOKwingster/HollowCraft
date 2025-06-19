package com.eokwingster.hollowcraft.spells;

import net.minecraft.server.level.ServerPlayer;

public interface ISpell {
    void release(ServerPlayer player, int keyDownTime);
}
