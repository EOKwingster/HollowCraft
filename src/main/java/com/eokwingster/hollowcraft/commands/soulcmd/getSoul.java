package com.eokwingster.hollowcraft.commands.soulcmd;

import com.eokwingster.hollowcraft.skills.Soul;
import com.eokwingster.hollowcraft.world.attachmentdata.HCAttachmentTypes;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;

public class getSoul implements Command<CommandSourceStack> {
    @Override
    public int run(CommandContext<CommandSourceStack> commandContext) throws CommandSyntaxException {
        CommandSourceStack source = commandContext.getSource();
        ServerPlayer player = source.getPlayer();
        Soul soulAttach = player.getData(HCAttachmentTypes.SOUL);
        source.sendSystemMessage(Component.translatable("commands.hollowCraft.getSoul").append(String.valueOf(soulAttach.getSoul())));
        return 0;
    }
}
