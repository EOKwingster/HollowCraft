package com.eokwingster.hollowcraft.world.item;

import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

import static com.eokwingster.hollowcraft.HollowCraft.MODID;

public class HCItems {
    public static final List<Supplier<? extends Item>> ITEM_SUPPLIERS = new ArrayList<>();
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(MODID);

    public static final Supplier<NailItem> OLD_NAIL = nailItemWithTab("old_nail", 5F);

    //creative mode tab image
    public static final Supplier<Item> KNIGHT_HEAD = ITEMS.register("knight_head", () -> new Item(new Item.Properties()));

    private static<T extends Item> Supplier<T> itemWithTab(String name, Supplier<T> sup) {
        var item = ITEMS.register(name, sup);
        ITEM_SUPPLIERS.add(item);
        return item;
    }

    private static Supplier<NailItem> nailItemWithTab(String name, float damage) {
        Supplier<NailItem> nail = ITEMS.register(name, () -> new NailItem(
                HCTiers.NAIL, new Item.Properties().attributes(NailItem.createAttributes(damage))
        ));
        ITEM_SUPPLIERS.add(nail);
        return nail;
    }
}
