package com.eokwingster.hollowcraft.tags;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

import static com.eokwingster.hollowcraft.HollowCraft.MODID;

public class HCItemTags {
    public static final TagKey<Item> NAIL = itemTag("nail");

    private static TagKey<Item> itemTag(String name){
        return TagKey.create(BuiltInRegistries.ITEM.key(), ResourceLocation.fromNamespaceAndPath(MODID, name));
    }
}
