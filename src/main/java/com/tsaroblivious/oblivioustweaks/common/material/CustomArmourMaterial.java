package com.tsaroblivious.oblivioustweaks.common.material;

import java.util.function.Supplier;

import com.tsaroblivious.oblivioustweaks.core.init.ItemInit;

import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;

public enum CustomArmourMaterial implements IArmorMaterial {
	GILDED_ARMOUR("gilded", 2, new int[] { 2, 5, 6, 2 }, 17, SoundEvents.ARMOR_EQUIP_GENERIC, 1f, 0.0f,
			() -> Ingredient.of(ItemInit.GILDED_INGOT.get()));

	// Name, durability modifier, armour stats, enchantability, sound, armour
	// toughness, knockback resist, repair ingredient

	private static final int[] baseDurability = { 128, 144, 160, 120 };
	private final String name;
	private final int durabilityMultiplier;
	private final int[] armourVal;
	private final int enchantability;
	private final SoundEvent equipSound;
	private final float toughness;
	private final float knockbackResistance;
	private final Ingredient repairIngredient;

	CustomArmourMaterial(String name, int durabilityModifier, int[] armourVal, int enchantability,
			SoundEvent equipSound, float toughness, float knockbandResistance, Supplier<Ingredient> repairIngredient) {
		this.name = name;
		this.durabilityMultiplier = durabilityModifier;
		this.armourVal = armourVal;
		this.enchantability = enchantability;
		this.equipSound = equipSound;
		this.toughness = toughness;
		this.knockbackResistance = knockbandResistance;
		this.repairIngredient = repairIngredient.get();
	}

	@SuppressWarnings("static-access")
	@Override
	public int getDurabilityForSlot(EquipmentSlotType slot) {
		return this.baseDurability[slot.getIndex()] * this.durabilityMultiplier;
	}

	@Override
	public int getDefenseForSlot(EquipmentSlotType slot) {
		return this.armourVal[slot.getIndex()];
	}

	@Override
	public int getEnchantmentValue() {
		return this.enchantability;
	}

	@Override
	public SoundEvent getEquipSound() {
		return this.equipSound;
	}

	@Override
	public Ingredient getRepairIngredient() {
		return this.repairIngredient;
	}

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public float getToughness() {
		return this.toughness;
	}

	@Override
	public float getKnockbackResistance() {
		return this.knockbackResistance;
	}

}
