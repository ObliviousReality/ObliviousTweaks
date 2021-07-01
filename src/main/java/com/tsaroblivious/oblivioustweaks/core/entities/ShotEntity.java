package com.tsaroblivious.oblivioustweaks.core.entities;

import java.util.Arrays;
import java.util.List;

import com.google.common.collect.Lists;
import com.tsaroblivious.oblivioustweaks.core.init.EntityInit;
import com.tsaroblivious.oblivioustweaks.core.init.ItemInit;

import it.unimi.dsi.fastutil.ints.IntOpenHashSet;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.TNTBlock;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.network.IPacket;
import net.minecraft.network.play.server.SChangeGameStatePacket;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.network.NetworkHooks;

public class ShotEntity extends AbstractArrowEntity {

	double baseDamage = 3f;

	private int life;

	private SoundEvent soundEvent = this.getDefaultHitGroundSoundEvent();

	private List<Entity> piercedAndKilledEntities;
	private IntOpenHashSet piercingIgnoreEntityIds;
	private BlockState lastState;

	private int knockback;

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
	protected void doPostHurtEffects(LivingEntity p_184548_1_) {
		super.doPostHurtEffects(p_184548_1_);
	}

	@Override
	protected void onHitBlock(BlockRayTraceResult p_230299_1_) {
		this.lastState = this.level.getBlockState(p_230299_1_.getBlockPos());
//		super.onHitBlock(p_230299_1_);
		Vector3d vector3d = p_230299_1_.getLocation().subtract(this.getX(), this.getY(), this.getZ());
		this.setDeltaMovement(vector3d);
		Vector3d vector3d1 = vector3d.normalize().scale((double) 0.05F);
		this.setPosRaw(this.getX() - vector3d1.x, this.getY() - vector3d1.y, this.getZ() - vector3d1.z);
//		this.playSound(this.getHitGroundSoundEvent(), 1.0F, 1.2F / (this.random.nextFloat() * 0.2F + 0.9F));
		this.inGround = true;
		if (this.level.getBlockState(p_230299_1_.getBlockPos()) == Blocks.TNT.defaultBlockState()) {
			this.level.setBlock(p_230299_1_.getBlockPos(), lastState.setValue(TNTBlock.UNSTABLE, true), 3);
		}
		this.shakeTime = 7;
		this.setCritArrow(false);
		this.setPierceLevel((byte) 0);
		this.setSoundEvent(SoundEvents.ARROW_HIT);
		this.setShotFromCrossbow(false);
		this.resetPiercedEntities();
//		this.kill();
	}

	private void resetPiercedEntities() {
		if (this.piercedAndKilledEntities != null) {
			this.piercedAndKilledEntities.clear();
		}

		if (this.piercingIgnoreEntityIds != null) {
			this.piercingIgnoreEntityIds.clear();
		}

	}

