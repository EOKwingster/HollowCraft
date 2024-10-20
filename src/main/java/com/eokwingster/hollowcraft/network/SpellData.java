package com.eokwingster.hollowcraft.network;

import com.eokwingster.hollowcraft.client.gui.hud.LookingDirectionIndicator;
import com.eokwingster.hollowcraft.skills.spells.Focus;
import com.eokwingster.hollowcraft.skills.spells.ISpell;
import com.eokwingster.hollowcraft.skills.spells.PlayerSpells;
import com.eokwingster.hollowcraft.world.attachmentdata.HCAttachmentTypes;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.network.handling.IPayloadContext;

import java.util.List;
import java.util.Map;
import java.util.function.Function;

import static com.eokwingster.hollowcraft.HollowCraft.MODID;

public record SpellData(String lookingDirection, int keyDownTime) implements CustomPacketPayload {
    public static final Type<SpellData> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(MODID, "spell_data"));

    public static SpellData make(LookingDirectionIndicator.LookingDirection lookingDirection, int keyDownTime) {
        String ld = switch (lookingDirection) {
            case LookingDirectionIndicator.LookingDirection.UPWARD -> "upward";
            case LookingDirectionIndicator.LookingDirection.FORWARD -> "forward";
            case LookingDirectionIndicator.LookingDirection.DOWNWARD -> "downward";
            default -> "void";
        };
        return new SpellData(ld, keyDownTime);
    }

    public static final StreamCodec<ByteBuf, SpellData> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.STRING_UTF8,
            SpellData::lookingDirection,
            ByteBufCodecs.INT,
            SpellData::keyDownTime,
            SpellData::new
    );

    // spell release logic
    public static void serverHandler(final SpellData data, final IPayloadContext context) {
        context.enqueueWork(() -> {
            ServerPlayer player = (ServerPlayer) context.player();
            int keyDowntime = data.keyDownTime();
            List<Integer> playerSpells = player.getData(HCAttachmentTypes.SPELLS).playerSpells;

            ISpell spell;
            Function<ISpell, ISpell> hasOrNull = pSpell -> PlayerSpells.hasSpell(playerSpells, pSpell) ? pSpell : null;
            if (keyDowntime < 0) {
                spell = switch (data.lookingDirection()) {
                    case "forward" -> null;
                    case "downward" -> null;
                    case "upward" -> null;
                    default -> null;
                };
            } else {
                spell = hasOrNull.apply(Focus.INSTANCE);
            }
            if (spell != null) {
                spell.release(player, keyDowntime);
            }
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
