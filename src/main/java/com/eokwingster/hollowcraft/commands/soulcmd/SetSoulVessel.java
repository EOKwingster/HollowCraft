package com.eokwingster.hollowcraft.commands.soulcmd;

import com.eokwingster.hollowcraft.skills.Soul;
import com.eokwingster.hollowcraft.network.SoulData;
import com.eokwingster.hollowcraft.world.attachmentdata.HCAttachmentTypes;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.network.PacketDistributor;

public class SetSoulVessel implements Command<CommandSourceStack> {
    @Override
    public int run(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        CommandSourceStack source = context.getSource();
        ServerPlayer player = source.getPlayer();
        Soul soulAttach = player.getData(HCAttachmentTypes.SOUL);

        int value = context.getArgument("value", Integer.class);
        soulAttach.setVessel(value);
        PacketDistributor.sendToPlayer(player, new SoulData(soulAttach.writeNBT()));

        int newVessel = soulAttach.getVessel();
        source.sendSystemMessage(Component.translatable("commands.hollowCraft.setSoulVessel").append(String.valueOf(newVessel)));
        return 0;
    }
}
