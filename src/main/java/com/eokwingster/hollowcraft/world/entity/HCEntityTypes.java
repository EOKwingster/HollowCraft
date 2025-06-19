package com.eokwingster.hollowcraft.world.entity;

import com.eokwingster.hollowcraft.world.entity.entities.ShadeEntity;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

import static com.eokwingster.hollowcraft.HollowCraft.MODID;

public class HCEntityTypes {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(Registries.ENTITY_TYPE, MODID);

    public static final Supplier<EntityType<ShadeEntity>> SHADE_ENTITY_TYPE = ENTITY_TYPES.register(
            "shade_entity_type",
            () -> EntityType.Builder.of(
                    ShadeEntity::new,
                    MobCategory.MONSTER
            )
            .sized(0.5F, 1.4F)
            .eyeHeight(0.94F)
            .clientTrackingRange(8)
            .build("shade")
    );
}
