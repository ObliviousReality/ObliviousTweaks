package com.tsaroblivious.oblivioustweaks.common.loot;

import java.util.List;
import java.util.Random;

import com.google.gson.JsonObject;
import com.tsaroblivious.oblivioustweaks.core.init.ItemInit;

import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.loot.LootContext;
import net.minecraft.loot.conditions.ILootCondition;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.loot.GlobalLootModifierSerializer;
import net.minecraftforge.common.loot.LootModifier;

public class CloverSeedsLootModifier extends LootModifier {

	protected CloverSeedsLootModifier(ILootCondition[] conditionsIn) {
		super(conditionsIn);
	}

	@Override
	protected List<ItemStack> doApply(List<ItemStack> generatedLoot, LootContext context) {
		ResourceLocation lootTable = context.getQueriedLootTableId();
		if (lootTable.equals(new ResourceLocation("minecraft:chests/simple_dungeon"))
				|| lootTable.equals(new ResourceLocation("minecraft:chests/abandoned_mineshaft"))
				|| lootTable.equals(new ResourceLocation("minecraft:chests/stronghold_corridor"))
				|| lootTable.equals(new ResourceLocation("minecraft:chests/shipwreck_treasure"))) {
			ItemStack clover = new ItemStack(ItemInit.CLOVER_SEEDS.get());
			ItemStack tea = new ItemStack(ItemInit.TEA_SEEDS.get(), 3);
			ItemStack none = new ItemStack(Items.AIR);
			ItemStack loot;
			Random random = context.getRandom();
			int val = random.nextInt(4);
			switch (val) {
			case 0:
				loot = clover;
				break;
			case 1:
				loot = none;
				break;
			case 2:
				loot = tea;
				break;
			case 3:
				loot = none;
				break;
			default:
				loot = none;
				break;
			}
			generatedLoot.add(loot);
		} else if (lootTable.equals(new ResourceLocation("minecraft:chests/underwater_ruin_big"))
				|| lootTable.equals(new ResourceLocation("minecraft:chests/underwater_ruin_small"))
				|| lootTable.equals(new ResourceLocation("minecraft:chests/woodland_mansion"))
				|| lootTable.equals(new ResourceLocation("minecraft:chests/shipwreck_supply"))) {
			ItemStack tea = new ItemStack(ItemInit.TEA_SEEDS.get(), 3);
			ItemStack none = new ItemStack(Items.AIR);
			ItemStack loot;
			Random random = context.getRandom();
			int val = random.nextInt(4);
			switch (val) {
			case 0:
				loot = none;
				break;
			case 1:
				loot = none;
				break;
			case 2:
				loot = tea;
				break;
			case 3:
				loot = none;
				break;
			default:
				loot = none;
				break;
			}
			generatedLoot.add(loot);
		}
		return generatedLoot;
	}

	public static class Serializer extends GlobalLootModifierSerializer<CloverSeedsLootModifier> {
		@Override
		public CloverSeedsLootModifier read(ResourceLocation location, JsonObject object,
				ILootCondition[] ailootcondition) {
			return new CloverSeedsLootModifier(ailootcondition);
		}

		@Override
		public JsonObject write(CloverSeedsLootModifier instance) {
			return this.makeConditions(instance.conditions);
		}
	}

}
