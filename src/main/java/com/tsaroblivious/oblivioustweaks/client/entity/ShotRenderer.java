package com.tsaroblivious.oblivioustweaks.client.entity;

import com.tsaroblivious.oblivioustweaks.ObliviousTweaks;
import com.tsaroblivious.oblivioustweaks.core.entities.ShotEntity;

import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
public class ShotRenderer extends ArrowRenderer<ShotEntity> {

	public static final ResourceLocation TEXTURE = new ResourceLocation(ObliviousTweaks.MOD_ID,
			"textures/entity/projectile/pistol_shot/pistol_shot.png");

	public ShotRenderer(EntityRendererManager p_i46193_1_) {
		super(p_i46193_1_);
	}

	@Override
	public ResourceLocation getTextureLocation(ShotEntity entity) {
		Item refItem = entity.getPickupItem().getItem();
		return new ResourceLocation(ObliviousTweaks.MOD_ID,
				"textures/entity/projectile/pistol_shot/" + refItem.getRegistryName().getPath() + ".png");
	}

}
