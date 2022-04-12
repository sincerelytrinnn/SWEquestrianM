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
import com.alaharranhonor.swem.container.*;
import net.minecraft.inventory.container.ContainerType;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class SWEMContainers {
	public static DeferredRegister<ContainerType<?>> CONTAINER_TYPES = DeferredRegister.create(ForgeRegistries.CONTAINERS, SWEM.MOD_ID);

	/**
	 * Init.
	 *
	 * @param modBus the mod bus
	 */
	public static void init(IEventBus modBus) {
		CONTAINER_TYPES.register(modBus);
	}

	public static final RegistryObject<ContainerType<SWEMHorseInventoryContainer>> SWEM_HORSE_CONTAINER = CONTAINER_TYPES.register("swem_horse_container",
			() -> IForgeContainerType.create(SWEMHorseInventoryContainer::new)
	);

	public static final RegistryObject<ContainerType<TackBoxContainer>> TACKBOX_CONTAINER = CONTAINER_TYPES.register("tackbox_container",
			() -> IForgeContainerType.create(TackBoxContainer::new)
	);

	public static final RegistryObject<ContainerType<CantazariteAnvilContainer>> CANTAZARITE_ANVIL_CONTAINER = CONTAINER_TYPES.register("cantazarite_anvil",
			() -> IForgeContainerType.create(CantazariteAnvilContainer::new)
	);

	public static final RegistryObject<ContainerType<SaddlebagAndBedrollContainer>> SADDLE_BAG_AND_BEDROLL_CONTAINER = CONTAINER_TYPES.register("saddle_bag_and_bedroll",
			() -> IForgeContainerType.create(SaddlebagAndBedrollContainer::new)
	);

	public static final RegistryObject<ContainerType<LockerContainer>> LOCKER_CONTAINER = CONTAINER_TYPES.register("locker",
			() -> IForgeContainerType.create(LockerContainer::new)
	);

	public static final RegistryObject<ContainerType<JumpContainer>> JUMP_CONTAINER = CONTAINER_TYPES.register("jump_container",
			() -> IForgeContainerType.create(JumpContainer::new)
	);
}
