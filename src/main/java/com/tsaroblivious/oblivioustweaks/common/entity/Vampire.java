package com.tsaroblivious.oblivioustweaks.common.entity;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.monster.WitherSkeletonEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.World;

public class Vampire extends WitherSkeletonEntity {

	public Vampire(EntityType<? extends WitherSkeletonEntity> p_i48576_1_, World p_i48576_2_) {
		super(p_i48576_1_, p_i48576_2_);
	}

	public static AttributeModifierMap.MutableAttribute createMobAttributes() {
		return MobEntity.createLivingAttributes().add(Attributes.MAX_HEALTH, 20.0f).add(Attributes.ATTACK_DAMAGE, 2f)
				.add(Attributes.ATTACK_SPEED, 0.5f).add(Attributes.MOVEMENT_SPEED, 0.25f)
				.add(Attributes.FOLLOW_RANGE, 12f).add(Attributes.ATTACK_KNOCKBACK, 0.5f);
//		VindicatorEntity
	}

//	@Override
//	protected void registerGoals() {
//		super.registerGoals();
//
//		this.goalSelector.addGoal(0, new SwimGoal(this));
//		this.goalSelector.addGoal(1, new LookAtGoal(this, PlayerEntity.class, 8.0f));
//		this.goalSelector.addGoal(2, new LookRandomlyGoal(this));
////		this.goalSelector.addGoal(1, new MeleeAttackGoal(null, boardingCooldown, blocksBuilding)
////		this.goalSelector.addGoal(0.8, new FleeSunGoal(this, 2d));
//		this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, PlayerEntity.class, true));
//	}

	@Override
	protected int getExperienceReward(PlayerEntity p_70693_1_) {
		return 1 + this.level.random.nextInt(4);
	}

	@Override
	protected SoundEvent getAmbientSound() {
		return SoundEvents.CREEPER_PRIMED;
	}

	@Override
	protected SoundEvent getDeathSound() {
		return Math.random() > 0.5 ? SoundEvents.WITCH_DEATH : SoundEvents.WITCH_DEATH;
	}

	@Override
	protected SoundEvent getStepSound() {
		return SoundEvents.ZOMBIE_STEP;
	}

	@Override
	protected SoundEvent getHurtSound(DamageSource p_184601_1_) {
		return SoundEvents.WITCH_HURT;
	}
}
