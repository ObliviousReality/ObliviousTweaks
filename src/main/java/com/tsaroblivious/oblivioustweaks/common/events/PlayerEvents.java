package com.tsaroblivious.oblivioustweaks.common.events;

import java.util.Map;

import com.tsaroblivious.oblivioustweaks.ObliviousTweaks;
import com.tsaroblivious.oblivioustweaks.core.blocks.Shelf;
import com.tsaroblivious.oblivioustweaks.core.init.BlockInit;
import com.tsaroblivious.oblivioustweaks.core.init.EffectsInit;
import com.tsaroblivious.oblivioustweaks.core.init.ItemInit;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.Minecraft;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.BoatEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.event.TickEvent.PlayerTickEvent;
import net.minecraftforge.event.entity.living.PotionEvent.PotionAddedEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.EntityInteract;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.RightClickBlock;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.Event.Result;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@EventBusSubscriber(modid = ObliviousTweaks.MOD_ID, bus = Bus.FORGE)
public class PlayerEvents {

	@SubscribeEvent
	public static void daggerAttack(EntityInteract event) {
		Item dagger = ItemInit.IRON_DAGGER.get();
		World world = event.getWorld();
		if (!world.isClientSide) {
			PlayerEntity player = event.getPlayer();
			Entity entity = event.getTarget();
			ItemStack heldItem = player.getItemInHand(event.getHand());
			if (heldItem.sameItem(new ItemStack(dagger))) {
				if (!player.getCooldowns().isOnCooldown(dagger)) {
					if (entity instanceof LivingEntity) {
						LivingEntity livingEntity = (LivingEntity) entity;
						double dist = livingEntity.distanceTo(player);
						if (dist < 7) {
							float damage = (float) ((float) 2f + (8 - ((dist / 7) * 8)));
							livingEntity.hurt(DamageSource.playerAttack(player), damage);
							player.getCooldowns().addCooldown(dagger, 40);
							if (!player.abilities.instabuild) {
								Map<Enchantment, Integer> enchants = EnchantmentHelper.getEnchantments(heldItem);
								if (enchants.get(Enchantments.UNBREAKING) != null) {
									float chance = (100f / (float) enchants.get(Enchantments.UNBREAKING) + 1f) / 100f;
									ObliviousTweaks.LOGGER.debug(chance);
									ObliviousTweaks.LOGGER.debug(enchants.get(Enchantments.UNBREAKING));
									if (Math.random() > chance) {
										heldItem.setDamageValue(heldItem.getDamageValue() + 1);
									}
								} else {
									heldItem.setDamageValue(heldItem.getDamageValue() + 1);
								}
							}
						}
					}
				}
			}
		}
	}

	@SubscribeEvent
	public static void bookshelfInteract(RightClickBlock event) {
		World world = event.getWorld();
		if (!world.isClientSide) {
			PlayerEntity player = event.getPlayer();
			if (player.isCrouching()) {
				Minecraft instance = Minecraft.getInstance();
				BlockRayTraceResult blockTrace = (BlockRayTraceResult) instance.hitResult;
				BlockPos pos = blockTrace.getBlockPos();
				BlockState block = world.getBlockState(pos);
				if (block == Blocks.BOOKSHELF.defaultBlockState()) {
					Direction direction = blockTrace.getDirection();
					Direction direction1 = direction.getAxis() == Direction.Axis.Y ? player.getDirection().getOpposite()
							: direction;
					ItemEntity itementity = new ItemEntity(world,
							(double) pos.getX() + 0.5D + (double) direction1.getStepX() * 0.65D,
							(double) pos.getY() + 0.1D,
							(double) pos.getZ() + 0.5D + (double) direction1.getStepZ() * 0.65D,
							new ItemStack(Items.BOOK, 1));
					itementity.setDeltaMovement(
							0.05D * (double) direction1.getStepX() + world.random.nextDouble() * 0.02D, 0.05D,
							0.05D * (double) direction1.getStepZ() + world.random.nextDouble() * 0.02D);
					world.addFreshEntity(itementity);
					BlockState shelfBlock = BlockInit.SHELF.get().defaultBlockState().setValue(Shelf.BOOKS, 2);
					world.setBlock(pos, shelfBlock, 3);
				}
			}
		}
	}

	@SubscribeEvent
	public static void vampirism(AttackEntityEvent event) {
		PlayerEntity player = event.getPlayer();
		if (!player.level.isClientSide) {
			if (player.hasEffect(EffectsInit.vampirism_effect)) {
				Entity entity = event.getTarget();
				if (entity instanceof LivingEntity) {
					LivingEntity livingEntity = (LivingEntity) entity;
					livingEntity.addEffect(new EffectInstance(Effects.WITHER, 200, 2));
				}
			}
		}

	}

	@SubscribeEvent
	public static void cureDisease(PotionAddedEvent event) {
		LivingEntity entity = event.getEntityLiving();
		Effect effectAdded = event.getPotionEffect().getEffect();

		ObliviousTweaks.LOGGER.debug("Drink");
		if (effectAdded == EffectsInit.cure_disease_effect) {
			if (effectAdded == Effects.WITHER || entity.hasEffect(Effects.WITHER)) {
				entity.removeEffect(Effects.WITHER);
			}
			if (effectAdded == Effects.POISON || entity.hasEffect(Effects.POISON)) {
				entity.removeEffect(Effects.POISON);
			}
			if (effectAdded == EffectsInit.vampirism_effect || entity.hasEffect(EffectsInit.vampirism_effect)) {
				entity.removeEffect(EffectsInit.vampirism_effect);
			}
		} else if (entity.hasEffect(EffectsInit.prevent_disease_effect)) {
			if (effectAdded == Effects.WITHER && event.getOldPotionEffect() == null) {
				entity.removeEffect(Effects.WITHER);
			}
			if (effectAdded == Effects.POISON && event.getOldPotionEffect() == null) {
				entity.removeEffect(Effects.POISON);
			}
			if (effectAdded == EffectsInit.vampirism_effect && event.getOldPotionEffect() == null) {
				entity.removeEffect(EffectsInit.vampirism_effect);
			}
		}

	}

	@SubscribeEvent
	public static void burnTest(PlayerTickEvent event) {
		PlayerEntity player = event.player;
		World level = player.level;
		if (!level.isClientSide) {
			if (player.hasEffect(EffectsInit.vampirism_effect)) {
				if (shouldBurn(player, level)) {
					player.setSecondsOnFire(5);
				}
			}
		}
	}

	public static boolean shouldBurn(LivingEntity entity, World level) {
		if (level.isDay() && !level.isClientSide) {
			float f = entity.getBrightness();
			BlockPos blockpos = entity.getVehicle() instanceof BoatEntity
					? (new BlockPos(entity.getX(), (double) Math.round(entity.getY()), entity.getZ())).above()
					: new BlockPos(entity.getX(), (double) Math.round(entity.getY()), entity.getZ());
			if (f > 0.5F && level.random.nextFloat() * 30.0F < (f - 0.4F) * 2.0F && level.canSeeSky(blockpos)) {
				return true;
			}
		}

		return false;
	}

	@SubscribeEvent
	public static void cropEvent(BlockEvent.CropGrowEvent.Pre event) {
		if (event.getState().getBlock() == BlockInit.CLOVER_CROP.get()) {
			double randVal = Math.random();
			if (randVal > 0.5) {
				event.setResult(Result.DENY);
			} else {
				event.setResult(Result.DEFAULT);
			}
		} else {
			event.setResult(Result.DEFAULT);
		}
	}
}
