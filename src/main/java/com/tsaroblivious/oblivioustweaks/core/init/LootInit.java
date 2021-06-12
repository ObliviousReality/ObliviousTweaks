package com.tsaroblivious.oblivioustweaks.core.init;

import com.tsaroblivious.oblivioustweaks.ObliviousTweaks;
import com.tsaroblivious.oblivioustweaks.common.loot.CloverSeedsLootModifier;

import net.minecraftforge.common.loot.GlobalLootModifierSerializer;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class LootInit {

	public static final DeferredRegister<GlobalLootModifierSerializer<?>> LOOT = DeferredRegister
			.create(ForgeRegistries.LOOT_MODIFIER_SERIALIZERS, ObliviousTweaks.MOD_ID);

	public static final RegistryObject<GlobalLootModifierSerializer<CloverSeedsLootModifier>> CLOVER_MODIFIER = LOOT
			.register("mineshaft_loot", CloverSeedsLootModifier.Serializer::new);

}
