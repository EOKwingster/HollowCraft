package com.eokwingster.hollowcraft.network;

import com.eokwingster.hollowcraft.client.gui.hud.LookingDirectionIndicator;
import com.eokwingster.hollowcraft.spells.Focus;
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
import net.neoforged.neoforge.network.handling.IPayloadContext;

import java.util.List;
import java.util.function.Function;

import static com.eokwingster.hollowcraft.HollowCraft.MODID;

public record SpellReleaseData(String lookingDirection, int keyDownTime) implements CustomPacketPayload {
    public static final Type<SpellReleaseData> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(MODID, "spell_release_data"));

    public static SpellReleaseData make(LookingDirectionIndicator.LookingDirection lookingDirection, int keyDownTime) {
        String ld = switch (lookingDirection) {
            case LookingDirectionIndicator.LookingDirection.UPWARD -> "upward";
            case LookingDirectionIndicator.LookingDirection.FORWARD -> "forward";
            case LookingDirectionIndicator.LookingDirection.DOWNWARD -> "downward";
            default -> "void";
        };
        return new SpellReleaseData(ld, keyDownTime);
    }

    public static final StreamCodec<ByteBuf, SpellReleaseData> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.STRING_UTF8,
            SpellReleaseData::lookingDirection,
            ByteBufCodecs.INT,
            SpellReleaseData::keyDownTime,
            SpellReleaseData::new
    );

    // spell release logic
    public static void serverHandler(final SpellReleaseData data, final IPayloadContext context) {
        context.enqueueWork(() -> {
            ServerPlayer player = (ServerPlayer) context.player();
            int keyDowntime = data.keyDownTime();
            List<Integer> playerSpells = player.getData(HCAttachmentTypes.SPELLS).playerSpells;

            ISpell spell;
            Function<ISpell, ISpell> ifPlayerHas = pSpell -> PlayerSpells.hasSpell(playerSpells, pSpell) ? pSpell : null;
            if (keyDowntime <= 5) {
                spell = switch (data.lookingDirection()) {
                    case "forward" -> null;
                    case "downward" -> null;
                    case "upward" -> null;
                    default -> null;
                };
            } else {
                spell = ifPlayerHas.apply(Focus.INSTANCE);
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