	@SuppressWarnings("deprecation")
	public void tick() {
//		super.tick();
		boolean flag = this.isNoPhysics();
		Vector3d vector3d = this.getDeltaMovement();
		if (this.xRotO == 0.0F && this.yRotO == 0.0F) {
			float f = MathHelper.sqrt(getHorizontalDistanceSqr(vector3d));
			this.yRot = (float) (MathHelper.atan2(vector3d.x, vector3d.z) * (double) (180F / (float) Math.PI));
			this.xRot = (float) (MathHelper.atan2(vector3d.y, (double) f) * (double) (180F / (float) Math.PI));
			this.yRotO = this.yRot;
			this.xRotO = this.xRot;
		}

		BlockPos blockpos = this.blockPosition();
		BlockState blockstate = this.level.getBlockState(blockpos);
		if (!blockstate.isAir(this.level, blockpos) && !flag) {
			VoxelShape voxelshape = blockstate.getCollisionShape(this.level, blockpos);
			if (!voxelshape.isEmpty()) {
				Vector3d vector3d1 = this.position();

				for (AxisAlignedBB axisalignedbb : voxelshape.toAabbs()) {
					if (axisalignedbb.move(blockpos).contains(vector3d1)) {
						this.inGround = true;
						break;
					}
				}
			}
		}

		if (this.shakeTime > 0) {
			--this.shakeTime;
			if (this.shakeTime == 0) {
				this.kill();
			}

		}
		if (this.isInWaterOrRain()) {
			this.clearFire();
		}

		if (this.inGround && !flag) {
			if (this.lastState != blockstate && this.shouldFall()) {
				this.startFalling();
			} else if (!this.level.isClientSide) {
				this.tickDespawn();
			}

			++this.inGroundTime;
		} else {
			this.inGroundTime = 0;
			Vector3d vector3d2 = this.position();
			Vector3d vector3d3 = vector3d2.add(vector3d);
			RayTraceResult raytraceresult = this.level.clip(new RayTraceContext(vector3d2, vector3d3,
					RayTraceContext.BlockMode.COLLIDER, RayTraceContext.FluidMode.NONE, this));
			if (raytraceresult.getType() != RayTraceResult.Type.MISS) {
				vector3d3 = raytraceresult.getLocation();
			}

			while (!this.removed) {
				EntityRayTraceResult entityraytraceresult = this.findHitEntity(vector3d2, vector3d3);
				if (entityraytraceresult != null) {
					raytraceresult = entityraytraceresult;
				}

				if (raytraceresult != null && raytraceresult.getType() == RayTraceResult.Type.ENTITY) {
					Entity entity = ((EntityRayTraceResult) raytraceresult).getEntity();
					Entity entity1 = this.getOwner();
					if (entity instanceof PlayerEntity && entity1 instanceof PlayerEntity
							&& !((PlayerEntity) entity1).canHarmPlayer((PlayerEntity) entity)) {
						raytraceresult = null;
						entityraytraceresult = null;
					}
				}

				if (raytraceresult != null && raytraceresult.getType() != RayTraceResult.Type.MISS && !flag
						&& !net.minecraftforge.event.ForgeEventFactory.onProjectileImpact(this, raytraceresult)) {
					this.onHit(raytraceresult);
					this.hasImpulse = true;
				}

				if (entityraytraceresult == null || this.getPierceLevel() <= 0) {
					break;
				}

				raytraceresult = null;
			}

			vector3d = this.getDeltaMovement();
			double d3 = vector3d.x;
			double d4 = vector3d.y;
			double d0 = vector3d.z;
			if (this.isCritArrow()) {
				for (int i = 0; i < 4; ++i) {
//					this.level.addParticle(ParticleTypes.SMOKE, this.getX() + d3 * (double) i / 4.0D,
//							this.getY() + d4 * (double) i / 4.0D, this.getZ() + d0 * (double) i / 4.0D, -d3, -d4 + 0.2D,
//							-d0);
					this.level.addParticle(ParticleTypes.SMOKE, this.getX() + d3 * (double) i / 4.0D,
							this.getY() + d4 * (double) i / 4.0D, this.getZ() + d0 * (double) i / 4.0D, 0, 0,
							0);
				}
			}

			double d5 = this.getX() + d3;
			double d1 = this.getY() + d4;
			double d2 = this.getZ() + d0;
			float f1 = MathHelper.sqrt(getHorizontalDistanceSqr(vector3d));
			if (flag) {
				this.yRot = (float) (MathHelper.atan2(-d3, -d0) * (double) (180F / (float) Math.PI));
			} else {
				this.yRot = (float) (MathHelper.atan2(d3, d0) * (double) (180F / (float) Math.PI));
			}

			this.xRot = (float) (MathHelper.atan2(d4, (double) f1) * (double) (180F / (float) Math.PI));
			this.xRot = lerpRotation(this.xRotO, this.xRot);
			this.yRot = lerpRotation(this.yRotO, this.yRot);
			float f2 = 0.99F;
//			float f3 = 0.05F;
			if (this.isInWater()) {
				for (int j = 0; j < 4; ++j) {
//					float f4 = 0.25F;
					this.level.addParticle(ParticleTypes.BUBBLE, d5 - d3 * 0.25D, d1 - d4 * 0.25D, d2 - d0 * 0.25D, d3,
							d4, d0);
				}

				f2 = this.getWaterInertia();
			}

			this.setDeltaMovement(vector3d.scale((double) f2));
			if (!this.isNoGravity() && !flag) {
				Vector3d vector3d4 = this.getDeltaMovement();
				this.setDeltaMovement(vector3d4.x, vector3d4.y - (double) 0.05F, vector3d4.z);
			}

			this.setPos(d5, d1, d2);
			this.checkInsideBlocks();
		}
	}

	// \/\/\/ BEGIN DUMP \/\/\/

