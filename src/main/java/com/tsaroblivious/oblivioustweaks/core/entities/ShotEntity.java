package com.tsaroblivious.oblivioustweaks.core.entities;

import java.util.List;

import com.tsaroblivious.oblivioustweaks.core.init.EntityInit;
import com.tsaroblivious.oblivioustweaks.core.init.ItemInit;

import it.unimi.dsi.fastutil.ints.IntOpenHashSet;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.TNTBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.IPacket;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

public class ShotEntity extends AbstractArrowEntity {

	final double baseDamage = 3f;

	private List<Entity> piercedAndKilledEntities;
	private IntOpenHashSet piercingIgnoreEntityIds;
	private BlockState lastState;

	public ShotEntity(EntityType<? extends AbstractArrowEntity> p_i50172_1_, World p_i50172_2_) {
		super(p_i50172_1_, p_i50172_2_);
		this.referenceItem = null;
	}

	public ShotEntity(LivingEntity shooter, World world, Item referenceItemIn) {
		super(EntityInit.SHOT_ENTITY.get(), shooter, world);
		this.referenceItem = referenceItemIn;
	}

	public ShotEntity(EntityType<? extends AbstractArrowEntity> p_i48547_1_, double p_i48547_2_, double p_i48547_4_,
			double p_i48547_6_, World p_i48547_8_) {
		super(p_i48547_1_, p_i48547_2_, p_i48547_4_, p_i48547_6_, p_i48547_8_);
		this.referenceItem = null;
	}

	public final Item referenceItem;

	@Override
	public ItemStack getPickupItem() {
		return new ItemStack(ItemInit.PISTOL_SHOT.get());
	}

	@Override
	public IPacket<?> getAddEntityPacket() {
		return NetworkHooks.getEntitySpawningPacket(this);
	}

	@Override
	protected void doPostHurtEffects(LivingEntity p_184548_1_) {
		super.doPostHurtEffects(p_184548_1_);
	}

	@Override
	protected void onHitBlock(BlockRayTraceResult p_230299_1_) {
		this.lastState = this.level.getBlockState(p_230299_1_.getBlockPos());
		super.onHitBlock(p_230299_1_);
		Vector3d vector3d = p_230299_1_.getLocation().subtract(this.getX(), this.getY(), this.getZ());
		this.setDeltaMovement(vector3d);
		Vector3d vector3d1 = vector3d.normalize().scale((double) 0.05F);
		this.setPosRaw(this.getX() - vector3d1.x, this.getY() - vector3d1.y, this.getZ() - vector3d1.z);
		this.playSound(this.getHitGroundSoundEvent(), 1.0F, 1.2F / (this.random.nextFloat() * 0.2F + 0.9F));
		this.inGround = true;
		if (this.level.getBlockState(p_230299_1_.getBlockPos()) == Blocks.TNT.defaultBlockState()) {
			this.level.setBlock(p_230299_1_.getBlockPos(), lastState.setValue(TNTBlock.UNSTABLE, true), 3);
		}
		this.shakeTime = 7;
		this.setCritArrow(false);
		this.setPierceLevel((byte) 0);
		this.setSoundEvent(SoundEvents.ARROW_HIT);
		this.setShotFromCrossbow(false);
		this.resetPiercedEntities();
	}

	private void resetPiercedEntities() {
		if (this.piercedAndKilledEntities != null) {
			this.piercedAndKilledEntities.clear();
		}

		if (this.piercingIgnoreEntityIds != null) {
			this.piercingIgnoreEntityIds.clear();
		}

	}
}
