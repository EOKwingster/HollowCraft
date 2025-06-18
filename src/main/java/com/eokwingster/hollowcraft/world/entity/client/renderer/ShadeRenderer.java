package com.eokwingster.hollowcraft.world.entity.client.renderer;

import com.eokwingster.hollowcraft.world.entity.client.HCModelLayers;
import com.eokwingster.hollowcraft.world.entity.client.model.ShadeModel;
import com.eokwingster.hollowcraft.world.entity.custom.ShadeEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

import static com.eokwingster.hollowcraft.HollowCraft.MODID;

public class ShadeRenderer extends MobRenderer<ShadeEntity, ShadeModel<ShadeEntity>> {
    private static final int DYING_TRANSPARENT_LENGTH = 20;

    public ShadeRenderer(EntityRendererProvider.Context pContext) {
        super(pContext, new ShadeModel<>(pContext.bakeLayer(HCModelLayers.SHADE_LAYER)), 0F);
    }

    @Override
    public ResourceLocation getTextureLocation(ShadeEntity pEntity) {
        return ResourceLocation.fromNamespaceAndPath(MODID, "textures/entity/shade.png");
    }
}
