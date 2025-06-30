package com.eokwingster.hollowcraft.data.worldgen;

import com.eokwingster.hollowcraft.world.level.dimension.HCDimensions;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.world.level.biome.*;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.dimension.LevelStem;
import net.minecraft.world.level.levelgen.NoiseBasedChunkGenerator;
import net.minecraft.world.level.levelgen.NoiseGeneratorSettings;

public class HCLevelStemsProvider {
    public static void bootstrap(BootstrapContext<LevelStem> context) {
        HolderGetter<Biome> biomesGetter = context.lookup(Registries.BIOME);
        HolderGetter<DimensionType> dimensionTypesGetter = context.lookup(Registries.DIMENSION_TYPE);
        HolderGetter<NoiseGeneratorSettings> noiseSettingsGetter = context.lookup(Registries.NOISE_SETTINGS);

        NoiseBasedChunkGenerator fixedBiomeNoiseChunkGenerator = new NoiseBasedChunkGenerator(
                new FixedBiomeSource(biomesGetter.getOrThrow(Biomes.BADLANDS)),
                noiseSettingsGetter.getOrThrow(NoiseGeneratorSettings.OVERWORLD)
        );

        LevelStem dirtmouthStem = new LevelStem(dimensionTypesGetter.getOrThrow(HCDimensions.DIRTMOUTH_TYPE), fixedBiomeNoiseChunkGenerator);

        context.register(HCDimensions.DIRTMOUTH_STEM, dirtmouthStem);
    }
}
