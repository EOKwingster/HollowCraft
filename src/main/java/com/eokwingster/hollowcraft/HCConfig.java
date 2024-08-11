package com.eokwingster.hollowcraft;

import net.neoforged.neoforge.common.ModConfigSpec;

public class HCConfig {
    private static final ModConfigSpec.Builder BUILDER0 = new ModConfigSpec.Builder()
            .comment("Options that control the behaviour of nails")
            .push("Nail Configs");

    private static final ModConfigSpec.IntValue BOUNCE_X_ROT = BUILDER0
            .comment("The vertical view angle to trigger the nail bounce in degrees.",
                    "Maximum look down is 90, maximum look up is -90, look straight ahead is 0.")
            .defineInRange("bounce_x_rot", 60, -90, 90);

    private static final ModConfigSpec.DoubleValue BOUNCE_STRENGTH = BUILDER0
            .comment("The strength of nail bounce. Vanilla jump is 0.42, no bounce is 0.")
            .defineInRange("bounce_strength", 0.3D, 0D, 32D);

    private static final ModConfigSpec.DoubleValue NAIL_ATTACK_SPEED = BUILDER0
            .comment("The attack speed of nails, vanilla swords' speed is -2.4")
            .defineInRange("nail_attack_speed", -1.5D, -4D, 0D);

    public static final ModConfigSpec CONFIG = BUILDER0.pop().build();


    public static float bounceXRot;
    public static double bounceStrength;
    public static float nailAttackSpeed;

    public static void initConfig() {
        bounceXRot = BOUNCE_X_ROT.get();
        bounceStrength = BOUNCE_STRENGTH.get();
        nailAttackSpeed = NAIL_ATTACK_SPEED.get().floatValue();
    }
}
