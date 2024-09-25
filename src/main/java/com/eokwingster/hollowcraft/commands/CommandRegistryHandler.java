package com.eokwingster.hollowcraft.commands;

import com.eokwingster.hollowcraft.commands.soulcmd.SetSoul;
import com.eokwingster.hollowcraft.commands.soulcmd.SetSoulVessel;
import com.eokwingster.hollowcraft.commands.soulcmd.getSoul;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.tree.LiteralCommandNode;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.RegisterCommandsEvent;

import static com.eokwingster.hollowcraft.HollowCraft.MODID;

@EventBusSubscriber(modid = MODID)
public class CommandRegistryHandler {
    @SubscribeEvent
    private static void registerCommands(RegisterCommandsEvent event) {
        CommandDispatcher<CommandSourceStack> dispatcher = event.getDispatcher();

        LiteralCommandNode<CommandSourceStack> setSoulCmd = dispatcher.register(monoArgCommand("setSoul", "value", IntegerArgumentType.integer(), new SetSoul()));
        LiteralCommandNode<CommandSourceStack> setSoulVesselCmd = dispatcher.register(monoArgCommand("setSoulVessel", "value", IntegerArgumentType.integer(), new SetSoulVessel()));
        LiteralCommandNode<CommandSourceStack> getSoul = dispatcher.register(freeArgCommand("getSoul", new getSoul()));
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
