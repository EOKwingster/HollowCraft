package com.eokwingster.hollowcraft.world.damagetype;

import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageType;

import static com.eokwingster.hollowcraft.HollowCraft.MODID;

public class HCDamageTypes {
    public static final ResourceKey<DamageType> NAIL_DAMAGE_TYPE =
            ResourceKey.create(Registries.DAMAGE_TYPE, ResourceLocation.fromNamespaceAndPath(MODID, "nail_damage_type"));
    public static final ResourceKey<DamageType> HOLLOW_FIRE =
            ResourceKey.create(Registries.DAMAGE_TYPE, ResourceLocation.fromNamespaceAndPath(MODID, "hollow_fire"));

    public static void bootstrap(BootstrapContext<DamageType> context) {
        context.register(NAIL_DAMAGE_TYPE, new DamageType("nailDamageType",0.0F));
        context.register(HOLLOW_FIRE, new DamageType("hollowFire", 0.0F));
    }
}
