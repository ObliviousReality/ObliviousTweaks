package com.tsaroblivious.oblivioustweaks.core.blocks;

import com.tsaroblivious.oblivioustweaks.core.init.ItemInit;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.CropsBlock;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;

public class CloverCrop extends CropsBlock {

	private static final VoxelShape[] SHAPES = new VoxelShape[] { Block.box(0, 0, 0, 16, 2, 16),
			Block.box(0, 0, 0, 16, 3, 16), Block.box(0, 0, 0, 16, 4, 16), Block.box(0, 0, 0, 16, 5, 16),
			Block.box(0, 0, 0, 16, 6, 16), Block.box(0, 0, 0, 16, 7, 16), Block.box(0, 0, 0, 16, 8, 16),
			Block.box(0, 0, 0, 16, 9, 16) };

	public CloverCrop(Properties p_i48421_1_) {
		super(p_i48421_1_);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected IItemProvider getBaseSeedId() {
		return ItemInit.CLOVER_SEEDS.get();
	}

	@Override
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
		return SHAPES[state.getValue(this.getAgeProperty())];
	}

	@Override
	public boolean isValidBonemealTarget(IBlockReader p_176473_1_, BlockPos p_176473_2_, BlockState p_176473_3_,
			boolean p_176473_4_) {
		return false;
	}

}
