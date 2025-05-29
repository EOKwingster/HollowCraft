package com.eokwingster.hollowcraft.event;

import com.eokwingster.hollowcraft.world.entity.HCEntityTypes;
import com.eokwingster.hollowcraft.world.entity.client.HCModelLayers;
import com.eokwingster.hollowcraft.world.entity.client.model.ShadeModel;
import com.eokwingster.hollowcraft.world.entity.client.renderer.ShadeRenderer;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;

import static com.eokwingster.hollowcraft.HollowCraft.MODID;

@EventBusSubscriber(modid = MODID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ModEventBusClientHandler {
    @SubscribeEvent
    public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
        EntityRenderers.register(HCEntityTypes.SHADE_ENTITY_TYPE.get(), ShadeRenderer::new);
    }

    @SubscribeEvent
    public static void registerLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(HCModelLayers.SHADE_LAYER, ShadeModel::createBodyLayer);
    }
}
