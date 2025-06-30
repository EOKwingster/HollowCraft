package com.eokwingster.hollowcraft.data.worldgen;

import com.eokwingster.hollowcraft.world.level.dimension.HCDimensions;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.dimension.BuiltinDimensionTypes;
import net.minecraft.world.level.dimension.DimensionType;

import java.util.OptionalLong;

public class HCDimensionTypesProvider {
    public static void bootstrap(BootstrapContext<DimensionType> pContext) {
        pContext.register(
                HCDimensions.DIRTMOUTH_TYPE,
                new DimensionType(
                        OptionalLong.of(18000L),
                        false,
                        false,
                        false,
                        true,
                        1.0F,
                        true,
                        false,
                        0,
                        256,
                        256,
                        BlockTags.INFINIBURN_OVERWORLD,
                        BuiltinDimensionTypes.OVERWORLD_EFFECTS,
                        0.0F,
                        new DimensionType.MonsterSettings(false, false, UniformInt.of(0, 7), 0)
                )
        );
    }
}
