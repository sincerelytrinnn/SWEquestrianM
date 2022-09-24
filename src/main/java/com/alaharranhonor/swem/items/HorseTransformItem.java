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

import com.alaharranhonor.swem.client.coats.SWEMCoatColor;
import com.alaharranhonor.swem.entities.SWEMHorseEntityBase;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;

public class HorseTransformItem extends Item {

    private SWEMCoatColor coat;

    /**
     * Instantiates a new Horse transform item.
     *
     * @param coat the coat
     */
    public HorseTransformItem(SWEMCoatColor coat) {
        super(new Item.Properties());
        this.coat = coat;
    }

    @Override
    public ActionResultType interactLivingEntity(
        ItemStack stack, PlayerEntity playerIn, LivingEntity target, Hand hand) {
        if (target instanceof SWEMHorseEntityBase) {
            SWEMHorseEntityBase horseEntity = (SWEMHorseEntityBase) target;
            if (horseEntity.isTamed() && horseEntity.canAccessHorse(playerIn) && !horseEntity.isBaby()) {
                horseEntity.setCoatColour(this.coat);
                stack.shrink(1);
                return ActionResultType.sidedSuccess(playerIn.level.isClientSide);
            }
        }
        return ActionResultType.PASS;
    }
}
