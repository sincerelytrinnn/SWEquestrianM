package com.alaharranhonor.swem.util.initialization;

import com.alaharranhonor.swem.SWEM;
import net.minecraft.particles.BasicParticleType;
import net.minecraft.particles.ParticleType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class SWEMParticles {

	public static final DeferredRegister<ParticleType<?>> PARTICLES = DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, SWEM.MOD_ID);


	public static final RegistryObject<BasicParticleType> BAD_1 = PARTICLES.register("bad_1", () -> new BasicParticleType(false));
	public static final RegistryObject<BasicParticleType> BAD_2 = PARTICLES.register("bad_2", () -> new BasicParticleType(false));
	public static final RegistryObject<BasicParticleType> BAD_3 = PARTICLES.register("bad_3", () -> new BasicParticleType(false));

}
