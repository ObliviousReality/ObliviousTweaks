package com.tsaroblivious.oblivioustweaks.core.blocks;

import java.util.stream.Stream;

import com.tsaroblivious.oblivioustweaks.ObliviousTweaks;
import com.tsaroblivious.oblivioustweaks.core.init.ItemInit;
import com.tsaroblivious.oblivioustweaks.core.init.TileEntityInit;

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
import net.minecraft.potion.PotionUtils;
import net.minecraft.potion.Potions;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer.Builder;
import net.minecraft.tileentity.TileEntity;
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

public class Kettle extends Block {

	private static final DirectionProperty FACING = HorizontalBlock.FACING;
	public static final IntegerProperty FILLLEVEL = IntegerProperty.create("filllevel", 0, 3);
	public static final BooleanProperty HOT = BooleanProperty.create("hot");

	public static final ItemStack WATER_BUCKET = new ItemStack(Items.WATER_BUCKET);
	public static final ItemStack WATER_BOTTLE = new ItemStack(Items.POTION);
	public static final ItemStack BUCKET = new ItemStack(Items.BUCKET);
	public static final ItemStack GLASS_BOTTLE = new ItemStack(Items.GLASS_BOTTLE);

	private static final VoxelShape SHAPE_N = Stream.of(Block.box(7, 13, 5, 9, 14, 13), Block.box(4, 0, 5, 12, 1, 13),
			Block.box(3, 1, 4, 13, 2, 14), Block.box(2, 2, 3, 14, 5, 15), Block.box(3, 5, 4, 13, 7, 14),
			Block.box(4, 7, 5, 12, 8, 13), Block.box(6, 8, 7, 10, 9, 11), Block.box(7, 9, 8, 9, 10, 10),
			Block.box(5, 8, 6, 6, 9, 12), Block.box(10, 8, 6, 11, 9, 12), Block.box(6, 8, 11, 10, 9, 12),
			Block.box(6, 8, 6, 10, 9, 7), Block.box(7, 5, 3, 9, 7, 4), Block.box(7, 4, 2, 9, 5, 3),
			Block.box(7, 5, 1, 9, 6, 2), Block.box(7, 5, 2, 9, 8, 3), Block.box(7, 6, 0, 9, 9, 2),
			Block.box(7, 7, 13, 9, 13, 14), Block.box(7, 7, 4, 9, 13, 5)).reduce((v1, v2) -> {
				return VoxelShapes.joinUnoptimized(v1, v2, IBooleanFunction.OR);
			}).get();

	public static final VoxelShape SHAPE_E = Stream.of(Block.box(3, 13, 7, 11, 14, 9), Block.box(3, 0, 4, 11, 1, 12),
			Block.box(2, 1, 3, 12, 2, 13), Block.box(1, 2, 2, 13, 5, 14), Block.box(2, 5, 3, 12, 7, 13),
			Block.box(3, 7, 4, 11, 8, 12), Block.box(5, 8, 6, 9, 9, 10), Block.box(6, 9, 7, 8, 10, 9),
			Block.box(4, 8, 5, 10, 9, 6), Block.box(4, 8, 10, 10, 9, 11), Block.box(4, 8, 6, 5, 9, 10),
			Block.box(9, 8, 6, 10, 9, 10), Block.box(12, 5, 7, 13, 7, 9), Block.box(13, 4, 7, 14, 5, 9),
			Block.box(14, 5, 7, 15, 6, 9), Block.box(13, 5, 7, 14, 8, 9), Block.box(14, 6, 7, 16, 9, 9),
			Block.box(2, 7, 7, 3, 13, 9), Block.box(11, 7, 7, 12, 13, 9)).reduce((v1, v2) -> {
				return VoxelShapes.joinUnoptimized(v1, v2, IBooleanFunction.OR);
			}).get();

