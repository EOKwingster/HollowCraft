package com.eokwingster.hollowcraft.data.model;

import com.eokwingster.hollowcraft.world.level.block.HCBlocks;
import com.eokwingster.hollowcraft.world.level.block.portal_block.AbstractPortalBlock;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.client.model.generators.BlockModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import static com.eokwingster.hollowcraft.HollowCraft.MODID;

public class HCBlockModelProvider extends BlockModelProvider {
    public HCBlockModelProvider(PackOutput output, String modid, ExistingFileHelper existingFileHelper) {
        super(output, modid, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        portalBlock(HCBlocks.DIRTMOUTH_PORTAL.get());
        portalBlock(HCBlocks.OVERWORLD_PORTAL.get());
    }

    private void portalBlock(AbstractPortalBlock portalBlock) {
        portalBlockOfAxis(portalBlock, 0, 0, 6, 16, 16, 10, Direction.NORTH, Direction.SOUTH, "_ns");
        portalBlockOfAxis(portalBlock, 0, 6, 0, 16, 10, 16, Direction.UP, Direction.DOWN, "_ud");
        portalBlockOfAxis(portalBlock, 6, 0, 0, 10, 16, 16, Direction.EAST, Direction.WEST, "_ew");
    }

    private void portalBlockOfAxis(AbstractPortalBlock portalBlock, float x1, float y1, float z1, float x2, float y2, float z2, Direction d1, Direction d2, String suffix) {
        ResourceLocation blockResourceLoc = BuiltInRegistries.BLOCK.getKey(portalBlock);
        getBuilder(blockResourceLoc + suffix)
                .texture("portal", ResourceLocation.fromNamespaceAndPath(MODID, "block/" + blockResourceLoc.getPath()))
                .element()
                .from(x1, y1, z1)
                .to(x2, y2, z2)
                .face(d1).texture("#portal").end()
                .face(d2).texture("#portal").end()
                .end();
    }
}
