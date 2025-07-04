package com.eokwingster.hollowcraft.network;

import com.eokwingster.hollowcraft.world.attachmentdata.HCAttachmentTypes;
import io.netty.buffer.ByteBuf;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.network.handling.IPayloadContext;

import static com.eokwingster.hollowcraft.HollowCraft.MODID;

public record SpellData(CompoundTag spellNbt) implements CustomPacketPayload {
    public static final Type<SpellData> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(MODID, "spell_data"));

    public static final StreamCodec<ByteBuf, SpellData> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.COMPOUND_TAG,
            SpellData::spellNbt,
            SpellData::new
    );

    public static void clientHandler(final SpellData spellData, final IPayloadContext context) {
        context.enqueueWork(() -> {
            context.player().getData(HCAttachmentTypes.SPELLS).readNBT(spellData.spellNbt());
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
