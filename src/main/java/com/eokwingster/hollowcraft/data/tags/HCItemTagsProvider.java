package com.eokwingster.hollowcraft.data.tags;

import com.eokwingster.hollowcraft.tags.HCItemTags;
import com.eokwingster.hollowcraft.world.item.HCItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class HCItemTagsProvider extends ItemTagsProvider {

    public HCItemTagsProvider(PackOutput pOutput, CompletableFuture<HolderLookup.Provider> pLookupProvider, CompletableFuture<TagLookup<Block>> pBlockTags, String modId, @Nullable ExistingFileHelper existingFileHelper) {
        super(pOutput, pLookupProvider, pBlockTags, modId, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider pProvider) {
        tag(HCItemTags.NAIL).add(
                HCItems.NAIL.get()
        );
    }
}
