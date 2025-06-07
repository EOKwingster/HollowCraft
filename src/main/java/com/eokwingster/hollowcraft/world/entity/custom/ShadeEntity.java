package com.eokwingster.hollowcraft.world.entity.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.FlyingMoveControl;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.fluids.FluidType;

public class ShadeEntity extends Monster {
    private boolean from_player = false;
    private int soul = 0;

    public final AnimationState idleAnimationState = new AnimationState();
    public final AnimationState moveAnimationState = new AnimationState();

    public ShadeEntity(EntityType<? extends Monster> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
        this.moveControl = new ShadeMoveControl(this);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes()
                .add(Attributes.FOLLOW_RANGE, 12.0)
                .add(Attributes.ATTACK_DAMAGE, 5.0)
                .add(Attributes.FLYING_SPEED, 0.23);
    }

    @Override
    protected void registerGoals() {
        this.targetSelector.addGoal(0, new MeleeAttackGoal(this, 1.0D, true));
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Player.class, true));
        this.goalSelector.addGoal(2, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(3, new RandomLookAroundGoal(this));
    }

    @Override
    public void tick() {
        super.tick();
        idleAnimationState.startIfStopped(tickCount);

        double delX = this.getDeltaMovement().x;
        double delZ = this.getDeltaMovement().z;
        if ((delX * delX + delZ * delZ) != 0) {
            moveAnimationState.startIfStopped(tickCount);
        } else {
            moveAnimationState.stop();
        }
    }

    @Override
    public void addAdditionalSaveData(CompoundTag pCompound) {
        super.addAdditionalSaveData(pCompound);
        pCompound.putBoolean("from_player", from_player);
        pCompound.putInt("soul", soul);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag pCompound) {
        super.readAdditionalSaveData(pCompound);
        if (pCompound.contains("from_player")) {
            this.from_player = pCompound.getBoolean("from_player");
        }
        if (pCompound.contains("soul")) {
            this.soul = pCompound.getInt("soul");
        }
    }

    @Override
    public float getWalkTargetValue(BlockPos pPos, LevelReader pLevel) {
        return pLevel.getBlockState(pPos).isAir() ? 10.0F : 0.0F;
    }

    @Override
    protected PathNavigation createNavigation(Level pLevel) {
        FlyingPathNavigation flyingpathnavigation = new FlyingPathNavigation(this, pLevel) {
            @Override
            public boolean isStableDestination(BlockPos p_27947_) {
                return !this.level.getBlockState(p_27947_.below()).isAir();
            }
        };
        flyingpathnavigation.setCanOpenDoors(false);
        flyingpathnavigation.setCanFloat(false);
        flyingpathnavigation.setCanPassDoors(true);
        return flyingpathnavigation;
    }

    @Override
    protected void playStepSound(BlockPos pPos, BlockState pState) {
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource pDamageSource) {
        return super.getHurtSound(pDamageSource);
    }

    @Override
    protected void checkFallDamage(double pY, boolean pOnGround, BlockState pState, BlockPos pPos) {
    }

    @Override
    public void jumpInFluid(FluidType type) {
        this.setDeltaMovement(this.getDeltaMovement().add(0.0, 0.01, 0.0));
    }

    @Override
    public boolean onGround() {
        return false;
    }

    static class ShadeMoveControl extends FlyingMoveControl {

        public ShadeMoveControl(Mob pMob) {
            super(pMob, 20, true);
        }

        @Override
        public void tick() {
            if (this.mob.getTarget() != null) {
                this.wantedY = this.mob.getTarget().getEyeY() - this.mob.getEyeHeight();
            }
            super.tick();
        }
    }
}
