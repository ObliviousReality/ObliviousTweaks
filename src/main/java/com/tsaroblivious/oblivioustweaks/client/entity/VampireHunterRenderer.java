package com.tsaroblivious.oblivioustweaks.client.entity;

import com.tsaroblivious.oblivioustweaks.ObliviousTweaks;
import com.tsaroblivious.oblivioustweaks.client.entity.model.VampireHunterModel;
import com.tsaroblivious.oblivioustweaks.common.entity.VampireHunter;

import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;

public class VampireHunterRenderer extends MobRenderer<VampireHunter, VampireHunterModel<VampireHunter>> {

	public static final ResourceLocation TEXTURE = new ResourceLocation(ObliviousTweaks.MOD_ID,
			"textures/entity/vampire_hunter/vampire_hunter.png");

	public VampireHunterRenderer(EntityRendererManager manager) {
		super(manager, new VampireHunterModel<>(), 0.7f);
	}

	@Override
	public ResourceLocation getTextureLocation(VampireHunter p_110775_1_) {
		return TEXTURE;
	}

}
