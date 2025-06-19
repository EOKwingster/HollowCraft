package com.eokwingster.hollowcraft.world.attachmentdata.data;

import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.neoforged.neoforge.common.util.INBTSerializable;
import org.jetbrains.annotations.UnknownNullability;

import java.util.List;

public class NailLevel implements INBTSerializable<CompoundTag> {
    private static final List<Integer> LEVEL_DAMAGES = List.of(5, 9, 13, 17, 21);
    private int level;

    public NailLevel() {
        this.level = 0; // Default to no damage
    }

    public int getDamage() {
        return LEVEL_DAMAGES.get(level);
    }

    public void setLevel(int level) {
        if (level < 0 || level >= LEVEL_DAMAGES.size()) {
            throw new IllegalArgumentException("Invalid damage level: " + level + ". Must be between 0 and " + (LEVEL_DAMAGES.size() - 1) + ".");
        }
        this.level = level;
    }

    public boolean nextLevel() {
        if (level < LEVEL_DAMAGES.size() - 1) {
            setLevel(level + 1);
            return true;
        }
        return false;
    }

    public boolean previousLevel() {
        if (level > 0) {
            setLevel(level - 1);
            return true;
        }
        return false;
    }

    @Override
    public @UnknownNullability CompoundTag serializeNBT(HolderLookup.Provider provider) {
        return writeNBT();
    }

    @Override
    public void deserializeNBT(HolderLookup.Provider provider, CompoundTag nbt) {
        readNBT(nbt);
    }

    public CompoundTag writeNBT() {
        CompoundTag nbt = new CompoundTag();
        nbt.putInt("damageLevel", level);
        return nbt;
    }

    public void readNBT(CompoundTag nbt) {
        if (nbt.contains("damageLevel")) {
            setLevel(nbt.getInt("damageLevel"));
        } else {
            throw new IllegalArgumentException("NBT does not contain 'damageLevel'");
        }
    }
}
