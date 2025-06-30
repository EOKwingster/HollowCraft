package com.eokwingster.hollowcraft.world.level.dimension;

import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.saveddata.SavedData;

import java.util.HashMap;
import java.util.Map;

public class DimensionSpawnPositionsSavedData extends SavedData {
    private final Map<ResourceKey<Level>, BlockPos> spawnPositions;

    private DimensionSpawnPositionsSavedData() {
        this.spawnPositions = new HashMap<>();
    }

    public static SavedData.Factory<DimensionSpawnPositionsSavedData> factory() {
        return new SavedData.Factory<>(DimensionSpawnPositionsSavedData::new, DimensionSpawnPositionsSavedData::load);
    }

    public BlockPos getSpawnPosition(ResourceKey<Level> dimension) {
        return spawnPositions.get(dimension);
    }

    public boolean containSpawnPosition(ResourceKey<Level> dimension) {
        return spawnPositions.containsKey(dimension);
    }

    public BlockPos setSpawnPosition(ResourceKey<Level> dimension, BlockPos pos) {
        BlockPos pos1 = getSpawnPosition(dimension);
        if (!pos.equals(pos1)) {
            spawnPositions.put(dimension, pos);
            setDirty();
        }
        return pos1;
    }

    public static DimensionSpawnPositionsSavedData load(CompoundTag tag, HolderLookup.Provider registries) {
        DimensionSpawnPositionsSavedData data = new DimensionSpawnPositionsSavedData();
        data.spawnPositions.clear();
        tag.getAllKeys().forEach(key -> {
            ResourceKey<Level> dimension = ResourceKey.create(Registries.DIMENSION, ResourceLocation.parse(key));
            CompoundTag posTag = tag.getCompound(key);
            BlockPos pos = new BlockPos(posTag.getInt("x"), posTag.getInt("y"), posTag.getInt("z"));
            data.spawnPositions.put(dimension, pos);
        });
        return data;
    }

    @Override
    public CompoundTag save(CompoundTag pTag, HolderLookup.Provider pRegistries) {
        spawnPositions.forEach((dimension, pos) -> {
            CompoundTag posTag = new CompoundTag();
            posTag.putInt("x", pos.getX());
            posTag.putInt("y", pos.getY());
            posTag.putInt("z", pos.getZ());
            pTag.put(dimension.location().toString(), posTag);
        });
        return pTag;
    }
}
