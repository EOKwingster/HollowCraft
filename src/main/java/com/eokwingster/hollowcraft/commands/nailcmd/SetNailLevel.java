package com.eokwingster.hollowcraft.commands.nailcmd;

import com.eokwingster.hollowcraft.network.NailLevelData;
import com.eokwingster.hollowcraft.world.attachmentdata.HCAttachmentTypes;
import com.eokwingster.hollowcraft.world.attachmentdata.data.NailLevel;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.network.PacketDistributor;

public class SetNailLevel implements Command<CommandSourceStack> {
    public static final String ARGUMENT_NAME = "nailLevel";

    @Override
    public int run(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        CommandSourceStack source = context.getSource();
        ServerPlayer player = source.getPlayer();
        NailLevel nailLevel = player.getData(HCAttachmentTypes.NAIL_LEVEL);

        int nailDamageLevel = context.getArgument(ARGUMENT_NAME, Integer.class);
        nailLevel.setLevel(nailDamageLevel);
        PacketDistributor.sendToPlayer(player, new NailLevelData(nailLevel.writeNBT()));

        int newDamage = nailLevel.getDamage();
        source.sendSystemMessage(Component.translatable("commands.hollowCraft.setNailLevel").append(String.valueOf(newDamage)));
        return 0;
    }
}
