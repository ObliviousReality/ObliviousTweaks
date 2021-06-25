package com.tsaroblivious.oblivioustweaks.core.init;

import com.tsaroblivious.oblivioustweaks.ObliviousTweaks;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class SoundInit {

	public static final DeferredRegister<SoundEvent> SOUNDS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS,
			ObliviousTweaks.MOD_ID);

	public static final RegistryObject<SoundEvent> VAMPIRE_ROAR = SOUNDS.register("vampire_roar",
			() -> new SoundEvent(new ResourceLocation(ObliviousTweaks.MOD_ID, "vampire_roar")));

	public static final RegistryObject<SoundEvent> VAMPIRE_HURT = SOUNDS.register("vampire_hurt",
			() -> new SoundEvent(new ResourceLocation(ObliviousTweaks.MOD_ID, "vampire_hurt")));

	public static final RegistryObject<SoundEvent> KNIFE_ATTACK = SOUNDS.register("knife_attack",
			() -> new SoundEvent(new ResourceLocation(ObliviousTweaks.MOD_ID, "knife_attack")));

	public static final RegistryObject<SoundEvent> PISTOL = SOUNDS.register("pistol",
			() -> new SoundEvent(new ResourceLocation(ObliviousTweaks.MOD_ID, "pistol")));

	public static final RegistryObject<SoundEvent> MUSKET = SOUNDS.register("musket",
			() -> new SoundEvent(new ResourceLocation(ObliviousTweaks.MOD_ID, "musket")));
	
	public static final RegistryObject<SoundEvent> SILVER_HIT = SOUNDS.register("silver_hit",
			() -> new SoundEvent(new ResourceLocation(ObliviousTweaks.MOD_ID, "silver_hit")));
}
