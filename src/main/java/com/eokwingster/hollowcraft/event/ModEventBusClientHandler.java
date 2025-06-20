package com.eokwingster.hollowcraft.event;

import com.eokwingster.hollowcraft.client.gui.HCGuiLayers;
import com.eokwingster.hollowcraft.client.gui.hud.LookingDirectionIndicator;
import com.eokwingster.hollowcraft.client.gui.hud.SoulMeterAndVessels;
import com.eokwingster.hollowcraft.client.keymapping.HCKeyMappings;
import com.eokwingster.hollowcraft.client.particle.HCParticleTypes;
import com.eokwingster.hollowcraft.client.particle.ShadeDeadParticle;
import com.eokwingster.hollowcraft.client.particle.ShadeDyingParticle;
import com.eokwingster.hollowcraft.world.entity.HCEntityTypes;
import com.eokwingster.hollowcraft.client.model.HCModelLayers;
import com.eokwingster.hollowcraft.client.model.ShadeModel;
import com.eokwingster.hollowcraft.client.renderer.ShadeRenderer;
import com.eokwingster.hollowcraft.world.item.HCItems;
import com.eokwingster.hollowcraft.world.item.NailItem;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.client.renderer.item.ClampedItemPropertyFunction;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.RegisterGuiLayersEvent;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import net.neoforged.neoforge.client.event.RegisterParticleProvidersEvent;
import net.neoforged.neoforge.client.gui.VanillaGuiLayers;

import static com.eokwingster.hollowcraft.HollowCraft.MODID;

@EventBusSubscriber(modid = MODID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ModEventBusClientHandler {
    @SubscribeEvent
    private static void onFMLClientSetup(FMLClientSetupEvent event) {
        event.enqueueWork(() -> ItemProperties.register(HCItems.NAIL.get(), ResourceLocation.fromNamespaceAndPath(MODID, "nail_state"), (ClampedItemPropertyFunction) NailItem::nailStateFunction));
    }

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

    @SubscribeEvent
    private static void registerParticleProviders(RegisterParticleProvidersEvent event) {
        event.registerSpriteSet(HCParticleTypes.SHADE_DYING_PARTICLE.get(), ShadeDyingParticle.Provider::new);
        event.registerSpriteSet(HCParticleTypes.SHADE_DEAD_PARTICLE.get(), ShadeDeadParticle.Provider::new);
    }
}
