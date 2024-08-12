package com.eokwingster.hollowcraft;

import com.eokwingster.hollowcraft.client.gui.LookingDirectionIndicator;
import net.minecraft.client.Minecraft;
import net.neoforged.neoforge.common.ModConfigSpec;

public class HCConfig {
    //viewAngles
    private static final ModConfigSpec.Builder BUILDER_VIEWANGLES = new ModConfigSpec.Builder()
            .comment("The configs control the angle that be used to determine the direction of view.",
                    "lookDownwardAngle/lookUpwardAngle + lookForwardAngle can not exceed 90!")
            .push("viewAngles");

    private static final ModConfigSpec.IntValue LOOK_DOWNWARD_ANGLE = BUILDER_VIEWANGLES
            .comment("Angle range between vertical downward view and view angle that is judged to be downward looking.",
                    "Vertical downward is 0, parallel forward is 90. The default value 30 means the range 0 ~ 30.")
            .defineInRange("lookDownwardAngle", 30, 0, 90);

    private static final ModConfigSpec.IntValue LOOK_FORWARD_ANGLE = BUILDER_VIEWANGLES
            .comment("Angle range between parallel forward looking angle and view angle that is judged to be forward looking.")
            .comment("parallel forward is 0, vertical downward is -90, vertical upward is 90. The default value 30 means the range -30 ~ 30")
            .defineInRange("lookForwardAngle", 30, 0, 90);

    private static final ModConfigSpec.IntValue LOOK_UPWARD_ANGLE = BUILDER_VIEWANGLES
            .comment("Angle range between vertical upward view and view angle that is judged to be upward looking.")
            .comment("Vertical upward is 0, parallel forward is 90. The default value 30 means the range 0 ~ 30.")
            .defineInRange("lookUpwardAngle", 30, 0, 90);

    //gui
    private static final ModConfigSpec.Builder BUILDER_GUI = BUILDER_VIEWANGLES
            .pop()
            .comment("GUI settings for: looking direction indicator")
            .push("gui");

    private static final ModConfigSpec.IntValue LDI_SCALE = BUILDER_GUI
            .comment("The size of the indicator of looking direction")
            .defineInRange("lookingDirectionIndicatorScale", 3, 1, 8);

    private static final ModConfigSpec.EnumValue<LookingDirectionIndicator.POSITION> LDI_POSITION = BUILDER_GUI
            .comment("The position of the looking direction indicator.")
            .defineEnum("lookingDirectionIndicatorPosition", LookingDirectionIndicator.POSITION.BOTTOM_LEFT);

    private static final ModConfigSpec.IntValue LDI_X_OFFSET = BUILDER_GUI
            .comment("The pixel horizontal distance from the looking direction indicator to the window side it near to.",
                    "The side it near to depends on its position. TOP_LEFT is near to the left window side.")
            .defineInRange("lookingDirectionIndicatorXOffset", 20, 0, 9999);

    private static final ModConfigSpec.IntValue LDI_Y_OFFSET = BUILDER_GUI
            .comment("The pixel vertical distance from the looking direction indicator to the window side it near to.",
                    "The side it near to depends on its position. TOP_LEFT is near to the top window side.")
            .defineInRange("lookingDirectionIndicatorYOffset", 20, 0, 9999);

    //skills
    private static final ModConfigSpec.Builder BUILDER_SKILLS = BUILDER_GUI
            .pop()
            .comment("The configs control the behaviour of skills")
            .push("skills");

    private static final ModConfigSpec.DoubleValue BOUNCE_STRENGTH = BUILDER_SKILLS
            .comment("The strength of nail bounce. Vanilla jump is 0.42, no bounce is 0, default 0.3.")
            .defineInRange("bounceStrength", 0.3D, 0D, 32D);

    private static final ModConfigSpec.DoubleValue NAIL_ATTACK_SPEED = BUILDER_SKILLS
            .comment("The attack speed of nails, vanilla swords' speed is -2.4, default -1.5(8 ticks once).")
            .defineInRange("nailAttackSpeed", -1.5D, -4D, 0D);

    public static final ModConfigSpec CONFIG = BUILDER_SKILLS.pop().build();

    //viewAngles
    public static float lookDownwardAngle;
    public static float lookForwardAngle;
    public static float lookUpwardAngle;

    //gui
    public static int lookingDirectionIndicatorScale;
    public static LookingDirectionIndicator.POSITION lookingDirectionIndicatorPosition;
    public static int lookingDirectionIndicatorXOffset;
    public static int lookingDirectionIndicatorYOffset;

    //skills
    public static double bounceStrength;
    public static float nailAttackSpeed;

    public static void initConfig() {
        lookDownwardAngle = LOOK_DOWNWARD_ANGLE.get();
        lookForwardAngle = LOOK_FORWARD_ANGLE.get();
        lookUpwardAngle = LOOK_UPWARD_ANGLE.get();

        lookingDirectionIndicatorScale = LDI_SCALE.get();
        lookingDirectionIndicatorPosition = LDI_POSITION.get();
        lookingDirectionIndicatorXOffset = LDI_X_OFFSET.get();
        lookingDirectionIndicatorYOffset = LDI_Y_OFFSET.get();

        bounceStrength = BOUNCE_STRENGTH.get();
        nailAttackSpeed = NAIL_ATTACK_SPEED.get().floatValue();
    }
}
