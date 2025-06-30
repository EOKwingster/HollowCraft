package com.eokwingster.hollowcraft.data;

import com.eokwingster.hollowcraft.data.worldgen.HCDimensionTypesProvider;
import com.eokwingster.hollowcraft.data.worldgen.HCLevelStemsProvider;
import com.eokwingster.hollowcraft.world.damagetype.HCDamageTypes;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;

public class HCRegistries {
    public static final RegistrySetBuilder BUILDER = new RegistrySetBuilder()
            .add(Registries.DIMENSION_TYPE, HCDimensionTypesProvider::bootstrap)
            .add(Registries.LEVEL_STEM, HCLevelStemsProvider::bootstrap)
            .add(Registries.DAMAGE_TYPE, HCDamageTypes::bootstrap);
}
