package com.eokwingster.hollowcraft.world.level.block.portal_block;

import com.eokwingster.hollowcraft.world.level.dimension.HCDimensions;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.phys.Vec3;

public class OverworldPortalBlock extends AbstractPortalBlock{
    public OverworldPortalBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected ResourceKey<Level> getTargetDimension() {
        return Level.OVERWORLD;
    }

    @Override
    protected BlockPos adjustTargetLevelSpawnPosition(ServerLevel startLevel, ServerLevel targetLevel, BlockPos pos) {
        ResourceKey<Level> startDimension = startLevel.dimension();
        BlockPos offsetPos = pos.offset(2, 0, 0);
        if (startDimension == HCDimensions.DIRTMOUTH_LEVEL) {
            Vec3 vec3 = offsetPos.getCenter();
            int i = targetLevel.getChunkAt(offsetPos).getHeight(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, offsetPos.getX(), offsetPos.getZ()) + 1;
            return BlockPos.containing(vec3.x, i, vec3.z);
        } else {
            return null;
        }
    }
}
