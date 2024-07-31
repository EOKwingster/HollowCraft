package com.eokwingster.hollowcraft;

import com.eokwingster.hollowcraft.world.item.HCCreativeModeTabs;
import com.eokwingster.hollowcraft.world.item.HCItems;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;

@Mod(HollowCraft.MODID)
public class HollowCraft
{
    public static final String MODID = "hollowcraft";

    public HollowCraft(IEventBus bus, ModContainer modContainer) {
        HCCreativeModeTabs.CREATIVE_MODE_TABS.register(bus);
        HCItems.ITEMS.register(bus);
    }
}
