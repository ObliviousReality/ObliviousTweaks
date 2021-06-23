package com.tsaroblivious.oblivioustweaks.client.entity;

import com.tsaroblivious.oblivioustweaks.ObliviousTweaks;
import com.tsaroblivious.oblivioustweaks.client.entity.model.PirateModel;
import com.tsaroblivious.oblivioustweaks.common.entity.Pirate;

import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.HeldItemLayer;
import net.minecraft.util.ResourceLocation;

public class PirateRenderer extends MobRenderer<Pirate, PirateModel<Pirate>> {

	public static final ResourceLocation TEXTURE = new ResourceLocation(ObliviousTweaks.MOD_ID,
			"textures/entity/pirate/pirate.png");

	public PirateRenderer(EntityRendererManager manager) {
		super(manager, new PirateModel<>(), 0.7f);
		this.addLayer(new HeldItemLayer<>(this));
	}

	@Override
	public ResourceLocation getTextureLocation(Pirate p_110775_1_) {
		return TEXTURE;
	}

}
