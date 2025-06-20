package com.eokwingster.hollowcraft.network;

import com.eokwingster.hollowcraft.spells.ISpell;
import com.eokwingster.hollowcraft.world.attachmentdata.data.PlayerSpells;
import com.eokwingster.hollowcraft.world.attachmentdata.HCAttachmentTypes;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.handling.IPayloadContext;

import static com.eokwingster.hollowcraft.HollowCraft.MODID;

public record SpellReleaseData(int spellId, int keyDownTime) implements CustomPacketPayload {
    public static final Type<SpellReleaseData> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(MODID, "spell_release_data"));

    public static final StreamCodec<ByteBuf, SpellReleaseData> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.INT,
            SpellReleaseData::spellId,
            ByteBufCodecs.INT,
            SpellReleaseData::keyDownTime,
            SpellReleaseData::new
    );

    public static void clientHandler(final SpellReleaseData data, final IPayloadContext context) {
        context.enqueueWork(() -> {
            bothSidesHandler(context.player(), data.spellId(), data.keyDownTime());
        }).exceptionally(e -> {
            context.disconnect(Component.translatable("network.hollowCraft.exception", e.getMessage()));
            return null;
        });
    }

    private static void bothSidesHandler(Player player, int spellId, int keyDownTime) {
        PlayerSpells spells = player.getData(HCAttachmentTypes.SPELLS);
        ISpell spell = PlayerSpells.getSpell(spellId);
        if (spells.hasSpell(spell)) {
            spell.release(player, keyDownTime);
        }
    }

    public static void serverHandler(final SpellReleaseData data, final IPayloadContext context) {
        context.enqueueWork(() -> {
            bothSidesHandler(context.player(), data.spellId(), data.keyDownTime());
            PacketDistributor.sendToPlayer((ServerPlayer) context.player(), data);
        }).exceptionally(e -> {
            context.disconnect(Component.translatable("network.hollowCraft.exception", e.getMessage()));
            return null;
        });
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
