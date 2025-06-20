package com.eokwingster.hollowcraft.world.entity;

import com.eokwingster.hollowcraft.client.particle.HCParticleTypes;
import com.eokwingster.hollowcraft.client.sounds.HCSoundEvents;
import com.eokwingster.hollowcraft.world.damagetype.HCDamageTypes;
import com.eokwingster.hollowcraft.client.animations.ShadeAnimations;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.FlyingMoveControl;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.util.DefaultRandomPos;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.Path;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.fluids.FluidType;

import java.util.EnumSet;
import java.util.function.Supplier;

public class ShadeEntity extends Monster implements IHCMob {
    // animations states
    public final AnimationState idleAnimationState = new AnimationState();
    public final AnimationState moveAnimationState = new AnimationState();
    public final AnimationState dieAnimationState = new AnimationState();
    // for die animation
    public static final int DIE_ANIMATION_LENGTH = (int) (ShadeAnimations.SHADE_DIE.lengthInSeconds() * 20);
    private final Supplier<Double> dyingShakeOffsetSupplier = () -> this.random.nextInt(-1, 2) / 100.0D;
    private int dyingTimer = 0;
    private Vec3 dyingLastPosition;
    // for light long time exposure damage
    private static final int LIGHT_EXPOSURE_THRESHOLD = 7;
    private static final int LIGHT_EXPOSURE_TIME_THRESHOLD = 200;
    private int lightExposureTimer = 0;

