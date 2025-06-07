package com.eokwingster.hollowcraft.tags;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.damagesource.DamageType;

import static com.eokwingster.hollowcraft.HollowCraft.MODID;

public class HCDamageTypeTags {
    public static final TagKey<DamageType> HC_DAMAGE = damageTypeTag("hc_damage");

    private static TagKey<DamageType> damageTypeTag(String name){
        return TagKey.create(Registries.DAMAGE_TYPE, ResourceLocation.fromNamespaceAndPath(MODID, name));
    }
}
