package com.eokwingster.hollowcraft.skills.spells;

import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.neoforged.neoforge.common.util.INBTSerializable;
import org.jetbrains.annotations.UnknownNullability;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class PlayerSpells implements INBTSerializable<CompoundTag> {
    public List<Integer> playerSpells = new ArrayList<>();
    public static final Map<Integer, ISpell> spellMap = Map.of(0, Focus.INSTANCE);

    public static void addSpell(List<Integer> playerSpells, ISpell spell) {
        int key = getKey(spell);
        if (!playerSpells.contains(key) && key != -1) {
            playerSpells.add(key);
        }
    }

    public static boolean hasSpell(List<Integer> playerSpells, ISpell spell) {
        return playerSpells.contains(getKey(spell));
    }

    public static int getKey(ISpell spell) {
        for (Map.Entry<Integer, ISpell> entry : spellMap.entrySet()) {
            if (entry.getValue().equals(spell)) {
                return entry.getKey();
            }
        }
        return -1;
    }

    @Override
    public @UnknownNullability CompoundTag serializeNBT(HolderLookup.Provider provider) {
        CompoundTag tag = new CompoundTag();
        tag.putIntArray("spellIDs", playerSpells);
        return tag;
    }

    @Override
    public void deserializeNBT(HolderLookup.Provider provider, CompoundTag nbt) {
        playerSpells = Arrays.stream(nbt.getIntArray("spellIDs")).boxed().collect(Collectors.toCollection(ArrayList::new));
    }
}
