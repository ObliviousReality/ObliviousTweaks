package com.tsaroblivious.oblivioustweaks.core.items;

import com.tsaroblivious.oblivioustweaks.ObliviousTweaks;
import com.tsaroblivious.oblivioustweaks.common.material.CustomToolMaterial;
import com.tsaroblivious.oblivioustweaks.core.itemgroup.ObliviousTweaksItemGroup;

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
		ObliviousTweaks.LOGGER.debug("Before " + entityA.getHealth());
		if (entityA instanceof WitherSkeletonEntity) {
			entityA.hurt(DamageSource.mobAttack(entityB), 8);
		}
		ObliviousTweaks.LOGGER.debug("After " + entityA.getHealth());
		return true;
	}
}
