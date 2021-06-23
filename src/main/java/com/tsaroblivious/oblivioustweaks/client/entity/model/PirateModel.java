package com.tsaroblivious.oblivioustweaks.client.entity.model;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.tsaroblivious.oblivioustweaks.common.entity.Pirate;

import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.entity.model.IHasArm;
import net.minecraft.client.renderer.entity.model.IHasHead;
import net.minecraft.client.renderer.model.ModelHelper;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.monster.AbstractIllagerEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.HandSide;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class PirateModel<T extends Pirate> extends EntityModel<T> implements IHasArm, IHasHead {

	private final ModelRenderer head;
	private final ModelRenderer hat;
	private final ModelRenderer nose;
	private final ModelRenderer body;
	private final ModelRenderer left_arm;
	private final ModelRenderer right_arm;
	private final ModelRenderer left_leg;
	private final ModelRenderer right_leg;

	public BipedModel.ArmPose left_armPose = BipedModel.ArmPose.EMPTY;
	public BipedModel.ArmPose right_armPose = BipedModel.ArmPose.EMPTY;

	public float swimAmount;
	public boolean crouching;

	public PirateModel() {
		texWidth = 64;
		texHeight = 64;

		head = new ModelRenderer(this);
		head.setPos(0.0F, 0.0F, 0.0F);
		head.texOffs(0, 0).addBox(-4.0F, -10.0F, -4.0F, 8.0F, 10.0F, 8.0F, 0.0F, false);
		head.texOffs(32, 8).addBox(-3.0F, -2.0F, -5.0F, 6.0F, 6.0F, 1.0F, 0.0F, false);
		head.texOffs(32, 8).addBox(-2.0F, 4.0F, -5.0F, 4.0F, 1.0F, 1.0F, 0.0F, false);
		head.texOffs(28, 46).addBox(-1.0F, -9.0F, -10.0F, 2.0F, 1.0F, 16.0F, 0.0F, false);
		head.texOffs(28, 46).addBox(1.0F, -9.0F, -9.0F, 1.0F, 1.0F, 15.0F, 0.0F, false);
		head.texOffs(28, 46).addBox(-2.0F, -9.0F, -9.0F, 1.0F, 1.0F, 15.0F, 0.0F, false);
		head.texOffs(28, 46).addBox(-3.0F, -9.0F, -8.0F, 1.0F, 1.0F, 14.0F, 0.0F, false);
		head.texOffs(28, 46).addBox(2.0F, -9.0F, -8.0F, 1.0F, 1.0F, 14.0F, 0.0F, false);
		head.texOffs(28, 46).addBox(3.0F, -9.0F, -7.0F, 1.0F, 1.0F, 13.0F, 0.0F, false);
		head.texOffs(28, 46).addBox(-4.0F, -9.0F, -7.0F, 1.0F, 1.0F, 13.0F, 0.0F, false);
		head.texOffs(28, 46).addBox(-5.0F, -9.0F, -6.0F, 1.0F, 1.0F, 11.0F, 0.0F, false);
		head.texOffs(28, 46).addBox(4.0F, -9.0F, -6.0F, 1.0F, 1.0F, 11.0F, 0.0F, false);
		head.texOffs(28, 46).addBox(5.0F, -9.0F, -5.0F, 1.0F, 1.0F, 10.0F, 0.0F, false);
		head.texOffs(28, 46).addBox(6.0F, -9.0F, -4.0F, 1.0F, 1.0F, 9.0F, 0.0F, false);
		head.texOffs(28, 46).addBox(-7.0F, -9.0F, -4.0F, 1.0F, 1.0F, 9.0F, 0.0F, false);
		head.texOffs(28, 46).addBox(-8.0F, -9.0F, -3.0F, 1.0F, 1.0F, 8.0F, 0.0F, false);
		head.texOffs(28, 46).addBox(7.0F, -9.0F, -3.0F, 1.0F, 1.0F, 8.0F, 0.0F, false);
		head.texOffs(28, 46).addBox(-6.0F, -9.0F, -5.0F, 1.0F, 1.0F, 10.0F, 0.0F, false);

		hat = new ModelRenderer(this);
		hat.setPos(0.0F, 24.0F, 0.0F);

		nose = new ModelRenderer(this);
		nose.setPos(0.0F, -2.0F, 0.0F);
		nose.texOffs(25, 1).addBox(-1.0F, -1.0F, -6.0F, 2.0F, 4.0F, 1.0F, 0.0F, false);
		nose.texOffs(25, 1).addBox(-1.0F, -1.0F, -5.0F, 2.0F, 1.0F, 1.0F, 0.0F, false);
		this.head.addChild(nose);

		body = new ModelRenderer(this);
		body.setPos(0.0F, 0.0F, 0.0F);
		body.texOffs(16, 20).addBox(-4.0F, 0.0F, -3.0F, 8.0F, 12.0F, 6.0F, 0.0F, false);
		body.texOffs(0, 38).addBox(-4.0F, 0.0F, -3.0F, 8.0F, 18.0F, 6.0F, 0.5F, false);

		left_arm = new ModelRenderer(this);
		left_arm.setPos(-5.0F, 2.0F, 0.0F);
		left_arm.texOffs(44, 20).addBox(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.0F, true);

		right_arm = new ModelRenderer(this);
		right_arm.setPos(5.0F, 2.0F, 0.0F);
		right_arm.texOffs(44, 20).addBox(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.0F, false);

		left_leg = new ModelRenderer(this);
		left_leg.setPos(-2.0F, 12.0F, 0.0F);
		left_leg.texOffs(0, 22).addBox(2.0F, 0.0F, -2.0F, 4.0F, 8.0F, 4.0F, 0.0F, true);
		left_leg.texOffs(37, 38).addBox(3.0F, 8.0F, -1.0F, 2.0F, 4.0F, 2.0F, 0.0F, false);

		right_leg = new ModelRenderer(this);
		right_leg.setPos(2.0F, 12.0F, 0.0F);
		right_leg.texOffs(0, 22).addBox(-6.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.0F, false);
		right_leg.texOffs(37, 38).addBox(-7.0F, 7.0F, -3.0F, 5.0F, 2.0F, 6.0F, 0.0F, false);

	}

	@Override
	public void renderToBuffer(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay,
			float red, float green, float blue, float alpha) {
		head.render(matrixStack, buffer, packedLight, packedOverlay);
		hat.render(matrixStack, buffer, packedLight, packedOverlay);
		body.render(matrixStack, buffer, packedLight, packedOverlay);
		left_arm.render(matrixStack, buffer, packedLight, packedOverlay);
		right_arm.render(matrixStack, buffer, packedLight, packedOverlay);
		left_leg.render(matrixStack, buffer, packedLight, packedOverlay);
		right_leg.render(matrixStack, buffer, packedLight, packedOverlay);
	}

	public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.xRot = x;
		modelRenderer.yRot = y;
		modelRenderer.zRot = z;
	}

	@Override
	public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw,
			float headPitch) {
		boolean flag = entity.getFallFlyingTicks() > 4;
		boolean flag1 = entity.isVisuallySwimming();
		this.head.yRot = netHeadYaw * ((float) Math.PI / 180F);
		if (flag) {
			this.head.xRot = (-(float) Math.PI / 4F);
		} else if (this.swimAmount > 0.0F) {
			if (flag1) {
				this.head.xRot = this.rotlerpRad(this.swimAmount, this.head.xRot, (-(float) Math.PI / 4F));
			} else {
				this.head.xRot = this.rotlerpRad(this.swimAmount, this.head.xRot, headPitch * ((float) Math.PI / 180F));
			}
		} else {
			this.head.xRot = headPitch * ((float) Math.PI / 180F);
		}

		this.body.yRot = 0.0F;
		this.right_arm.z = 0.0F;
		this.right_arm.x = -5.0F;
		this.left_arm.z = 0.0F;
		this.left_arm.x = 5.0F;
		float f = 1.0F;
		if (flag) {
			f = (float) entity.getDeltaMovement().lengthSqr();
			f = f / 0.2F;
			f = f * f * f;
		}

		if (f < 1.0F) {
			f = 1.0F;
		}

		this.right_arm.xRot = MathHelper.cos(limbSwing * 0.6662F + (float) Math.PI) * 2.0F * limbSwingAmount * 0.5F / f;
		this.left_arm.xRot = MathHelper.cos(limbSwing * 0.6662F) * 2.0F * limbSwingAmount * 0.5F / f;
		this.right_arm.zRot = 0.0F;
		this.left_arm.zRot = 0.0F;
		this.right_leg.xRot = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount / f;
		this.left_leg.xRot = MathHelper.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.4F * limbSwingAmount / f;
		this.right_leg.yRot = 0.0F;
		this.left_leg.yRot = 0.0F;
		this.right_leg.zRot = 0.0F;
		this.left_leg.zRot = 0.0F;
		if (this.riding) {
			this.right_arm.xRot += (-(float) Math.PI / 5F);
			this.left_arm.xRot += (-(float) Math.PI / 5F);
			this.right_leg.xRot = -1.4137167F;
			this.right_leg.yRot = ((float) Math.PI / 10F);
			this.right_leg.zRot = 0.07853982F;
			this.left_leg.xRot = -1.4137167F;
			this.left_leg.yRot = (-(float) Math.PI / 10F);
			this.left_leg.zRot = -0.07853982F;
		}

		this.right_arm.yRot = 0.0F;
		this.left_arm.yRot = 0.0F;
		boolean flag2 = entity.getMainArm() == HandSide.RIGHT;
		boolean flag3 = flag2 ? this.left_armPose.isTwoHanded() : this.right_armPose.isTwoHanded();
		if (flag2 != flag3) {
			this.poseleft_arm(entity);
			this.poseright_arm(entity);
		} else {
			this.poseright_arm(entity);
			this.poseleft_arm(entity);
		}

		this.setupAttackAnimation(entity, ageInTicks);
		if (this.crouching) {
			this.body.xRot = 0.5F;
			this.right_arm.xRot += 0.4F;
			this.left_arm.xRot += 0.4F;
			this.right_leg.z = 4.0F;
			this.left_leg.z = 4.0F;
			this.right_leg.y = 12.2F;
			this.left_leg.y = 12.2F;
			this.head.y = 4.2F;
			this.body.y = 3.2F;
			this.left_arm.y = 5.2F;
			this.right_arm.y = 5.2F;
		} else {
			this.body.xRot = 0.0F;
			this.right_leg.z = 0.1F;
			this.left_leg.z = 0.1F;
			this.right_leg.y = 12.0F;
			this.left_leg.y = 12.0F;
			this.head.y = 0.0F;
			this.body.y = 0.0F;
			this.left_arm.y = 2.0F;
			this.right_arm.y = 2.0F;
		}

		ModelHelper.bobArms(this.right_arm, this.left_arm, ageInTicks);
		if (this.swimAmount > 0.0F) {
			float f1 = limbSwing % 26.0F;
			HandSide handside = this.getAttackArm(entity);
			float f2 = handside == HandSide.RIGHT && this.attackTime > 0.0F ? 0.0F : this.swimAmount;
			float f3 = handside == HandSide.LEFT && this.attackTime > 0.0F ? 0.0F : this.swimAmount;
			if (f1 < 14.0F) {
				this.left_arm.xRot = this.rotlerpRad(f3, this.left_arm.xRot, 0.0F);
				this.right_arm.xRot = MathHelper.lerp(f2, this.right_arm.xRot, 0.0F);
				this.left_arm.yRot = this.rotlerpRad(f3, this.left_arm.yRot, (float) Math.PI);
				this.right_arm.yRot = MathHelper.lerp(f2, this.right_arm.yRot, (float) Math.PI);
				this.left_arm.zRot = this.rotlerpRad(f3, this.left_arm.zRot,
						(float) Math.PI + 1.8707964F * this.quadraticArmUpdate(f1) / this.quadraticArmUpdate(14.0F));
				this.right_arm.zRot = MathHelper.lerp(f2, this.right_arm.zRot,
						(float) Math.PI - 1.8707964F * this.quadraticArmUpdate(f1) / this.quadraticArmUpdate(14.0F));
			} else if (f1 >= 14.0F && f1 < 22.0F) {
				float f6 = (f1 - 14.0F) / 8.0F;
				this.left_arm.xRot = this.rotlerpRad(f3, this.left_arm.xRot, ((float) Math.PI / 2F) * f6);
				this.right_arm.xRot = MathHelper.lerp(f2, this.right_arm.xRot, ((float) Math.PI / 2F) * f6);
				this.left_arm.yRot = this.rotlerpRad(f3, this.left_arm.yRot, (float) Math.PI);
				this.right_arm.yRot = MathHelper.lerp(f2, this.right_arm.yRot, (float) Math.PI);
				this.left_arm.zRot = this.rotlerpRad(f3, this.left_arm.zRot, 5.012389F - 1.8707964F * f6);
				this.right_arm.zRot = MathHelper.lerp(f2, this.right_arm.zRot, 1.2707963F + 1.8707964F * f6);
			} else if (f1 >= 22.0F && f1 < 26.0F) {
				float f4 = (f1 - 22.0F) / 4.0F;
				this.left_arm.xRot = this.rotlerpRad(f3, this.left_arm.xRot,
						((float) Math.PI / 2F) - ((float) Math.PI / 2F) * f4);
				this.right_arm.xRot = MathHelper.lerp(f2, this.right_arm.xRot,
						((float) Math.PI / 2F) - ((float) Math.PI / 2F) * f4);
				this.left_arm.yRot = this.rotlerpRad(f3, this.left_arm.yRot, (float) Math.PI);
				this.right_arm.yRot = MathHelper.lerp(f2, this.right_arm.yRot, (float) Math.PI);
				this.left_arm.zRot = this.rotlerpRad(f3, this.left_arm.zRot, (float) Math.PI);
				this.right_arm.zRot = MathHelper.lerp(f2, this.right_arm.zRot, (float) Math.PI);
			}

//			float f7 = 0.3F;
//			float f5 = 0.33333334F;
			this.left_leg.xRot = MathHelper.lerp(this.swimAmount, this.left_leg.xRot,
					0.3F * MathHelper.cos(limbSwing * 0.33333334F + (float) Math.PI));
			this.right_leg.xRot = MathHelper.lerp(this.swimAmount, this.right_leg.xRot,
					0.3F * MathHelper.cos(limbSwing * 0.33333334F));
		}

		this.hat.copyFrom(this.head);

		AbstractIllagerEntity.ArmPose abstractillagerentity$armpose = entity.getArmPose();
		if (abstractillagerentity$armpose == AbstractIllagerEntity.ArmPose.ATTACKING) {
			if (entity.getMainHandItem().isEmpty()) {
				ModelHelper.animateZombieArms(this.left_arm, this.right_arm, true, this.attackTime, ageInTicks);
			} else {
				ModelHelper.swingWeaponDown(this.right_arm, this.left_arm, entity, this.attackTime, ageInTicks);
			}
		} else if (abstractillagerentity$armpose == AbstractIllagerEntity.ArmPose.CROSSBOW_HOLD) {
			ModelHelper.animateCrossbowHold(this.right_arm, this.left_arm, this.head, true);
		} else if (abstractillagerentity$armpose == AbstractIllagerEntity.ArmPose.CROSSBOW_CHARGE) {
			ModelHelper.animateCrossbowCharge(this.right_arm, this.left_arm, entity, true);
		} else {
			this.left_arm.xRot = 0;
			this.left_arm.yRot = 0;
			this.left_arm.xRot = 0;
			this.right_arm.xRot = 0;
			this.right_arm.yRot = 0;
			this.right_arm.zRot = 0;
		}
	}

	protected float rotlerpRad(float p_205060_1_, float p_205060_2_, float p_205060_3_) {
		float f = (p_205060_3_ - p_205060_2_) % ((float) Math.PI * 2F);
		if (f < -(float) Math.PI) {
			f += ((float) Math.PI * 2F);
		}

		if (f >= (float) Math.PI) {
			f -= ((float) Math.PI * 2F);
		}

		return p_205060_2_ + p_205060_1_ * f;
	}

	private void poseright_arm(T p_241654_1_) {
		switch (this.right_armPose) {
		case EMPTY:
			this.right_arm.yRot = 0.0F;
			break;
		case BLOCK:
			this.right_arm.xRot = this.right_arm.xRot * 0.5F - 0.9424779F;
			this.right_arm.yRot = (-(float) Math.PI / 6F);
			break;
		case ITEM:
			this.right_arm.xRot = this.right_arm.xRot * 0.5F - ((float) Math.PI / 10F);
			this.right_arm.yRot = 0.0F;
			break;
		case THROW_SPEAR:
			this.right_arm.xRot = this.right_arm.xRot * 0.5F - (float) Math.PI;
			this.right_arm.yRot = 0.0F;
			break;
		case BOW_AND_ARROW:
			this.right_arm.yRot = -0.1F + this.head.yRot;
			this.left_arm.yRot = 0.1F + this.head.yRot + 0.4F;
			this.right_arm.xRot = (-(float) Math.PI / 2F) + this.head.xRot;
			this.left_arm.xRot = (-(float) Math.PI / 2F) + this.head.xRot;
			break;
		case CROSSBOW_CHARGE:
			ModelHelper.animateCrossbowCharge(this.right_arm, this.left_arm, p_241654_1_, true);
			break;
		case CROSSBOW_HOLD:
			ModelHelper.animateCrossbowHold(this.right_arm, this.left_arm, this.head, true);
		}

	}

	private void poseleft_arm(T p_241655_1_) {
		switch (this.left_armPose) {
		case EMPTY:
			this.left_arm.yRot = 0.0F;
			break;
		case BLOCK:
			this.left_arm.xRot = this.left_arm.xRot * 0.5F - 0.9424779F;
			this.left_arm.yRot = ((float) Math.PI / 6F);
			break;
		case ITEM:
			this.left_arm.xRot = this.left_arm.xRot * 0.5F - ((float) Math.PI / 10F);
			this.left_arm.yRot = 0.0F;
			break;
		case THROW_SPEAR:
			this.left_arm.xRot = this.left_arm.xRot * 0.5F - (float) Math.PI;
			this.left_arm.yRot = 0.0F;
			break;
		case BOW_AND_ARROW:
			this.right_arm.yRot = -0.1F + this.head.yRot - 0.4F;
			this.left_arm.yRot = 0.1F + this.head.yRot;
			this.right_arm.xRot = (-(float) Math.PI / 2F) + this.head.xRot;
			this.left_arm.xRot = (-(float) Math.PI / 2F) + this.head.xRot;
			break;
		case CROSSBOW_CHARGE:
			ModelHelper.animateCrossbowCharge(this.right_arm, this.left_arm, p_241655_1_, false);
			break;
		case CROSSBOW_HOLD:
			ModelHelper.animateCrossbowHold(this.right_arm, this.left_arm, this.head, false);
		}

	}

	protected void setupAttackAnimation(T p_230486_1_, float p_230486_2_) {
		if (!(this.attackTime <= 0.0F)) {
			HandSide handside = this.getAttackArm(p_230486_1_);
			ModelRenderer modelrenderer = this.getArm(handside);
			float f = this.attackTime;
			this.body.yRot = MathHelper.sin(MathHelper.sqrt(f) * ((float) Math.PI * 2F)) * 0.2F;
			if (handside == HandSide.LEFT) {
				this.body.yRot *= -1.0F;
			}

			this.right_arm.z = MathHelper.sin(this.body.yRot) * 5.0F;
			this.right_arm.x = -MathHelper.cos(this.body.yRot) * 5.0F;
			this.left_arm.z = -MathHelper.sin(this.body.yRot) * 5.0F;
			this.left_arm.x = MathHelper.cos(this.body.yRot) * 5.0F;
			this.right_arm.yRot += this.body.yRot;
			this.left_arm.yRot += this.body.yRot;
			this.left_arm.xRot += this.body.yRot;
			f = 1.0F - this.attackTime;
			f = f * f;
			f = f * f;
			f = 1.0F - f;
			float f1 = MathHelper.sin(f * (float) Math.PI);
			float f2 = MathHelper.sin(this.attackTime * (float) Math.PI) * -(this.head.xRot - 0.7F) * 0.75F;
			modelrenderer.xRot = (float) ((double) modelrenderer.xRot - ((double) f1 * 1.2D + (double) f2));
			modelrenderer.yRot += this.body.yRot * 2.0F;
			modelrenderer.zRot += MathHelper.sin(this.attackTime * (float) Math.PI) * -0.4F;
		}
	}

	public void translateToHand(HandSide p_225599_1_, MatrixStack p_225599_2_) {
		this.getArm(p_225599_1_).translateAndRotate(p_225599_2_);
	}

	protected ModelRenderer getArm(HandSide p_187074_1_) {
		return p_187074_1_ == HandSide.LEFT ? this.left_arm : this.right_arm;
	}

	public ModelRenderer getHead() {
		return this.head;
	}

	protected HandSide getAttackArm(T p_217147_1_) {
		HandSide handside = p_217147_1_.getMainArm();
		return p_217147_1_.swingingArm == Hand.MAIN_HAND ? handside : handside.getOpposite();
	}

	@OnlyIn(Dist.CLIENT)
	public static enum ArmPose {
		EMPTY(false), ITEM(false), BLOCK(false), BOW_AND_ARROW(true), THROW_SPEAR(false), CROSSBOW_CHARGE(true),
		CROSSBOW_HOLD(true);

		private final boolean twoHanded;

		private ArmPose(boolean p_i241257_3_) {
			this.twoHanded = p_i241257_3_;
		}

		public boolean isTwoHanded() {
			return this.twoHanded;
		}
	}

	private float quadraticArmUpdate(float p_203068_1_) {
		return -65.0F * p_203068_1_ + p_203068_1_ * p_203068_1_;
	}

}
