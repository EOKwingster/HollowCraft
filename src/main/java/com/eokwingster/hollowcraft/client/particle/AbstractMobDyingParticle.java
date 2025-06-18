package com.eokwingster.hollowcraft.client.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;

import java.util.Random;

abstract public class AbstractMobDyingParticle extends TextureSheetParticle {
    private final SpriteSet spriteSet;
    protected final Random random = new Random();
    protected int decelerateAge;
    protected int stopAge;

    protected AbstractMobDyingParticle(
            ClientLevel pLevel,
            double pX,
            double pY,
            double pZ,
            SpriteSet spriteSet,
            double speed,
            float quadSize,
            int lifeTime,
            int decelerateAge,
            int stopAge) {
        super(pLevel, pX, pY, pZ);
        this.spriteSet = spriteSet;
        this.gravity = 0.0F;
        this.hasPhysics = false;
        this.lifetime = lifeTime;
        this.quadSize = quadSize;
        this.decelerateAge = decelerateAge;
        this.stopAge = stopAge;
        this.setSpriteFromAge(spriteSet);
        this.setParticleRandomDirectionSpeed(speed);
    }

    private void setParticleRandomDirectionSpeed(double speed) {
        double dx = random.nextDouble(-speed, speed);
        double dy = random.nextDouble(-speed, speed);
        double dz = Math.sqrt(speed * speed - (dx * dx + dy * dy)) * (random.nextBoolean() ? 1 : -1);
        this.setParticleSpeed(dx, dy, dz);
    }

    @Override
    public void tick() {
        this.setSpriteFromAge(spriteSet);
        super.tick();
        if (this.age >= decelerateAge) {
            double speedPercentage = (this.stopAge - this.age) / (this.stopAge - (double) this.decelerateAge);
            this.xd *= speedPercentage;
            this.yd *= speedPercentage;
            this.zd *= speedPercentage;
        }
    }

    @Override
    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_OPAQUE;
    }
}