	private boolean shouldFall() {
		return this.inGround
				&& this.level.noCollision((new AxisAlignedBB(this.position(), this.position())).inflate(0.06D));
	}

	private void startFalling() {
		this.inGround = false;
		Vector3d vector3d = this.getDeltaMovement();
		this.setDeltaMovement(vector3d.multiply((double) (this.random.nextFloat() * 0.2F),
				(double) (this.random.nextFloat() * 0.2F), (double) (this.random.nextFloat() * 0.2F)));
		this.life = 0;
	}

	public void shoot(double p_70186_1_, double p_70186_3_, double p_70186_5_, float p_70186_7_, float p_70186_8_) {
		super.shoot(p_70186_1_, p_70186_3_, p_70186_5_, p_70186_7_, p_70186_8_);
		this.life = 0;
	}

	@OnlyIn(Dist.CLIENT)
	public void lerpMotion(double p_70016_1_, double p_70016_3_, double p_70016_5_) {
		super.lerpMotion(p_70016_1_, p_70016_3_, p_70016_5_);
		this.life = 0;
	}

	protected void tickDespawn() {
		++this.life;
		if (this.life >= 1200) {
			this.remove();
		}

	}

	@SuppressWarnings("deprecation")
	public void addAdditionalSaveData(CompoundNBT p_213281_1_) {
		super.addAdditionalSaveData(p_213281_1_);
		p_213281_1_.putShort("life", (short) this.life);
		if (this.lastState != null) {
			p_213281_1_.put("inBlockState", NBTUtil.writeBlockState(this.lastState));
		}

		p_213281_1_.putByte("shake", (byte) this.shakeTime);
		p_213281_1_.putBoolean("inGround", this.inGround);
		p_213281_1_.putByte("pickup", (byte) this.pickup.ordinal());
		p_213281_1_.putDouble("damage", this.baseDamage);
		p_213281_1_.putBoolean("crit", this.isCritArrow());
		p_213281_1_.putByte("PierceLevel", this.getPierceLevel());
		p_213281_1_.putString("SoundEvent", Registry.SOUND_EVENT.getKey(this.soundEvent).toString());
		p_213281_1_.putBoolean("ShotFromCrossbow", this.shotFromCrossbow());
	}

	@SuppressWarnings("deprecation")
	public void readAdditionalSaveData(CompoundNBT p_70037_1_) {
		super.readAdditionalSaveData(p_70037_1_);
		this.life = p_70037_1_.getShort("life");
		if (p_70037_1_.contains("inBlockState", 10)) {
			this.lastState = NBTUtil.readBlockState(p_70037_1_.getCompound("inBlockState"));
		}

		this.shakeTime = p_70037_1_.getByte("shake") & 255;
		this.inGround = p_70037_1_.getBoolean("inGround");
		if (p_70037_1_.contains("damage", 99)) {
			this.baseDamage = p_70037_1_.getDouble("damage");
		}

		if (p_70037_1_.contains("pickup", 99)) {
			this.pickup = AbstractArrowEntity.PickupStatus.byOrdinal(p_70037_1_.getByte("pickup"));
		} else if (p_70037_1_.contains("player", 99)) {
			this.pickup = p_70037_1_.getBoolean("player") ? AbstractArrowEntity.PickupStatus.ALLOWED
					: AbstractArrowEntity.PickupStatus.DISALLOWED;
		}

		this.setCritArrow(p_70037_1_.getBoolean("crit"));
		this.setPierceLevel(p_70037_1_.getByte("PierceLevel"));
		if (p_70037_1_.contains("SoundEvent", 8)) {
			this.soundEvent = Registry.SOUND_EVENT.getOptional(new ResourceLocation(p_70037_1_.getString("SoundEvent")))
					.orElse(this.getDefaultHitGroundSoundEvent());
		}

		this.setShotFromCrossbow(p_70037_1_.getBoolean("ShotFromCrossbow"));
	}

	public void setSoundEvent(SoundEvent p_213869_1_) {
		this.soundEvent = p_213869_1_;
	}

