package com.alaharranhonor.swem.armor;


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
import com.alaharranhonor.swem.items.SWEMArmorItem;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import net.minecraft.item.Item.Properties;

public class AmethystLeggings extends SWEMArmorItem {
	/**
	 * Instantiates a new Amethyst leggings.
	 *
	 * @param path       the path
	 * @param materialIn the material in
	 * @param slot       the slot
	 * @param builder    the builder
	 */
	public AmethystLeggings(String path, IArmorMaterial materialIn, EquipmentSlotType slot, Properties builder) {
		super(path, materialIn, slot, builder);
	}

	@Override
	public boolean makesPiglinsNeutral(ItemStack stack, LivingEntity wearer) {
		return true;
	}


	@Mod.EventBusSubscriber(modid = SWEM.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
	public static class LastStand {

		/**
		 * On hurt event.
		 *
		 * @param event the event
		 */
		@SubscribeEvent
		public static void onHurtEvent(LivingHurtEvent event) {
			if (!(event.getEntity() instanceof PlayerEntity)) return;

			PlayerEntity player = (PlayerEntity) event.getEntity();

			if (!(player.getItemBySlot(EquipmentSlotType.LEGS).getItem() instanceof AmethystLeggings)) return;

			float playerHealth = player.getHealth();
			float healthAfterHit = playerHealth - event.getAmount();
			if (healthAfterHit < 0.5F) {
				float playerExp = player.totalExperience;
				float requiredXp = (healthAfterHit * -1) * 40.0F;

				if (playerExp >= requiredXp) {
					player.giveExperiencePoints((int)-requiredXp);
					event.setAmount(-1);
					event.setCanceled(true);
					player.setHealth(6);
				}

			}

		}

	}
}
