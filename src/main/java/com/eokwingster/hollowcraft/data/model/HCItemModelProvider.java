package com.eokwingster.hollowcraft.data.model;

import com.eokwingster.hollowcraft.world.item.HCItems;
import com.eokwingster.hollowcraft.world.item.NailItem;
import com.eokwingster.hollowcraft.world.level.block.portal_block.AbstractPortalBlock;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.client.model.generators.ItemModelBuilder;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import java.util.Objects;

import static com.eokwingster.hollowcraft.HollowCraft.MODID;

public class HCItemModelProvider extends ItemModelProvider {
    public HCItemModelProvider(PackOutput output, String modid, ExistingFileHelper existingFileHelper) {
        super(output, modid, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        nailItem(HCItems.NAIL.get());
        basicItem(HCItems.KNIGHT_SKULL.get());
        basicSpawnEgg(HCItems.SHADE_SPAWN_EGG.get());
        portalBlockItem(HCItems.DIRTMOUTH_PORTAL.get());
        portalBlockItem(HCItems.OVERWORLD_PORTAL.get());
    }

    private ItemModelBuilder portalBlockItem(Item portalBlock) {
        ResourceLocation itemLoc = Objects.requireNonNull(BuiltInRegistries.ITEM.getKey(portalBlock));
        return withExistingParent(
                itemLoc.toString(),
                "item/generated"
        ).texture(
                "layer0",
                ResourceLocation.fromNamespaceAndPath(
                        MODID,
                        "block/" + itemLoc.getPath()
                )
        );
    }

    private ItemModelBuilder handheldItem(Item item, String textureName) {
        ResourceLocation rl = Objects.requireNonNull(BuiltInRegistries.ITEM.getKey(item));
        return handheldItem(rl.toString(), textureName);
    }

    private ItemModelBuilder handheldItem(String resourceLoc, String textureName) {
        return withExistingParent(resourceLoc, "item/handheld").texture("layer0", ResourceLocation.fromNamespaceAndPath(MODID, "item/" + textureName));
    }

    private ItemModelBuilder handheldItem(String name) {
        String resourceLoc = MODID + ":item/" + name;
        return handheldItem(resourceLoc, name);
    }

    private ItemModelBuilder basicSpawnEgg(Item item) {
        ResourceLocation rl = Objects.requireNonNull(BuiltInRegistries.ITEM.getKey(item));
        return withExistingParent(rl.toString(), "item/template_spawn_egg");
    }

    private ItemModelBuilder nailItem(NailItem nail) {
        return handheldItem(nail, "old_nail")

                .override()
                .predicate(ResourceLocation.fromNamespaceAndPath(MODID, "nail_state"), 0.1F)
                .model(handheldItem("sharpened_nail"))
                .end()

                .override()
                .predicate(ResourceLocation.fromNamespaceAndPath(MODID, "nail_state"), 0.2F)
                .model(handheldItem("channelled_nail"))
                .end()

                .override()
                .predicate(ResourceLocation.fromNamespaceAndPath(MODID, "nail_state"), 0.3F)
                .model(handheldItem("coiled_nail"))
                .end()

                .override()
                .predicate(ResourceLocation.fromNamespaceAndPath(MODID, "nail_state"), 0.4F)
                .model(handheldItem("pure_nail"))
                .end();
    }
}
