package com.tsaroblivious.oblivioustweaks.core.blocks;

import com.tsaroblivious.oblivioustweaks.ObliviousTweaks;
import com.tsaroblivious.oblivioustweaks.core.init.BlockInit;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.common.ToolType;

public class Shelf extends Block {

	public Shelf() {
		super(Block.Properties.of(Material.WOOD).strength(1.5f, 1.5f).harvestTool(ToolType.AXE));
	}

	@Override
	public ActionResultType use(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand,
			BlockRayTraceResult brtr) {
		if (hand == Hand.MAIN_HAND) {
			ItemStack held = player.getMainHandItem();
			ItemStack itemStack = new ItemStack(Items.BOOK);
			if(player.isCrouching()) {
				if(!world.isClientSide) {
					world.setBlockAndUpdate(pos, Blocks.IRON_BLOCK.defaultBlockState());
				}else {
					world.addFreshEntity(new ItemEntity(world, pos.getX(), pos.getY(), pos.getZ(), new ItemStack(Items.BOOK)));
				}
			}
			else if (held.sameItem(itemStack)) {
				ObliviousTweaks.LOGGER.debug("Worked");
				if (world.isClientSide) {
					held.setCount(held.getCount() - 1);
				}
				else {
					world.setBlockAndUpdate(pos, Blocks.NETHERITE_BLOCK.defaultBlockState());
				}
			}
		}
		return super.use(state, world, pos, player, hand, brtr);

	}

}
