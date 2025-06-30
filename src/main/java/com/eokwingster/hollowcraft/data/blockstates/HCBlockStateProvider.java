package com.eokwingster.hollowcraft.data.blockstates;

import com.eokwingster.hollowcraft.world.level.block.portal_block.AbstractPortalBlock;
import com.eokwingster.hollowcraft.world.level.block.HCBlocks;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import static com.eokwingster.hollowcraft.HollowCraft.MODID;

public class HCBlockStateProvider extends BlockStateProvider {
    public HCBlockStateProvider(PackOutput output, String modid, ExistingFileHelper exFileHelper) {
        super(output, modid, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        registerPortalBlock(HCBlocks.DIRTMOUTH_PORTAL.get());
        registerPortalBlock(HCBlocks.OVERWORLD_PORTAL.get());
    }

    private void registerPortalBlock(AbstractPortalBlock portalBlock) {
        ResourceLocation resLoc = BuiltInRegistries.BLOCK.getKey(portalBlock);
        String blockId = resLoc.getPath().substring(resLoc.getPath().lastIndexOf("/") + 1);
        ModelFile modelZAxis = models().getExistingFile(ResourceLocation.fromNamespaceAndPath(MODID, blockId + "_ew"));
        ModelFile modelXAxis = models().getExistingFile(ResourceLocation.fromNamespaceAndPath(MODID, blockId + "_ns"));
        ModelFile modelYAxis = models().getExistingFile(ResourceLocation.fromNamespaceAndPath(MODID, blockId + "_ud"));
        getVariantBuilder(portalBlock)
                .partialState().with(AbstractPortalBlock.AXIS, Direction.Axis.Z).modelForState().modelFile(modelZAxis).addModel()
                .partialState().with(AbstractPortalBlock.AXIS, Direction.Axis.X).modelForState().modelFile(modelXAxis).addModel()
                .partialState().with(AbstractPortalBlock.AXIS, Direction.Axis.Y).modelForState().modelFile(modelYAxis).addModel();
    }
}
