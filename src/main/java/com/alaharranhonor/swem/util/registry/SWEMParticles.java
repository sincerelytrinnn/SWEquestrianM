package com.alaharranhonor.swem.util.registry;

/*
 * All Rights Reserved
 *
 * Copyright (c) 2021, AlaharranHonor, Legenden.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

import com.alaharranhonor.swem.SWEM;
import net.minecraft.particles.BasicParticleType;
import net.minecraft.particles.ParticleType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class SWEMParticles {

  public static final DeferredRegister<ParticleType<?>> PARTICLES =
      DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, SWEM.MOD_ID);

  /**
   * Init.
   *
   * @param modBus the mod bus
   */
  public static void init(IEventBus modBus) {
    PARTICLES.register(modBus);
  }

  // Current particle just picks a random sprite, from the sprite list.
  // if we want particles,that animate/transition, take a look ath the SpellParticle.
  public static final RegistryObject<BasicParticleType> BAD =
      PARTICLES.register("bad", () -> new BasicParticleType(false));
  public static final RegistryObject<BasicParticleType> ECH =
      PARTICLES.register("ech", () -> new BasicParticleType(false));
  public static final RegistryObject<BasicParticleType> MEH =
      PARTICLES.register("meh", () -> new BasicParticleType(false));
  public static final RegistryObject<BasicParticleType> YAY =
      PARTICLES.register("yay", () -> new BasicParticleType(false));
  public static final RegistryObject<BasicParticleType> WOOT =
      PARTICLES.register("woot", () -> new BasicParticleType(false));
}
