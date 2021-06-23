package com.tsaroblivious.oblivioustweaks.common.goals;

import java.util.EnumSet;

import com.tsaroblivious.oblivioustweaks.common.entity.IPistolUser;
import com.tsaroblivious.oblivioustweaks.core.init.ItemInit;
import com.tsaroblivious.oblivioustweaks.core.items.Pistol;

import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.projectile.ProjectileHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.util.RangedInteger;

public class RangedPistolAttackGoal<T extends MonsterEntity & IRangedAttackMob & IPistolUser> extends Goal {

	public static final RangedInteger PATHFINDING_DELAY_RANGE = new RangedInteger(20, 40);
	private final T mob;
	private RangedPistolAttackGoal.PistolState pistolState = RangedPistolAttackGoal.PistolState.UNCHARGED;
	private final double speedModifier;
	private final float attackRadiusSqr;
	private int seeTime;
	private int attackDelay;
	private int updatePathDelay;

	public RangedPistolAttackGoal(T p_i50322_1_, double p_i50322_2_, float p_i50322_4_) {
		this.mob = p_i50322_1_;
		this.speedModifier = p_i50322_2_;
		this.attackRadiusSqr = p_i50322_4_ * p_i50322_4_;
		this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
	}

	@Override
	public boolean canUse() {
		return this.isValidTarget() && this.isHoldingPistol();
	}

	private boolean isHoldingPistol() {
		return this.mob.isHolding(ItemInit.PISTOL.get());
	}

	public boolean canContinueToUse() {
		return this.isValidTarget() && (this.canUse() || !this.mob.getNavigation().isDone()) && this.isHoldingPistol();
	}

	private boolean isValidTarget() {
		return this.mob.getTarget() != null && this.mob.getTarget().isAlive();
	}

	public void stop() {
		super.stop();
		this.mob.setAggressive(false);
		this.mob.setTarget((LivingEntity) null);
		this.seeTime = 0;
		if (this.mob.isUsingItem()) {
			this.mob.stopUsingItem();
			this.mob.setChargingPistol(false);
			Pistol.setCharged(this.mob.getUseItem(), false);
		}

	}

	public void tick() {
		LivingEntity livingentity = this.mob.getTarget();
		if (livingentity != null) {
			boolean flag = this.mob.getSensing().canSee(livingentity);
			boolean flag1 = this.seeTime > 0;
			if (flag != flag1) {
				this.seeTime = 0;
			}

			if (flag) {
				++this.seeTime;
			} else {
				--this.seeTime;
			}

			double d0 = this.mob.distanceToSqr(livingentity);
			boolean flag2 = (d0 > (double) this.attackRadiusSqr || this.seeTime < 5) && this.attackDelay == 0;
			if (flag2) {
				--this.updatePathDelay;
				if (this.updatePathDelay <= 0) {
					this.mob.getNavigation().moveTo(livingentity,
							this.canRun() ? this.speedModifier : this.speedModifier * 0.5D);
					this.updatePathDelay = PATHFINDING_DELAY_RANGE.randomValue(this.mob.getRandom());
				}
			} else {
				this.updatePathDelay = 0;
				this.mob.getNavigation().stop();
			}

			this.mob.getLookControl().setLookAt(livingentity, 30.0F, 30.0F);
			if (this.pistolState == RangedPistolAttackGoal.PistolState.UNCHARGED) {
				if (!flag2) {
					this.mob.startUsingItem(ProjectileHelper.getWeaponHoldingHand(this.mob, ItemInit.PISTOL.get()));
					this.pistolState = RangedPistolAttackGoal.PistolState.CHARGING;
					this.mob.setChargingPistol(true);
				}
			} else if (this.pistolState == RangedPistolAttackGoal.PistolState.CHARGING) {
				if (!this.mob.isUsingItem()) {
					this.pistolState = RangedPistolAttackGoal.PistolState.UNCHARGED;
				}

				int i = this.mob.getTicksUsingItem();
				ItemStack itemstack = this.mob.getUseItem();
				if (i >= Pistol.getChargeDuration(itemstack)) {
					this.mob.releaseUsingItem();
					this.pistolState = RangedPistolAttackGoal.PistolState.CHARGED;
					this.attackDelay = 20 + this.mob.getRandom().nextInt(20);
					this.mob.setChargingPistol(false);
				}
			} else if (this.pistolState == RangedPistolAttackGoal.PistolState.CHARGED) {
				--this.attackDelay;
				if (this.attackDelay == 0) {
					this.pistolState = RangedPistolAttackGoal.PistolState.READY_TO_ATTACK;
				}
			} else if (this.pistolState == RangedPistolAttackGoal.PistolState.READY_TO_ATTACK && flag) {
				this.mob.performRangedAttack(livingentity, 1.0F);
				ItemStack itemstack1 = this.mob
						.getItemInHand(ProjectileHelper.getWeaponHoldingHand(this.mob, ItemInit.PISTOL.get()));
				Pistol.setCharged(itemstack1, false);
				this.pistolState = RangedPistolAttackGoal.PistolState.UNCHARGED;
			}

		}
	}

	private boolean canRun() {
		return this.pistolState == RangedPistolAttackGoal.PistolState.UNCHARGED;
	}

	static enum PistolState {
		UNCHARGED, CHARGING, CHARGED, READY_TO_ATTACK;
	}

}
