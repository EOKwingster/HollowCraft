package com.eokwingster.hollowcraft.data.model.item;

import com.eokwingster.hollowcraft.world.item.HCItems;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class HCItemModelProvider extends ItemModelProvider {
    public HCItemModelProvider(PackOutput output, String modid, ExistingFileHelper existingFileHelper) {
        super(output, modid, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        basicItem(HCItems.OLD_NAIL.get());
        basicItem(HCItems.KNIGHT_HEAD.get());
    }
}
