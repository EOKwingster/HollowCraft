package com.eokwingster.hollowcraft.world.entity.client.model;// Made with Blockbench 4.12.4
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports


import com.eokwingster.hollowcraft.world.entity.client.animations.ShadeAnimations;
import com.eokwingster.hollowcraft.world.entity.custom.ShadeEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;

import static com.eokwingster.hollowcraft.HollowCraft.MODID;

public class ShadeModel<T extends Entity> extends HierarchicalModel<T> {
	// This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(MODID, "shade_model"), "main");
	private final ModelPart root;
	private final ModelPart head;
	private final ModelPart mainhead;
	private final ModelPart antenna;
	private final ModelPart west_antenna;
	private final ModelPart east_antenna;
	private final ModelPart body;
	private final ModelPart tentacles;
	private final ModelPart north_tentacle;
	private final ModelPart north_east_tentacle;
	private final ModelPart east_tentacle;
	private final ModelPart south_east_tentacle;
	private final ModelPart south_tentacle;
	private final ModelPart south_west_tentacle;
	private final ModelPart west_tentacle;
	private final ModelPart north_west_tentacle;
	private final ModelPart chest;

	public ShadeModel(ModelPart root) {
		this.root = root.getChild("root");
		this.head = this.root.getChild("head");
		this.mainhead = this.head.getChild("mainhead");
		this.antenna = this.head.getChild("antenna");
		this.west_antenna = this.antenna.getChild("west_antenna");
		this.east_antenna = this.antenna.getChild("east_antenna");
		this.body = this.root.getChild("body");
		this.tentacles = this.body.getChild("tentacles");
		this.north_tentacle = this.tentacles.getChild("north_tentacle");
		this.north_east_tentacle = this.tentacles.getChild("north_east_tentacle");
		this.east_tentacle = this.tentacles.getChild("east_tentacle");
		this.south_east_tentacle = this.tentacles.getChild("south_east_tentacle");
		this.south_tentacle = this.tentacles.getChild("south_tentacle");
		this.south_west_tentacle = this.tentacles.getChild("south_west_tentacle");
		this.west_tentacle = this.tentacles.getChild("west_tentacle");
		this.north_west_tentacle = this.tentacles.getChild("north_west_tentacle");
		this.chest = this.body.getChild("chest");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offsetAndRotation(-0.5F, 12.0F, 0.0F, 0.0F, 3.1416F, 0.0F));

		PartDefinition head = root.addOrReplaceChild("head", CubeListBuilder.create(), PartPose.offset(0.0F, 1.0F, 0.0F));

