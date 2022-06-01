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

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import net.minecraft.block.SoundType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class PendantItem extends Item {

  ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(1);

  /** Instantiates a new Pendant item. */
  public PendantItem() {
    super(new Item.Properties());
  }

  @Override
  public void inventoryTick(
      ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
    if (entityIn.tickCount % 200 == 0) {
      if (worldIn.getRandom().nextInt(5) == 4) {
        BlockPos posToPlay1 =
            entityIn.blockPosition().relative(entityIn.getDirection().getOpposite(), 3);
        BlockPos posToPlay2 =
            entityIn.blockPosition().relative(entityIn.getDirection().getOpposite(), 2);
        BlockPos posToPlay3 =
            entityIn.blockPosition().relative(entityIn.getDirection().getOpposite(), 1);
        SoundType soundType =
            worldIn
                .getBlockState(entityIn.blockPosition().below())
                .getSoundType(worldIn, entityIn.blockPosition().below(), entityIn);

        executor.schedule(
            new Runnable() {
              public void run() {
                worldIn.playSound(
                    (PlayerEntity) entityIn,
                    posToPlay1,
                    soundType.getStepSound(),
                    SoundCategory.PLAYERS,
                    0.20f,
                    soundType.getPitch());
              }
            },
            1,
            TimeUnit.SECONDS);

        executor.schedule(
            new Runnable() {
              public void run() {
                worldIn.playSound(
                    (PlayerEntity) entityIn,
                    posToPlay2,
                    soundType.getStepSound(),
                    SoundCategory.PLAYERS,
                    0.20f,
                    soundType.getPitch());
              }
            },
            2,
            TimeUnit.SECONDS);

        executor.schedule(
            new Runnable() {
              public void run() {
                worldIn.playSound(
                    (PlayerEntity) entityIn,
                    posToPlay3,
                    soundType.getStepSound(),
                    SoundCategory.PLAYERS,
                    0.20f,
                    soundType.getPitch());
              }
            },
            3,
            TimeUnit.SECONDS);
      }
    }

    super.inventoryTick(stack, worldIn, entityIn, itemSlot, isSelected);
  }
}