	public static final VoxelShape SHAPE_S = Stream.of(Block.box(7, 13, 3, 9, 14, 11), Block.box(4, 0, 3, 12, 1, 11),
			Block.box(3, 1, 2, 13, 2, 12), Block.box(2, 2, 1, 14, 5, 13), Block.box(3, 5, 2, 13, 7, 12),
			Block.box(4, 7, 3, 12, 8, 11), Block.box(6, 8, 5, 10, 9, 9), Block.box(7, 9, 6, 9, 10, 8),
			Block.box(10, 8, 4, 11, 9, 10), Block.box(5, 8, 4, 6, 9, 10), Block.box(6, 8, 4, 10, 9, 5),
			Block.box(6, 8, 9, 10, 9, 10), Block.box(7, 5, 12, 9, 7, 13), Block.box(7, 4, 13, 9, 5, 14),
			Block.box(7, 5, 14, 9, 6, 15), Block.box(7, 5, 13, 9, 8, 14), Block.box(7, 6, 14, 9, 9, 16),
			Block.box(7, 7, 2, 9, 13, 3), Block.box(7, 7, 11, 9, 13, 12)).reduce((v1, v2) -> {
				return VoxelShapes.joinUnoptimized(v1, v2, IBooleanFunction.OR);
			}).get();

	public static final VoxelShape SHAPE_W = Stream.of(Block.box(5, 13, 7, 13, 14, 9), Block.box(5, 0, 4, 13, 1, 12),
			Block.box(4, 1, 3, 14, 2, 13), Block.box(3, 2, 2, 15, 5, 14), Block.box(4, 5, 3, 14, 7, 13),
			Block.box(5, 7, 4, 13, 8, 12), Block.box(7, 8, 6, 11, 9, 10), Block.box(8, 9, 7, 10, 10, 9),
			Block.box(6, 8, 10, 12, 9, 11), Block.box(6, 8, 5, 12, 9, 6), Block.box(11, 8, 6, 12, 9, 10),
			Block.box(6, 8, 6, 7, 9, 10), Block.box(3, 5, 7, 4, 7, 9), Block.box(2, 4, 7, 3, 5, 9),
			Block.box(1, 5, 7, 2, 6, 9), Block.box(2, 5, 7, 3, 8, 9), Block.box(0, 6, 7, 2, 9, 9),
			Block.box(13, 7, 7, 14, 13, 9), Block.box(4, 7, 7, 5, 13, 9)).reduce((v1, v2) -> {
				return VoxelShapes.joinUnoptimized(v1, v2, IBooleanFunction.OR);
			}).get();

	public Kettle() {
		super(AbstractBlock.Properties.of(Material.METAL, MaterialColor.TERRACOTTA_GRAY).strength(5f, 6f)
				.harvestTool(ToolType.PICKAXE).harvestLevel(2).sound(SoundType.METAL));
		this.registerDefaultState(
				this.stateDefinition.any().setValue(FILLLEVEL, Integer.valueOf(0)).setValue(HOT, false));
	}

