package com.eokwingster.hollowcraft;

import net.neoforged.neoforge.common.ModConfigSpec;

public class HCConfig {
    //viewAngles
    private static final ModConfigSpec.Builder BUILDER_VIEWANGLES = new ModConfigSpec.Builder()
            .comment("The configs control the angle that be used to determine the direction of view.",
                    "lookDownwardAngle/lookUpwardAngle + lookForwardAngle can not exceed 90!")
            .push("viewAngles");

    private static final ModConfigSpec.IntValue LOOK_DOWNWARD_ANGLE = BUILDER_VIEWANGLES
            .comment("The angle range between vertical downward view and view angle will be seen as looking downward.",
                    "The default value 30 means the range 0 ~ 30. (Vertical downward is 0, parallel forward is 90).")
            .defineInRange("lookDownwardAngle", 30, 0, 90);

    private static final ModConfigSpec.IntValue LOOK_FORWARD_ANGLE = BUILDER_VIEWANGLES
            .comment("The angle range between parallel forward view and this view angle will be seen as looking forward.")
            .comment("The default value 30 means the range -30 ~ 30 (Parallel forward is 0, vertical downward is -90, vertical upward is 90).")
            .defineInRange("lookForwardAngle", 30, 0, 90);

    private static final ModConfigSpec.IntValue LOOK_UPWARD_ANGLE = BUILDER_VIEWANGLES
            .comment("The angle range between vertical upward view and this view angle will be seen as looking upward.")
            .comment("The default value 30 means the range 0 ~ 30 (Vertical upward is 0, parallel forward is 90).")
            .defineInRange("lookUpwardAngle", 30, 0, 90);

    //gui
    private static final ModConfigSpec.Builder BUILDER_GUI = BUILDER_VIEWANGLES
            .pop()
            .comment("GUI settings for: looking direction indicator, soul meter and soul vessel")
            .push("gui");

    private static final ModConfigSpec.IntValue LDI_SCALE = BUILDER_GUI
            .comment("The size of the indicator of looking direction")
            .defineInRange("lookingDirectionIndicatorScale", 4, 1, 8);

    private static final ModConfigSpec.IntValue LDI_X = BUILDER_GUI
            .comment("The pixel horizontal distance from the looking direction indicator to the left side of window.")
            .defineInRange("lookingDirectionIndicatorX", 20, 0, 9999);

    private static final ModConfigSpec.IntValue LDI_Y = BUILDER_GUI
            .comment("The pixel vertical distance from the looking direction indicator to the top side of window.")
            .defineInRange("lookingDirectionIndicatorY", 160, 0, 9999);

    private static final ModConfigSpec.IntValue SM_SCALE = BUILDER_GUI
            .comment("The size of the soul meter")
            .defineInRange("soulMeterScale", 4, 1, 8);

    private static final ModConfigSpec.IntValue SM_X = BUILDER_GUI
            .comment("The pixel horizontal distance from the soul meter to the left side of window.")
            .defineInRange("soulMeterX", 20, 0, 9999);

    private static final ModConfigSpec.IntValue SM_Y = BUILDER_GUI
            .comment("The pixel vertical distance from the soul meter to the top side of window.")
            .defineInRange("soulMeterY", 20, 0, 9999);

    //soul
    private static final ModConfigSpec.Builder BUILDER_SOUL = BUILDER_GUI
            .pop()
            .comment("The configs about the soul of the player, this section need restart of the game")
            .push("soul");

    private static final ModConfigSpec.IntValue SOUL_METER_CAPACITY = BUILDER_SOUL
            .comment("The max amount of the soul that the soul meter can save",
                    "soul meter is the circular meter that fills up with white liquid, revealing two eye holes and giving the meter the appearance of a face")
            .defineInRange("soulMeterCapacity", 99, 0, 9999);

    private static final ModConfigSpec.IntValue SOUL_VESSEL_CAPACITY = BUILDER_SOUL
            .comment("The max amount of the soul that the soul vessel can save",
                    "Soul vessels are additional storage for soul, represented as small circles around the soul meter.")
            .defineInRange("soulVesselCapacity", 33, 0, 9999);

    private static final ModConfigSpec.BooleanValue KEEP_SOUL = BUILDER_SOUL
            .comment("Whether your soul should be kept when you respawn if the gamerule keepInventory is true.")
            .define("keepSoul", false);

    //skills
    private static final ModConfigSpec.Builder BUILDER_SKILLS = BUILDER_SOUL
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
    public static int lookingDirectionIndicatorX;
    public static int lookingDirectionIndicatorY;

    public static int soulMeterScale;
    public static int soulMeterX;
    public static int soulMeterY;

    //soul
    public static int soulMeterCapacity;
    public static int soulVesselCapacity;
    public static boolean keepSoul;

    //skills
    public static double bounceStrength;
    public static float nailAttackSpeed;

    public static void initConfig() {
        //viewAngles
        lookDownwardAngle = LOOK_DOWNWARD_ANGLE.get();
        lookForwardAngle = LOOK_FORWARD_ANGLE.get();
        lookUpwardAngle = LOOK_UPWARD_ANGLE.get();
        //gui
        lookingDirectionIndicatorScale = LDI_SCALE.get();
        lookingDirectionIndicatorX = LDI_X.get();
        lookingDirectionIndicatorY = LDI_Y.get();
        soulMeterScale = SM_SCALE.get();
        soulMeterX = SM_X.get();
        soulMeterY = SM_Y.get();
        //soul
        soulMeterCapacity = SOUL_METER_CAPACITY.get();
        soulVesselCapacity = SOUL_VESSEL_CAPACITY.get();
        keepSoul = KEEP_SOUL.get();
        //skills
        bounceStrength = BOUNCE_STRENGTH.get();
        nailAttackSpeed = NAIL_ATTACK_SPEED.get().floatValue();
    }
}
