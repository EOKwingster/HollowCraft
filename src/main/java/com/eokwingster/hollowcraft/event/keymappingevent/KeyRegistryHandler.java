package com.eokwingster.hollowcraft.event.keymappingevent;

import com.eokwingster.hollowcraft.client.keymapping.HCKeyMappings;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;

import static com.eokwingster.hollowcraft.HollowCraft.MODID;

@EventBusSubscriber(modid = MODID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class KeyRegistryHandler {
    @SubscribeEvent
    private static void registerKeyMappings(RegisterKeyMappingsEvent event) {
        event.register(HCKeyMappings.KEY_SPELL);
        event.register(HCKeyMappings.KEY_ABILITY);
    }
}
