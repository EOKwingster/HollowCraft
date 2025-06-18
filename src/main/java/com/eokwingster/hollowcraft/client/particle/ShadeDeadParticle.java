package com.eokwingster.hollowcraft.client.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.core.particles.SimpleParticleType;
import org.jetbrains.annotations.Nullable;

import java.util.Random;

public class ShadeDeadParticle extends AbstractMobDyingParticle{
    protected ShadeDeadParticle(ClientLevel pLevel, double pX, double pY, double pZ, SpriteSet spriteSet) {
        super(pLevel, pX, pY, pZ, spriteSet, 0.5D, 0.04F, 20, 20, 20);
        this.lifetime = new Random().nextInt(15,35);
        this.decelerateAge = this.lifetime + 1;
        this.stopAge = this.lifetime + 1;
    }

    public static class Provider implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet spriteSet;

        public Provider(SpriteSet spriteSet) {
            this.spriteSet = spriteSet;
        }

        @Override
        public @Nullable Particle createParticle(SimpleParticleType pType, ClientLevel pLevel, double pX, double pY, double pZ, double pXSpeed, double pYSpeed, double pZSpeed) {
            return new ShadeDeadParticle(pLevel, pX, pY, pZ, spriteSet);
        }
    }
}