		PartDefinition mainhead = head.addOrReplaceChild("mainhead", CubeListBuilder.create().texOffs(0, 0).addBox(-3.5F, -7.0F, -3.5F, 7.0F, 7.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition antenna = head.addOrReplaceChild("antenna", CubeListBuilder.create(), PartPose.offset(0.0F, -7.0F, 0.0F));

		PartDefinition west_antenna = antenna.addOrReplaceChild("west_antenna", CubeListBuilder.create().texOffs(26, 24).addBox(-0.6F, -2.2F, -1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(3.5F, 0.0F, 0.0F));

		PartDefinition upper_r1 = west_antenna.addOrReplaceChild("upper_r1", CubeListBuilder.create().texOffs(8, 25).addBox(-0.77F, -2.3F, -1.0F, 2.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -2.0F, 0.0F, 0.0F, 0.0F, -0.6545F));

		PartDefinition lower_r1 = west_antenna.addOrReplaceChild("lower_r1", CubeListBuilder.create().texOffs(0, 25).addBox(-1.0F, -1.0F, -1.0F, 2.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.637F));

		PartDefinition east_antenna = antenna.addOrReplaceChild("east_antenna", CubeListBuilder.create().texOffs(8, 14).addBox(-0.6F, -2.2F, -1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.5F, 0.0F, 0.0F, -3.1416F, 0.0F, 3.1416F));

		PartDefinition upper_r2 = east_antenna.addOrReplaceChild("upper_r2", CubeListBuilder.create().texOffs(26, 19).addBox(-0.77F, -2.3F, -1.0F, 2.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -2.0F, 0.0F, 0.0F, 0.0F, -0.6545F));

		PartDefinition lower_r2 = east_antenna.addOrReplaceChild("lower_r2", CubeListBuilder.create().texOffs(26, 14).addBox(-1.0F, -1.0F, -1.0F, 2.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.637F));

		PartDefinition body = root.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offset(0.0F, 1.0F, 0.0F));

		PartDefinition tentacles = body.addOrReplaceChild("tentacles", CubeListBuilder.create(), PartPose.offset(0.0F, 5.0F, 0.0F));

		PartDefinition north_tentacle = tentacles.addOrReplaceChild("north_tentacle", CubeListBuilder.create().texOffs(28, 10).addBox(-0.5F, 0.0F, 0.0F, 1.0F, 4.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 1.0F, -2.0F));

		PartDefinition north_east_tentacle = tentacles.addOrReplaceChild("north_east_tentacle", CubeListBuilder.create().texOffs(26, 28).addBox(0.0F, 0.0F, 0.0F, 1.0F, 5.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(-2.5F, 1.0F, -2.0F));

		PartDefinition east_r1 = north_east_tentacle.addOrReplaceChild("east_r1", CubeListBuilder.create().texOffs(28, 28).addBox(0.0F, -1.0F, 0.0F, 1.0F, 5.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 1.0F, 0.0F, 0.0F, -1.5708F, 0.0F));

		PartDefinition east_tentacle = tentacles.addOrReplaceChild("east_tentacle", CubeListBuilder.create().texOffs(28, 4).addBox(0.0F, 0.0F, -0.5F, 0.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(-2.5F, 0.0F, 0.0F));

		PartDefinition south_east_tentacle = tentacles.addOrReplaceChild("south_east_tentacle", CubeListBuilder.create().texOffs(20, 28).addBox(-1.0F, 0.0F, 0.0F, 1.0F, 5.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.5F, 0.0F, 2.0F, -3.1416F, 0.0F, 3.1416F));

		PartDefinition west_r1 = south_east_tentacle.addOrReplaceChild("west_r1", CubeListBuilder.create().texOffs(22, 28).addBox(-1.0F, 1.0F, 0.0F, 1.0F, 5.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -1.0F, 0.0F, 0.0F, 1.5272F, 0.0F));

		PartDefinition south_tentacle = tentacles.addOrReplaceChild("south_tentacle", CubeListBuilder.create().texOffs(4, 30).addBox(-0.5F, 0.0F, 0.0F, 1.0F, 3.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 1.0F, 2.0F));

		PartDefinition south_west_tentacle = tentacles.addOrReplaceChild("south_west_tentacle", CubeListBuilder.create().texOffs(0, 30).addBox(0.0F, 0.0F, 0.0F, 1.0F, 4.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.5F, 1.0F, 2.0F, -3.1416F, 0.0F, 3.1416F));

		PartDefinition east_r2 = south_west_tentacle.addOrReplaceChild("east_r2", CubeListBuilder.create().texOffs(2, 30).addBox(0.0F, -1.0F, 0.0F, 1.0F, 4.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 1.0F, 0.0F, 0.0F, -1.5708F, 0.0F));

		PartDefinition west_tentacle = tentacles.addOrReplaceChild("west_tentacle", CubeListBuilder.create().texOffs(24, 27).addBox(0.0F, 0.0F, -0.5F, 0.0F, 6.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(2.5F, 0.0F, 0.0F));

		PartDefinition north_west_tentacle = tentacles.addOrReplaceChild("north_west_tentacle", CubeListBuilder.create().texOffs(16, 28).addBox(-1.0F, 0.0F, 0.0F, 1.0F, 5.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(2.5F, 0.0F, -2.0F));

		PartDefinition west_r2 = north_west_tentacle.addOrReplaceChild("west_r2", CubeListBuilder.create().texOffs(18, 28).addBox(-1.0F, 1.0F, 0.0F, 1.0F, 5.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -1.0F, 0.0F, 0.0F, 1.5272F, 0.0F));

		PartDefinition chest = body.addOrReplaceChild("chest", CubeListBuilder.create().texOffs(16, 14).addBox(-2.5F, 0.0F, -2.0F, 5.0F, 7.0F, 0.0F, new CubeDeformation(0.0F))
		.texOffs(16, 21).addBox(-2.5F, 0.0F, 2.0F, 5.0F, 7.0F, 0.0F, new CubeDeformation(0.0F))
		.texOffs(0, 14).addBox(2.5F, 0.0F, -2.0F, 0.0F, 7.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(8, 14).addBox(-2.5F, 0.0F, -2.0F, 0.0F, 7.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(2, 30).addBox(-2.5F, 0.0F, -2.0F, 5.0F, 0.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 64, 64);
	}

	@Override
	public void setupAnim(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		root().getAllParts().forEach(ModelPart::resetPose);
		animate(((ShadeEntity) entity).idleAnimationState, ShadeAnimations.SHADE_IDLE, ageInTicks);
		animate(((ShadeEntity) entity).moveAnimationState, ShadeAnimations.SHADE_MOVE, ageInTicks);
		animate(((ShadeEntity) entity).dieAnimationState, ShadeAnimations.SHADE_DIE, ageInTicks);
	}

	@Override
	public void renderToBuffer(PoseStack pPoseStack, VertexConsumer pBuffer, int pPackedLight, int pPackedOverlay, int pColor) {
		root().render(pPoseStack, pBuffer, pPackedLight, pPackedOverlay, pColor);
	}

	@Override
	public ModelPart root() {
		return root;
	}
}