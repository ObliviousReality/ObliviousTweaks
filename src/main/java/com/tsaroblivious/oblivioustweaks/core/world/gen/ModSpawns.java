package com.tsaroblivious.oblivioustweaks.core.world.gen;

import java.util.Arrays;

import com.tsaroblivious.oblivioustweaks.ObliviousTweaks;
import com.tsaroblivious.oblivioustweaks.core.init.EntityInit;

import net.minecraft.entity.EntityClassification;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.MobSpawnInfo;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;

@Mod.EventBusSubscriber(modid = ObliviousTweaks.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModSpawns {

	private static Biome.Category[] biomes = { Biome.Category.PLAINS, Biome.Category.FOREST, Biome.Category.DESERT,
			Biome.Category.ICY, Biome.Category.TAIGA, Biome.Category.SWAMP, Biome.Category.JUNGLE, Biome.Category.MESA,
			Biome.Category.SAVANNA };

	public static void spawnEntities(BiomeLoadingEvent event) {
		Biome biome = ForgeRegistries.BIOMES.getValue(event.getName());
		if (Arrays.asList(biomes).contains(biome.getBiomeCategory())) {
			event.getSpawns().getSpawner(EntityClassification.MONSTER)
					.add(new MobSpawnInfo.Spawners(EntityInit.VAMPIRE.get(), 2, 1, 1));
			event.getSpawns().getSpawner(EntityClassification.MONSTER)
					.add(new MobSpawnInfo.Spawners(EntityInit.VAMPIRE_HUNTER.get(), 2, 2, 4));
		}
		if (biome.getBiomeCategory() == Biome.Category.BEACH) {
			event.getSpawns().getSpawner(EntityClassification.MONSTER)
					.add(new MobSpawnInfo.Spawners(EntityInit.PIRATE.get(), 2, 2, 4));
		}
	}

}
