package com.tsaroblivious.oblivioustweaks.core.init;

import com.tsaroblivious.oblivioustweaks.ObliviousTweaks;
import com.tsaroblivious.oblivioustweaks.common.material.CustomArmourMaterial;
import com.tsaroblivious.oblivioustweaks.common.material.CustomToolMaterial;
import com.tsaroblivious.oblivioustweaks.core.itemgroup.ObliviousTweaksItemGroup;
import com.tsaroblivious.oblivioustweaks.core.items.BlackTea;
import com.tsaroblivious.oblivioustweaks.core.items.CupOfTea;
import com.tsaroblivious.oblivioustweaks.core.items.HotWaterBottle;
import com.tsaroblivious.oblivioustweaks.core.items.IronDagger;
import com.tsaroblivious.oblivioustweaks.core.items.MilkBottle;
import com.tsaroblivious.oblivioustweaks.core.items.MilkyTea;
import com.tsaroblivious.oblivioustweaks.core.items.Pistol;
import com.tsaroblivious.oblivioustweaks.core.items.PistolShot;
import com.tsaroblivious.oblivioustweaks.core.items.SilverSword;
import com.tsaroblivious.oblivioustweaks.core.items.Spear;

import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.AxeItem;
import net.minecraft.item.BlockItem;
import net.minecraft.item.HoeItem;
import net.minecraft.item.Item;
import net.minecraft.item.PickaxeItem;
import net.minecraft.item.ShovelItem;
import net.minecraft.item.SwordItem;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ItemInit {
	public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS,
			ObliviousTweaks.MOD_ID);

	public static final RegistryObject<Item> GILDED_INGOT = ITEMS.register("gilded_ingot",
			() -> new Item(new Item.Properties().tab(ObliviousTweaksItemGroup.OBLIVIOUS_TWEAKS)));

	public static final RegistryObject<Item> SPEAR = ITEMS.register("spear", () -> new Spear());

	public static final RegistryObject<Item> SILVER_SWORD = ITEMS.register("silver_sword", () -> new SilverSword());

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

	public static final RegistryObject<Item> PISTOL = ITEMS.register("pistol", () -> new Pistol());

	public static final RegistryObject<Item> GILDED_SWORD = ITEMS.register("gilded_sword",
			() -> new SwordItem(CustomToolMaterial.GILDED_SWORD, 0, -2.4f,
					new Item.Properties().tab(ObliviousTweaksItemGroup.OBLIVIOUS_TWEAKS)));

	public static final RegistryObject<Item> GILDED_PICKAXE = ITEMS.register("gilded_pickaxe",
			() -> new PickaxeItem(CustomToolMaterial.GILDED_PICKAXE, 0, -2.8f,
					new Item.Properties().tab(ObliviousTweaksItemGroup.OBLIVIOUS_TWEAKS)));

	public static final RegistryObject<Item> GILDED_AXE = ITEMS.register("gilded_axe",
			() -> new AxeItem(CustomToolMaterial.GILDED_AXE, 0, -3.1f,
					new Item.Properties().tab(ObliviousTweaksItemGroup.OBLIVIOUS_TWEAKS)));

	public static final RegistryObject<Item> GILDED_SHOVEL = ITEMS.register("gilded_shovel",
			() -> new ShovelItem(CustomToolMaterial.GILDED_SHOVEL, 0, -3f,
					new Item.Properties().tab(ObliviousTweaksItemGroup.OBLIVIOUS_TWEAKS)));

	public static final RegistryObject<Item> GILDED_HOE = ITEMS.register("gilded_hoe",
			() -> new HoeItem(CustomToolMaterial.GILDED_HOE, 0, -0.5f,
					new Item.Properties().tab(ObliviousTweaksItemGroup.OBLIVIOUS_TWEAKS)));

	public static final RegistryObject<Item> IRON_DAGGER = ITEMS.register("iron_dagger", () -> new IronDagger());

	public static final RegistryObject<Item> VAMPIRE_TOOTH = ITEMS.register("vampire_tooth",
			() -> new Item(new Item.Properties().tab(ObliviousTweaksItemGroup.OBLIVIOUS_TWEAKS)));

	public static final RegistryObject<Item> FUNGUS_BOWL = ITEMS.register("fungus_bowl",
			() -> new Item(new Item.Properties().tab(ObliviousTweaksItemGroup.OBLIVIOUS_TWEAKS)));

	public static final RegistryObject<Item> CLOVER_SEEDS = ITEMS.register("clover_seeds",
			() -> new BlockItem(BlockInit.CLOVER_CROP.get(),
					new Item.Properties().tab(ObliviousTweaksItemGroup.OBLIVIOUS_TWEAKS)));

	public static final RegistryObject<Item> CLOVER_LEAF = ITEMS.register("clover_leaf",
			() -> new Item(new Item.Properties().tab(ObliviousTweaksItemGroup.OBLIVIOUS_TWEAKS)));

	public static final RegistryObject<Item> CLOVER_FLOWER = ITEMS.register("clover_flower",
			() -> new Item(new Item.Properties().tab(ObliviousTweaksItemGroup.OBLIVIOUS_TWEAKS)));

	public static final RegistryObject<Item> PISTOL_SHOT = ITEMS.register("pistol_shot", () -> new PistolShot());

	public static final RegistryObject<Item> TEA_SEEDS = ITEMS.register("tea_seeds",
			() -> new BlockItem(BlockInit.TEA_CROP.get(),
					new Item.Properties().tab(ObliviousTweaksItemGroup.OBLIVIOUS_TWEAKS)));

	public static final RegistryObject<Item> TEA_LEAF = ITEMS.register("tea_leaf",
			() -> new Item(new Item.Properties().tab(ObliviousTweaksItemGroup.OBLIVIOUS_TWEAKS)));

	public static final RegistryObject<Item> HOT_WATER_BUCKET = ITEMS.register("hot_water_bucket",
			() -> new Item(new Item.Properties().tab(ObliviousTweaksItemGroup.OBLIVIOUS_TWEAKS).stacksTo(1)));

	public static final RegistryObject<Item> HOT_WATER_BOTTLE = ITEMS.register("hot_water_bottle",
			() -> new HotWaterBottle());

	public static final RegistryObject<Item> MUG = ITEMS.register("mug",
			() -> new Item(new Item.Properties().tab(ObliviousTweaksItemGroup.OBLIVIOUS_TWEAKS)));

	public static final RegistryObject<Item> CUP_OF_TEA = ITEMS.register("cup_of_tea", () -> new CupOfTea());

	public static final RegistryObject<Item> BLACK_TEA = ITEMS.register("black_tea", () -> new BlackTea());

	public static final RegistryObject<Item> MILK_TEA = ITEMS.register("milk_tea", () -> new MilkyTea());

	public static final RegistryObject<Item> MILK_BOTTLE = ITEMS.register("milk_bottle", () -> new MilkBottle());

	public static final RegistryObject<Item> PIRATE_HAT = ITEMS.register("pirate_hat",
			() -> new ArmorItem(ArmorMaterial.LEATHER, EquipmentSlotType.HEAD,
					new Item.Properties().tab(ObliviousTweaksItemGroup.OBLIVIOUS_TWEAKS)));

	public static final RegistryObject<Item> PARASOL = ITEMS.register("parasol",
			() -> new Item(new Item.Properties().tab(ObliviousTweaksItemGroup.OBLIVIOUS_TWEAKS).stacksTo(1)));

}
