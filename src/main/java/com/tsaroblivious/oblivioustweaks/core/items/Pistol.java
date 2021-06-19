package com.tsaroblivious.oblivioustweaks.core.items;

import java.util.List;
import java.util.Random;
import java.util.function.Predicate;

import com.google.common.collect.Lists;
import com.tsaroblivious.oblivioustweaks.core.init.ItemInit;
import com.tsaroblivious.oblivioustweaks.core.itemgroup.ObliviousTweaksItemGroup;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.ICrossbowUser;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.item.ArrowItem;
import net.minecraft.item.CrossbowItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.stats.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.vector.Quaternion;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.world.World;

public class Pistol extends CrossbowItem {

	private boolean startSoundPlayed = false;
	private boolean midLoadSoundPlayed = false;

	public static final Predicate<ItemStack> ARROW_ONLY = (p_220002_0_) -> {
		return p_220002_0_.sameItem(new ItemStack(ItemInit.PISTOL_SHOT.get()));
	};

	public Pistol() {
		super(new Item.Properties().stacksTo(2).tab(ObliviousTweaksItemGroup.OBLIVIOUS_TWEAKS).durability(250));
	}

	@Override
	public Predicate<ItemStack> getSupportedHeldProjectiles() {
		return ARROW_ONLY;
	}

	@Override
	public Predicate<ItemStack> getAllSupportedProjectiles() {
		return ARROW_ONLY;
	}

	@Override
	public ActionResult<ItemStack> use(World p_77659_1_, PlayerEntity p_77659_2_, Hand p_77659_3_) {
		ItemStack itemstack = p_77659_2_.getItemInHand(p_77659_3_);
		if (isCharged(itemstack)) {
			performShooting(p_77659_1_, p_77659_2_, p_77659_3_, itemstack, getShootingPower(itemstack), 1.0F);
			setCharged(itemstack, false);
			return ActionResult.consume(itemstack);
		} else if (!p_77659_2_.getProjectile(itemstack).isEmpty()) {
			if (!isCharged(itemstack)) {
				this.startSoundPlayed = false;
				this.midLoadSoundPlayed = false;
				p_77659_2_.startUsingItem(p_77659_3_);
			}

			return ActionResult.consume(itemstack);
		} else {
			return ActionResult.fail(itemstack);
		}
	}

	private static float getShootingPower(ItemStack p_220013_0_) {
		return p_220013_0_.getItem() == Items.CROSSBOW && containsChargedProjectile(p_220013_0_, Items.FIREWORK_ROCKET)
				? 1.6F
				: 3.15F;
//		return 10F;
	}

