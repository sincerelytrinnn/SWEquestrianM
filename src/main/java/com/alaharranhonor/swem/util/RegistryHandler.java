package com.alaharranhonor.swem.util;


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
import com.alaharranhonor.swem.util.registry.*;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.IForgeRegistryEntry;

public class RegistryHandler {

    public static <T extends IForgeRegistryEntry<T>> T setup(final T entry, final String name) {
        return setup(entry, new ResourceLocation(SWEM.MOD_ID, name));
    }

    public static <T extends IForgeRegistryEntry<T>> T setup(final T entry, final ResourceLocation registryName) {
        entry.setRegistryName(registryName);
        return entry;
    }

    public static void init(IEventBus modBus){
        SWEMItems.init(modBus);
        SWEMBlocks.init(modBus);
        SWEMEntities.init(modBus);
        SWEMContainers.init(modBus);
        SWEMParticles.init(modBus);
        SWEMTileEntities.init(modBus);
        SWEMPaintings.init(modBus);
        SWEMStructure.init(modBus);
        SWEMEnchantments.init(modBus);
        SWEMLootModifiers.init(modBus);
    }

}
