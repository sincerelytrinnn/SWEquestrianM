package com.alaharranhonor.swem.integration.placeableitems;
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

import me.ferdz.placeableitems.block.PlaceableItemsBlock;
import me.ferdz.placeableitems.block.PlaceableItemsBlockBuilder;
import net.minecraft.block.Block;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.IForgeRegistry;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class PlaceableItemsSWEMBlockRegistry {

  public static PlaceableItemsBlock LEATHER_HELMET;

  /**
   * On blocks register.
   *
   * @param event the event
   */
  @SubscribeEvent
  public static void onBlocksRegister(RegistryEvent.Register<Block> event) {
    if (!ModList.get().isLoaded("placeableitems")) return;
    IForgeRegistry<Block> registry = event.getRegistry();
    LEATHER_HELMET =
        new PlaceableItemsBlockBuilder()
            .build()
            .setShape(VoxelShapes.block())
            .register("leather_helmet_pi", registry);
  }
}
