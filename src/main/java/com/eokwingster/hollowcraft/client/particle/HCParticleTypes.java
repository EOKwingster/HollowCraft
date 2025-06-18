package com.eokwingster.hollowcraft.client.particle;

import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

import static com.eokwingster.hollowcraft.HollowCraft.MODID;

public class HCParticleTypes {
    public static final DeferredRegister<ParticleType<?>> PARTICLE_TYPES = DeferredRegister.create(BuiltInRegistries.PARTICLE_TYPE, MODID);

    public static final Supplier<SimpleParticleType> SHADE_DYING_PARTICLE = PARTICLE_TYPES.register(
            "mob_dying_particle",
            () -> new SimpleParticleType(true)
    );

    public static final Supplier<SimpleParticleType> SHADE_DEAD_PARTICLE = PARTICLE_TYPES.register(
            "shade_dead_particle",
            () -> new SimpleParticleType(true)
    );
}
