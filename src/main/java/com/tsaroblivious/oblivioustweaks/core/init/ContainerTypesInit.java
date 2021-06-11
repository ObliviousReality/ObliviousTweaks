package com.tsaroblivious.oblivioustweaks.core.init;

import com.tsaroblivious.oblivioustweaks.ObliviousTweaks;

import net.minecraft.inventory.container.ContainerType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ContainerTypesInit {

	public static final DeferredRegister<ContainerType<?>> CONTAINER_TYPES = DeferredRegister
			.create(ForgeRegistries.CONTAINERS, ObliviousTweaks.MOD_ID);

}
