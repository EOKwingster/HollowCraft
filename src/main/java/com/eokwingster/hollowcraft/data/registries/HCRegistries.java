package com.eokwingster.hollowcraft.data.registries;

import com.eokwingster.hollowcraft.world.damagetype.HCDamageTypes;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;

public class HCRegistries {
    public static final RegistrySetBuilder BUILDER = new RegistrySetBuilder()
            .add(Registries.DAMAGE_TYPE, HCDamageTypes::bootstrap);
}
