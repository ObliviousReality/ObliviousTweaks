package com.tsaroblivious.oblivioustweaks.core.init;

import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectType;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionUtils;
import net.minecraft.potion.Potions;
import net.minecraftforge.common.brewing.BrewingRecipeRegistry;

public class EffectsInit {

	public static Potion prevent_disease_potion = null;
	public static Effect prevent_disease_effect = null;

	public static Potion vampirism_potion = null;
	public static Effect vampirism_effect = null;

	public static Potion cure_disease_potion = null;
	public static Effect cure_disease_effect = null;

	public static void addBrewingRecipes() {
		BrewingRecipeRegistry.addRecipe(
				Ingredient.of(PotionUtils.setPotion(new ItemStack(Items.POTION), Potions.AWKWARD)),
				Ingredient.of(new ItemStack(ItemInit.FUNGUS_BOWL.get())),
				PotionUtils.setPotion(new ItemStack(Items.POTION), EffectsInit.prevent_disease_potion));
		BrewingRecipeRegistry.addRecipe(
				Ingredient.of(PotionUtils.setPotion(new ItemStack(Items.POTION), Potions.AWKWARD)),
				Ingredient.of(new ItemStack(ItemInit.VAMPIRE_TOOTH.get())),
				PotionUtils.setPotion(new ItemStack(Items.POTION), EffectsInit.vampirism_potion));
		BrewingRecipeRegistry.addRecipe(
				Ingredient.of(PotionUtils.setPotion(new ItemStack(Items.POTION), Potions.AWKWARD)),
				Ingredient.of(new ItemStack(Items.POISONOUS_POTATO)),
				PotionUtils.setPotion(new ItemStack(Items.POTION), EffectsInit.cure_disease_potion));
	}

	public static class PreventDiseaseEffect extends Effect {

		public PreventDiseaseEffect(EffectType type, int colour) {
			super(type, colour);
		}

	}

	public static class VampirismEffect extends Effect {

		public VampirismEffect(EffectType type, int colour) {
			super(type, colour);
		}

	}

	public static class CureDiseaseEffect extends Effect {

		public CureDiseaseEffect(EffectType type, int colour) {
			super(type, colour);
		}

	}

}
