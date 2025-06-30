package com.eokwingster.hollowcraft.world.level.block;

import com.eokwingster.hollowcraft.world.level.block.portal_block.AbstractPortalBlock;
import com.eokwingster.hollowcraft.world.level.block.portal_block.DirtmouthPortalBlock;
import com.eokwingster.hollowcraft.world.level.block.portal_block.OverworldPortalBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

import static com.eokwingster.hollowcraft.HollowCraft.MODID;

public class HCBlocks {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.createBlocks(MODID);

    public static final Supplier<AbstractPortalBlock> DIRTMOUTH_PORTAL = BLOCKS.register(
            "dirtmouth_portal", () -> new DirtmouthPortalBlock(getPortalProperties())
    );

    public static final Supplier<AbstractPortalBlock> OVERWORLD_PORTAL = BLOCKS.register(
            "overworld_portal", () -> new OverworldPortalBlock(getPortalProperties())
    );

    private static BlockBehaviour.Properties getPortalProperties() {
        return BlockBehaviour.Properties.of()
                .mapColor(MapColor.COLOR_BLACK)
                .noCollission()
                .lightLevel((state) -> 15)
                .strength(-1.0F, 3600000.0F)
                .sound(SoundType.GLASS)
                .noLootTable()
                .pushReaction(PushReaction.BLOCK);
    }
}
