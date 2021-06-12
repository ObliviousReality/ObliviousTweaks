package com.tsaroblivious.oblivioustweaks.core.init;

import com.tsaroblivious.oblivioustweaks.ObliviousTweaks;
import com.tsaroblivious.oblivioustweaks.core.blocks.CloverCrop;
import com.tsaroblivious.oblivioustweaks.core.blocks.Shelf;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class BlockInit {
	public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS,
			ObliviousTweaks.MOD_ID);

	public static final RegistryObject<Block> SHELF = BLOCKS.register("shelf", () -> new Shelf());

	public static final RegistryObject<Block> CLOVER_CROP = BLOCKS.register("clover_crop",
			() -> new CloverCrop(Block.Properties.of(Material.PLANT, MaterialColor.COLOR_GREEN).strength(0)
					.noCollission().randomTicks().sound(SoundType.CROP)));

	public static final RegistryObject<Block> GILDED_BLOCK = BLOCKS.register("gilded_block",
			() -> new Block(AbstractBlock.Properties.of(Material.METAL, MaterialColor.GOLD).strength(5f, 6f)
					.harvestTool(ToolType.PICKAXE).harvestLevel(2).sound(SoundType.METAL)));
}
