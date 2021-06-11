package com.tsaroblivious.oblivioustweaks.core.blocks;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.Property;
import net.minecraft.state.StateContainer.Builder;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.common.ToolType;

public class Shelf extends Block {

	public static final Property<Integer> BOOKS = IntegerProperty.create("books", 0, 3);

	public static final ItemStack item = new ItemStack(Items.BOOK);

	public Shelf() {
		super(AbstractBlock.Properties.of(Material.WOOD).strength(1.5f, 1.5f).harvestTool(ToolType.AXE));
		this.registerDefaultState(this.stateDefinition.any().setValue(BOOKS, Integer.valueOf(0)));
	}

	@SuppressWarnings("deprecation")
	@Override
	public ActionResultType use(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand handIn,
			BlockRayTraceResult hit) {
		int currentBooks = state.getValue(BOOKS);
		ItemStack held = player.getItemInHand(handIn);
		if (player.isCrouching() && currentBooks > 0 && handIn == Hand.MAIN_HAND) {
			if (!world.isClientSide) {
				world.setBlock(pos, state.setValue(BOOKS, currentBooks - 1), 3);
				Direction direction = hit.getDirection();
				Direction direction1 = direction.getAxis() == Direction.Axis.Y ? player.getDirection().getOpposite()
						: direction;
				ItemEntity itementity = new ItemEntity(world,
						(double) pos.getX() + 0.5D + (double) direction1.getStepX() * 0.65D, (double) pos.getY() + 0.1D,
						(double) pos.getZ() + 0.5D + (double) direction1.getStepZ() * 0.65D,
						new ItemStack(Items.BOOK, 1));
				itementity.setDeltaMovement(0.05D * (double) direction1.getStepX() + world.random.nextDouble() * 0.02D,
						0.05D, 0.05D * (double) direction1.getStepZ() + world.random.nextDouble() * 0.02D);
				world.addFreshEntity(itementity);
			}

		} else if (held.sameItem(item) && currentBooks < 3) {
			if (!world.isClientSide) {
				held.setCount(held.getCount() - 1);
				world.setBlock(pos, state.setValue(BOOKS, currentBooks + 1), 3);
			}
			return ActionResultType.sidedSuccess(world.isClientSide);
		}
		return super.use(state, world, pos, player, handIn, hit);
	}

	@Override
	protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
		builder.add(BOOKS);
	}

	@Override
	public BlockState getStateForPlacement(BlockItemUseContext p_196258_1_) {
		return this.defaultBlockState().setValue(BOOKS, 0);
	}

}
