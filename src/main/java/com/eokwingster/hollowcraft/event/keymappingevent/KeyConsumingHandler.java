package com.eokwingster.hollowcraft.event.keymappingevent;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ClientTickEvent;

import static com.eokwingster.hollowcraft.HollowCraft.MODID;

@EventBusSubscriber(modid = MODID, value = Dist.CLIENT)
public class KeyConsumingHandler {
    
    @SubscribeEvent
    private static void clientTickPost(ClientTickEvent.Post event) {
        //consume skill
    }
}
