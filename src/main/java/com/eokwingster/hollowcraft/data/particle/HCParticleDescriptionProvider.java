package com.eokwingster.hollowcraft.data.particle;

import com.eokwingster.hollowcraft.client.particle.HCParticleTypes;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.common.data.ParticleDescriptionProvider;

import static com.eokwingster.hollowcraft.HollowCraft.MODID;

public class HCParticleDescriptionProvider extends ParticleDescriptionProvider {
    public HCParticleDescriptionProvider(PackOutput output, ExistingFileHelper fileHelper) {
        super(output, fileHelper);
    }

    @Override
    protected void addDescriptions() {
        sprite(HCParticleTypes.SHADE_DYING_PARTICLE.get(), ResourceLocation.fromNamespaceAndPath(MODID, "shade_dying_particle"));
        sprite(HCParticleTypes.SHADE_DEAD_PARTICLE.get(), ResourceLocation.fromNamespaceAndPath(MODID, "shade_dying_particle"));
    }
}
