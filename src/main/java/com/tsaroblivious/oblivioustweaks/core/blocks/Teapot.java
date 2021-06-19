package com.tsaroblivious.oblivioustweaks.core.blocks;

import java.util.stream.Stream;

import com.tsaroblivious.oblivioustweaks.core.init.ItemInit;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer.Builder;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.IBooleanFunction;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.common.ToolType;

public class Teapot extends Block {

	private static final DirectionProperty FACING = HorizontalBlock.FACING;

	private static final IntegerProperty TEA_FILL = IntegerProperty.create("tea_fill", 0, 3);
	private static final IntegerProperty WATER_FILL = IntegerProperty.create("water_fill", 0, 3);

	private static final VoxelShape SHAPE_N = Stream.of(Block.box(4, 0, 4, 12, 1, 12), Block.box(4, 1, 3, 12, 7, 13),
			Block.box(12, 2, 4, 13, 6, 12), Block.box(3, 2, 4, 4, 6, 12), Block.box(4, 7, 4, 12, 8, 12),
			Block.box(6, 9, 6, 10, 10, 10), Block.box(5, 8, 5, 11, 9, 11), Block.box(7, 10, 7, 9, 11, 9),
			Block.box(7, 3, 0, 9, 7, 2), Block.box(6, 2, 2, 10, 6, 3), Block.box(7, 3, 15, 9, 7, 16),
			Block.box(7, 2, 13, 9, 3, 15), Block.box(7, 6, 13, 9, 7, 15)).reduce((v1, v2) -> {
				return VoxelShapes.joinUnoptimized(v1, v2, IBooleanFunction.OR);
			}).get();
	private static final VoxelShape SHAPE_E = Stream.of(Block.box(4, 0, 4, 12, 1, 12), Block.box(3, 1, 4, 13, 7, 12),
			Block.box(4, 2, 12, 12, 6, 13), Block.box(4, 2, 3, 12, 6, 4), Block.box(4, 7, 4, 12, 8, 12),
			Block.box(6, 9, 6, 10, 10, 10), Block.box(5, 8, 5, 11, 9, 11), Block.box(7, 10, 7, 9, 11, 9),
			Block.box(14, 3, 7, 16, 7, 9), Block.box(13, 2, 6, 14, 6, 10), Block.box(0, 3, 7, 1, 7, 9),
			Block.box(1, 2, 7, 3, 3, 9), Block.box(1, 6, 7, 3, 7, 9)).reduce((v1, v2) -> {
				return VoxelShapes.joinUnoptimized(v1, v2, IBooleanFunction.OR);
			}).get();
	private static final VoxelShape SHAPE_S = Stream.of(Block.box(4, 0, 4, 12, 1, 12), Block.box(4, 1, 3, 12, 7, 13),
			Block.box(3, 2, 4, 4, 6, 12), Block.box(12, 2, 4, 13, 6, 12), Block.box(4, 7, 4, 12, 8, 12),
			Block.box(6, 9, 6, 10, 10, 10), Block.box(5, 8, 5, 11, 9, 11), Block.box(7, 10, 7, 9, 11, 9),
			Block.box(7, 3, 14, 9, 7, 16), Block.box(6, 2, 13, 10, 6, 14), Block.box(7, 3, 0, 9, 7, 1),
			Block.box(7, 2, 1, 9, 3, 3), Block.box(7, 6, 1, 9, 7, 3)).reduce((v1, v2) -> {
				return VoxelShapes.joinUnoptimized(v1, v2, IBooleanFunction.OR);
			}).get();
	private static final VoxelShape SHAPE_W = Stream.of(Block.box(4, 0, 4, 12, 1, 12), Block.box(3, 1, 4, 13, 7, 12),
			Block.box(4, 2, 3, 12, 6, 4), Block.box(4, 2, 12, 12, 6, 13), Block.box(4, 7, 4, 12, 8, 12),
			Block.box(6, 9, 6, 10, 10, 10), Block.box(5, 8, 5, 11, 9, 11), Block.box(7, 10, 7, 9, 11, 9),
			Block.box(0, 3, 7, 2, 7, 9), Block.box(2, 2, 6, 3, 6, 10), Block.box(15, 3, 7, 16, 7, 9),
			Block.box(13, 2, 7, 15, 3, 9), Block.box(13, 6, 7, 15, 7, 9)).reduce((v1, v2) -> {
				return VoxelShapes.joinUnoptimized(v1, v2, IBooleanFunction.OR);
			}).get();

	public Teapot() {
		super(AbstractBlock.Properties.of(Material.METAL, MaterialColor.TERRACOTTA_LIGHT_GRAY).strength(5f, 6f)
				.harvestTool(ToolType.PICKAXE).harvestLevel(2).sound(SoundType.METAL));
	}

