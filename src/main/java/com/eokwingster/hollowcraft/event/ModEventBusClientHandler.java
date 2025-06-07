package com.eokwingster.hollowcraft.event;

import com.eokwingster.hollowcraft.client.gui.HCGuiLayers;
import com.eokwingster.hollowcraft.client.gui.hud.LookingDirectionIndicator;
import com.eokwingster.hollowcraft.client.gui.hud.SoulMeterAndVessels;
import com.eokwingster.hollowcraft.client.keymapping.HCKeyMappings;
import com.eokwingster.hollowcraft.world.entity.HCEntityTypes;
import com.eokwingster.hollowcraft.world.entity.client.HCModelLayers;
import com.eokwingster.hollowcraft.world.entity.client.model.ShadeModel;
import com.eokwingster.hollowcraft.world.entity.client.renderer.ShadeRenderer;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.RegisterGuiLayersEvent;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import net.neoforged.neoforge.client.gui.VanillaGuiLayers;

import static com.eokwingster.hollowcraft.HollowCraft.MODID;

@EventBusSubscriber(modid = MODID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ModEventBusClientHandler {
    @SubscribeEvent
    private static void registerGuiLayers(RegisterGuiLayersEvent event) {
        event.registerAbove(VanillaGuiLayers.AIR_LEVEL, HCGuiLayers.LOOKING_DIRECTION_INDICATOR, new LookingDirectionIndicator());
        event.registerAbove(VanillaGuiLayers.AIR_LEVEL, HCGuiLayers.SOUL_METER_INDICATOR, new SoulMeterAndVessels());
    }

    @SubscribeEvent
    private static void registerKeyMappings(RegisterKeyMappingsEvent event) {
        event.register(HCKeyMappings.KEY_SPELL);
        event.register(HCKeyMappings.KEY_ABILITY);
    }

    @SubscribeEvent
    private static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
        EntityRenderers.register(HCEntityTypes.SHADE_ENTITY_TYPE.get(), ShadeRenderer::new);
    }

    @SubscribeEvent
    private static void registerLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(HCModelLayers.SHADE_LAYER, ShadeModel::createBodyLayer);
    }
}
