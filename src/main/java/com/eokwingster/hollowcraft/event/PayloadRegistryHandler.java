package com.eokwingster.hollowcraft.event;

import com.eokwingster.hollowcraft.network.SoulData;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

import static com.eokwingster.hollowcraft.HollowCraft.MODID;

@EventBusSubscriber(modid = MODID, bus = EventBusSubscriber.Bus.MOD)
public class PayloadRegistryHandler {
    @SubscribeEvent
    private static void registerPayloadHandler(final RegisterPayloadHandlersEvent event) {
        final PayloadRegistrar registrar = event.registrar("1.0");

        registrar.playToClient(
                SoulData.TYPE,
                SoulData.STREAM_CODEC,
                SoulData::clientHandler
        );
    }
}
