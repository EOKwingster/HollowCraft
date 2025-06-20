package com.eokwingster.hollowcraft.spells;

import net.minecraft.world.entity.player.Player;

public interface ISpell {
    void release(Player player, int keyDownTime);
}
