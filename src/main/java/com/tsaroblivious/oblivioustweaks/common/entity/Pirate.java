package com.tsaroblivious.oblivioustweaks.common.entity;

import java.util.Random;

import javax.annotation.Nullable;

import com.tsaroblivious.oblivioustweaks.common.goals.RangedPistolAttackGoal;
import com.tsaroblivious.oblivioustweaks.core.init.ItemInit;
import com.tsaroblivious.oblivioustweaks.core.items.Pistol;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.HurtByTargetGoal;
import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.ai.goal.LookRandomlyGoal;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.ai.goal.WaterAvoidingRandomWalkingGoal;
import net.minecraft.entity.monster.AbstractIllagerEntity;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.entity.projectile.ProjectileHelper;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.ShootableItem;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class Pirate extends MonsterEntity implements IPistolUser {
	private static final DataParameter<Boolean> IS_CHARGING_CROSSBOW = EntityDataManager.defineId(Pirate.class,
			DataSerializers.BOOLEAN);
	private final Inventory inventory = new Inventory(5);

	private final RangedPistolAttackGoal<Pirate> pistolGoal = new RangedPistolAttackGoal<>(this, 1.0D, 8.0F);

	private final MeleeAttackGoal meleeGoal = new MeleeAttackGoal(this, 1.2D, false) {
		public void stop() {
			super.stop();
			Pirate.this.setAggressive(false);
		}

		public void start() {
			super.start();
			Pirate.this.setAggressive(true);
		}
	};

	public Pirate(EntityType<? extends MonsterEntity> p_i48549_1_, World p_i48549_2_) {
		super(p_i48549_1_, p_i48549_2_);
		addGoal();
		setPersistenceRequired();
	}

	public static AttributeModifierMap.MutableAttribute createMobAttributes() {
		return MobEntity.createLivingAttributes().add(Attributes.MAX_HEALTH, 20.0f).add(Attributes.ATTACK_DAMAGE, 3f)
				.add(Attributes.ATTACK_SPEED, 0.5f).add(Attributes.MOVEMENT_SPEED, (double) 0.23F)
				.add(Attributes.FOLLOW_RANGE, 30f).add(Attributes.ATTACK_KNOCKBACK, 0.5f);
	}

	@Override
	protected void registerGoals() {
		super.registerGoals();

		this.goalSelector.addGoal(0, new SwimGoal(this));
		this.goalSelector.addGoal(4, new WaterAvoidingRandomWalkingGoal(this, 1.0D));
		this.goalSelector.addGoal(5, new LookAtGoal(this, PlayerEntity.class, 8.0f));
		this.goalSelector.addGoal(6, new LookRandomlyGoal(this));
		this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
	}

	@Nullable
	@Override
	public ILivingEntityData finalizeSpawn(IServerWorld p_213386_1_, DifficultyInstance p_213386_2_,
			SpawnReason p_213386_3_, @Nullable ILivingEntityData p_213386_4_, @Nullable CompoundNBT p_213386_5_) {
		this.populateDefaultEquipmentSlots(p_213386_2_);
		this.populateDefaultEquipmentEnchantments(p_213386_2_);
		this.addGoal();

		return super.finalizeSpawn(p_213386_1_, p_213386_2_, p_213386_3_, p_213386_4_, p_213386_5_);
	}

	@Override
	protected int getExperienceReward(PlayerEntity p_70693_1_) {
		return 1 + this.level.random.nextInt(4);
	}

	@Override
	protected ResourceLocation getDefaultLootTable() {
		return super.getDefaultLootTable();
	}

	protected SoundEvent getAmbientSound() {
		return SoundEvents.PILLAGER_AMBIENT;
	}

	protected SoundEvent getDeathSound() {
		return SoundEvents.PILLAGER_DEATH;
	}

	protected SoundEvent getHurtSound(DamageSource p_184601_1_) {
		return SoundEvents.PILLAGER_HURT;
	}

	public void addGoal() {
		if (this.level != null && !this.level.isClientSide) {
			this.goalSelector.removeGoal(this.meleeGoal);
			this.goalSelector.removeGoal(this.pistolGoal);
			ItemStack itemstack = this
					.getItemInHand(ProjectileHelper.getWeaponHoldingHand(this, ItemInit.PISTOL.get()));
			if (itemstack.getItem() == ItemInit.PISTOL.get()) {
				this.goalSelector.addGoal(3, this.pistolGoal);
			} else {
				this.goalSelector.addGoal(3, this.meleeGoal);
			}
		}
	}

	@Override
	protected void populateDefaultEquipmentSlots(DifficultyInstance p_180481_1_) {
		super.populateDefaultEquipmentSlots(p_180481_1_);
		if (this.random.nextFloat() < 0.5) {
			this.setItemSlot(EquipmentSlotType.MAINHAND, new ItemStack(Items.IRON_SWORD));
		} else {
			this.setItemSlot(EquipmentSlotType.MAINHAND, new ItemStack(ItemInit.PISTOL.get()));
		}
		addGoal();
	}

	@Override
	public void setChargingPistol(boolean p_213671_1_) {
		this.entityData.set(IS_CHARGING_CROSSBOW, p_213671_1_);
	}

	@Override
	public void shootPistolProjectile(LivingEntity p_230284_1_, ItemStack p_230284_2_, ProjectileEntity p_230284_3_,
			float p_230284_4_) {
		this.shootPistolProjectile(this, p_230284_1_, p_230284_3_, p_230284_4_, 1.6F);
	}

	@Override
	public boolean canFireProjectileWeapon(ShootableItem p_230280_1_) {
		return p_230280_1_ == ItemInit.PISTOL.get();
	}

	@Override
	public void onPistolAttackPerformed() {
		this.noActionTime = 0;
	}

	@Override
	public void performRangedAttack(LivingEntity p_82196_1_, float p_82196_2_) {
		this.performPistolAttack(this, 1.6F);
	}

	// dump from here

	protected void defineSynchedData() {
		super.defineSynchedData();
		this.entityData.define(IS_CHARGING_CROSSBOW, false);
	}

	@OnlyIn(Dist.CLIENT)
	public boolean isChargingPistol() {
		return this.entityData.get(IS_CHARGING_CROSSBOW);
	}

	public void addAdditionalSaveData(CompoundNBT p_213281_1_) {
		super.addAdditionalSaveData(p_213281_1_);
		ListNBT listnbt = new ListNBT();

		for (int i = 0; i < this.inventory.getContainerSize(); ++i) {
			ItemStack itemstack = this.inventory.getItem(i);
			if (!itemstack.isEmpty()) {
				listnbt.add(itemstack.save(new CompoundNBT()));
			}
		}

		p_213281_1_.put("Inventory", listnbt);
	}

	@OnlyIn(Dist.CLIENT)
	public AbstractIllagerEntity.ArmPose getArmPose() {
		if (this.isChargingPistol()) {
			return AbstractIllagerEntity.ArmPose.CROSSBOW_CHARGE;

		} else if (this.isHolding(ItemInit.PISTOL.get()) && Pistol.isCharged(this.getMainHandItem())) {
			return AbstractIllagerEntity.ArmPose.CROSSBOW_HOLD;
		} else if (this.isHolding(ItemInit.PISTOL.get())) {
			return AbstractIllagerEntity.ArmPose.NEUTRAL;
		} else {
			return this.isAggressive() ? AbstractIllagerEntity.ArmPose.ATTACKING
					: AbstractIllagerEntity.ArmPose.NEUTRAL;
		}
	}

	public void readAdditionalSaveData(CompoundNBT p_70037_1_) {
		super.readAdditionalSaveData(p_70037_1_);
		ListNBT listnbt = p_70037_1_.getList("Inventory", 10);

		for (int i = 0; i < listnbt.size(); ++i) {
			ItemStack itemstack = ItemStack.of(listnbt.getCompound(i));
			if (!itemstack.isEmpty()) {
				this.inventory.addItem(itemstack);
			}
		}

		this.setCanPickUpLoot(true);
		this.addGoal();
	}

	public boolean setSlot(int p_174820_1_, ItemStack p_174820_2_) {
		if (super.setSlot(p_174820_1_, p_174820_2_)) {
			this.addGoal();
			return true;
		} else {
			int i = p_174820_1_ - 300;
			if (i >= 0 && i < this.inventory.getContainerSize()) {
				this.inventory.setItem(i, p_174820_2_);
				this.addGoal();
				return true;
			} else {
				return false;
			}
		}

	}

	public static boolean checkMonsterSpawnRules(EntityType<? extends MonsterEntity> p_223325_0_,
			IServerWorld p_223325_1_, SpawnReason p_223325_2_, BlockPos p_223325_3_, Random p_223325_4_) {
		return p_223325_1_.getDifficulty() != Difficulty.PEACEFUL && true
				&& checkMobSpawnRules(p_223325_0_, p_223325_1_, p_223325_2_, p_223325_3_, p_223325_4_);
	}

}
