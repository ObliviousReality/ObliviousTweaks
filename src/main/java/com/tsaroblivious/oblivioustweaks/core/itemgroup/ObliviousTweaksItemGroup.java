package com.tsaroblivious.oblivioustweaks.core.itemgroup;

import com.tsaroblivious.oblivioustweaks.core.init.ItemInit;

import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;

public class ObliviousTweaksItemGroup extends ItemGroup {

	public static final ObliviousTweaksItemGroup OBLIVIOUS_TWEAKS = new ObliviousTweaksItemGroup(ItemGroup.TABS.length,
			"oblivioustweaks");

	public ObliviousTweaksItemGroup(int index, String label) {
		super(index, label);
	}

	@Override
	public ItemStack makeIcon() {
		return new ItemStack(ItemInit.SPEAR.get());
	}

}
