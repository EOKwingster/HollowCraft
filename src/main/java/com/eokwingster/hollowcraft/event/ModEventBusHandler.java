package com.eokwingster.hollowcraft.event;

import com.eokwingster.hollowcraft.world.entity.HCEntityTypes;
import com.eokwingster.hollowcraft.world.entity.custom.ShadeEntity;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;

import static com.eokwingster.hollowcraft.HollowCraft.MODID;

@EventBusSubscriber(modid = MODID, bus = EventBusSubscriber.Bus.MOD)
public class ModEventBusHandler {
    @SubscribeEvent
    public static void entityAttributeCreation(EntityAttributeCreationEvent event) {
        event.put(HCEntityTypes.SHADE_ENTITY_TYPE.get(), ShadeEntity.createAttributes().build());
    }
}
