package com.tsaroblivious.oblivioustweaks.core.init;

import com.tsaroblivious.oblivioustweaks.ObliviousTweaks;

import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class TileEntityInit {
	public static final DeferredRegister<TileEntityType<?>> TILE_ENTITY_TYPE = DeferredRegister
			.create(ForgeRegistries.TILE_ENTITIES, ObliviousTweaks.MOD_ID);



}
