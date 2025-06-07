package com.eokwingster.hollowcraft.tags;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;

import static com.eokwingster.hollowcraft.HollowCraft.MODID;

public class HCEntityTypeTags {
    public static final TagKey<EntityType<?>> HC_ENTITIES = entityTypeTag("hc_entities");

    private static TagKey<EntityType<?>> entityTypeTag(String name){
        return TagKey.create(BuiltInRegistries.ENTITY_TYPE.key(), ResourceLocation.fromNamespaceAndPath(MODID, name));
    }
}
