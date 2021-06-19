package com.tsaroblivious.oblivioustweaks.core.items;

import com.tsaroblivious.oblivioustweaks.core.itemgroup.ObliviousTweaksItemGroup;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.Food;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.UseAction;
import net.minecraft.stats.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.DrinkHelper;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public class MilkBottle extends Item {

	public MilkBottle() {
		super(new Item.Properties().tab(ObliviousTweaksItemGroup.OBLIVIOUS_TWEAKS)
				.food(new Food.Builder().nutrition(0).saturationMod(0).alwaysEat().build()).stacksTo(16));
	}

	@Override
	public ItemStack finishUsingItem(ItemStack p_77654_1_, World p_77654_2_, LivingEntity p_77654_3_) {
		if (!p_77654_2_.isClientSide)
			p_77654_3_.curePotionEffects(p_77654_1_); // FORGE - move up so stack.shrink does not turn stack into air
		if (p_77654_3_ instanceof ServerPlayerEntity) {
			ServerPlayerEntity serverplayerentity = (ServerPlayerEntity) p_77654_3_;
			serverplayerentity.awardStat(Stats.ITEM_USED.get(this));

			if (p_77654_1_.isEmpty()) {
				serverplayerentity.setItemInHand(serverplayerentity.getUsedItemHand(),
						new ItemStack(Items.GLASS_BOTTLE));
			} else if (!serverplayerentity.inventory.add(new ItemStack(Items.GLASS_BOTTLE))) {
				serverplayerentity.drop(new ItemStack(Items.GLASS_BOTTLE), false);
			}
			serverplayerentity.refreshContainer(serverplayerentity.inventoryMenu);
			return super.finishUsingItem(p_77654_1_, p_77654_2_, p_77654_3_);

		}
		return p_77654_1_;

	}

	public int getUseDuration(ItemStack p_77626_1_) {
		return 32;
	}

	public UseAction getUseAnimation(ItemStack p_77661_1_) {
		return UseAction.DRINK;
	}

	public ActionResult<ItemStack> use(World p_77659_1_, PlayerEntity p_77659_2_, Hand p_77659_3_) {
		return DrinkHelper.useDrink(p_77659_1_, p_77659_2_, p_77659_3_);
	}

	@Override
	public net.minecraftforge.common.capabilities.ICapabilityProvider initCapabilities(ItemStack stack,
			@javax.annotation.Nullable net.minecraft.nbt.CompoundNBT nbt) {
		return new net.minecraftforge.fluids.capability.wrappers.FluidBucketWrapper(stack);
	}

}
