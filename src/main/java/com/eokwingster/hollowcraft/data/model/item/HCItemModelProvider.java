package com.eokwingster.hollowcraft.data.model.item;

import com.eokwingster.hollowcraft.world.item.HCItems;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.client.model.generators.ItemModelBuilder;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import java.util.Objects;

public class HCItemModelProvider extends ItemModelProvider {
    public HCItemModelProvider(PackOutput output, String modid, ExistingFileHelper existingFileHelper) {
        super(output, modid, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        basicItem(HCItems.NAIL.get());
        basicItem(HCItems.KNIGHT_SKULL.get());
        basicSpawnEgg(HCItems.SHADE_SPAWN_EGG.get());
    }

    public ItemModelBuilder basicSpawnEgg(Item item) {
        ResourceLocation rl = Objects.requireNonNull(BuiltInRegistries.ITEM.getKey(item));
        return getBuilder(rl.toString()).parent(new ModelFile.UncheckedModelFile("item/template_spawn_egg"));
    }
}
