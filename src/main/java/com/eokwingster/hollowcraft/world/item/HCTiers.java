package com.eokwingster.hollowcraft.world.item;

import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.neoforge.common.SimpleTier;

public class HCTiers {
    public static final Tier NAIL = new SimpleTier(
            BlockTags.ALL_SIGNS, Integer.MAX_VALUE, 0F, 0F, 0, () -> Ingredient.EMPTY
    );
}
