package com.eokwingster.hollowcraft.client.keymapping;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.neoforged.neoforge.client.settings.KeyConflictContext;
import org.lwjgl.glfw.GLFW;

import static com.eokwingster.hollowcraft.HollowCraft.MODID;

public class HCKeyMappings {
    public static final String CATEGORY_HOLLOWSKILLS = formatKeyCategory("hollowskills");

    public static final KeyMapping KEY_SPELL = newKey("key_spell", KeyConflictContext.IN_GAME, InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_G, CATEGORY_HOLLOWSKILLS);
    public static final KeyMapping KEY_ABILITY = newKey("key_ability", KeyConflictContext.IN_GAME, InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_V, CATEGORY_HOLLOWSKILLS);

    private static KeyMapping newKey(String name, KeyConflictContext keyConflictContext, InputConstants.Type inputType, int keyCode, String category) {
        return new KeyMapping(formatKey(name), keyConflictContext, inputType, keyCode, category);
    }

    public static String formatKey(String name) {
        return "key." + MODID + "." + name;
    }

    private static String formatKeyCategory(String name) {
        return "key.categories." + MODID + "." + name;
    }
}
