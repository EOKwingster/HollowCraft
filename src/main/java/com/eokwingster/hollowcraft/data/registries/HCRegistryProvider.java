package com.eokwingster.hollowcraft.data.registries;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.DatapackBuiltinEntriesProvider;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class HCRegistryProvider extends DatapackBuiltinEntriesProvider {
    public HCRegistryProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries, Set<String> modIds) {
        super(output, registries, HCRegistries.BUILDER, modIds);
    }
}
