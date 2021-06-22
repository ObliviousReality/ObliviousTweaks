package com.tsaroblivious.oblivioustweaks.core.init;

import com.tsaroblivious.oblivioustweaks.ObliviousTweaks;
import com.tsaroblivious.oblivioustweaks.common.entity.Vampire;
import com.tsaroblivious.oblivioustweaks.common.entity.VampireHunter;
import com.tsaroblivious.oblivioustweaks.core.entities.ShotEntity;
import com.tsaroblivious.oblivioustweaks.core.entities.SpearEntity;

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

	public static final RegistryObject<EntityType<ShotEntity>> SHOT_ENTITY = ENTITY_TYPES.register("shot_entity",
			() -> EntityType.Builder.<ShotEntity>of(ShotEntity::new, EntityClassification.MISC).sized(0.1f, 0.1f)
					.build(new ResourceLocation(ObliviousTweaks.MOD_ID, "shot_entity").toString()));

	public static final RegistryObject<EntityType<VampireHunter>> VAMPIRE_HUNTER = ENTITY_TYPES.register(
			"vampire_hunter",
			() -> EntityType.Builder.of(VampireHunter::new, EntityClassification.MONSTER).sized(1.0f, 2.0f)
					.build(new ResourceLocation(ObliviousTweaks.MOD_ID, "vampire_hunter").toString()));

	public static final RegistryObject<EntityType<SpearEntity>> SPEAR_ENTITY = ENTITY_TYPES.register("spear_entity",
			() -> EntityType.Builder.<SpearEntity>of(SpearEntity::new, EntityClassification.MISC).sized(0.2f, 0.2f)
					.build(new ResourceLocation(ObliviousTweaks.MOD_ID, "spear_entity").toString()));

}
