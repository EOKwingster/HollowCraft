package com.eokwingster.hollowcraft.world.level.block.portal_block;

import com.eokwingster.hollowcraft.world.level.dimension.HCDimensions;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.phys.Vec3;

public class DirtmouthPortalBlock extends AbstractPortalBlock {
    public DirtmouthPortalBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected ResourceKey<Level> getTargetDimension() {
        return HCDimensions.DIRTMOUTH_LEVEL;
    }

    @Override
    protected BlockPos adjustTargetLevelSpawnPosition(ServerLevel startLevel, ServerLevel targetLevel, BlockPos pos) {
        ResourceKey<Level> startDimension = startLevel.dimension();
        if (startDimension == Level.OVERWORLD) {
            Vec3 vec3 = pos.getCenter();
            int i = targetLevel.getChunkAt(pos).getHeight(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, pos.getX(), pos.getZ()) + 1;
            return BlockPos.containing(vec3.x, i, vec3.z);
        } else {
            return null;
        }
    }
}
