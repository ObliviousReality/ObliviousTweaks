package com.tsaroblivious.oblivioustweaks.common.material;

import java.util.function.Supplier;

import com.tsaroblivious.oblivioustweaks.core.init.ItemInit;

import net.minecraft.item.IItemTier;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;

public enum CustomToolMaterial implements IItemTier {

	// EXAMPLE_TOOL(harvestLevel, maxUses, efficiency(F), attackDamage,
	// enchantability, () -> Ingredient.of(ItemInit.EXAMPLE_ITEM.get())),

	SPEAR(1, 125, 0.5f, 5f, 17, () -> Ingredient.of(Items.IRON_INGOT)),
	SILVER_SWORD(1, 350, 12f, 5, 17, () -> Ingredient.of(Items.IRON_INGOT));

	private final int harvestLevel;
	private final int maxUses;
	private final float efficiency;
	private final float attackDamage;
	private final int enchantability;
	private final Ingredient repairMaterial;

	CustomToolMaterial(int harvestLevel, int maxUses, float efficiency, float attackDamage, int enchantability,
			Supplier<Ingredient> repairMaterial) {
		this.harvestLevel = harvestLevel;
		this.maxUses = maxUses;
		this.efficiency = efficiency;
		this.attackDamage = attackDamage;
		this.enchantability = enchantability;
		this.repairMaterial = repairMaterial.get();
	}

	@Override
	public int getUses() {
		return this.maxUses;
	}

	@Override
	public float getSpeed() {
		return this.efficiency;
	}

	@Override
	public float getAttackDamageBonus() {
		return this.attackDamage;
	}

	@Override
	public int getLevel() {
		return this.harvestLevel;
	}

	@Override
	public int getEnchantmentValue() {
		return this.enchantability;
	}

	@Override
	public Ingredient getRepairIngredient() {
		return this.repairMaterial;
	}

}
