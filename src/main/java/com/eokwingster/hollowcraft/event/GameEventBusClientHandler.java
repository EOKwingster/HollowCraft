package com.eokwingster.hollowcraft.event;

import com.eokwingster.hollowcraft.client.keymapping.HCKeyMappings;
import com.eokwingster.hollowcraft.network.SpellReleaseData;
import com.eokwingster.hollowcraft.spells.ISpell;
import com.eokwingster.hollowcraft.world.attachmentdata.data.PlayerSpells;
import com.eokwingster.hollowcraft.spells.Focus;
import com.eokwingster.hollowcraft.tags.HCItemTags;
import com.eokwingster.hollowcraft.world.attachmentdata.HCAttachmentTypes;
import com.eokwingster.hollowcraft.world.item.NailItem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.client.event.InputEvent;
import net.neoforged.neoforge.network.PacketDistributor;

import static com.eokwingster.hollowcraft.HollowCraft.MODID;

@EventBusSubscriber(modid = MODID, value = Dist.CLIENT)
public class GameEventBusClientHandler {
    //make nails can not attack while attack strength is not full
    @SubscribeEvent
    private static void onInteractionKeyTriggered(InputEvent.InteractionKeyMappingTriggered event) {
        if (event.isAttack()) {
            LocalPlayer player = Minecraft.getInstance().player;
            if (player.getItemInHand(event.getHand()).is(HCItemTags.NAIL) && player.getAttackStrengthScale(0F) < 1) {
                event.setSwingHand(false);
                event.setCanceled(true);
            }
        }
    }

    private static int spellKeyDownTime;
    @SubscribeEvent
    private static void postClientTick(ClientTickEvent.Post event) {
        LocalPlayer player = Minecraft.getInstance().player;
        // handle nail bounce
        NailItem.tickNailBounce(player);
        // spell key logic
        if (HCKeyMappings.KEY_SPELL.isDown()) {
            if (spellKeyDownTime > 5) {
                tryToReleaseSpell(player, Focus.INSTANCE, spellKeyDownTime);
            }
            spellKeyDownTime++;
        } else {
            if (spellKeyDownTime > 0) {
                if (spellKeyDownTime <= 5) {
                    // space for releasing one time spell

                } else {
                    tryToReleaseSpell(player, Focus.INSTANCE, -1);
                }
                spellKeyDownTime = 0;
            }
        }
    }

    private static void tryToReleaseSpell(LocalPlayer player, ISpell spell, int keyDownTime) {
        if (player.getData(HCAttachmentTypes.SPELLS).hasSpell(spell)) {
            PacketDistributor.sendToServer(new SpellReleaseData(PlayerSpells.getKey(spell), keyDownTime));
        }
    }
}
