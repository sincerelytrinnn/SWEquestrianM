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

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public class ShrimpItem extends Item {
    /**
     * Instantiates a new Shrimp item.
     *
     * @param pProperties the p properties
     */
    public ShrimpItem(Properties pProperties) {
        super(pProperties);
    }

    /**
     * Called each tick as long the item is on a player inventory. Uses by maps to check if is on a
     * player hand and update it's contents.
     *
     * @param pStack
     * @param pLevel
     * @param pEntity
     * @param pItemSlot
     * @param pIsSelected
     */
    @Override
    public void inventoryTick(
            ItemStack pStack, World pLevel, Entity pEntity, int pItemSlot, boolean pIsSelected) {
        if ((!pEntity.getStringUUID().equals("982efc4699ea4be99f035520dc9a8217")
                || !pEntity.getStringUUID().equals("982efc46-99ea-4be9-9f03-5520dc9a8217"))
                && !pLevel.isClientSide
                && pEntity instanceof PlayerEntity
                && pLevel.getGameTime() % 40 == 0) {
            pEntity.thunderHit(
                    (ServerWorld) pLevel,
                    EntityType.LIGHTNING_BOLT.spawn(
                            (ServerWorld) pLevel,
                            new CompoundNBT(),
                            new StringTextComponent("Delphi's candy"),
                            (PlayerEntity) pEntity,
                            pEntity.blockPosition(),
                            SpawnReason.TRIGGERED,
                            true,
                            false));
        }

        super.inventoryTick(pStack, pLevel, pEntity, pItemSlot, pIsSelected);
    }
}
