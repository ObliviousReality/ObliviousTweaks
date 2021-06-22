package com.tsaroblivious.oblivioustweaks.client.entity.model;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class SpearModel extends EntityModel<Entity> {

	private final ModelRenderer spear;

	public SpearModel() {
		texWidth = 32;
		texHeight = 32;
		spear = new ModelRenderer(this);
//		spear.setPos(0.0F, 20.9F, 0.0F);
		setRotationAngle(spear, 0.0F, 0.0F, -3.1416F);
		spear.texOffs(0, 0).addBox(-0.5F, -27.0F, -0.5F, 1.0F, 25.0F, 1.0F, 0.0F, false);
		spear.texOffs(4, 0).addBox(-1.5F, -2.0F, -0.5F, 3.0F, 2.0F, 1.0F, 0.0F, false);
		spear.texOffs(8, 3).addBox(-0.5F, -2.0F, 0.5F, 1.0F, 2.0F, 1.0F, 0.0F, false);
		spear.texOffs(7, 7).addBox(-0.5F, -2.0F, -1.5F, 1.0F, 2.0F, 1.0F, 0.0F, false);
		spear.texOffs(4, 3).addBox(-0.5F, 0.0F, -0.5F, 1.0F, 4.0F, 1.0F, 0.0F, false);

	}

	@Override
	public void renderToBuffer(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay,
			float red, float green, float blue, float alpha) {
		spear.render(matrixStack, buffer, packedLight, packedOverlay);
	}

	public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.xRot = x;
		modelRenderer.yRot = y;
		modelRenderer.zRot = z;
	}

	@Override
	public void setupAnim(Entity p_225597_1_, float p_225597_2_, float p_225597_3_, float p_225597_4_,
			float p_225597_5_, float p_225597_6_) {
		// TODO Auto-generated method stub

	}

}