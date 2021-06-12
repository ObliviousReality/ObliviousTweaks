package com.tsaroblivious.oblivioustweaks.common.entity;

import com.tsaroblivious.oblivioustweaks.ObliviousTweaks;
import com.tsaroblivious.oblivioustweaks.core.items.EffectsInit;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.FleeSunGoal;
import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.ai.goal.LookRandomlyGoal;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.ai.goal.WaterAvoidingRandomWalkingGoal;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.World;

public class Vampire extends MonsterEntity {

	private final MeleeAttackGoal meleeGoal = new MeleeAttackGoal(this, 1.2D, false) {
		public void stop() {
			super.stop();
			Vampire.this.setAggressive(false);
		}

		public void start() {
			super.start();
			Vampire.this.setAggressive(true);
		}
	};

	public Vampire(EntityType<? extends MonsterEntity> p_i48576_1_, World p_i48576_2_) {
		super(p_i48576_1_, p_i48576_2_);
		addGoal();
	}

	public static AttributeModifierMap.MutableAttribute createMobAttributes() {
		return MobEntity.createLivingAttributes().add(Attributes.MAX_HEALTH, 20.0f).add(Attributes.ATTACK_DAMAGE, 2f)
				.add(Attributes.ATTACK_SPEED, 0.5f).add(Attributes.MOVEMENT_SPEED, 0.25f)
				.add(Attributes.FOLLOW_RANGE, 12f).add(Attributes.ATTACK_KNOCKBACK, 0.5f);
//		VindicatorEntity
	}

	@Override
	protected void registerGoals() {
		super.registerGoals();

		this.goalSelector.addGoal(0, new SwimGoal(this));
		this.goalSelector.addGoal(2, new FleeSunGoal(this, 2d));

		this.goalSelector.addGoal(4, new WaterAvoidingRandomWalkingGoal(this, 1.0D));
		this.goalSelector.addGoal(5, new LookAtGoal(this, PlayerEntity.class, 8.0f));
		this.goalSelector.addGoal(6, new LookRandomlyGoal(this));
		this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, PlayerEntity.class, true));
	}

	@Override
	protected int getExperienceReward(PlayerEntity p_70693_1_) {
		return 1 + this.level.random.nextInt(4);
	}

	@Override
	protected ResourceLocation getDefaultLootTable() {
		// TODO Auto-generated method stub
		return super.getDefaultLootTable();
	}

	@Override
	protected SoundEvent getAmbientSound() {
		return SoundEvents.WITCH_CELEBRATE;
	}

	@Override
	protected SoundEvent getDeathSound() {
		return Math.random() > 0.5 ? SoundEvents.WITCH_DEATH : SoundEvents.WITCH_DEATH;
	}

	@Override
	protected SoundEvent getHurtSound(DamageSource p_184601_1_) {
		return SoundEvents.WITCH_HURT;
	}

	public void addGoal() {
		this.goalSelector.addGoal(3, this.meleeGoal);
	}

	@Override
	public boolean doHurtTarget(Entity entity) {
		if (!super.doHurtTarget(entity)) {
			return false;
		} else {
			if (entity instanceof LivingEntity) {
				((LivingEntity) entity).addEffect(new EffectInstance(Effects.WITHER, 200));
				double randVal = Math.random();
				ObliviousTweaks.LOGGER.debug(randVal);
				if (randVal < 0.1) {
					((LivingEntity) entity)
							.addEffect(new EffectInstance(EffectsInit.vampirism_effect, Integer.MAX_VALUE));
				}
			}
			return true;
		}
	}

	@Override
	public void aiStep() {
		if (this.isSunBurnTick()) {
			this.setSecondsOnFire(10);
		}
		super.aiStep();
	}
}
