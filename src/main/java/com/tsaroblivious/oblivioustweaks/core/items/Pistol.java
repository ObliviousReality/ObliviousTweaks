package com.tsaroblivious.oblivioustweaks.core.items;

import com.tsaroblivious.oblivioustweaks.core.itemgroup.ObliviousTweaksItemGroup;

import net.minecraft.item.CrossbowItem;
import net.minecraft.item.Item;

public class Pistol extends CrossbowItem {

	public Pistol() {
		super(new Item.Properties().tab(ObliviousTweaksItemGroup.OBLIVIOUS_TWEAKS).defaultDurability(250));
	}

}
