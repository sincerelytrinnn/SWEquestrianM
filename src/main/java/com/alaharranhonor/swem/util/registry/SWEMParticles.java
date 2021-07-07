package com.alaharranhonor.swem.util.registry;

import com.alaharranhonor.swem.SWEM;
import net.minecraft.particles.BasicParticleType;
import net.minecraft.particles.ParticleType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class SWEMParticles {

	public static final DeferredRegister<ParticleType<?>> PARTICLES = DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, SWEM.MOD_ID);

	public static void init(IEventBus modBus) {
		PARTICLES.register(modBus);
	}

	// Current particle just picks a random sprite, from the sprite list.
	// if we want particles,that animate/transition, take a look ath the SpellParticle.
	public static final RegistryObject<BasicParticleType> BAD = PARTICLES.register("bad", () -> new BasicParticleType(false));
	public static final RegistryObject<BasicParticleType> ECH = PARTICLES.register("ech", () -> new BasicParticleType(false));
	public static final RegistryObject<BasicParticleType> MEH = PARTICLES.register("meh", () -> new BasicParticleType(false));
	public static final RegistryObject<BasicParticleType> YAY = PARTICLES.register("yay", () -> new BasicParticleType(false));
	public static final RegistryObject<BasicParticleType> WOOT = PARTICLES.register("woot", () -> new BasicParticleType(false));

}
