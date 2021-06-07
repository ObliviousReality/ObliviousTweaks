package com.tsaroblivious.oblivioustweaks.core.init;

import com.tsaroblivious.oblivioustweaks.ObliviousTweaks;
import com.tsaroblivious.oblivioustweaks.common.material.CustomArmourMaterial;
import com.tsaroblivious.oblivioustweaks.core.itemgroup.ObliviousTweaksItemGroup;
import com.tsaroblivious.oblivioustweaks.core.items.SilverSword;
import com.tsaroblivious.oblivioustweaks.core.items.Spear;

import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.Item;
import net.minecraft.item.TridentItem;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ItemInit {
	public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS,
			ObliviousTweaks.MOD_ID);

	public static final RegistryObject<Item> EXAMPLE_ITEM = ITEMS.register("example_item",
			() -> new Item(new Item.Properties().tab(ObliviousTweaksItemGroup.OBLIVIOUS_TWEAKS)));

	public static final RegistryObject<Item> SPEAR = ITEMS.register("spear", () -> new Spear());

	public static final RegistryObject<Item> SILVER_SWORD = ITEMS.register("silver_sword", () -> new SilverSword());

	public static final RegistryObject<Item> GILDED_INGOT = ITEMS.register("gilded_ingot",
			() -> new Item(new Item.Properties().tab(ObliviousTweaksItemGroup.OBLIVIOUS_TWEAKS)));

	public static final RegistryObject<Item> GILDED_HELMET = ITEMS.register("gilded_helmet",
			() -> new ArmorItem(CustomArmourMaterial.GILDED_ARMOUR, EquipmentSlotType.HEAD,
					new Item.Properties().tab(ObliviousTweaksItemGroup.OBLIVIOUS_TWEAKS)));

	public static final RegistryObject<Item> GILDED_CHESTPLATE = ITEMS.register("gilded_chestplate",
			() -> new ArmorItem(CustomArmourMaterial.GILDED_ARMOUR, EquipmentSlotType.CHEST,
					new Item.Properties().tab(ObliviousTweaksItemGroup.OBLIVIOUS_TWEAKS)));

	public static final RegistryObject<Item> GILDED_LEGGINGS = ITEMS.register("gilded_leggings",
			() -> new ArmorItem(CustomArmourMaterial.GILDED_ARMOUR, EquipmentSlotType.LEGS,
					new Item.Properties().tab(ObliviousTweaksItemGroup.OBLIVIOUS_TWEAKS)));

	public static final RegistryObject<Item> GILDED_BOOTS = ITEMS.register("gilded_boots",
			() -> new ArmorItem(CustomArmourMaterial.GILDED_ARMOUR, EquipmentSlotType.FEET,
					new Item.Properties().tab(ObliviousTweaksItemGroup.OBLIVIOUS_TWEAKS)));

}
