package com.eokwingster.hollowcraft.event.skillevent;

import com.eokwingster.hollowcraft.HCConfig;
import com.eokwingster.hollowcraft.skills.soul.Soul;
import com.eokwingster.hollowcraft.network.SoulData;
import com.eokwingster.hollowcraft.skills.spells.Focus;
import com.eokwingster.hollowcraft.skills.spells.PlayerSpells;
import com.eokwingster.hollowcraft.world.attachmentdata.HCAttachmentTypes;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.network.PacketDistributor;

import java.util.List;

import static com.eokwingster.hollowcraft.HollowCraft.MODID;

@EventBusSubscriber(modid = MODID)
public class GameEventsHandler {
    @SubscribeEvent
    private static void entityJoinLevel(EntityJoinLevelEvent event) {
        Entity entity = event.getEntity();
        Level level = event.getLevel();

        if (!level.isClientSide()) {
            if (entity instanceof ServerPlayer player) {
                //sync soul when player joining the level
                Soul soulAttach = player.getData(HCAttachmentTypes.SOUL);
                PacketDistributor.sendToPlayer(player, new SoulData(soulAttach.writeNBT()));

                //give players spell: focus when they join level
                List<Integer> playerSpells = player.getData(HCAttachmentTypes.SPELLS).playerSpells;
                PlayerSpells.addSpell(playerSpells, Focus.INSTANCE);
            }
        }
    }

    @SubscribeEvent
    private static void playerClone(PlayerEvent.Clone event) {
        Player original = event.getOriginal();
        Player player = event.getEntity();
        Level level = player.level();

        //copy the soul when the player die if the config keepSoul is true
        if (HCConfig.keepSoul && level.getGameRules().getBoolean(GameRules.RULE_KEEPINVENTORY) && event.isWasDeath()) {
            player.setData(HCAttachmentTypes.SOUL, original.getData(HCAttachmentTypes.SOUL));
        }
    }
}
