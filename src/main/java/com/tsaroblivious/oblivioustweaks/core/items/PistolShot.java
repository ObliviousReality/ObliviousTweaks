package com.tsaroblivious.oblivioustweaks.core.items;

import com.tsaroblivious.oblivioustweaks.core.entities.ShotEntity;
import com.tsaroblivious.oblivioustweaks.core.itemgroup.ObliviousTweaksItemGroup;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class PistolShot extends Item {

	public PistolShot() {
		super(new Item.Properties().tab(ObliviousTweaksItemGroup.OBLIVIOUS_TWEAKS));
	}

	public AbstractArrowEntity createArrow(World p_200887_1_, ItemStack p_200887_2_, LivingEntity p_200887_3_) {
		ShotEntity arrowentity = new ShotEntity(p_200887_3_, p_200887_1_, this);
		arrowentity.setBaseDamage(3f);
		return arrowentity;
	}

	public boolean isInfinite(ItemStack stack, ItemStack bow, net.minecraft.entity.player.PlayerEntity player) {
		int enchant = net.minecraft.enchantment.EnchantmentHelper
				.getItemEnchantmentLevel(net.minecraft.enchantment.Enchantments.INFINITY_ARROWS, bow);
		return enchant <= 0 ? false : this.getClass() == PistolShot.class;
	}

}
