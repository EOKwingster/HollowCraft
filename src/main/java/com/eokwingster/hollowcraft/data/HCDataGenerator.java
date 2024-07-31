package com.eokwingster.hollowcraft.data;

import com.eokwingster.hollowcraft.data.lang.HCLanguageProvider;
import com.eokwingster.hollowcraft.data.model.item.HCItemModelProvider;
import com.eokwingster.hollowcraft.data.tags.HCBlockTagsProvider;
import com.eokwingster.hollowcraft.data.tags.HCItemTagsProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.concurrent.CompletableFuture;

import static com.eokwingster.hollowcraft.HollowCraft.MODID;

@EventBusSubscriber(modid = MODID, bus = EventBusSubscriber.Bus.MOD)
public class HCDataGenerator {
    @SubscribeEvent
    private static void gatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        PackOutput output = generator.getPackOutput();
        ExistingFileHelper exFileHelper = event.getExistingFileHelper();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();
        boolean client = event.includeClient();
        boolean server = event.includeServer();

        //client
        generator.addProvider(client, new HCLanguageProvider(output, MODID, "en_us"));
        generator.addProvider(client, new HCItemModelProvider(output, MODID, exFileHelper));

        //server
        HCBlockTagsProvider blockTagsProvider = generator.addProvider(server, new HCBlockTagsProvider(output, lookupProvider, MODID, exFileHelper));
        generator.addProvider(server, new HCItemTagsProvider(output, lookupProvider, blockTagsProvider.contentsGetter(), MODID, exFileHelper));
    }
}
