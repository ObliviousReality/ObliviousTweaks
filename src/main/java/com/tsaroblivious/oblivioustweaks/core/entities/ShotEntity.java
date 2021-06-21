package com.tsaroblivious.oblivioustweaks.core.entities;

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

	final double baseDamage = 3f;

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
	}

//	@Override
//	protected void onHitEntity(EntityRayTraceResult p_213868_1_) {
//		super.onHitEntity(p_213868_1_);
//		Entity entity = p_213868_1_.getEntity();
//		float f = (float) this.getDeltaMovement().length();
//		int i = MathHelper.ceil(MathHelper.clamp((double) f * this.baseDamage, 0.0D, 2.147483647E9D));
//		if (this.getPierceLevel() > 0) {
//			if (this.piercingIgnoreEntityIds == null) {
//				this.piercingIgnoreEntityIds = new IntOpenHashSet(5);
//			}
//
//			if (this.piercedAndKilledEntities == null) {
//				this.piercedAndKilledEntities = Lists.newArrayListWithCapacity(5);
//			}
//
//			if (this.piercingIgnoreEntityIds.size() >= this.getPierceLevel() + 1) {
//				this.remove();
//				return;
//			}
//
//			this.piercingIgnoreEntityIds.add(entity.getId());
//		}
//
//		if (this.isCritArrow()) {
//			long j = (long) this.random.nextInt(i / 2 + 2);
//			i = (int) Math.min(j + (long) i, 2147483647L);
//		}
//
//		Entity entity1 = this.getOwner();
//		DamageSource damagesource;
//		if (entity1 == null) {
//			damagesource = DamageSource.arrow(this, this);
//		} else {
//			damagesource = DamageSource.arrow(this, entity1);
//			if (entity1 instanceof LivingEntity) {
//				((LivingEntity) entity1).setLastHurtMob(entity);
//			}
//		}
//
//		boolean flag = entity.getType() == EntityType.ENDERMAN;
//		int k = entity.getRemainingFireTicks();
//		if (this.isOnFire() && !flag) {
//			entity.setSecondsOnFire(5);
//		}
//
//		if (entity.hurt(damagesource, (float) i)) {
//			if (flag) {
//				return;
//			}
//
//			if (entity instanceof LivingEntity) {
//				LivingEntity livingentity = (LivingEntity) entity;
//				if (!this.level.isClientSide && this.getPierceLevel() <= 0) {
//					livingentity.setArrowCount(livingentity.getArrowCount() + 1);
//				}
//
//				if (this.knockback > 0) {
//					Vector3d vector3d = this.getDeltaMovement().multiply(1.0D, 0.0D, 1.0D).normalize()
//							.scale((double) this.knockback * 0.6D);
//					if (vector3d.lengthSqr() > 0.0D) {
//						livingentity.push(vector3d.x, 0.1D, vector3d.z);
//					}
//				}
//
//				if (!this.level.isClientSide && entity1 instanceof LivingEntity) {
//					EnchantmentHelper.doPostHurtEffects(livingentity, entity1);
//					EnchantmentHelper.doPostDamageEffects((LivingEntity) entity1, livingentity);
//				}
//
//				this.doPostHurtEffects(livingentity);
//				if (entity1 != null && livingentity != entity1 && livingentity instanceof PlayerEntity
//						&& entity1 instanceof ServerPlayerEntity && !this.isSilent()) {
//					((ServerPlayerEntity) entity1).connection
//							.send(new SChangeGameStatePacket(SChangeGameStatePacket.ARROW_HIT_PLAYER, 0.0F));
//				}
//
//				if (!entity.isAlive() && this.piercedAndKilledEntities != null) {
//					this.piercedAndKilledEntities.add(livingentity);
//				}
//
//				if (!this.level.isClientSide && entity1 instanceof ServerPlayerEntity) {
//					ServerPlayerEntity serverplayerentity = (ServerPlayerEntity) entity1;
//					if (this.piercedAndKilledEntities != null && this.shotFromCrossbow()) {
//						CriteriaTriggers.KILLED_BY_CROSSBOW.trigger(serverplayerentity, this.piercedAndKilledEntities);
//					} else if (!entity.isAlive() && this.shotFromCrossbow()) {
//						CriteriaTriggers.KILLED_BY_CROSSBOW.trigger(serverplayerentity, Arrays.asList(entity));
//					}
//				}
//			}
//
//			this.playSound(this.soundEvent, 1.0F, 1.2F / (this.random.nextFloat() * 0.2F + 0.9F));
//			if (this.getPierceLevel() <= 0) {
//				this.remove();
//			}
//		} else {
//			entity.setRemainingFireTicks(k);
//			this.setDeltaMovement(this.getDeltaMovement().scale(-0.1D));
//			this.yRot += 180.0F;
//			this.yRotO += 180.0F;
//			if (!this.level.isClientSide && this.getDeltaMovement().lengthSqr() < 1.0E-7D) {
//				if (this.pickup == AbstractArrowEntity.PickupStatus.ALLOWED) {
//					this.spawnAtLocation(this.getPickupItem(), 0.1F);
//				}
//
//				this.remove();
//			}
//		}
//
//	}
}
