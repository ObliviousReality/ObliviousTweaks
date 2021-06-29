package com.tsaroblivious.oblivioustweaks;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.tsaroblivious.oblivioustweaks.common.item.CustomSpawnEggItem;
import com.tsaroblivious.oblivioustweaks.core.blocks.CloverCrop;
import com.tsaroblivious.oblivioustweaks.core.blocks.TeaCrop;
import com.tsaroblivious.oblivioustweaks.core.init.BlockInit;
import com.tsaroblivious.oblivioustweaks.core.init.EffectsInit;
import com.tsaroblivious.oblivioustweaks.core.init.EntityInit;
import com.tsaroblivious.oblivioustweaks.core.init.ItemInit;
import com.tsaroblivious.oblivioustweaks.core.init.LootInit;
import com.tsaroblivious.oblivioustweaks.core.init.SoundInit;
import com.tsaroblivious.oblivioustweaks.core.init.TileEntityInit;
import com.tsaroblivious.oblivioustweaks.core.itemgroup.ObliviousTweaksItemGroup;
import com.tsaroblivious.oblivioustweaks.core.world.gen.ModSpawns;

import net.minecraft.entity.EntityType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.EffectType;
import net.minecraft.potion.Potion;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod("oblivioustweaks")
@Mod.EventBusSubscriber(modid = ObliviousTweaks.MOD_ID, bus = Bus.MOD)
public class ObliviousTweaks {
	public static final Logger LOGGER = LogManager.getLogger();

	public static final String MOD_ID = "oblivioustweaks";

	public ObliviousTweaks() {
		IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
		EntityInit.ENTITY_TYPES.register(bus);
		BlockInit.BLOCKS.register(bus);
		ItemInit.ITEMS.register(bus);
		TileEntityInit.TILE_ENTITY_TYPE.register(bus);
		LootInit.LOOT.register(bus);
		SoundInit.SOUNDS.register(bus);
		MinecraftForge.EVENT_BUS.register(this);
		final ClientSideOnlyModEventRegistrar csomer = new ClientSideOnlyModEventRegistrar(bus);
		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
		DistExecutor.safeRunWhenOn(Dist.CLIENT, () -> csomer::registerClientOnlyEvents);

	}

	private void setup(final FMLCommonSetupEvent event) {
		event.enqueueWork(() -> EffectsInit.addBrewingRecipes());
	}

	@SubscribeEvent
	public static void onRegisterItems(final RegistryEvent.Register<Item> event) {
		BlockInit.BLOCKS.getEntries().stream().filter(block -> !(block.get() instanceof CloverCrop))
				.filter(block -> !(block.get() instanceof TeaCrop)).map(RegistryObject::get).forEach(block -> {
					event.getRegistry().register(
							new BlockItem(block, new Item.Properties().tab(ObliviousTweaksItemGroup.OBLIVIOUS_TWEAKS))
									.setRegistryName(block.getRegistryName()));
				});

	}

	@SubscribeEvent
	public static void onRegisterPotions(final RegistryEvent.Register<Potion> event) {
		event.getRegistry()
				.registerAll(EffectsInit.prevent_disease_potion = new Potion(
						new EffectInstance(EffectsInit.prevent_disease_effect, 6000))
								.setRegistryName(new ResourceLocation(MOD_ID, "preventdisease")),
						EffectsInit.vampirism_potion = new Potion(
								new EffectInstance(EffectsInit.vampirism_effect, Integer.MAX_VALUE))
										.setRegistryName(new ResourceLocation(MOD_ID, "vampirism")),
						EffectsInit.cure_disease_potion = new Potion(
								new EffectInstance(EffectsInit.cure_disease_effect, 1))
										.setRegistryName(new ResourceLocation(MOD_ID, "curedisease")));
	}

	@SubscribeEvent
	public static void onRegisterEffects(final RegistryEvent.Register<Effect> event) {
		event.getRegistry().registerAll(
				EffectsInit.prevent_disease_effect = new EffectsInit.PreventDiseaseEffect(EffectType.BENEFICIAL,
						0xFF00FF).setRegistryName(new ResourceLocation(MOD_ID, "preventdisease")),
				EffectsInit.vampirism_effect = new EffectsInit.VampirismEffect(EffectType.HARMFUL, 0x000000)
						.setRegistryName(MOD_ID, "vampirism"),
				EffectsInit.cure_disease_effect = new EffectsInit.CureDiseaseEffect(EffectType.BENEFICIAL, 0x69C45C)
						.setRegistryName(MOD_ID, "curedisease"));
	}

	@SubscribeEvent
	public void onBiomeLoadFromJSON(BiomeLoadingEvent event) {
		ModSpawns.spawnEntities(event);
	}

	@SubscribeEvent
	public static void onRegisterEntities(final RegistryEvent.Register<EntityType<?>> event) {
		CustomSpawnEggItem.initSpawnEggs();
	}

}
