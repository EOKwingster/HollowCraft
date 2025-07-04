package com.eokwingster.hollowcraft;

import com.eokwingster.hollowcraft.client.particle.HCParticleTypes;
import com.eokwingster.hollowcraft.client.sounds.HCSoundEvents;
import com.eokwingster.hollowcraft.world.attachmentdata.HCAttachmentTypes;
import com.eokwingster.hollowcraft.world.entity.HCEntityTypes;
import com.eokwingster.hollowcraft.world.item.HCCreativeModeTabs;
import com.eokwingster.hollowcraft.world.item.HCItems;
import com.eokwingster.hollowcraft.world.level.block.HCBlocks;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;

@Mod(HollowCraft.MODID)
public class HollowCraft
{
    public static final String MODID = "hollowcraft";

    public HollowCraft(IEventBus bus, ModContainer modContainer) {
        HCCreativeModeTabs.CREATIVE_MODE_TABS.register(bus);
        HCBlocks.BLOCKS.register(bus);
        HCItems.ITEMS.register(bus);
        HCAttachmentTypes.ATTACHMENT_TYPES.register(bus);
        HCSoundEvents.SOUND_EVENTS.register(bus);
        HCEntityTypes.ENTITY_TYPES.register(bus);
        HCParticleTypes.PARTICLE_TYPES.register(bus);

        modContainer.registerConfig(ModConfig.Type.COMMON, HCConfig.CONFIG);
    }
}
