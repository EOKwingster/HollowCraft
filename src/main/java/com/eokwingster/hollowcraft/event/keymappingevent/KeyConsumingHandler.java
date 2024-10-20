package com.eokwingster.hollowcraft.event.keymappingevent;

import com.eokwingster.hollowcraft.client.gui.hud.LookingDirectionIndicator;
import com.eokwingster.hollowcraft.client.keymapping.HCKeyMappings;
import com.eokwingster.hollowcraft.network.SpellData;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.network.PacketDistributor;

import static com.eokwingster.hollowcraft.HollowCraft.MODID;

@EventBusSubscriber(modid = MODID, value = Dist.CLIENT)
public class KeyConsumingHandler {
    private static int spellKeyDownTime;

    @SubscribeEvent
    private static void clientTickPost(ClientTickEvent.Post event) {

        //spell key logic
        if (HCKeyMappings.KEY_SPELL.isDown()) {
            //focus logic
            if (spellKeyDownTime > 5) {
                PacketDistributor.sendToServer(SpellData.make(LookingDirectionIndicator.lookingDirection, spellKeyDownTime));
            }

            spellKeyDownTime++;
        } else {
            //instant spells logic
            if (spellKeyDownTime > 0 && spellKeyDownTime <= 5) {
                PacketDistributor.sendToServer(SpellData.make(LookingDirectionIndicator.lookingDirection, -1));
            }

            spellKeyDownTime = 0;
        }
    }
}
