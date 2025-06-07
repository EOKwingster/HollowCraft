package com.eokwingster.hollowcraft.client.sounds;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

import static com.eokwingster.hollowcraft.HollowCraft.MODID;

public class HCSoundEvents {
    public static final DeferredRegister<SoundEvent> SOUND_EVENTS = DeferredRegister.create(Registries.SOUND_EVENT, MODID);

    public static final Supplier<SoundEvent> FOCUS_CHARGING = registerSound("focus_charging");
    public static final Supplier<SoundEvent> FOCUS_HEAL = registerSound("focus_heal");
    public static final Supplier<SoundEvent> SWORD_HIT_REJECT = registerSound("sword_hit_reject");
    public static final Supplier<SoundEvent> SHADE_IDLE = registerSound("hollow_shade_idle");

    private static Supplier<SoundEvent> registerSound(String name) {
        return SOUND_EVENTS.register(name, () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(MODID, name)));
    }
}
