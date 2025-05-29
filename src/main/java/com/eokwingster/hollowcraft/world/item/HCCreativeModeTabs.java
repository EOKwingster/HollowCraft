package com.eokwingster.hollowcraft.world.item;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

import static com.eokwingster.hollowcraft.HollowCraft.MODID;

public class HCCreativeModeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(BuiltInRegistries.CREATIVE_MODE_TAB, MODID);

    public static final Supplier<CreativeModeTab> TAB_HOLLOW_CRAFT = CREATIVE_MODE_TABS.register(
            "hollow_craft", () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup." + MODID + ".title"))
                    .icon(() -> HCItems.KNIGHT_SKULL.get().getDefaultInstance())
                    .displayItems((pParameters, pOutput) -> HCItems.ITEM_SUPPLIERS.forEach(item -> pOutput.accept(item.get())))
                    .build()
    );
}
