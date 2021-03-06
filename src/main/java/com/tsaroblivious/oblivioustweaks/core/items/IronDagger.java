package com.tsaroblivious.oblivioustweaks.core.items;

import com.tsaroblivious.oblivioustweaks.common.material.CustomToolMaterial;
import com.tsaroblivious.oblivioustweaks.core.init.SoundInit;
import com.tsaroblivious.oblivioustweaks.core.itemgroup.ObliviousTweaksItemGroup;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public class IronDagger extends SwordItem {

	public IronDagger() {
		super(CustomToolMaterial.IRON_DAGGER, 0, -1,
				new Item.Properties().tab(ObliviousTweaksItemGroup.OBLIVIOUS_TWEAKS));
	}

	@Override
	public ActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
		player.swing(hand);
		player.playSound(SoundInit.KNIFE_ATTACK.get(), 2.0F, 1.0F);
		return ActionResult.pass(player.getItemInHand(hand));
	}

}
