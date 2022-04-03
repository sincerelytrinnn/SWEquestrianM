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
import com.alaharranhonor.swem.network.HorseStateChange;
import com.alaharranhonor.swem.network.SWEMPacketHandler;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;

public class DesensitizingItem extends ItemBase {

	private int id;

	/**
	 * Instantiates a new Desensitizing item.
	 *
	 * @param id the id
	 */
	public DesensitizingItem(int id) {
		this.id = id;
	}

	@Override
	public ActionResultType interactLivingEntity(ItemStack stack, PlayerEntity playerIn, LivingEntity target, Hand hand) {
		if (target instanceof SWEMHorseEntityBase && target.level.isClientSide) {
			SWEMHorseEntityBase horse = (SWEMHorseEntityBase) target;
			switch (this.id) {
				case 0: {
					SWEMPacketHandler.INSTANCE.sendToServer(new HorseStateChange(1, horse.getId()));
					return ActionResultType.CONSUME;
				}
				case 1: {
					SWEMPacketHandler.INSTANCE.sendToServer(new HorseStateChange(2, horse.getId()));
					return ActionResultType.CONSUME;
				}
				case 2: {
					SWEMPacketHandler.INSTANCE.sendToServer(new HorseStateChange(3, horse.getId()));
					return ActionResultType.CONSUME;
				}
				case 3: {
					SWEMPacketHandler.INSTANCE.sendToServer(new HorseStateChange(4, horse.getId()));
					return ActionResultType.CONSUME;
				}
				case 4: {
					SWEMPacketHandler.INSTANCE.sendToServer(new HorseStateChange(5, horse.getId()));
					return ActionResultType.CONSUME;
				}
			}
		}
		return ActionResultType.CONSUME;
	}
}
