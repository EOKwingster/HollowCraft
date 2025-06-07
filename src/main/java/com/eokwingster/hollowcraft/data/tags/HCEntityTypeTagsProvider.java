package com.eokwingster.hollowcraft.data.tags;

import com.eokwingster.hollowcraft.tags.HCEntityTypeTags;
import com.eokwingster.hollowcraft.world.entity.HCEntityTypes;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.EntityTypeTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class HCEntityTypeTagsProvider extends EntityTypeTagsProvider {
    public HCEntityTypeTagsProvider(PackOutput pOutput, CompletableFuture<HolderLookup.Provider> pProvider, String modId, @Nullable ExistingFileHelper existingFileHelper) {
        super(pOutput, pProvider, modId, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider pProvider) {
        tag(HCEntityTypeTags.HC_ENTITIES).add(
                HCEntityTypes.SHADE_ENTITY_TYPE.get()
        );
    }
}
