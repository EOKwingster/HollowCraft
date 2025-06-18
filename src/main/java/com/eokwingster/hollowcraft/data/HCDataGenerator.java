package com.eokwingster.hollowcraft.data;

import com.eokwingster.hollowcraft.data.lang.HCLanguageProvider;
import com.eokwingster.hollowcraft.data.model.item.HCItemModelProvider;
import com.eokwingster.hollowcraft.data.particle.HCParticleDescriptionProvider;
import com.eokwingster.hollowcraft.data.registries.HCRegistryProvider;
import com.eokwingster.hollowcraft.data.sounds.HCSoundDefinitionProvider;
import com.eokwingster.hollowcraft.data.tags.HCBlockTagsProvider;
import com.eokwingster.hollowcraft.data.tags.HCDamageTypeTagsProvider;
import com.eokwingster.hollowcraft.data.tags.HCEntityTypeTagsProvider;
import com.eokwingster.hollowcraft.data.tags.HCItemTagsProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

import static com.eokwingster.hollowcraft.HollowCraft.MODID;

@EventBusSubscriber(modid = MODID, bus = EventBusSubscriber.Bus.MOD)
public class HCDataGenerator {
    @SubscribeEvent
    private static void onGatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        PackOutput output = generator.getPackOutput();
        ExistingFileHelper exFileHelper = event.getExistingFileHelper();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();
        boolean client = event.includeClient();
        boolean server = event.includeServer();

        //client
        generator.addProvider(client, new HCLanguageProvider(output, MODID, "en_us"));
        generator.addProvider(client, new HCItemModelProvider(output, MODID, exFileHelper));
        generator.addProvider(client, new HCSoundDefinitionProvider(output, MODID, exFileHelper));
        generator.addProvider(client, new HCParticleDescriptionProvider(output, exFileHelper));

        //server
        HCBlockTagsProvider blockTagsProvider = generator.addProvider(server, new HCBlockTagsProvider(output, lookupProvider, MODID, exFileHelper));
        generator.addProvider(server, new HCItemTagsProvider(output, lookupProvider, blockTagsProvider.contentsGetter(), MODID, exFileHelper));
        generator.addProvider(server, new HCEntityTypeTagsProvider(output, lookupProvider, MODID, exFileHelper));
        HCRegistryProvider registryProvider = generator.addProvider(server, new HCRegistryProvider(output, lookupProvider, Set.of(MODID)));
        lookupProvider = registryProvider.getRegistryProvider();
        generator.addProvider(server, new HCDamageTypeTagsProvider(output, lookupProvider, MODID, exFileHelper));
    }
}
