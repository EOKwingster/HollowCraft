package com.eokwingster.hollowcraft.world.level.dimension;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.dimension.LevelStem;

import static com.eokwingster.hollowcraft.HollowCraft.MODID;

public class HCDimensions {
    public static final String DIRTMOUTH_ID = "dirtmouth";
    public static final ResourceKey<DimensionType> DIRTMOUTH_TYPE = ResourceKey.create(Registries.DIMENSION_TYPE, ResourceLocation.fromNamespaceAndPath(MODID, DIRTMOUTH_ID));
    public static final ResourceKey<LevelStem> DIRTMOUTH_STEM = ResourceKey.create(Registries.LEVEL_STEM, ResourceLocation.fromNamespaceAndPath(MODID, DIRTMOUTH_ID));
    public static final ResourceKey<Level> DIRTMOUTH_LEVEL = ResourceKey.create(Registries.DIMENSION, ResourceLocation.fromNamespaceAndPath(MODID, DIRTMOUTH_ID));
}
