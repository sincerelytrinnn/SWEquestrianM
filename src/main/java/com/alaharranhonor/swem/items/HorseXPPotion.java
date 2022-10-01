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

import com.alaharranhonor.swem.entities.SWEMHorseEntityBase;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.world.server.ServerWorld;

public class HorseXPPotion extends ItemBase {

    private final String leveler;

    /**
     * Instantiates a new Horse xp bottle.
     *
     * @param leveler the leveler
     */
    public HorseXPPotion(String leveler) {
        super();
        this.leveler = leveler;
    }


    @Override
    public ActionResultType interactLivingEntity(
            ItemStack stack, PlayerEntity playerIn, LivingEntity target, Hand hand) {
        if (target instanceof SWEMHorseEntityBase &&  (!target.level.isClientSide)) {
            SWEMHorseEntityBase horse = (SWEMHorseEntityBase) target;
            stack.shrink(1);
            if (this.leveler.equals("affinity")) {
                horse.progressionManager.getAffinityLeveling().addXP(50);
                horse.emitWootParticles((ServerWorld) horse.level, 3);
                return ActionResultType.sidedSuccess(playerIn.getCommandSenderWorld().isClientSide);
            } else if (this.leveler.equals("speed")) {
                horse.progressionManager.getSpeedLeveling().addXP(50);
                horse.emitWootParticles((ServerWorld) horse.level, 3);
                return ActionResultType.sidedSuccess(playerIn.getCommandSenderWorld().isClientSide);
            } else if (this.leveler.equals("jump")) {
                horse.progressionManager.getJumpLeveling().addXP(50);
                horse.emitWootParticles((ServerWorld) horse.level, 3);
                return ActionResultType.sidedSuccess(playerIn.getCommandSenderWorld().isClientSide);
            } else if (this.leveler.equals("health")) {
                horse.progressionManager.getHealthLeveling().addXP(50);
                horse.emitWootParticles((ServerWorld) horse.level, 3);
                return ActionResultType.sidedSuccess(playerIn.getCommandSenderWorld().isClientSide);
            } else {
                horse.progressionManager.getAffinityLeveling().addXP(50);
                horse.progressionManager.getJumpLeveling().addXP(50);
                horse.progressionManager.getSpeedLeveling().addXP(50);
                horse.progressionManager.getHealthLeveling().addXP(50);
                horse.emitWootParticles((ServerWorld) horse.level, 4);
                return ActionResultType.sidedSuccess(playerIn.getCommandSenderWorld().isClientSide);
            }
        }
        return ActionResultType.PASS;
    }
}