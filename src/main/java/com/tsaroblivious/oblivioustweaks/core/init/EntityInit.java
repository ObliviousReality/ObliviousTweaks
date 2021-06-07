package com.tsaroblivious.oblivioustweaks.core.init;

import com.tsaroblivious.oblivioustweaks.ObliviousTweaks;
import com.tsaroblivious.oblivioustweaks.client.entity.SpearRenderer;
import com.tsaroblivious.oblivioustweaks.core.entities.SpearEntity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EntityType.Builder;
import net.minecraft.util.registry.Registry;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class EntityInit {

	@SuppressWarnings("deprecation")
	private static <T extends Entity> EntityType<T> register(String key, EntityType.Builder<T> builder) {
		// TODO Auto-generated method stub
		return Registry.register(Registry.ENTITY_TYPE, ObliviousTweaks.MOD_ID + ":" + key, builder.build(key));
	}

	public static final EntityType<com.tsaroblivious.oblivioustweaks.core.entities.SpearEntity> spear = register(
			"spear",
			EntityType.Builder.<com.tsaroblivious.oblivioustweaks.core.entities.SpearEntity>of(SpearEntity::new,
					EntityClassification.MISC).sized(0.5f, 0.5f));

}