	@Override
	public void onUseTick(World p_219972_1_, LivingEntity p_219972_2_, ItemStack p_219972_3_, int p_219972_4_) {
		if (!p_219972_1_.isClientSide) {
			int i = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.QUICK_CHARGE, p_219972_3_);
			SoundEvent soundevent = this.getStartSound(i);
			SoundEvent soundevent1 = i == 0 ? SoundEvents.CROSSBOW_LOADING_MIDDLE : null;
			float f = (float) (p_219972_3_.getUseDuration() - p_219972_4_) / (float) getChargeDuration(p_219972_3_);
			if (f < 0.2F) {
				this.startSoundPlayed = false;
				this.midLoadSoundPlayed = false;
			}

			if (f >= 0.2F && !this.startSoundPlayed) {
				this.startSoundPlayed = true;
				p_219972_1_.playSound((PlayerEntity) null, p_219972_2_.getX(), p_219972_2_.getY(), p_219972_2_.getZ(),
						soundevent, SoundCategory.PLAYERS, 0.5F, 1.0F);
			}

			if (f >= 0.5F && soundevent1 != null && !this.midLoadSoundPlayed) {
				this.midLoadSoundPlayed = true;
				p_219972_1_.playSound((PlayerEntity) null, p_219972_2_.getX(), p_219972_2_.getY(), p_219972_2_.getZ(),
						soundevent1, SoundCategory.PLAYERS, 0.5F, 1.0F);
			}
		}

	}

	public static void performShooting(World p_220014_0_, LivingEntity p_220014_1_, Hand p_220014_2_,
			ItemStack p_220014_3_, float p_220014_4_, float p_220014_5_) {
		List<ItemStack> list = getChargedProjectiles(p_220014_3_);
		float[] afloat = getShotPitches(p_220014_1_.getRandom());

		for (int i = 0; i < list.size(); ++i) {
			ItemStack itemstack = list.get(i);
			boolean flag = p_220014_1_ instanceof PlayerEntity && ((PlayerEntity) p_220014_1_).abilities.instabuild;
			if (!itemstack.isEmpty()) {
				if (i == 0) {
					shootProjectile(p_220014_0_, p_220014_1_, p_220014_2_, p_220014_3_, itemstack, afloat[i], flag,
							p_220014_4_, p_220014_5_, 0.0F);
				} else if (i == 1) {
					shootProjectile(p_220014_0_, p_220014_1_, p_220014_2_, p_220014_3_, itemstack, afloat[i], flag,
							p_220014_4_, p_220014_5_, -10.0F);
				} else if (i == 2) {
					shootProjectile(p_220014_0_, p_220014_1_, p_220014_2_, p_220014_3_, itemstack, afloat[i], flag,
							p_220014_4_, p_220014_5_, 10.0F);
				}
			}
		}

		onCrossbowShot(p_220014_0_, p_220014_1_, p_220014_3_);
	}

	private static List<ItemStack> getChargedProjectiles(ItemStack p_220018_0_) {
		List<ItemStack> list = Lists.newArrayList();
		CompoundNBT compoundnbt = p_220018_0_.getTag();
		if (compoundnbt != null && compoundnbt.contains("ChargedProjectiles", 9)) {
			ListNBT listnbt = compoundnbt.getList("ChargedProjectiles", 10);
			if (listnbt != null) {
				for (int i = 0; i < listnbt.size(); ++i) {
					CompoundNBT compoundnbt1 = listnbt.getCompound(i);
					list.add(ItemStack.of(compoundnbt1));
				}
			}
		}

		return list;
	}

	private static void shootProjectile(World p_220016_0_, LivingEntity p_220016_1_, Hand p_220016_2_,
			ItemStack p_220016_3_, ItemStack p_220016_4_, float p_220016_5_, boolean p_220016_6_, float p_220016_7_,
			float p_220016_8_, float p_220016_9_) {
		if (!p_220016_0_.isClientSide) {
			ProjectileEntity projectileentity = getArrow(p_220016_0_, p_220016_1_, p_220016_3_, p_220016_4_);
			projectileentity.setNoGravity(true);
			if (p_220016_6_ || p_220016_9_ != 0.0F) {
				((AbstractArrowEntity) projectileentity).pickup = AbstractArrowEntity.PickupStatus.CREATIVE_ONLY;
			}

			if (p_220016_1_ instanceof ICrossbowUser) {
				ICrossbowUser icrossbowuser = (ICrossbowUser) p_220016_1_;
				icrossbowuser.shootCrossbowProjectile(icrossbowuser.getTarget(), p_220016_3_, projectileentity,
						p_220016_9_);
			} else {
				Vector3d vector3d1 = p_220016_1_.getUpVector(1.0F);
				Quaternion quaternion = new Quaternion(new Vector3f(vector3d1), p_220016_9_, true);
				Vector3d vector3d = p_220016_1_.getViewVector(1.0F);
				Vector3f vector3f = new Vector3f(vector3d);
				vector3f.transform(quaternion);
				projectileentity.shoot((double) vector3f.x(), (double) vector3f.y(), (double) vector3f.z(), p_220016_7_,
						p_220016_8_);
			}

			p_220016_3_.hurtAndBreak(1, p_220016_1_, (p_220017_1_) -> {
				p_220017_1_.broadcastBreakEvent(p_220016_2_);
			});
			p_220016_0_.addFreshEntity(projectileentity);
			p_220016_0_.playSound((PlayerEntity) null, p_220016_1_.getX(), p_220016_1_.getY(), p_220016_1_.getZ(),
					SoundEvents.ANVIL_LAND, SoundCategory.PLAYERS, 1.0F, p_220016_5_);
		}
	}

	private static AbstractArrowEntity getArrow(World p_220024_0_, LivingEntity p_220024_1_, ItemStack p_220024_2_,
			ItemStack p_220024_3_) {
		PistolShot arrowitem = (PistolShot) (p_220024_3_.getItem() instanceof PistolShot ? p_220024_3_.getItem()
				: ItemInit.PISTOL_SHOT.get());
		AbstractArrowEntity abstractarrowentity = arrowitem.createArrow(p_220024_0_, p_220024_3_, p_220024_1_);
		if (p_220024_1_ instanceof PlayerEntity) {
			abstractarrowentity.setCritArrow(true);
		}
		abstractarrowentity.setSoundEvent(SoundEvents.CROSSBOW_HIT);
		abstractarrowentity.setShotFromCrossbow(true);
		int i = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.PIERCING, p_220024_2_);
		if (i > 0) {
			abstractarrowentity.setPierceLevel((byte) i);
		}
		return abstractarrowentity;
	}

	private SoundEvent getStartSound(int p_220025_1_) {
		switch (p_220025_1_) {
		case 1:
			return SoundEvents.CROSSBOW_QUICK_CHARGE_1;
		case 2:
			return SoundEvents.CROSSBOW_QUICK_CHARGE_2;
		case 3:
			return SoundEvents.CROSSBOW_QUICK_CHARGE_3;
		default:
			return SoundEvents.CROSSBOW_LOADING_START;
		}
	}

	private static float[] getShotPitches(Random p_220028_0_) {
		boolean flag = p_220028_0_.nextBoolean();
		return new float[] { 1.0F, getRandomShotPitch(flag), getRandomShotPitch(!flag) };
	}

	private static float getRandomShotPitch(boolean p_220032_0_) {
		float f = p_220032_0_ ? 0.63F : 0.43F;
		return 1.0F / (random.nextFloat() * 0.5F + 1.8F) + f;
	}

	private static void onCrossbowShot(World p_220015_0_, LivingEntity p_220015_1_, ItemStack p_220015_2_) {
		if (p_220015_1_ instanceof ServerPlayerEntity) {
			ServerPlayerEntity serverplayerentity = (ServerPlayerEntity) p_220015_1_;
			if (!p_220015_0_.isClientSide) {
				CriteriaTriggers.SHOT_CROSSBOW.trigger(serverplayerentity, p_220015_2_);
			}

			serverplayerentity.awardStat(Stats.ITEM_USED.get(p_220015_2_.getItem()));
		}

		clearChargedProjectiles(p_220015_2_);
	}

	private static void clearChargedProjectiles(ItemStack p_220027_0_) {
		CompoundNBT compoundnbt = p_220027_0_.getTag();
		if (compoundnbt != null) {
			ListNBT listnbt = compoundnbt.getList("ChargedProjectiles", 9);
			listnbt.clear();
			compoundnbt.put("ChargedProjectiles", listnbt);
		}

	}

	private final Enchantment[] VALID_ENCHANTMENTS = { Enchantments.PUNCH_ARROWS, Enchantments.POWER_ARROWS,
			Enchantments.MENDING, Enchantments.UNBREAKING, Enchantments.INFINITY_ARROWS, Enchantments.PIERCING,
			Enchantments.QUICK_CHARGE };

	@Override
	public boolean canApplyAtEnchantingTable(ItemStack stack, net.minecraft.enchantment.Enchantment enchantment) {
		for (Enchantment enchantmentToCheck : VALID_ENCHANTMENTS) {
			if (enchantmentToCheck == enchantment)
				return true;
		}
		return false;
	}

	public static int getChargeDuration(ItemStack p_220026_0_) {
		int i = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.QUICK_CHARGE, p_220026_0_);
		return i == 0 ? 35 : 35 - 5 * i;
	}

	public int getUseDuration(ItemStack p_77626_1_) {
		return getChargeDuration(p_77626_1_) + 3;
	}

	private static float getPowerForTime(int p_220031_0_, ItemStack p_220031_1_) {
		float f = (float) p_220031_0_ / (float) getChargeDuration(p_220031_1_);
		if (f > 1.0F) {
			f = 1.0F;
		}

		return f;
	}

	public void releaseUsing(ItemStack p_77615_1_, World p_77615_2_, LivingEntity p_77615_3_, int p_77615_4_) {
		int i = this.getUseDuration(p_77615_1_) - p_77615_4_;
		float f = getPowerForTime(i, p_77615_1_);
		if (f >= 1.0F && !isCharged(p_77615_1_) && tryLoadProjectiles(p_77615_3_, p_77615_1_)) {
			setCharged(p_77615_1_, true);
			SoundCategory soundcategory = p_77615_3_ instanceof PlayerEntity ? SoundCategory.PLAYERS
					: SoundCategory.HOSTILE;
			p_77615_2_.playSound((PlayerEntity) null, p_77615_3_.getX(), p_77615_3_.getY(), p_77615_3_.getZ(),
					SoundEvents.CROSSBOW_LOADING_END, soundcategory, 1.0F,
					1.0F / (random.nextFloat() * 0.5F + 1.0F) + 0.2F);
		}

	}

	private static boolean tryLoadProjectiles(LivingEntity p_220021_0_, ItemStack p_220021_1_) {
		int i = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.MULTISHOT, p_220021_1_);
		int j = i == 0 ? 1 : 3;
		boolean flag = p_220021_0_ instanceof PlayerEntity && ((PlayerEntity) p_220021_0_).abilities.instabuild;
		ItemStack itemstack = p_220021_0_.getProjectile(p_220021_1_);
		ItemStack itemstack1 = itemstack.copy();

		for (int k = 0; k < j; ++k) {
			if (k > 0) {
				itemstack = itemstack1.copy();
			}

			if (itemstack.isEmpty() && flag) {
				itemstack = new ItemStack(Items.ARROW);
				itemstack1 = itemstack.copy();
			}

			if (!loadProjectile(p_220021_0_, p_220021_1_, itemstack, k > 0, flag)) {
				return false;
			}
		}

		return true;
	}

	private static boolean loadProjectile(LivingEntity p_220023_0_, ItemStack p_220023_1_, ItemStack p_220023_2_,
			boolean p_220023_3_, boolean p_220023_4_) {
		if (p_220023_2_.isEmpty()) {
			return false;
		} else {
			boolean flag = p_220023_4_ && p_220023_2_.getItem() instanceof ArrowItem;
			ItemStack itemstack;
			if (!flag && !p_220023_4_ && !p_220023_3_) {
				itemstack = p_220023_2_.split(1);
				if (p_220023_2_.isEmpty() && p_220023_0_ instanceof PlayerEntity) {
					((PlayerEntity) p_220023_0_).inventory.removeItem(p_220023_2_);
				}
			} else {
				itemstack = p_220023_2_.copy();
			}

			addChargedProjectile(p_220023_1_, itemstack);
			return true;
		}
	}

	private static void addChargedProjectile(ItemStack p_220029_0_, ItemStack p_220029_1_) {
		CompoundNBT compoundnbt = p_220029_0_.getOrCreateTag();
		ListNBT listnbt;
		if (compoundnbt.contains("ChargedProjectiles", 9)) {
			listnbt = compoundnbt.getList("ChargedProjectiles", 10);
		} else {
			listnbt = new ListNBT();
		}

		CompoundNBT compoundnbt1 = new CompoundNBT();
		p_220029_1_.save(compoundnbt1);
		listnbt.add(compoundnbt1);
		compoundnbt.put("ChargedProjectiles", listnbt);
	}
}
