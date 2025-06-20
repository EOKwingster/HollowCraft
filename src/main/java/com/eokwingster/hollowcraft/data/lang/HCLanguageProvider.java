package com.eokwingster.hollowcraft.data.lang;

import com.eokwingster.hollowcraft.client.keymapping.HCKeyMappings;
import com.eokwingster.hollowcraft.world.item.HCCreativeModeTabs;
import com.eokwingster.hollowcraft.world.item.HCItems;
import com.eokwingster.hollowcraft.world.item.NailItem;
import net.minecraft.client.KeyMapping;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.LanguageProvider;

public class HCLanguageProvider extends LanguageProvider {
    public HCLanguageProvider(PackOutput output, String modid, String locale) {
        super(output, modid, locale);
    }

    @Override
    protected void addTranslations() {
        //key category
        add(HCKeyMappings.CATEGORY_HOLLOWSKILLS, "Hollow Skills");

        //keys
        addKey(HCKeyMappings.KEY_ABILITY, "Ability");
        addKey(HCKeyMappings.KEY_SPELL, "Spell");

        //creative mod tabs
        add(HCCreativeModeTabs.TAB_HOLLOW_CRAFT.get().getDisplayName().getString(), "Hollow Craft");

        //items
        add(HCItems.KNIGHT_SKULL.get(), "Knight Skull");
        add(HCItems.SHADE_SPAWN_EGG.get(), "Shade");
        add(HCItems.NAIL.get(), "Nail");
        add(NailItem.TRANSLATABLE_ID_OLD_NAIL, "Old Nail");
        add(NailItem.TRANSLATABLE_ID_SHARPENED_NAIL, "Sharpened Nail");
        add(NailItem.TRANSLATABLE_ID_CHANNELLED_NAIL, "Channelled Nail");
        add(NailItem.TRANSLATABLE_ID_COILED_NAIL, "Coiled Nail");
        add(NailItem.TRANSLATABLE_ID_PURE_NAIL, "Pure Nail");

        //messages
        add("network.hollowCraft.exception", "Network Exception!");
        add("commands.hollowCraft.setSoul", "Soul set to: ");
        add("commands.hollowCraft.setSoulVessel", "Soul Vessel set to: ");
        add("commands.hollowCraft.getSoul", "Soul: ");
        add("commands.hollowCraft.setNailLevel", "Nail Level set to: ");
        add("commands.hollowCraft.getNailDamage", "Nail Damage: ");
    }

    private void addKey(KeyMapping key, String name) {
        add(key.getName(), name);
    }
}
