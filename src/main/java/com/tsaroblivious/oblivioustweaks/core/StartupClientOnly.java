package com.tsaroblivious.oblivioustweaks.core;

import com.tsaroblivious.oblivioustweaks.client.entity.ShotRenderer;
import com.tsaroblivious.oblivioustweaks.client.entity.SpearRenderer;
import com.tsaroblivious.oblivioustweaks.client.entity.VampireHunterRenderer;
import com.tsaroblivious.oblivioustweaks.client.entity.VampireRenderer;
import com.tsaroblivious.oblivioustweaks.common.entity.Vampire;
import com.tsaroblivious.oblivioustweaks.common.entity.VampireHunter;
import com.tsaroblivious.oblivioustweaks.core.init.BlockInit;
import com.tsaroblivious.oblivioustweaks.core.init.EntityInit;
import com.tsaroblivious.oblivioustweaks.core.init.ItemInit;
import com.tsaroblivious.oblivioustweaks.core.items.Pistol;
import com.tsaroblivious.oblivioustweaks.core.items.Spear;

import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.entity.ai.attributes.GlobalEntityTypeAttributes;
import net.minecraft.item.ItemModelsProperties;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DeferredWorkQueue;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

public class StartupClientOnly {
	@SuppressWarnings("deprecation")
	@SubscribeEvent
	public static void onClientStartupEvent(FMLClientSetupEvent event) {
		event.enqueueWork(StartupClientOnly::propertyOverride);
		DeferredWorkQueue.runLater(() -> {
			GlobalEntityTypeAttributes.put(EntityInit.VAMPIRE.get(), Vampire.createMobAttributes().build());
		});
		DeferredWorkQueue.runLater(() -> {
			GlobalEntityTypeAttributes.put(EntityInit.VAMPIRE_HUNTER.get(),
					VampireHunter.createMobAttributes().build());
		});
		RenderingRegistry.registerEntityRenderingHandler(EntityInit.VAMPIRE.get(), VampireRenderer::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityInit.VAMPIRE_HUNTER.get(), VampireHunterRenderer::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityInit.SPEAR_ENTITY.get(), SpearRenderer::new);
		RenderTypeLookup.setRenderLayer(BlockInit.CLOVER_CROP.get(), RenderType.cutout());
		RenderTypeLookup.setRenderLayer(BlockInit.TEA_CROP.get(), RenderType.cutout());
		RenderTypeLookup.setRenderLayer(BlockInit.KETTLE.get(), RenderType.cutout());
		RenderingRegistry.registerEntityRenderingHandler(EntityInit.SHOT_ENTITY.get(), ShotRenderer::new);
	}

	public static void propertyOverride() {
		ItemModelsProperties.register(ItemInit.SPEAR.get(), new ResourceLocation("throwing"), Spear::isBeingThrown);
		ItemModelsProperties.register(ItemInit.PISTOL.get(), new ResourceLocation("loading"), Pistol::isBeingLoaded);
		ItemModelsProperties.register(ItemInit.PISTOL.get(), new ResourceLocation("loaded"), Pistol::isCharged);
	}
}
