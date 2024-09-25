package com.eokwingster.hollowcraft.event.nailevent;

import com.eokwingster.hollowcraft.tags.HCItemTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.CriticalHitEvent;

import static com.eokwingster.hollowcraft.HollowCraft.MODID;

@EventBusSubscriber(modid = MODID)
public class NailServerHandler {

    //make nails no critical damage
    @SubscribeEvent
    private static void criticalDamage(CriticalHitEvent event) {
        if (event.isCriticalHit()) {
            Player player = event.getEntity();
            if (player.getItemInHand(InteractionHand.MAIN_HAND).is(HCItemTags.NAIL)) {
                event.setCriticalHit(false);
            }
        }
    }
}
