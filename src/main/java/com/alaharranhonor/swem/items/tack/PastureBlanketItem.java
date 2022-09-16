
/*
 * All Rights Reserved
 *
 * Copyright (c) 2022, AlaharranHonor, Legenden.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package com.alaharranhonor.swem.items.tack;

import com.alaharranhonor.swem.entities.ISWEMEquipable;
import com.alaharranhonor.swem.items.SWEMHorseArmorItem;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;

public class PastureBlanketItem extends SWEMHorseArmorItem {

    /**
     * Instantiates a new Swem horse armor item.
     *
     * @param tier       the tier
     * @param armorValue the armor value
     * @param texture    the texture
     * @param builder    the builder
     */
    public PastureBlanketItem(HorseArmorTier tier, int armorValue, String texture, Properties builder) {
        super(tier, armorValue, "pasture_blanket/" + texture, builder);
    }

    @Override
    public ActionResultType interactLivingEntity(ItemStack stack, PlayerEntity playerIn, LivingEntity target, Hand hand) {
        if (target instanceof ISWEMEquipable && target.isAlive()) {
            ISWEMEquipable iequipable = (ISWEMEquipable) target;
            if (iequipable.isSaddleable(playerIn) && iequipable.canEquipPastureBlanket()) {
                if (!playerIn.level.isClientSide) {
                    iequipable.equipSaddle(SoundCategory.NEUTRAL, stack, playerIn);
                    if (!playerIn.abilities.instabuild) stack.shrink(1);
                }

                return ActionResultType.sidedSuccess(playerIn.level.isClientSide);
            }
        }
        return ActionResultType.PASS;
    }
}
