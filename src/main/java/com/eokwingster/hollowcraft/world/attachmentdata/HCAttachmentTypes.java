package com.eokwingster.hollowcraft.world.attachmentdata;

import com.eokwingster.hollowcraft.world.attachmentdata.data.NailLevel;
import com.eokwingster.hollowcraft.world.attachmentdata.data.Soul;
import com.eokwingster.hollowcraft.world.attachmentdata.data.PlayerSpells;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.function.Supplier;

import static com.eokwingster.hollowcraft.HollowCraft.MODID;

public class HCAttachmentTypes {
    public static final DeferredRegister<AttachmentType<?>> ATTACHMENT_TYPES = DeferredRegister.create(NeoForgeRegistries.ATTACHMENT_TYPES, MODID);

    public static final Supplier<AttachmentType<Soul>> SOUL = ATTACHMENT_TYPES.register(
            "soul", () -> AttachmentType.serializable(Soul::new).build()
    );

    public static final Supplier<AttachmentType<PlayerSpells>> SPELLS = ATTACHMENT_TYPES.register(
            "spells", () -> AttachmentType.serializable(PlayerSpells::new).copyOnDeath().build()
    );

    public static final Supplier<AttachmentType<NailLevel>> NAIL_LEVEL = ATTACHMENT_TYPES.register(
            "nail_level", () -> AttachmentType.serializable(NailLevel::new).copyOnDeath().build()
    );
}
