package com.alaharranhonor.swem.items;
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

import com.alaharranhonor.swem.entities.SWEMHorseEntityBase;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.world.server.ServerWorld;

public class BrushItem extends ItemBase{

	@Override
	public ActionResultType interactLivingEntity(ItemStack pStack, PlayerEntity pPlayer, LivingEntity pTarget, Hand pHand) {
		if (!pPlayer.level.isClientSide) {
			if (pTarget instanceof SWEMHorseEntityBase) {
				SWEMHorseEntityBase horse = (SWEMHorseEntityBase) pTarget;
				if (horse.canAccessHorse(pPlayer) && horse.isTamed()) {
					horse.brush();
				} else {
					horse.emitBadParticles((ServerWorld) pPlayer.level, 4);
				}
				return ActionResultType.CONSUME;
			}
		}
		return super.interactLivingEntity(pStack, pPlayer, pTarget, pHand);
	}
}
