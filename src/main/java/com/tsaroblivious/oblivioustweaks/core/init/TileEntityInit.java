package com.tsaroblivious.oblivioustweaks.core.init;

import com.tsaroblivious.oblivioustweaks.ObliviousTweaks;
import com.tsaroblivious.oblivioustweaks.common.te.KettleTileEntity;

import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class TileEntityInit {
	public static final DeferredRegister<TileEntityType<?>> TILE_ENTITY_TYPE = DeferredRegister
			.create(ForgeRegistries.TILE_ENTITIES, ObliviousTweaks.MOD_ID);

	public static final RegistryObject<TileEntityType<KettleTileEntity>> KETTLE_TILE_ENTITY = TILE_ENTITY_TYPE
			.register("kettle",
					() -> TileEntityType.Builder.of(KettleTileEntity::new, BlockInit.KETTLE.get()).build(null));

}