	protected void onHitEntity(EntityRayTraceResult p_213868_1_) {
		super.onHitEntity(p_213868_1_);
		Entity entity = p_213868_1_.getEntity();
		float f = (float) this.getDeltaMovement().length();
		int i = MathHelper.ceil(MathHelper.clamp((double) f * this.baseDamage, 0.0D, 2.147483647E9D));
		if (this.getPierceLevel() > 0) {
			if (this.piercingIgnoreEntityIds == null) {
				this.piercingIgnoreEntityIds = new IntOpenHashSet(5);
			}

			if (this.piercedAndKilledEntities == null) {
				this.piercedAndKilledEntities = Lists.newArrayListWithCapacity(5);
			}

			if (this.piercingIgnoreEntityIds.size() >= this.getPierceLevel() + 1) {
				this.remove();
				return;
			}

			this.piercingIgnoreEntityIds.add(entity.getId());
		}

		if (this.isCritArrow()) {
			long j = (long) this.random.nextInt(i / 2 + 2);
			i = (int) Math.min(j + (long) i, 2147483647L);
		}

		Entity entity1 = this.getOwner();
		DamageSource damagesource;
		if (entity1 == null) {
			damagesource = DamageSource.arrow(this, this);
		} else {
			damagesource = DamageSource.arrow(this, entity1);
			if (entity1 instanceof LivingEntity) {
				((LivingEntity) entity1).setLastHurtMob(entity);
			}
		}

		boolean flag = entity.getType() == EntityType.ENDERMAN;
		int k = entity.getRemainingFireTicks();
		if (this.isOnFire() && !flag) {
			entity.setSecondsOnFire(5);
		}

		if (entity.hurt(damagesource, (float) i)) {
			if (flag) {
				return;
			}

			if (entity instanceof LivingEntity) {
				LivingEntity livingentity = (LivingEntity) entity;
				if (!this.level.isClientSide && this.getPierceLevel() <= 0) {
					livingentity.setArrowCount(livingentity.getArrowCount() + 1);
				}

				if (this.knockback > 0) {
					Vector3d vector3d = this.getDeltaMovement().multiply(1.0D, 0.0D, 1.0D).normalize()
							.scale((double) this.knockback * 0.6D);
					if (vector3d.lengthSqr() > 0.0D) {
						livingentity.push(vector3d.x, 0.1D, vector3d.z);
					}
				}

				if (!this.level.isClientSide && entity1 instanceof LivingEntity) {
					EnchantmentHelper.doPostHurtEffects(livingentity, entity1);
					EnchantmentHelper.doPostDamageEffects((LivingEntity) entity1, livingentity);
				}

				this.doPostHurtEffects(livingentity);
				if (entity1 != null && livingentity != entity1 && livingentity instanceof PlayerEntity
						&& entity1 instanceof ServerPlayerEntity && !this.isSilent()) {
					((ServerPlayerEntity) entity1).connection
							.send(new SChangeGameStatePacket(SChangeGameStatePacket.ARROW_HIT_PLAYER, 0.0F));
				}

				if (!entity.isAlive() && this.piercedAndKilledEntities != null) {
					this.piercedAndKilledEntities.add(livingentity);
				}

				if (!this.level.isClientSide && entity1 instanceof ServerPlayerEntity) {
					ServerPlayerEntity serverplayerentity = (ServerPlayerEntity) entity1;
					if (this.piercedAndKilledEntities != null && this.shotFromCrossbow()) {
						CriteriaTriggers.KILLED_BY_CROSSBOW.trigger(serverplayerentity, this.piercedAndKilledEntities);
					} else if (!entity.isAlive() && this.shotFromCrossbow()) {
						CriteriaTriggers.KILLED_BY_CROSSBOW.trigger(serverplayerentity, Arrays.asList(entity));
					}
				}
			}

			this.playSound(this.soundEvent, 1.0F, 1.2F / (this.random.nextFloat() * 0.2F + 0.9F));
			if (this.getPierceLevel() <= 0) {
				this.remove();
			}
		} else {
			entity.setRemainingFireTicks(k);
			this.setDeltaMovement(this.getDeltaMovement().scale(-0.1D));
			this.yRot += 180.0F;
			this.yRotO += 180.0F;
			if (!this.level.isClientSide && this.getDeltaMovement().lengthSqr() < 1.0E-7D) {
				if (this.pickup == AbstractArrowEntity.PickupStatus.ALLOWED) {
					this.spawnAtLocation(this.getPickupItem(), 0.1F);
				}

				this.remove();
			}
		}

	}

	public void setKnockback(int p_70240_1_) {
		this.knockback = p_70240_1_;
	}

}