    public ShadeEntity(EntityType<? extends Monster> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
        this.moveControl = new ShadeMoveControl(this);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes()
                .add(Attributes.FOLLOW_RANGE, 12.0)
                .add(Attributes.ATTACK_DAMAGE, 5.0)
                .add(Attributes.FLYING_SPEED, 0.4);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new ShadeAvoidLightGoal(this, LIGHT_EXPOSURE_THRESHOLD, 16));
        this.goalSelector.addGoal(2, new ShadeMeleeAttackGoal(this, 1.0D, true));
        this.goalSelector.addGoal(3, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(4, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Player.class, true));
    }

    @Override
    public void tick() {
        super.tick();
        idleAnimationState.startIfStopped(tickCount);
        this.moveAnimationTick();
        this.lightExposureTick();
        this.dieAnimationTick();
    }

    private void lightExposureTick() {
        if (level().getBrightness(LightLayer.BLOCK, this.blockPosition()) > LIGHT_EXPOSURE_THRESHOLD) {
            lightExposureTimer++;
            if (level() instanceof ServerLevel serverLevel && !dieAnimationState.isStarted()) {
                serverLevel.sendParticles(HCParticleTypes.SHADE_DYING_PARTICLE.get(), this.position().x, this.position().y + this.getBbHeight() / 2, this.position().z, 1, 0.0, 0.0, 0.0, 1.0);
            }
            if (lightExposureTimer > LIGHT_EXPOSURE_TIME_THRESHOLD) {
                Holder<DamageType> hollowFireDamageType = this.level().registryAccess().lookupOrThrow(Registries.DAMAGE_TYPE).getOrThrow(HCDamageTypes.HOLLOW_FIRE);
                this.hurt(new DamageSource(hollowFireDamageType), 5.0F);
            }
        } else {
            lightExposureTimer = 0;
        }
    }

    private void moveAnimationTick() {
        double delX = this.getDeltaMovement().x;
        double delZ =  this.getDeltaMovement().z;
        if ((delX * delX + delZ * delZ) != 0) {
            moveAnimationState.startIfStopped(tickCount);
        } else {
            moveAnimationState.stop();
        }
    }

    private void dieAnimationTick() {
        if (!dieAnimationState.isStarted() && super.isDeadOrDying()) {
            this.startToDie();
        }
        if (dieAnimationState.isStarted()) {
            dyingTimer++;
            this.setDeltaMovement(0.0, 0.0, 0.0);
            this.dyingLastPosition = this.position();
            double posX = this.position().x;
            double posY = this.position().y;
            double posZ = this.position().z;
            this.moveTo(dyingLastPosition);
            this.move(MoverType.SELF, new Vec3(dyingShakeOffsetSupplier.get(), dyingShakeOffsetSupplier.get(), dyingShakeOffsetSupplier.get()));
            float halfHeight = this.getBbHeight() / 2;
            if (this.dyingTimer > 1 && this.dyingTimer < DIE_ANIMATION_LENGTH - 2) {
                if (level() instanceof ServerLevel serverLevel) {
                    serverLevel.sendParticles(HCParticleTypes.SHADE_DYING_PARTICLE.get(), posX, posY + halfHeight, posZ, 10, 0.0, 0.0, 0.0, 1.0);
                }
            } else if (this.dyingTimer == 1) {
                if (level() instanceof ServerLevel serverLevel) {
                    serverLevel.sendParticles(HCParticleTypes.SHADE_DEAD_PARTICLE.get(), posX, posY + halfHeight, posZ, 300, 0.0, 0.0, 0.0, 1.0);
                } else {
                    LocalPlayer localPlayer = Minecraft.getInstance().player;
                    level().playSound(localPlayer, this, HCSoundEvents.BOSS_EXPLODE_CLEAN.get(), SoundSource.HOSTILE, 1.0F, 1.0F);
                }
            }
            if (dyingTimer == DIE_ANIMATION_LENGTH + 1) {
                this.setInvisible(true);
            }
        }
    }

    public void startToDie() {
        this.dieAnimationState.start(this.tickCount);
        this.dyingLastPosition = this.position();
        this.setInvulnerable(true);
        this.setNoAi(true);
    }

    @Override
    public boolean isDeadOrDying() {
        return dyingTimer > DIE_ANIMATION_LENGTH && super.isDeadOrDying();
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

    @Override
    protected SoundEvent getHurtSound(DamageSource pDamageSource) {
        return this.getHollowHurtSound(pDamageSource);
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.EMPTY;
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

    static class ShadeAvoidLightGoal extends Goal {
        private final PathfinderMob mob;
        private final int avoidLightLevel;
        private final int avoidRange;
        private Path path;
        private final PathNavigation pathNav;

        public ShadeAvoidLightGoal(PathfinderMob mob, int avoidLightLevel, int avoidRange) {
            this.mob = mob;
            this.pathNav = mob.getNavigation();
            this.avoidLightLevel = avoidLightLevel;
            this.avoidRange = avoidRange;
            this.setFlags(EnumSet.of(Goal.Flag.MOVE));
        }

        @Override
        public boolean canUse() {
            if (this.mob.level().getBrightness(LightLayer.BLOCK, this.mob.blockPosition()) > this.avoidLightLevel) {
                Vec3 vec3 = findLowLightPos();
                if (vec3 != null) {
                    this.path = this.pathNav.createPath(vec3.x, vec3.y, vec3.z, 0);
                    return this.path != null;
                } else {
                    this.pathNav.stop();
                    return false;
                }
            }
            return false;
        }

        private Vec3 findLowLightPos() {
            Vec3 vec3 = null;
            for (int i = 0; i < 20; i++) {
                Vec3 randomPos = DefaultRandomPos.getPosAway(this.mob, avoidRange, avoidRange, this.mob.position());
                if (randomPos != null) {
                    BlockPos pos = BlockPos.containing(randomPos);
                    if (this.mob.level().getBrightness(LightLayer.BLOCK, pos) <= this.avoidLightLevel) {
                        if (vec3 == null || this.mob.distanceToSqr(randomPos) < this.mob.distanceToSqr(vec3)) {
                            vec3 =  randomPos;
                        }
                    }
                }
            }
            return vec3;
        }

        @Override
        public boolean canContinueToUse() {
            if (mob.level().getBrightness(LightLayer.BLOCK, this.mob.blockPosition()) > this.avoidLightLevel) {
                return !this.pathNav.isDone();
            }
            return false;
        }

        @Override
        public void start() {
            this.pathNav.moveTo(this.path, 1.5D);
        }

        @Override
        public void stop() {
            this.pathNav.stop();
        }
    }

    static class ShadeMeleeAttackGoal extends MeleeAttackGoal {
        public ShadeMeleeAttackGoal(PathfinderMob mob, double speedModifier, boolean useLongMemory) {
            super(mob, speedModifier, useLongMemory);
        }

        @Override
        public boolean canUse() {
            LivingEntity target = this.mob.getTarget();
            if (target != null) {
                BlockPos targetPos = target.blockPosition();
                if (this.mob.level().getBrightness(LightLayer.BLOCK, targetPos) > LIGHT_EXPOSURE_THRESHOLD) {
                    this.mob.getNavigation().stop();
                }
            }
            return super.canUse();
        }

        @Override
        public boolean canContinueToUse() {
            LivingEntity target = this.mob.getTarget();
            if (target != null) {
                BlockPos targetPos = target.blockPosition();
                if (this.mob.level().getBrightness(LightLayer.BLOCK, targetPos) > LIGHT_EXPOSURE_THRESHOLD) {
                    this.mob.getNavigation().stop();
                }
            }
            return super.canContinueToUse();
        }
    }
}



