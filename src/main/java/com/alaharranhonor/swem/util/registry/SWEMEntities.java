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
import com.alaharranhonor.swem.entities.PoopEntity;
import com.alaharranhonor.swem.entities.SWEMHorseEntity;
import com.alaharranhonor.swem.entities.WormieBoiEntity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class SWEMEntities {

	public static DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.ENTITIES, SWEM.MOD_ID);

	/**
	 * Init.
	 *
	 * @param modBus the mod bus
	 */
	public static void init(IEventBus modBus) {
		ENTITY_TYPES.register(modBus);
	}

	public static final RegistryObject<EntityType<SWEMHorseEntity>> SWEM_HORSE_ENTITY = ENTITY_TYPES.register("swem_horse",
			() -> EntityType.Builder.of(SWEMHorseEntity::new, EntityClassification.CREATURE)
					.sized(1.60f, 1.99f) // Hitbox Size
					.build(new ResourceLocation(SWEM.MOD_ID, "swem_horse").toString())
	);

	public static final RegistryObject<EntityType<WormieBoiEntity>> WORMIE_BOI_ENTITY = ENTITY_TYPES.register("wormieboi",
			() -> EntityType.Builder.of(WormieBoiEntity::new, EntityClassification.CREATURE)
					.sized(1.0f, 0.4f)
					.build(new ResourceLocation(SWEM.MOD_ID, "wormieboi").toString())
	);
	public static final RegistryObject<EntityType<PoopEntity>> HORSE_POOP_ENTITY = ENTITY_TYPES.register("horse_poop",
			() -> EntityType.Builder.of(PoopEntity::new, EntityClassification.MISC)
					.sized(0.6f, 0.2f) // 0.186f
					.build(new ResourceLocation(SWEM.MOD_ID, "horse_poop").toString())
	);
}
