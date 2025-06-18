package com.eokwingster.hollowcraft.event;

import com.eokwingster.hollowcraft.HCConfig;
import com.eokwingster.hollowcraft.client.sounds.EntityBoundFadingSoundInstance;
import com.eokwingster.hollowcraft.client.sounds.HCSoundEvents;
import com.eokwingster.hollowcraft.network.SoulData;
import com.eokwingster.hollowcraft.skills.soul.Soul;
import com.eokwingster.hollowcraft.skills.spells.Focus;
import com.eokwingster.hollowcraft.skills.spells.PlayerSpells;
import com.eokwingster.hollowcraft.tags.HCDamageTypeTags;
import com.eokwingster.hollowcraft.tags.HCEntityTypeTags;
import com.eokwingster.hollowcraft.tags.HCItemTags;
import com.eokwingster.hollowcraft.world.attachmentdata.HCAttachmentTypes;
import com.eokwingster.hollowcraft.world.damagetype.HCDamageTypes;
import com.eokwingster.hollowcraft.world.entity.HCEntityTypes;
import com.eokwingster.hollowcraft.world.entity.custom.ShadeEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.sounds.SoundManager;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.neoforged.neoforge.event.entity.player.AttackEntityEvent;
import net.neoforged.neoforge.event.entity.player.CriticalHitEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.network.PacketDistributor;

import java.util.List;

import static com.eokwingster.hollowcraft.HollowCraft.MODID;

@EventBusSubscriber(modid = MODID)
public class GameEventBusHandler {
    //make nails no critical damage
    @SubscribeEvent
    private static void onCriticalHit(CriticalHitEvent event) {
        if (event.isCriticalHit()) {
            Player player = event.getEntity();
            if (player.getItemInHand(InteractionHand.MAIN_HAND).is(HCItemTags.NAIL)) {
                event.setCriticalHit(false);
            }
        }
    }

    @SubscribeEvent
    private static void onLivingIncomingDamage(LivingIncomingDamageEvent event) {
        DamageSource source = event.getSource();
        Player player = Minecraft.getInstance().player;
        LivingEntity entity = event.getEntity();
        Level level = entity.level();
        float amount = event.getAmount();

        // change nails' damageSource
        if (source.is(DamageTypes.PLAYER_ATTACK) && player.getMainHandItem().is(HCItemTags.NAIL)) {
            event.setCanceled(true);
            DamageSource nailDamageSource = new DamageSource(
                    level.registryAccess().lookupOrThrow(Registries.DAMAGE_TYPE).getOrThrow(HCDamageTypes.NAIL_DAMAGE_TYPE),
                    player,
                    player
            );
            entity.hurt(nailDamageSource, amount);

        // make hollow craft entities only hurt by hollow craft damageTypes
        } else if (entity.getType().is(HCEntityTypeTags.HC_ENTITIES) && !source.is(HCDamageTypeTags.HC_DAMAGE)) {
            entity.playSound(HCSoundEvents.SWORD_HIT_REJECT.get());
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    private static void onEntityJoinLevel(EntityJoinLevelEvent event) {
        Entity entity = event.getEntity();
        Level level = event.getLevel();
        SoundManager soundManager = Minecraft.getInstance().getSoundManager();

        if (!level.isClientSide()) {
            if (entity instanceof ServerPlayer player) {
                //sync soul when player joining the level
                Soul soulAttach = player.getData(HCAttachmentTypes.SOUL);
                PacketDistributor.sendToPlayer(player, new SoulData(soulAttach.writeNBT()));

                //give players spell: focus when they join level
                List<Integer> playerSpells = player.getData(HCAttachmentTypes.SPELLS).playerSpells;
                PlayerSpells.addSpell(playerSpells, Focus.INSTANCE);
            }
        } else {
            // handle shade idle sound
            if (entity.getType() == HCEntityTypes.SHADE_ENTITY_TYPE.get()) {
                soundManager.play(new EntityBoundFadingSoundInstance(HCSoundEvents.SHADE_IDLE.get(), SoundSource.HOSTILE, 0.8F, 1.0F, entity));
            }
        }
    }

    @SubscribeEvent
    private static void onPlayerClone(PlayerEvent.Clone event) {
        Player original = event.getOriginal();
        Player player = event.getEntity();
        Level level = player.level();

        //copy the soul when the player die if the config keepSoul is true
        if (HCConfig.keepSoul && level.getGameRules().getBoolean(GameRules.RULE_KEEPINVENTORY) && event.isWasDeath()) {
            player.setData(HCAttachmentTypes.SOUL, original.getData(HCAttachmentTypes.SOUL));
        }
    }
}
