package com.tsaroblivious.oblivioustweaks.core.init;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectType;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionBrewing;
import net.minecraft.potion.Potions;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;

public class EffectsInit {

	public static Potion prevent_disease_potion = null;
	public static Effect prevent_disease_effect = null;

	public static Potion vampirism_potion = null;
	public static Effect vampirism_effect = null;

	public static Potion cure_disease_potion = null;
	public static Effect cure_disease_effect = null;

	private static Method brewingMixes;

	private static void addMix(Potion start, Item ingredient, Potion result) {
		if (brewingMixes == null) {
			brewingMixes = ObfuscationReflectionHelper.findMethod(PotionBrewing.class, "addMix", Potion.class,
					Item.class, Potion.class);
			brewingMixes.setAccessible(true);
		}

		try {
			brewingMixes.invoke(null, start, ingredient, result);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
		}
	}

	public static void addBrewingRecipes() {
		addMix(Potions.AWKWARD, ItemInit.FUNGUS_BOWL.get(), EffectsInit.prevent_disease_potion);
		addMix(Potions.AWKWARD, ItemInit.VAMPIRE_TOOTH.get(), EffectsInit.vampirism_potion);
		addMix(Potions.AWKWARD, Items.POISONOUS_POTATO, EffectsInit.cure_disease_potion);
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
