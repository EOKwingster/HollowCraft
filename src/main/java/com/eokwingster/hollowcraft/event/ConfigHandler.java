package com.eokwingster.hollowcraft.event;

import com.eokwingster.hollowcraft.HCConfig;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.config.ModConfigEvent;

import static com.eokwingster.hollowcraft.HollowCraft.MODID;

@EventBusSubscriber(modid = MODID, bus = EventBusSubscriber.Bus.MOD)
public class ConfigHandler {
    @SubscribeEvent
    private static void onLoad(ModConfigEvent.Loading event) {
        HCConfig.initConfig();
    }

    @SubscribeEvent
    private static void onReload(ModConfigEvent.Reloading event) {
        HCConfig.initConfig();
    }
}
