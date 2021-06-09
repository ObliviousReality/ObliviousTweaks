package com.tsaroblivious.oblivioustweaks.common.events;

import com.tsaroblivious.oblivioustweaks.ObliviousTweaks;
import com.tsaroblivious.oblivioustweaks.core.init.ItemInit;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.EntityInteract;
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
			ObliviousTweaks.LOGGER.debug("event");
			PlayerEntity player = event.getPlayer();
			Entity entity = event.getTarget();
			if (player.getItemInHand(event.getHand()).sameItem(new ItemStack(dagger))) {
				if (!player.getCooldowns().isOnCooldown(dagger)) {
					if (entity instanceof LivingEntity) {
						LivingEntity livingEntity = (LivingEntity) entity;
						ObliviousTweaks.LOGGER.debug("holding dagger");
						double dist = livingEntity.distanceTo(player);
						if (dist < 7) {
							float damage = (float) ((float) 2f + (8 - ((dist / 7) * 8)));
							ObliviousTweaks.LOGGER.debug("Damage to deal: " + damage);
							ObliviousTweaks.LOGGER.debug("Before Health: " + livingEntity.getHealth());
							livingEntity.hurt(DamageSource.playerAttack(player), damage);
							player.getCooldowns().addCooldown(dagger, 40);
							ObliviousTweaks.LOGGER.debug("After Health: " + livingEntity.getHealth());
						}
					}
				}
			}
		}
	}
}
