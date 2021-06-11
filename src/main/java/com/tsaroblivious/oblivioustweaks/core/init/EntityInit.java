package com.tsaroblivious.oblivioustweaks.core.init;

import com.tsaroblivious.oblivioustweaks.ObliviousTweaks;
import com.tsaroblivious.oblivioustweaks.common.entity.Vampire;

import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class EntityInit {

	public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.ENTITIES,
			ObliviousTweaks.MOD_ID);

	public static final RegistryObject<EntityType<Vampire>> VAMPIRE = ENTITY_TYPES.register("vampire",
			() -> EntityType.Builder.of(Vampire::new, EntityClassification.MONSTER).sized(1.0f, 2.0f)
					.build(new ResourceLocation(ObliviousTweaks.MOD_ID, "vampire").toString()));

}
