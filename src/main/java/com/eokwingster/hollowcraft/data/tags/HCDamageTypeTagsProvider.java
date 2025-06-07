package com.eokwingster.hollowcraft.data.tags;

import com.eokwingster.hollowcraft.tags.HCDamageTypeTags;
import com.eokwingster.hollowcraft.world.damagetype.HCDamageTypes;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.DamageTypeTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class HCDamageTypeTagsProvider extends DamageTypeTagsProvider {
    public HCDamageTypeTagsProvider(PackOutput pOutput, CompletableFuture<HolderLookup.Provider> pLookupProvider, String modId, @Nullable ExistingFileHelper existingFileHelper) {
        super(pOutput, pLookupProvider, modId, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider pProvider) {
        tag(HCDamageTypeTags.HC_DAMAGE).add(
                HCDamageTypes.NAIL_DAMAGE_TYPE
        );
    }
}
