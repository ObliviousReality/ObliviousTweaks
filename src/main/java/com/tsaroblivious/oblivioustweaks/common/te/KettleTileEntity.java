package com.tsaroblivious.oblivioustweaks.common.te;

import java.util.Arrays;

import com.tsaroblivious.oblivioustweaks.core.blocks.Kettle;
import com.tsaroblivious.oblivioustweaks.core.init.TileEntityInit;

import net.minecraft.block.AbstractFurnaceBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.CampfireBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;

public class KettleTileEntity extends TileEntity implements ITickableTileEntity {

	private static final int COOKTIME = 600;
	private int currentCookTime = 0;
	private boolean cooked = false;

	private Block[] furnii = { Blocks.FURNACE, Blocks.BLAST_FURNACE, Blocks.SMOKER };
	private Block[] fires = { Blocks.FIRE, Blocks.SOUL_FIRE, Blocks.TORCH, Blocks.SOUL_TORCH, Blocks.LAVA,
			Blocks.MAGMA_BLOCK };

	public KettleTileEntity(TileEntityType<?> p_i48289_1_) {
		super(p_i48289_1_);
	}

	public KettleTileEntity() {
		this(TileEntityInit.KETTLE_TILE_ENTITY.get());
	}

	@Override
	public void tick() {
		if (this.level != null) {
			BlockState block = this.getBlockState();
			BlockState below = this.level.getBlockState(this.worldPosition.below());
			boolean hasHeatSource = false;
			if (Arrays.asList(furnii).contains(below.getBlock())) {
				if (below.getValue(AbstractFurnaceBlock.LIT) == true) {
					hasHeatSource = true;
				}
			} else if (below.getBlock() instanceof CampfireBlock) {
				if (below.getValue(CampfireBlock.LIT) == true) {
					hasHeatSource = true;
				}
			} else if (Arrays.asList(fires).contains(below.getBlock())) {
				hasHeatSource = true;
			}
			if (!block.getValue(Kettle.HOT) && hasHeatSource) {
				if (block.getValue(Kettle.FILLLEVEL) > 0) {
					currentCookTime++;
					if (currentCookTime % 75 == 0) {
						this.level.playSound((PlayerEntity) null, this.worldPosition,
								SoundEvents.BUBBLE_COLUMN_UPWARDS_AMBIENT, SoundCategory.BLOCKS, 1f, 1f);
					}
				}
				if (currentCookTime >= COOKTIME) {
					this.level.setBlock(this.worldPosition,
							this.level.getBlockState(this.worldPosition).setValue(Kettle.HOT, true), 3);
				}
			} else if (!cooked && block.getValue(Kettle.HOT)) {
				this.level.playSound((PlayerEntity) null,
						this.worldPosition, SoundEvents.ANVIL_LAND,
						SoundCategory.BLOCKS, 1f, 1f);
				cooked = true;
			}
			if (block.getValue(Kettle.FILLLEVEL) == 0 && cooked) {
				cooked = false;
				currentCookTime = 0;
				this.level.setBlock(this.worldPosition,
						this.level.getBlockState(this.worldPosition).setValue(Kettle.HOT, false), 3);

			}
			if (!hasHeatSource) {
				cooked = false;
				currentCookTime = 0;
				this.level.setBlock(this.worldPosition,
						this.level.getBlockState(this.worldPosition).setValue(Kettle.HOT, false), 3);
			}
		}

	}

}
