package com.tsaroblivious.oblivioustweaks.client.entity.model;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.model.Model;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.util.ResourceLocation;

public class SpearModel extends Model {

	public static final ResourceLocation TEXTURE = new ResourceLocation("oblivioustweaks:textures/entities/spear.png");
	private final ModelRenderer renderer = new ModelRenderer(16, 16, 0, 6);

	public SpearModel() {
		super(RenderType::entitySolid);
		this.renderer.setPos(0f, 0f, 0f);
		this.renderer.texOffs(12, 0).addBox(-8.0F, -1.0F, 6.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
		this.renderer.texOffs(12, 0).addBox(-7.0F, -1.0F, 5.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
		this.renderer.texOffs(12, 0).addBox(-7.0F, -1.0F, 7.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
		this.renderer.texOffs(12, 0).addBox(-6.0F, -1.0F, 6.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
		this.renderer.texOffs(12, 0).addBox(-6.0F, -1.0F, 4.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
		this.renderer.texOffs(3, 11).addBox(-5.0F, -1.0F, 3.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
		this.renderer.texOffs(3, 11).addBox(-4.0F, -1.0F, 2.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
		this.renderer.texOffs(3, 11).addBox(-3.0F, -1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
		this.renderer.texOffs(3, 11).addBox(-2.0F, -1.0F, 0.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
		this.renderer.texOffs(3, 11).addBox(-1.0F, -1.0F, -1.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
		this.renderer.texOffs(3, 11).addBox(0.0F, -1.0F, -2.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
		this.renderer.texOffs(3, 11).addBox(1.0F, -1.0F, -3.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
		this.renderer.texOffs(3, 11).addBox(3.0F, -1.0F, -5.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
		this.renderer.texOffs(3, 11).addBox(2.0F, -1.0F, -4.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
		this.renderer.texOffs(3, 11).addBox(4.0F, -1.0F, -6.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
		this.renderer.texOffs(3, 11).addBox(5.0F, -1.0F, -7.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
		this.renderer.texOffs(3, 11).addBox(6.0F, -1.0F, -8.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
		this.renderer.texOffs(12, 0).addBox(-5.0F, -1.0F, 5.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
		this.renderer.texOffs(3, 11).addBox(-4.0F, -1.0F, 4.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
		this.renderer.texOffs(3, 11).addBox(-3.0F, -1.0F, 3.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
		this.renderer.texOffs(3, 11).addBox(-2.0F, -1.0F, 2.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
		this.renderer.texOffs(3, 11).addBox(-1.0F, -1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
		this.renderer.texOffs(3, 11).addBox(0.0F, -1.0F, 0.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
		this.renderer.texOffs(3, 11).addBox(1.0F, -1.0F, -1.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
		this.renderer.texOffs(3, 11).addBox(2.0F, -1.0F, -2.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
		this.renderer.texOffs(3, 11).addBox(4.0F, -1.0F, -4.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
		this.renderer.texOffs(3, 11).addBox(3.0F, -1.0F, -3.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
		this.renderer.texOffs(3, 11).addBox(5.0F, -1.0F, -5.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
		this.renderer.texOffs(3, 11).addBox(6.0F, -1.0F, -6.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
		this.renderer.texOffs(3, 11).addBox(7.0F, -1.0F, -7.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
		this.renderer.texOffs(3, 11).addBox(6.0F, -1.0F, -7.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
		this.renderer.texOffs(3, 11).addBox(7.0F, -1.0F, -8.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
		this.renderer.texOffs(12, 0).addBox(-8.0F, -1.0F, 7.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
		this.renderer.texOffs(12, 0).addBox(-7.0F, -1.0F, 6.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
		this.renderer.texOffs(12, 0).addBox(-6.0F, -1.0F, 5.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
		this.renderer.texOffs(12, 0).addBox(-5.0F, -1.0F, 4.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
		this.renderer.texOffs(3, 11).addBox(-4.0F, -1.0F, 3.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
		this.renderer.texOffs(3, 11).addBox(-3.0F, -1.0F, 2.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
		this.renderer.texOffs(3, 11).addBox(-2.0F, -1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
		this.renderer.texOffs(3, 11).addBox(-1.0F, -1.0F, 0.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
		this.renderer.texOffs(3, 11).addBox(0.0F, -1.0F, -1.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
		this.renderer.texOffs(3, 11).addBox(1.0F, -1.0F, -2.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
		this.renderer.texOffs(3, 11).addBox(2.0F, -1.0F, -3.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
		this.renderer.texOffs(3, 11).addBox(3.0F, -1.0F, -4.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
		this.renderer.texOffs(3, 11).addBox(4.0F, -1.0F, -5.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
		this.renderer.texOffs(3, 11).addBox(5.0F, -1.0F, -6.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);

	}

	@Override
	public void renderToBuffer(MatrixStack a, IVertexBuilder b, int c, int d, float e, float f, float g, float h) {
		this.renderer.render(a, b, c, d, e, f, g, h);
	}

}