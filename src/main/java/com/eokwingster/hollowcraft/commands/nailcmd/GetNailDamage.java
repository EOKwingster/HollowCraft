package com.eokwingster.hollowcraft.commands.nailcmd;

import com.eokwingster.hollowcraft.world.attachmentdata.HCAttachmentTypes;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;

public class GetNailDamage implements Command<CommandSourceStack> {
    @Override
    public int run(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        CommandSourceStack source = context.getSource();
        ServerPlayer player = source.getPlayer();
        int nailDamage = player.getData(HCAttachmentTypes.NAIL_LEVEL).getDamage();
        source.sendSystemMessage(Component.translatable("commands.hollowCraft.getNailDamage").append(String.valueOf(nailDamage)));
        return 0;
    }
}
