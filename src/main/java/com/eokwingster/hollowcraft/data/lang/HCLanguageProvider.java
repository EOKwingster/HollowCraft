package com.eokwingster.hollowcraft.data.lang;

import com.eokwingster.hollowcraft.client.keymapping.HCKeyMappings;
import com.eokwingster.hollowcraft.world.item.HCCreativeModeTabs;
import com.eokwingster.hollowcraft.world.item.HCItems;
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

        //creative mod tabs
        add(HCCreativeModeTabs.TAB_HOLLOW_CRAFT.get().getDisplayName().getString(), "Hollow Craft");
        //items
        add(HCItems.OLD_NAIL.get(), "Old Nail");
        add(HCItems.KNIGHT_HEAD.get(), "Knight Head");
    }

    private void addKey(KeyMapping key, String name) {
        add(key.getName(), name);
    }
}
