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

public record NailLevelData(CompoundTag nailLevelNbt) implements CustomPacketPayload {

    public static final Type<NailLevelData> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(MODID, "nail_damage_data"));

    public static final StreamCodec<ByteBuf, NailLevelData> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.COMPOUND_TAG,
            NailLevelData::nailLevelNbt,
            NailLevelData::new
    );

    public static void clientHandler(final NailLevelData nailLevelData, final IPayloadContext context) {
        context.enqueueWork(() -> {
            context.player().getData(HCAttachmentTypes.NAIL_LEVEL).readNBT(nailLevelData.nailLevelNbt());
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
