package com.eokwingster.hollowcraft.event;

import com.eokwingster.hollowcraft.tags.HCItemTags;
import com.eokwingster.hollowcraft.world.item.NailItem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.client.event.InputEvent;

import static com.eokwingster.hollowcraft.HollowCraft.MODID;

@EventBusSubscriber(modid = MODID, value = Dist.CLIENT)
public class NailClientHandler {

    //handle nail bounce
    @SubscribeEvent
    private static void clientTickPost(ClientTickEvent.Post event) {
        LocalPlayer localPlayer = Minecraft.getInstance().player;
        if (localPlayer == null) return;

        NailItem.tickNailBounce(localPlayer);
    }

    //make nails can not attack while attack indicator is processing
    @SubscribeEvent
    private static void interactionKey(InputEvent.InteractionKeyMappingTriggered event) {
        if (event.isAttack()) {
            var player = Minecraft.getInstance().player;
            if (player != null && player.getItemInHand(event.getHand()).is(HCItemTags.NAIL) && player.getAttackStrengthScale(0F) < 1) {
                event.setSwingHand(false);
                event.setCanceled(true);
            }
        }
    }
}
