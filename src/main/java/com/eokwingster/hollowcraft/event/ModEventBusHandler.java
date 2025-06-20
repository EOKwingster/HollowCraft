package com.eokwingster.hollowcraft.event;

import com.eokwingster.hollowcraft.HCConfig;
import com.eokwingster.hollowcraft.network.NailLevelData;
import com.eokwingster.hollowcraft.network.SoulData;
import com.eokwingster.hollowcraft.network.SpellData;
import com.eokwingster.hollowcraft.network.SpellReleaseData;
import com.eokwingster.hollowcraft.world.entity.HCEntityTypes;
import com.eokwingster.hollowcraft.world.entity.ShadeEntity;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.config.ModConfigEvent;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.handling.DirectionalPayloadHandler;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

import static com.eokwingster.hollowcraft.HollowCraft.MODID;

@EventBusSubscriber(modid = MODID, bus = EventBusSubscriber.Bus.MOD)
public class ModEventBusHandler {
    @SubscribeEvent
    private static void onConfigLoad(ModConfigEvent.Loading event) {
        HCConfig.initConfig();
    }

    @SubscribeEvent
    private static void onConfigReload(ModConfigEvent.Reloading event) {
        HCConfig.initConfig();
    }

    @SubscribeEvent
    private static void entityAttributeCreation(EntityAttributeCreationEvent event) {
        event.put(HCEntityTypes.SHADE_ENTITY_TYPE.get(), ShadeEntity.createAttributes().build());
    }

    @SubscribeEvent
    private static void registerPayloadHandler(final RegisterPayloadHandlersEvent event) {
        final PayloadRegistrar registrar = event.registrar("1.0");
        registrar.playToClient(
                SoulData.TYPE,
                SoulData.STREAM_CODEC,
                SoulData::clientHandler
        );
        registrar.playBidirectional(
                SpellReleaseData.TYPE,
                SpellReleaseData.STREAM_CODEC,
                new DirectionalPayloadHandler<>(
                        SpellReleaseData::clientHandler,
                        SpellReleaseData::serverHandler
                )
        );
        registrar.playToClient(
                NailLevelData.TYPE,
                NailLevelData.STREAM_CODEC,
                NailLevelData::clientHandler
        );
        registrar.playToClient(
                SpellData.TYPE,
                SpellData.STREAM_CODEC,
                SpellData::clientHandler
        );
    }
}
