package com.eokwingster.hollowcraft.world.attachmentdata.data;

import com.eokwingster.hollowcraft.spells.Focus;
import com.eokwingster.hollowcraft.spells.ISpell;
import com.google.common.collect.HashBiMap;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.neoforged.neoforge.common.util.INBTSerializable;
import org.jetbrains.annotations.UnknownNullability;

import java.util.*;

public class PlayerSpells implements INBTSerializable<CompoundTag> {
    private static final HashBiMap<Integer, ISpell> ID_SPELL_BI_MAP = HashBiMap.create(Map.of(0, Focus.INSTANCE));
    private final Set<Integer> spells = new HashSet<>();

    public PlayerSpells() {
        this.addSpell(Focus.INSTANCE);
    }

    public void addSpell(ISpell spell) {
        Integer key = getKey(spell);
        if (key != null) {
            spells.add(key);
        }
    }

    public boolean hasSpell(ISpell spell) {
        return spells.contains(getKey(spell));
    }

    public static Integer getKey(ISpell spell) {
        return ID_SPELL_BI_MAP.inverse().get(spell);
    }

    public static ISpell getSpell(int spellId) {
        return ID_SPELL_BI_MAP.get(spellId);
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
        CompoundTag tag = new CompoundTag();
        tag.putIntArray("spellIDs", spells.stream().toList());
        return tag;
    }

    public void readNBT(CompoundTag tag) {
        Arrays.stream(tag.getIntArray("spellIDs")).forEach((id) -> {
            if (ID_SPELL_BI_MAP.containsKey(id)) {
                spells.add(id);
            }
        });
    }
}
