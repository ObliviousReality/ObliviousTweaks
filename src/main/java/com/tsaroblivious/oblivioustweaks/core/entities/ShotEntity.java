package com.tsaroblivious.oblivioustweaks.core.entities;

import com.tsaroblivious.oblivioustweaks.ObliviousTweaks;
import com.tsaroblivious.oblivioustweaks.core.init.EntityInit;
import com.tsaroblivious.oblivioustweaks.core.init.ItemInit;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.IPacket;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.network.NetworkHooks;

public class ShotEntity extends AbstractArrowEntity {

	public ShotEntity(EntityType<? extends AbstractArrowEntity> p_i50172_1_, World p_i50172_2_) {
		super(p_i50172_1_, p_i50172_2_);
		this.referenceItem = null;
	}

	public ShotEntity(LivingEntity shooter, World world, Item referenceItemIn) {
		super(EntityInit.SHOT_ENTITY.get(), shooter, world);
		this.referenceItem = referenceItemIn;
	}

	public ShotEntity(EntityType<? extends AbstractArrowEntity> p_i48547_1_, double p_i48547_2_, double p_i48547_4_,
			double p_i48547_6_, World p_i48547_8_) {
		super(p_i48547_1_, p_i48547_2_, p_i48547_4_, p_i48547_6_, p_i48547_8_);
		this.referenceItem = null;
	}

	public final Item referenceItem;

	@Override
	public ItemStack getPickupItem() {
		return new ItemStack(ItemInit.PISTOL_SHOT.get());
	}

	@Override
	public IPacket<?> getAddEntityPacket() {
		return NetworkHooks.getEntitySpawningPacket(this);
	}

	@Override
	public void tick() {
		super.tick();
		if (this.level.isClientSide) {
//			if (this.inGround) {
//				if (this.inGroundTime % 5 == 0) {
//					this.makeParticle(1);
//				}
//			} else {
//				this.makeParticle(2);
//			}
		} else if (this.inGround && this.inGroundTime != 0 && this.inGroundTime >= 600) {
			this.level.broadcastEntityEvent(this, (byte) 0);
		}

	}

//	private void makeParticle(int p_184556_1_) {
//		int i = this.getColor();
//		if (i != -1 && p_184556_1_ > 0) {
//			double d0 = (double) (i >> 16 & 255) / 255.0D;
//			double d1 = (double) (i >> 8 & 255) / 255.0D;
//			double d2 = (double) (i >> 0 & 255) / 255.0D;
//
//			for (int j = 0; j < p_184556_1_; ++j) {
//				this.level.addParticle(ParticleTypes.ENTITY_EFFECT, this.getRandomX(0.5D), this.getRandomY(),
//						this.getRandomZ(0.5D), d0, d1, d2);
//			}
//
//		}
//	}
//
//	public int getColor() {
//		return this.entityData.get(ID_EFFECT_COLOR);
//	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public void handleEntityEvent(byte p_70103_1_) {
//		if (p_70103_1_ == 0) {
//			int i = this.getColor();
//			if (i != -1) {
//				double d0 = (double) (i >> 16 & 255) / 255.0D;
//				double d1 = (double) (i >> 8 & 255) / 255.0D;
//				double d2 = (double) (i >> 0 & 255) / 255.0D;
//
//				for (int j = 0; j < 20; ++j) {
//					this.level.addParticle(ParticleTypes.ENTITY_EFFECT, this.getRandomX(0.5D), this.getRandomY(),
//							this.getRandomZ(0.5D), d0, d1, d2);
//				}
//			}
//		} else {
//		super.handleEntityEvent(p_70103_1_);
//		}

	}

	@Override
	protected void doPostHurtEffects(LivingEntity p_184548_1_) {
		super.doPostHurtEffects(p_184548_1_);
		ObliviousTweaks.LOGGER.debug(p_184548_1_.getHealth());
	}

}
