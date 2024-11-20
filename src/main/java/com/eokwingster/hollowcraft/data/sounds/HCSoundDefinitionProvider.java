package com.eokwingster.hollowcraft.data.sounds;

import com.eokwingster.hollowcraft.client.sounds.HCSoundEvents;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.common.data.SoundDefinition;
import net.neoforged.neoforge.common.data.SoundDefinitionsProvider;

public class HCSoundDefinitionProvider extends SoundDefinitionsProvider {

    public HCSoundDefinitionProvider(PackOutput output, String modId, ExistingFileHelper helper) {
        super(output, modId, helper);
    }

    @Override
    public void registerSounds() {
        add(HCSoundEvents.FOCUS_CHARGING, SoundDefinition.definition()
                .with(
                        sound(HCSoundEvents.FOCUS_CHARGING.get().getLocation())
                                .stream()
                )
        );

        add(HCSoundEvents.FOCUS_HEAL, SoundDefinition.definition()
                .with(
                        sound(HCSoundEvents.FOCUS_HEAL.get().getLocation())
                )
        );
    }
}