	@SuppressWarnings("deprecation")
	@Override
	public ActionResultType use(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand,
			BlockRayTraceResult result) {
		ItemStack held = player.getItemInHand(hand);
		int fillLevel = state.getValue(FILLLEVEL);
		boolean hot = state.getValue(HOT);
		if (held.sameItem(WATER_BUCKET)) {
			if (!hot) {
				if (fillLevel != 3) {
					if (!world.isClientSide) {
						world.setBlock(pos, state.setValue(FILLLEVEL, 3), 3);
						world.playSound((PlayerEntity) null, pos, SoundEvents.BUCKET_EMPTY, SoundCategory.BLOCKS, 1.0F,
								1.0F);
					}
					if (!player.abilities.instabuild) {
						player.setItemInHand(hand, new ItemStack(Items.BUCKET));
					}

					return ActionResultType.sidedSuccess(world.isClientSide);
				}
			}
		} else if (held.sameItem(WATER_BOTTLE)) {
			if (!hot) {
				if (PotionUtils.getPotion(held) == Potions.WATER) {
					if (fillLevel != 3) {
						if (!world.isClientSide) {
							world.setBlock(pos, state.setValue(FILLLEVEL, fillLevel + 1), 3);
							world.playSound((PlayerEntity) null, pos, SoundEvents.BOTTLE_EMPTY, SoundCategory.BLOCKS,
									1.0F, 1.0F);
						}
						if (!player.abilities.instabuild) {
							player.setItemInHand(hand, new ItemStack(Items.GLASS_BOTTLE));
							if (player instanceof ServerPlayerEntity) {
								((ServerPlayerEntity) player).refreshContainer(player.inventoryMenu);
							}
						}

						return ActionResultType.sidedSuccess(world.isClientSide);
					}
				}
			}
		} else if (held.sameItem(BUCKET)) {
			if (fillLevel == 3) {
				if (hot) {
					if (!world.isClientSide) {
						world.setBlock(pos, state.setValue(FILLLEVEL, 0), 3);
						world.playSound((PlayerEntity) null, pos, SoundEvents.BUCKET_FILL_LAVA, SoundCategory.BLOCKS,
								1.0F, 1.0F);
					}
					if (!player.abilities.instabuild) {
						held.shrink(1);
						if (held.isEmpty()) {
							player.setItemInHand(hand, new ItemStack(ItemInit.HOT_WATER_BUCKET.get())); // HOT WATER
																										// BUCKET
						} else if (!player.inventory.add(new ItemStack(ItemInit.HOT_WATER_BUCKET.get()))) {// HOT WATER
																											// BUCKET
							player.drop(new ItemStack(ItemInit.HOT_WATER_BUCKET.get()), false);// HOT WATER BUCKET
						}
					}

					return ActionResultType.sidedSuccess(world.isClientSide);
				} else {
					if (!world.isClientSide) {
						world.setBlock(pos, state.setValue(FILLLEVEL, 0), 3);
						world.playSound((PlayerEntity) null, pos, SoundEvents.BUCKET_FILL, SoundCategory.BLOCKS, 1.0F,
								1.0F);
					}
					if (!player.abilities.instabuild) {
						held.shrink(1);
						if (held.isEmpty()) {
							player.setItemInHand(hand, new ItemStack(Items.WATER_BUCKET));
						} else if (!player.inventory.add(new ItemStack(Items.WATER_BUCKET))) {
							player.drop(new ItemStack(Items.WATER_BUCKET), false);
						}
					}

					return ActionResultType.sidedSuccess(world.isClientSide);
				}
			}
		} else if (held.sameItem(GLASS_BOTTLE)) {
			if (fillLevel > 0) {
				if (hot) {
					if (!world.isClientSide) {
						world.setBlock(pos, state.setValue(FILLLEVEL, fillLevel - 1), 3);
						world.playSound((PlayerEntity) null, pos, SoundEvents.BOTTLE_FILL, SoundCategory.BLOCKS, 1.0F,
								1.0F);
					}
					if (!player.abilities.instabuild) {
						ItemStack waterBottle = new ItemStack(ItemInit.HOT_WATER_BOTTLE.get());
						held.shrink(1);
						if (held.isEmpty()) {
							player.setItemInHand(hand, waterBottle);
						} else if (!player.inventory.add(waterBottle)) {
							player.drop(waterBottle, false);
						} else if (player instanceof ServerPlayerEntity) {
							((ServerPlayerEntity) player).refreshContainer(player.inventoryMenu);
						}
					}
				} else {
					if (!world.isClientSide) {
						world.setBlock(pos, state.setValue(FILLLEVEL, fillLevel - 1), 3);
						world.playSound((PlayerEntity) null, pos, SoundEvents.BOTTLE_FILL, SoundCategory.BLOCKS, 1.0F,
								1.0F);
					}
					if (!player.abilities.instabuild) {
						ItemStack waterBottle = PotionUtils.setPotion(new ItemStack(Items.POTION), Potions.WATER);
						held.shrink(1);
						if (held.isEmpty()) {
							player.setItemInHand(hand, waterBottle);
						} else if (!player.inventory.add(waterBottle)) {
							player.drop(waterBottle, false);
						} else if (player instanceof ServerPlayerEntity) {
							((ServerPlayerEntity) player).refreshContainer(player.inventoryMenu);
						}
					}
				}

				return ActionResultType.sidedSuccess(world.isClientSide);
			}
		} else {
			ObliviousTweaks.LOGGER.debug(state.getValue(Kettle.FILLLEVEL));
		}

		if (player instanceof ServerPlayerEntity) {
			((ServerPlayerEntity) player).refreshContainer(player.inventoryMenu);
		}

		return super.use(state, world, pos, player, hand, result);
	}

	@Override
	public BlockState getStateForPlacement(BlockItemUseContext context) {
		return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite())
				.setValue(FILLLEVEL, 0).setValue(HOT, false);
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
		builder.add(FILLLEVEL);
		builder.add(HOT);
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

	@Override
	public boolean hasTileEntity(BlockState state) {
		return true;
	}

	@Override
	public TileEntity createTileEntity(BlockState state, IBlockReader world) {
		return TileEntityInit.KETTLE_TILE_ENTITY.get().create();
	}

}
