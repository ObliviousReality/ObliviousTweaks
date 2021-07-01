package com.tsaroblivious.oblivioustweaks.core.init;

import com.tsaroblivious.oblivioustweaks.ObliviousTweaks;

import net.minecraft.particles.ParticleType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ParticleInit {

	public static final DeferredRegister<ParticleType<?>> PARTICLES = DeferredRegister
			.create(ForgeRegistries.PARTICLE_TYPES, ObliviousTweaks.MOD_ID);

}
