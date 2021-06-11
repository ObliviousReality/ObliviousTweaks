package com.tsaroblivious.oblivioustweaks.client.entity;

import com.tsaroblivious.oblivioustweaks.ObliviousTweaks;
import com.tsaroblivious.oblivioustweaks.client.entity.model.VampireModel;
import com.tsaroblivious.oblivioustweaks.common.entity.Vampire;

import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;

public class VampireRenderer extends MobRenderer<Vampire, VampireModel<Vampire>> {

	public static final ResourceLocation TEXTURE = new ResourceLocation(ObliviousTweaks.MOD_ID,
			"textures/entity/vampire/vampire.png");

	public VampireRenderer(EntityRendererManager manager) {
		super(manager, new VampireModel<>(), 0.7f);
		// TODO Auto-generated constructor stub
	}

	@Override
	public ResourceLocation getTextureLocation(Vampire p_110775_1_) {
		return TEXTURE;
	}

}
