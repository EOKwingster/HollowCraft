package com.eokwingster.hollowcraft.event;

import com.eokwingster.hollowcraft.client.gui.hud.LookingDirectionIndicator;
import com.eokwingster.hollowcraft.client.gui.HCGuiLayers;
import com.eokwingster.hollowcraft.client.gui.hud.SoulMeterAndVessels;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterGuiLayersEvent;
import net.neoforged.neoforge.client.gui.VanillaGuiLayers;

import static com.eokwingster.hollowcraft.HollowCraft.MODID;

@EventBusSubscriber(modid = MODID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class LayerRegistryHandler {
    @SubscribeEvent
    private static void registerGuiLayers(RegisterGuiLayersEvent event) {
        event.registerAbove(VanillaGuiLayers.AIR_LEVEL, HCGuiLayers.LOOKING_DIRECTION_INDICATOR, new LookingDirectionIndicator());
        event.registerAbove(VanillaGuiLayers.AIR_LEVEL, HCGuiLayers.SOUL_METER_INDICATOR, new SoulMeterAndVessels());
    }
}