	@SuppressWarnings("deprecation")
	@Override
	public ActionResultType use(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand,
			BlockRayTraceResult result) {
		ItemStack held = player.getItemInHand(hand);
		int waterFill = state.getValue(WATER_FILL);
		int teaFill = state.getValue(TEA_FILL);
		int totalFill = waterFill + teaFill;
		if (totalFill != 3 && teaFill == 0) {
			if (held.sameItem(new ItemStack(ItemInit.HOT_WATER_BOTTLE.get()))) {
				if (!world.isClientSide) {
					world.setBlock(pos, state.setValue(WATER_FILL, waterFill + 1), 3);
					world.playSound((PlayerEntity) null, pos, SoundEvents.BOTTLE_EMPTY, SoundCategory.BLOCKS, 1.0F,
							1.0F);
				}
				if (!player.abilities.instabuild) {
					held.shrink(1);
					if (held.isEmpty()) {
						player.setItemInHand(hand, new ItemStack(Items.GLASS_BOTTLE));
					} else if (!player.inventory.add(new ItemStack(Items.GLASS_BOTTLE))) {
						player.drop(new ItemStack(Items.GLASS_BOTTLE), false);
					}
				}
			} else if (held.sameItem(new ItemStack(ItemInit.HOT_WATER_BUCKET.get()))) {
				if (!world.isClientSide) {
					world.setBlock(pos, state.setValue(WATER_FILL, 3), 3);
					world.playSound((PlayerEntity) null, pos, SoundEvents.BUCKET_EMPTY, SoundCategory.BLOCKS, 1.0F,
							1.0F);
				}
				if (!player.abilities.instabuild) {
					player.setItemInHand(hand, new ItemStack(Items.BUCKET));
				}
			}
		} else if (teaFill == 0) {
			if (held.sameItem(new ItemStack(ItemInit.TEA_LEAF.get()))) {
				if (!world.isClientSide) {
					world.setBlock(pos, state.setValue(TEA_FILL, waterFill).setValue(WATER_FILL, 0), 3);
					world.playSound((PlayerEntity) null, pos, SoundEvents.GENERIC_SPLASH, SoundCategory.BLOCKS, 1.0F,
							1.0F);
				}
				held.shrink(1);
			}
		} else if (teaFill > 0) {
			if (held.sameItem(new ItemStack(ItemInit.MUG.get()))) {
				if (!world.isClientSide) {
					world.setBlock(pos, state.setValue(TEA_FILL, teaFill - 1), 3);
					world.playSound((PlayerEntity) null, pos, SoundEvents.BOTTLE_FILL, SoundCategory.BLOCKS, 1.0F,
							1.0F);
				}
				if (!player.abilities.instabuild) {
					held.shrink(1);
					if (held.isEmpty()) {
						player.setItemInHand(hand, new ItemStack(ItemInit.BLACK_TEA.get()));
					} else if (!player.inventory.add(new ItemStack(ItemInit.BLACK_TEA.get()))) {
						player.drop(new ItemStack(ItemInit.BLACK_TEA.get()), false);
					} else if (player instanceof ServerPlayerEntity) {
						((ServerPlayerEntity) player).refreshContainer(player.inventoryMenu);
					}
				}
			}
		}

		if (player instanceof ServerPlayerEntity) {
			((ServerPlayerEntity) player).refreshContainer(player.inventoryMenu);
		}
		return super.use(state, world, pos, player, hand, result);
	}

	@Override
	public BlockState getStateForPlacement(BlockItemUseContext context) {
		return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite())
				.setValue(TEA_FILL, 0).setValue(WATER_FILL, 0);
	}

	@Override
	public BlockState rotate(BlockState state, Rotation rot) {
		return state.setValue(FACING, rot.rotate(state.getValue(FACING)));
	}

	@SuppressWarnings("deprecation")
	@Override
	public BlockState mirror(BlockState state, Mirror mirrorIn) {
		return state.rotate(mirrorIn.getRotation(state.getValue(FACING)));
	}

	@Override
	protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
		builder.add(FACING);
		builder.add(TEA_FILL);
		builder.add(WATER_FILL);
	}

	@Override
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
		switch (state.getValue(FACING)) {
		case NORTH:
			return SHAPE_N;
		case EAST:
			return SHAPE_E;
		case SOUTH:
			return SHAPE_S;
		case WEST:
			return SHAPE_W;
		default:
			return SHAPE_N;
		}
	}

	@Override
	public float getShadeBrightness(BlockState p_220080_1_, IBlockReader p_220080_2_, BlockPos p_220080_3_) {
		return 0.6f;
	}

}
