package com.tsaroblivious.oblivioustweaks.common.events;

import java.util.Map;

import com.tsaroblivious.oblivioustweaks.ObliviousTweaks;
import com.tsaroblivious.oblivioustweaks.core.init.ItemInit;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.EntityInteract;
import net.minecraftforge.event.world.BlockEvent;
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
	public static void bookshelfInteract(PlayerInteractEvent.RightClickBlock event) {
		ObliviousTweaks.LOGGER.debug("click");
	}
}
