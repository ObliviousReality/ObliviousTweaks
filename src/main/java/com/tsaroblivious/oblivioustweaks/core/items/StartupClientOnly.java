package com.tsaroblivious.oblivioustweaks.core.items;

import com.tsaroblivious.oblivioustweaks.ObliviousTweaks;
import com.tsaroblivious.oblivioustweaks.client.entity.VampireRenderer;
import com.tsaroblivious.oblivioustweaks.common.entity.Vampire;
import com.tsaroblivious.oblivioustweaks.core.init.BlockInit;
import com.tsaroblivious.oblivioustweaks.core.init.EntityInit;
import com.tsaroblivious.oblivioustweaks.core.init.ItemInit;

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
		ObliviousTweaks.LOGGER.debug("FMLClientSetupEvent");
		event.enqueueWork(StartupClientOnly::propertyOverride);
//		RenderingRegistry.registerEntityRenderingHandler(SpearEntity.class, new SpearRenderer.RenderFactor());
		DeferredWorkQueue.runLater(() -> {
			GlobalEntityTypeAttributes.put(EntityInit.VAMPIRE.get(), Vampire.createMobAttributes().build());
		});
		RenderingRegistry.registerEntityRenderingHandler(EntityInit.VAMPIRE.get(), VampireRenderer::new);
		RenderTypeLookup.setRenderLayer(BlockInit.CLOVER_CROP.get(), RenderType.cutout());
	}

	public static void propertyOverride() {
		ItemModelsProperties.register(ItemInit.SPEAR.get(), new ResourceLocation("throwing"), Spear::isBeingThrown);
	}
}
