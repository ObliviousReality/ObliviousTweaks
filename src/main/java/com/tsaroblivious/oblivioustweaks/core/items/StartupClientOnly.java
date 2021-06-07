package com.tsaroblivious.oblivioustweaks.core.items;

import com.tsaroblivious.oblivioustweaks.ObliviousTweaks;
import com.tsaroblivious.oblivioustweaks.client.entity.SpearRenderer;
import com.tsaroblivious.oblivioustweaks.core.entities.SpearEntity;
import com.tsaroblivious.oblivioustweaks.core.init.EntityInit;
import com.tsaroblivious.oblivioustweaks.core.init.ItemInit;

import net.minecraft.item.ItemModelsProperties;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

public class StartupClientOnly {
	@SubscribeEvent
	public static void onClientStartupEvent(FMLClientSetupEvent event) {
		ObliviousTweaks.LOGGER.debug("FMLClientSetupEvent");
		event.enqueueWork(StartupClientOnly::propertyOverride);
//		RenderingRegistry.registerEntityRenderingHandler(SpearEntity.class, new SpearRenderer.RenderFactor());
	}

	public static void propertyOverride() {
		ItemModelsProperties.register(ItemInit.SPEAR.get(), new ResourceLocation("throwing"), Spear::isBeingThrown);
	}
}
