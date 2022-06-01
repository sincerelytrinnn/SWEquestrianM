package com.alaharranhonor.swem.items;

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

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class PestleMortarItem extends Item {

  /**
   * Instantiates a new Pestle mortar item.
   *
   * @param properties the properties
   */
  public PestleMortarItem(final Properties properties) {
    super(properties);
  }

  @Override
  public ItemStack getContainerItem(ItemStack itemStack) {
    return new ItemStack(itemStack.getItem());
  }

  @Override
  public boolean hasCraftingRemainingItem() {
    return true;
  }
}
