package com.eokwingster.hollowcraft.event;

import com.eokwingster.hollowcraft.world.item.NailItem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ClientTickEvent;

import static com.eokwingster.hollowcraft.HollowCraft.MODID;

@EventBusSubscriber(modid = MODID)
public class SlashKnockbackHandler {
    @SubscribeEvent
    private static void clientTickPost(ClientTickEvent.Post event) {
        LocalPlayer localPlayer = Minecraft.getInstance().player;
        if (localPlayer == null) return;

        NailItem.tickSlashKnockback(localPlayer);
    }}
