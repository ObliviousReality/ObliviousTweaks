package com.tsaroblivious.oblivioustweaks.core.items;

import com.tsaroblivious.oblivioustweaks.core.itemgroup.ObliviousTweaksItemGroup;

import net.minecraft.item.Food;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.UseAction;

public class HotWaterBottle extends Item {

	public HotWaterBottle() {
		super(new Item.Properties().tab(ObliviousTweaksItemGroup.OBLIVIOUS_TWEAKS)
				.food(new Food.Builder().nutrition(0).saturationMod(0).alwaysEat().fast().build()).stacksTo(16));
	}

	@Override
	public UseAction getUseAnimation(ItemStack p_77661_1_) {
		return UseAction.DRINK;
	}

}
