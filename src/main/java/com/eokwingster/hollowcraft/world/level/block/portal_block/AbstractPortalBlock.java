package com.eokwingster.hollowcraft.world.level.block.portal_block;

import com.eokwingster.hollowcraft.world.level.dimension.DimensionSpawnPositionsSavedData;
import com.eokwingster.hollowcraft.world.level.dimension.HCDimensions;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.Portal;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.portal.DimensionTransition;
import net.minecraft.world.level.portal.PortalShape;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.neoforged.neoforge.common.extensions.IBlockExtension;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Map;

public abstract class AbstractPortalBlock extends RotatedPillarBlock implements Portal, IBlockExtension {
    private static final Map<ResourceKey<Level>, List<ResourceKey<Level>>> PORTAL_DESTINATIONS = Map.of(
            Level.OVERWORLD, List.of(
                    HCDimensions.DIRTMOUTH_LEVEL
            ),
            HCDimensions.DIRTMOUTH_LEVEL, List.of(
                    Level.OVERWORLD
            )
    );
    public static final VoxelShape X_AXIS_AABB = Block.box(0.0, 0.0, 6.0, 16.0, 16.0, 10.0);
    public static final VoxelShape Y_AXIS_AABB = Block.box(0.0, 6.0, 0.0, 16.0, 10.0, 16.0);
    public static final VoxelShape Z_AXIS_AABB = Block.box(6.0, 0.0, 0.0, 10.0, 16.0, 16.0);

    public AbstractPortalBlock(Properties p_55926_) {
        super(p_55926_);
    }


    @Override
    protected VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return switch (pState.getValue(AXIS)) {
            case Z -> Z_AXIS_AABB;
            case Y -> Y_AXIS_AABB;
            default -> X_AXIS_AABB;
        };
    }

    @Override
    protected BlockState updateShape(BlockState pState, Direction pFacing, BlockState pFacingState, LevelAccessor pLevel, BlockPos pCurrentPos, BlockPos pFacingPos) {
        Direction.Axis axis = pFacing.getAxis();
        Direction.Axis axis1 = pState.getValue(AXIS);
        return axis1 == axis && !pFacingState.is(this) && !new PortalShape(pLevel, pCurrentPos, axis1).isComplete()
                ? Blocks.AIR.defaultBlockState()
                : super.updateShape(pState, pFacing, pFacingState, pLevel, pCurrentPos, pFacingPos);
    }

    @Override
    protected void entityInside(BlockState pState, Level pLevel, BlockPos pPos, Entity pEntity) {
        if (pEntity.canUsePortal(false)) {
            pEntity.setAsInsidePortal(this, pPos);
        }
    }

    @Override
    public ItemStack getCloneItemStack(BlockState state, HitResult target, LevelReader level, BlockPos pos, Player player) {
        return ItemStack.EMPTY;
    }

    @Override
    protected boolean canBeReplaced(BlockState pState, Fluid pFluid) {
        return false;
    }

    @Override
    public @Nullable DimensionTransition getPortalDestination(ServerLevel pLevel, Entity pEntity, BlockPos pPos) {
        ServerLevel targetLevel = pLevel.getServer().getLevel(getTargetDimension());
        if (targetLevel == null) {
            return null;
        }
        if (pEntity instanceof Player player) {
            if (isPortalDestinationLevel(pLevel.dimension(), targetLevel.dimension())) {
                BlockPos blockpos = getSpawnPosition(pLevel, targetLevel, pPos);
                Vec3 vec3 = blockpos.getBottomCenter();
                float f = player.getYRot();
                return new DimensionTransition(
                        targetLevel,
                        vec3,
                        player.getDeltaMovement(),
                        f,
                        player.getXRot(),
                        DimensionTransition.PLAY_PORTAL_SOUND.then(DimensionTransition.PLACE_PORTAL_TICKET)
                );
            } else {
                pLevel.destroyBlock(pPos, false, player);
                return null;
            }
        } else {
            return null;
        }
    }

    protected static boolean isPortalDestinationLevel (ResourceKey<Level> startDimension, ResourceKey<Level> targetDimension) {
        return PORTAL_DESTINATIONS.getOrDefault(startDimension, List.of()).contains(targetDimension);
    }

    protected abstract ResourceKey<Level> getTargetDimension();

    protected BlockPos getSpawnPosition(ServerLevel startLevel, ServerLevel targetLevel, BlockPos entryPosition) {
        BlockPos targetSpawnPosition = adjustTargetLevelSpawnPosition(startLevel, targetLevel, entryPosition);
        DimensionSpawnPositionsSavedData spawnPoints = startLevel.getDataStorage().computeIfAbsent(DimensionSpawnPositionsSavedData.factory(), "dimension_spawn_points");
        ResourceKey<Level> targetDimension = targetLevel.dimension();
        if (!spawnPoints.containSpawnPosition(targetDimension)) {
            spawnPoints.setSpawnPosition(targetDimension, targetSpawnPosition);
        }
        return spawnPoints.getSpawnPosition(targetDimension);
    }

    protected abstract BlockPos adjustTargetLevelSpawnPosition(ServerLevel startLevel, ServerLevel targetLevel, BlockPos pos);
}
