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

import com.alaharranhonor.swem.SWEM;
import com.alaharranhonor.swem.entities.SWEMHorseEntityBase;
import com.alaharranhonor.swem.util.registry.SWEMParticles;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import java.util.Objects;
import java.util.UUID;

public class WhistleItem extends Item {

    /**
     * Instantiates a new Whistle item.
     */
    public WhistleItem() {
        super(new Properties().tab(SWEM.TAB).stacksTo(1));
    }

    @Override
    public ActionResult<ItemStack> use(World worldIn, PlayerEntity playerIn, Hand handIn) {
        if (worldIn.isClientSide) {
            return ActionResult.pass(playerIn.getItemInHand(handIn));
        }

        ItemStack stack = playerIn.getItemInHand(handIn);
        CompoundNBT tag = stack.getOrCreateTag();

        if (!tag.contains("boundHorse")) {
            return ActionResult.fail(stack);
        }

        UUID horseUUID = tag.getUUID("boundHorse");
        SWEMHorseEntityBase horse =
                ((SWEMHorseEntityBase) ((ServerWorld) worldIn).getEntity(horseUUID));

        if (horse == null) {
            return ActionResult.fail(stack);
        }

        if (!horse.blockPosition().closerThan(playerIn.blockPosition(), 100.0f)) {
            return ActionResult.fail(stack);
        }

        horse.setWhistlePos(playerIn.blockPosition());
        worldIn.addParticle(
                SWEMParticles.YAY.get(), horse.getX(), horse.getY(), horse.getZ(), 20.0, 0.0, 0.0);
        return ActionResult.consume(stack);

        //
    }

    /**
     * Returns true if the item can be used on the given entity, e.g. shears on sheep.
     *
     * @param stack
     * @param playerIn
     * @param target
     * @param hand
     */
    @Override
    public ActionResultType interactLivingEntity(
            ItemStack stack, PlayerEntity playerIn, LivingEntity target, Hand hand) {
        CompoundNBT tag = stack.getOrCreateTag();

        if (!(target instanceof SWEMHorseEntityBase)) {
            return ActionResultType.PASS;
        }

        SWEMHorseEntityBase horse = (SWEMHorseEntityBase) target;
        UUID horseOwnerId = horse.getOwnerUUID();
        UUID playerId = playerIn.getUUID();

        if (!Objects.equals(playerId, horseOwnerId)) {
            return ActionResultType.FAIL;
        }

        tag.putUUID("boundHorse", horse.getUUID());

        return ActionResultType.sidedSuccess(playerIn.getCommandSenderWorld().isClientSide);
    }
}
