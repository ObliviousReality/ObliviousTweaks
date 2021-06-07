package com.tsaroblivious.oblivioustweaks.core.items;

import javax.annotation.Nullable;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.ImmutableMultimap.Builder;
import com.google.common.collect.Multimap;
import com.tsaroblivious.oblivioustweaks.core.itemgroup.ObliviousTweaksItemGroup;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.TridentItem;
import net.minecraft.world.World;

public class Spear extends TridentItem {

	private Multimap<Attribute, AttributeModifier> defaultModifiers;

	public Spear() {
		super(new Item.Properties().defaultDurability(128).tab(ObliviousTweaksItemGroup.OBLIVIOUS_TWEAKS));
		Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
		builder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_UUID, "Tool modifier", 5.0D,
				AttributeModifier.Operation.ADDITION));
		builder.put(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_UUID, "Tool modifier",
				(double) -3.2F, AttributeModifier.Operation.ADDITION));
		this.defaultModifiers = builder.build();

	}

	@Override
	public Multimap<Attribute, AttributeModifier> getDefaultAttributeModifiers(EquipmentSlotType p_111205_1_) {
		return p_111205_1_ == EquipmentSlotType.MAINHAND ? this.defaultModifiers
				: super.getDefaultAttributeModifiers(p_111205_1_);
	}

//	public void releaseUsing(ItemStack p_77615_1_, World p_77615_2_, LivingEntity p_77615_3_, int p_77615_4_) {
//		if (p_77615_3_ instanceof PlayerEntity) {
//			PlayerEntity playerentity = (PlayerEntity) p_77615_3_;
//			int i = this.getUseDuration(p_77615_1_) - p_77615_4_;
//			if (i >= 10) {
//				int j = EnchantmentHelper.getRiptide(p_77615_1_);
//				if (j <= 0 || playerentity.isInWaterOrRain()) {
//					if (!p_77615_2_.isClientSide) {
//						p_77615_1_.hurtAndBreak(1, playerentity, (p_220047_1_) -> {
//							p_220047_1_.broadcastBreakEvent(p_77615_3_.getUsedItemHand());
//						});
//						if (j == 0) {
//							SpearEntity spearentity = new SpearEntity(p_77615_2_, playerentity, p_77615_1_);
//							spearentity.shootFromRotation(playerentity, playerentity.xRot, playerentity.yRot, 0.0F,
//									2.5F + (float) j * 0.5F, 1.0F);
//							if (playerentity.abilities.instabuild) {
//								spearentity.pickup = AbstractArrowEntity.PickupStatus.CREATIVE_ONLY;
//							}
//
//							p_77615_2_.addFreshEntity(spearentity);
//							p_77615_2_.playSound((PlayerEntity) null, spearentity, SoundEvents.TRIDENT_THROW,
//									SoundCategory.PLAYERS, 1.0F, 1.0F);
//							if (!playerentity.abilities.instabuild) {
//								playerentity.inventory.removeItem(p_77615_1_);
//							}
//						}
//					}
//
//					playerentity.awardStat(Stats.ITEM_USED.get(this));
//					if (j > 0) {
//						float f7 = playerentity.yRot;
//						float f = playerentity.xRot;
//						float f1 = -MathHelper.sin(f7 * ((float) Math.PI / 180F))
//								* MathHelper.cos(f * ((float) Math.PI / 180F));
//						float f2 = -MathHelper.sin(f * ((float) Math.PI / 180F));
//						float f3 = MathHelper.cos(f7 * ((float) Math.PI / 180F))
//								* MathHelper.cos(f * ((float) Math.PI / 180F));
//						float f4 = MathHelper.sqrt(f1 * f1 + f2 * f2 + f3 * f3);
//						float f5 = 3.0F * ((1.0F + (float) j) / 4.0F);
//						f1 = f1 * (f5 / f4);
//						f2 = f2 * (f5 / f4);
//						f3 = f3 * (f5 / f4);
//						playerentity.push((double) f1, (double) f2, (double) f3);
//						playerentity.startAutoSpinAttack(20);
//						if (playerentity.isOnGround()) {
//							float f6 = 1.1999999F;
//							playerentity.move(MoverType.SELF, new Vector3d(0.0D, (double) 1.1999999F, 0.0D));
//						}
//
//						SoundEvent soundevent;
//						if (j >= 3) {
//							soundevent = SoundEvents.TRIDENT_RIPTIDE_3;
//						} else if (j == 2) {
//							soundevent = SoundEvents.TRIDENT_RIPTIDE_2;
//						} else {
//							soundevent = SoundEvents.TRIDENT_RIPTIDE_1;
//						}
//
//						p_77615_2_.playSound((PlayerEntity) null, playerentity, soundevent, SoundCategory.PLAYERS, 1.0F,
//								1.0F);
//					}
//
//				}
//			}
//		}
//	}

	public static float isBeingThrown(ItemStack itemStack, @Nullable World world, @Nullable LivingEntity livingEntity) {
		final float NOT_PULLED = 0.0F;
		final float IS_PULLED = 1.0F;
		if (livingEntity == null)
			return NOT_PULLED;
		if (livingEntity.isUsingItem() && livingEntity.getUseItem() == itemStack)
			return IS_PULLED;
		return NOT_PULLED;
	}

	private final Enchantment[] VALID_ENCHANTMENTS = { Enchantments.KNOCKBACK, Enchantments.SHARPNESS,
			Enchantments.MENDING, Enchantments.BANE_OF_ARTHROPODS, Enchantments.SMITE, Enchantments.UNBREAKING };

	@Override
	public boolean canApplyAtEnchantingTable(ItemStack stack, net.minecraft.enchantment.Enchantment enchantment) {
		for (Enchantment enchantmentToCheck : VALID_ENCHANTMENTS) {
			if (enchantmentToCheck == enchantment)
				return true;
		}
		return false;
	}

}
