package com.eokwingster.hollowcraft.world.item;

import com.eokwingster.hollowcraft.world.entity.HCEntityTypes;
import com.eokwingster.hollowcraft.world.level.block.HCBlocks;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.common.DeferredSpawnEggItem;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

import static com.eokwingster.hollowcraft.HollowCraft.MODID;

public class HCItems {
    public static final List<Supplier<? extends Item>> ITEM_SUPPLIERS = new ArrayList<>();
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(MODID);

    public static final Supplier<NailItem> NAIL = itemWithTab(
            "nail", () -> new NailItem(
                    HCTiers.NAIL, new Item.Properties().attributes(NailItem.createAttributes())
            )
    );
    public static final Supplier<Item> SHADE_SPAWN_EGG = itemWithTab(
            "shade_spawn_egg", () -> new DeferredSpawnEggItem(
                    HCEntityTypes.SHADE_ENTITY_TYPE, 0, 3289650, new Item.Properties()
            )
    );
    public static final Supplier<Item> DIRTMOUTH_PORTAL = ITEMS.register(
            "dirtmouth_portal", () -> new BlockItem(HCBlocks.DIRTMOUTH_PORTAL.get(), new Item.Properties())
    );
    public static final Supplier<Item> OVERWORLD_PORTAL = ITEMS.register(
            "overworld_portal", () -> new BlockItem(HCBlocks.OVERWORLD_PORTAL.get(), new Item.Properties())
    );

    //creative mode tab image
    public static final Supplier<Item> KNIGHT_SKULL = ITEMS.register("knight_skull", () -> new Item(new Item.Properties()));

    private static<T extends Item> Supplier<T> itemWithTab(String name, Supplier<T> sup) {
        DeferredItem<T> item = ITEMS.register(name, sup);
        ITEM_SUPPLIERS.add(item);
        return item;
    }
}
