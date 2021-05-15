package com.alaharranhonor.swem.util.initialization;

import com.alaharranhonor.swem.SWEM;
import net.minecraft.client.particle.HeartParticle;
import net.minecraft.particles.BasicParticleType;
import net.minecraft.particles.ParticleType;
import net.minecraft.particles.ParticleTypes;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class SWEMParticles {

	public static final DeferredRegister<ParticleType<?>> PARTICLES = DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, SWEM.MOD_ID);

	public static void init(IEventBus modBus) {
		PARTICLES.register(modBus);
	}

	public static final RegistryObject<BasicParticleType> BAD = PARTICLES.register("bad", () -> new BasicParticleType(false));

}
