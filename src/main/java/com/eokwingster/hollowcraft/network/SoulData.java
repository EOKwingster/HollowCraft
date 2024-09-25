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

public record SoulData(CompoundTag soulNbt) implements CustomPacketPayload {
    public static final Type<SoulData> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(MODID, "soul_data"));

    public static final StreamCodec<ByteBuf, SoulData> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.COMPOUND_TAG,
            SoulData::soulNbt,
            SoulData::new
    );

    public static void clientHandler(final SoulData soulData, final IPayloadContext context) {
        context.enqueueWork(() -> {
            context.player().getData(HCAttachmentTypes.SOUL).readNBT(soulData.soulNbt());
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
