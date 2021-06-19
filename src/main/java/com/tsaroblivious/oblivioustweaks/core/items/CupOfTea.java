package com.tsaroblivious.oblivioustweaks.core.items;

import com.tsaroblivious.oblivioustweaks.core.init.ItemInit;
import com.tsaroblivious.oblivioustweaks.core.itemgroup.ObliviousTweaksItemGroup;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.Food;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.UseAction;
import net.minecraft.stats.Stats;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;

public class CupOfTea extends Item {

	public CupOfTea() {
		super(new Item.Properties().tab(ObliviousTweaksItemGroup.OBLIVIOUS_TWEAKS)
				.food(new Food.Builder().nutrition(1).saturationMod(3F).alwaysEat().build()).stacksTo(16));
	}

	@Override
	public ItemStack finishUsingItem(ItemStack p_77654_1_, World p_77654_2_, LivingEntity p_77654_3_) {

		if (p_77654_3_ instanceof ServerPlayerEntity) {
			ServerPlayerEntity serverplayerentity = (ServerPlayerEntity) p_77654_3_;
			serverplayerentity.awardStat(Stats.ITEM_USED.get(this));

			if (p_77654_1_.isEmpty()) {
				serverplayerentity.setItemInHand(serverplayerentity.getUsedItemHand(),
						new ItemStack(ItemInit.MUG.get()));
			} else if (!serverplayerentity.inventory.add(new ItemStack(ItemInit.MUG.get()))) {
				serverplayerentity.drop(new ItemStack(ItemInit.MUG.get()), false);
			}
			serverplayerentity.refreshContainer(serverplayerentity.inventoryMenu);
			return super.finishUsingItem(p_77654_1_, p_77654_2_, p_77654_3_);

		}
		return p_77654_1_;

	}

	@Override
	public UseAction getUseAnimation(ItemStack p_77661_1_) {
		return UseAction.DRINK;
	}

	@Override
	public SoundEvent getEatingSound() {
		return null;
	}

}
