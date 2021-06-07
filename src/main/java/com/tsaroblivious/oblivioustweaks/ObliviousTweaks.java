package com.tsaroblivious.oblivioustweaks;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.tsaroblivious.oblivioustweaks.core.init.BlockInit;
import com.tsaroblivious.oblivioustweaks.core.init.ItemInit;
import com.tsaroblivious.oblivioustweaks.core.itemgroup.ObliviousTweaksItemGroup;

import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod("oblivioustweaks")
@Mod.EventBusSubscriber(modid = ObliviousTweaks.MOD_ID, bus = Bus.MOD)
public class ObliviousTweaks {
	public static final Logger LOGGER = LogManager.getLogger();

	public static final String MOD_ID = "oblivioustweaks";

	public ObliviousTweaks() {
		IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
		ItemInit.ITEMS.register(bus);
		BlockInit.BLOCKS.register(bus);
		MinecraftForge.EVENT_BUS.register(this);
		final ClientSideOnlyModEventRegistrar csomer = new ClientSideOnlyModEventRegistrar(bus);
		DistExecutor.safeRunWhenOn(Dist.CLIENT, () -> csomer::registerClientOnlyEvents);
	}
	

	@SubscribeEvent
	public static void onRegisterItems(final RegistryEvent.Register<Item> event) {
		BlockInit.BLOCKS.getEntries().stream().map(RegistryObject::get).forEach(block -> {
			event.getRegistry()
					.register(new BlockItem(block, new Item.Properties().tab(ObliviousTweaksItemGroup.OBLIVIOUS_TWEAKS))
							.setRegistryName(block.getRegistryName()));
		});

	}
}
