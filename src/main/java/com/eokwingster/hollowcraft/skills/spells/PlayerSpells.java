package com.eokwingster.hollowcraft.skills.spells;

import com.google.common.collect.HashBiMap;
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
    private static final HashBiMap<Integer, ISpell> ID_SPELL_BI_MAP = HashBiMap.create(Map.of(0, Focus.INSTANCE));

    public static void addSpell(List<Integer> playerSpells, ISpell spell) {
        Integer key = getKey(spell);
        if (key != null && !playerSpells.contains(key)) {
            playerSpells.add(key);
        }
    }

    public static boolean hasSpell(List<Integer> playerSpells, ISpell spell) {
        return playerSpells.contains(getKey(spell));
    }

    public static Integer getKey(ISpell spell) {
        return ID_SPELL_BI_MAP.inverse().get(spell);
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
