package com.eokwingster.hollowcraft.event;

import com.eokwingster.hollowcraft.HCConfig;
import com.eokwingster.hollowcraft.client.sounds.EntityBoundFadingSoundInstance;
import com.eokwingster.hollowcraft.client.sounds.HCSoundEvents;
import com.eokwingster.hollowcraft.commands.nailcmd.GetNailDamage;
import com.eokwingster.hollowcraft.commands.nailcmd.SetNailLevel;
import com.eokwingster.hollowcraft.commands.soulcmd.SetSoul;
import com.eokwingster.hollowcraft.commands.soulcmd.SetSoulVessel;
import com.eokwingster.hollowcraft.commands.soulcmd.GetSoul;
import com.eokwingster.hollowcraft.network.NailLevelData;
import com.eokwingster.hollowcraft.network.SoulData;
import com.eokwingster.hollowcraft.network.SpellData;
import com.eokwingster.hollowcraft.tags.HCDamageTypeTags;
import com.eokwingster.hollowcraft.tags.HCEntityTypeTags;
import com.eokwingster.hollowcraft.tags.HCItemTags;
import com.eokwingster.hollowcraft.world.attachmentdata.HCAttachmentTypes;
import com.eokwingster.hollowcraft.world.damagetype.HCDamageTypes;
import com.eokwingster.hollowcraft.world.entity.HCEntityTypes;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.client.Minecraft;
import net.minecraft.client.sounds.SoundManager;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
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
import net.neoforged.neoforge.event.RegisterCommandsEvent;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.neoforged.neoforge.event.entity.player.CriticalHitEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.network.PacketDistributor;

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

        // change nails' damageSource and handle nail damage
        if (source.is(DamageTypes.PLAYER_ATTACK) && player.getMainHandItem().is(HCItemTags.NAIL)) {
            event.setCanceled(true);
            DamageSource nailDamageSource = new DamageSource(
                    level.registryAccess().lookupOrThrow(Registries.DAMAGE_TYPE).getOrThrow(HCDamageTypes.NAIL_DAMAGE_TYPE),
                    player,
                    player
            );
            entity.hurt(nailDamageSource, player.getData(HCAttachmentTypes.NAIL_LEVEL).getDamage());

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
            if (entity instanceof ServerPlayer serverPlayer) {
                syncAttachmentsToClient(serverPlayer);
            }
        }
        if (level.isClientSide()) {
            if (entity.getType() == HCEntityTypes.SHADE_ENTITY_TYPE.get()) {
                soundManager.play(new EntityBoundFadingSoundInstance(HCSoundEvents.SHADE_IDLE.get(), SoundSource.HOSTILE, 0.8F, 1.0F, entity));
            }
        }
    }

    private static void syncAttachmentsToClient(ServerPlayer player) {
        PacketDistributor.sendToPlayer(player, new SoulData(player.getData(HCAttachmentTypes.SOUL).writeNBT()));
        PacketDistributor.sendToPlayer(player, new SpellData(player.getData(HCAttachmentTypes.SPELLS).writeNBT()));
        PacketDistributor.sendToPlayer(player, new NailLevelData(player.getData(HCAttachmentTypes.NAIL_LEVEL).writeNBT()));
    }

    @SubscribeEvent
    private static void onPlayerClone(PlayerEvent.Clone event) {
        Player original = event.getOriginal();
        Player player = event.getEntity();
        Level level = player.level();

        // handle custom attachments copying
        if (event.isWasDeath()) {
            if (HCConfig.keepSoul && level.getGameRules().getBoolean(GameRules.RULE_KEEPINVENTORY)) {
                player.setData(HCAttachmentTypes.SOUL, original.getData(HCAttachmentTypes.SOUL));
            } else {
                player.setData(HCAttachmentTypes.SOUL, original.getData(HCAttachmentTypes.SOUL).getPlayerCloneSoul());
            }
        }
    }

    @SubscribeEvent
    private static void registerCommands(RegisterCommandsEvent event) {
        CommandDispatcher<CommandSourceStack> dispatcher = event.getDispatcher();
        dispatcher.register(monoArgCommand("setSoul", SetSoul.ARGUMENT_NAME, IntegerArgumentType.integer(), new SetSoul()));
        dispatcher.register(monoArgCommand("setSoulVessel", SetSoulVessel.ARGUMENT_NAME, IntegerArgumentType.integer(), new SetSoulVessel()));
        dispatcher.register(freeArgCommand("getSoul", new GetSoul()));
        dispatcher.register(monoArgCommand("setNailLevel", SetNailLevel.ARGUMENT_NAME, IntegerArgumentType.integer(), new SetNailLevel()));
        dispatcher.register(freeArgCommand("getNailDamage", new GetNailDamage()));
    }

    private static LiteralArgumentBuilder<CommandSourceStack> monoArgCommand(String name, String argumentName, ArgumentType<?> type, Command<CommandSourceStack> command) {
        return Commands.literal(MODID).then(
                Commands.literal(name).requires(commandSourceStack -> commandSourceStack.hasPermission(2)).then(
                        Commands.argument(argumentName, type).executes(command)
                )
        );
    }

    private static LiteralArgumentBuilder<CommandSourceStack> freeArgCommand(String name, Command<CommandSourceStack> command) {
        return Commands.literal(MODID).then(
                Commands.literal(name).requires(commandSourceStack -> commandSourceStack.hasPermission(2)).executes(command)
        );
    }
}
