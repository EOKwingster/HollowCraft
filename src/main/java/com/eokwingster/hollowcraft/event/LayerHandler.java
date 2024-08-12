package com.eokwingster.hollowcraft.event;

import com.eokwingster.hollowcraft.client.gui.LookingDirectionIndicator;
import com.eokwingster.hollowcraft.client.gui.HCGuiLayers;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterGuiLayersEvent;
import net.neoforged.neoforge.client.gui.VanillaGuiLayers;

import static com.eokwingster.hollowcraft.HollowCraft.MODID;

@EventBusSubscriber(modid = MODID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class LayerHandler {
    @SubscribeEvent
    private static void registerGuiLayers(RegisterGuiLayersEvent event) {
        event.registerBelow(VanillaGuiLayers.CROSSHAIR, HCGuiLayers.LOOKING_DIRECTION_INDICATOR, new LookingDirectionIndicator());
    }
}
