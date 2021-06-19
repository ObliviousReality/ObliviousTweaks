package com.tsaroblivious.oblivioustweaks.common.material;

import java.util.function.Supplier;

import com.tsaroblivious.oblivioustweaks.core.init.ItemInit;

import net.minecraft.item.IItemTier;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;

public enum CustomToolMaterial implements IItemTier {

	// Efficiencies: Hand = 1, Wood = 2, Stone = 4, Iron = 6, Diamond = 8,
	// Netherite = 9, Gold = 12.

	// All swords are 1.5

	// EXAMPLE_TOOL(harvestLevel, maxUses, efficiency(F), attackDamage - 1,
	// enchantability, () -> Ingredient.of(ItemInit.EXAMPLE_ITEM.get())),

	SPEAR(1, 125, 0.5f, 1.5f, 17, () -> Ingredient.of(Items.IRON_INGOT)),
	SILVER_SWORD(1, 350, 1.5f, 5, 14, () -> Ingredient.of(Items.BARRIER)),
	GILDED_SWORD(1, 350, 1.5f, 5f, 17, () -> Ingredient.of(ItemInit.GILDED_INGOT.get())),
	GILDED_PICKAXE(2, 350, 7f, 3.5f, 17, () -> Ingredient.of(ItemInit.GILDED_INGOT.get())),
	GILDED_AXE(2, 350, 7f, 8f, 17, () -> Ingredient.of(ItemInit.GILDED_INGOT.get())),
	GILDED_SHOVEL(2, 350, 7f, 3f, 17, () -> Ingredient.of(ItemInit.GILDED_INGOT.get())),
	GILDED_HOE(2, 350, 7f, 0f, 17, () -> Ingredient.of(ItemInit.GILDED_INGOT.get())),
	IRON_DAGGER(2, 100, 1.5f, 2, 14, () -> Ingredient.of(Items.IRON_INGOT));

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
