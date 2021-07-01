package com.tsaroblivious.oblivioustweaks.core.items;

import java.util.Map;

import com.tsaroblivious.oblivioustweaks.common.entity.Vampire;
import com.tsaroblivious.oblivioustweaks.common.material.CustomToolMaterial;
import com.tsaroblivious.oblivioustweaks.core.init.EffectsInit;
import com.tsaroblivious.oblivioustweaks.core.itemgroup.ObliviousTweaksItemGroup;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.monster.WitherSkeletonEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Rarity;
import net.minecraft.item.SwordItem;
import net.minecraft.util.DamageSource;

public class SilverSword extends SwordItem {

	public SilverSword() {
		super(CustomToolMaterial.SILVER_SWORD, 0, -2.4f,
				new Item.Properties().tab(ObliviousTweaksItemGroup.OBLIVIOUS_TWEAKS).rarity(Rarity.UNCOMMON));
	}

	@Override
	public boolean hurtEnemy(ItemStack item, LivingEntity entityA, LivingEntity entityB) {
		item.hurtAndBreak(1, entityB, (p_220045_0_) -> {
			p_220045_0_.broadcastBreakEvent(EquipmentSlotType.MAINHAND);
		});
		if (entityA instanceof WitherSkeletonEntity || entityA instanceof Vampire
				|| entityA.hasEffect(EffectsInit.vampirism_effect)) {
			entityA.hurt(DamageSource.mobAttack(entityB), 12);
		} else {
			Map<Enchantment, Integer> enchants = EnchantmentHelper.getEnchantments(item);
			if (enchants.get(Enchantments.UNBREAKING) != null) {
				float chance = (100f / (float) enchants.get(Enchantments.UNBREAKING) + 1f) / 100f;
				if (Math.random() > chance) {
					item.setDamageValue(item.getDamageValue() + 1);
				}
			} else {
				item.setDamageValue(item.getDamageValue() + 1);
			}
		}
		return true;
	}
}
