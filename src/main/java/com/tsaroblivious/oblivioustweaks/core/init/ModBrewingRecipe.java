package com.tsaroblivious.oblivioustweaks.core.init;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraftforge.common.brewing.BrewingRecipe;

public class ModBrewingRecipe extends BrewingRecipe {

	public ModBrewingRecipe(Ingredient input, Ingredient ingredient, ItemStack output) {
		super(input, ingredient, output);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean isInput(ItemStack stack) {
		return ItemStack.isSame(super.getInput().getItems()[0], stack);
	}

}
