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
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.Color;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.world.World;

import net.minecraft.item.Item.Properties;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class AmethystRidingBoots extends DiamondRidingBoots {
	public AmethystRidingBoots(String path, IArmorMaterial materialIn, EquipmentSlotType slot, Properties builderIn) {
		super(path, materialIn, slot, builderIn);
	}

	@Override
	public void onCraftedBy(ItemStack stack, World worldIn, PlayerEntity playerIn) {
		super.onCraftedBy(stack, worldIn, playerIn);
	}

	@Override
	public void appendHoverText(ItemStack p_77624_1_, @Nullable World p_77624_2_, List<ITextComponent> p_77624_3_, ITooltipFlag p_77624_4_) {
		p_77624_3_.add(new StringTextComponent("I wish I could fly...").setStyle(Style.EMPTY.withColor(Color.parseColor("#585858"))));
	}

	@Override
	public void onArmorTick(ItemStack stack, World world, PlayerEntity player) {
		super.onArmorTick(stack, world, player);
		if (player.isCrouching()) return;
		Vector3d motion = player.getDeltaMovement();
		if (!player.isOnGround() && motion.y < 0.0D) {
			player.addEffect(new EffectInstance(Effects.SLOW_FALLING, 1, 1, false, false, true));
			//player.setDeltaMovement(motion.multiply(1.0D, 0.7D, 1.0D));
		}
	}

	@Mod.EventBusSubscriber(modid = SWEM.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
	public static class AmethystRidingBootsEquipped {

		@SubscribeEvent
		public static void onFall(LivingFallEvent event) {
			if (event.getEntityLiving() instanceof PlayerEntity) {
				PlayerEntity player = (PlayerEntity) event.getEntityLiving();
				if (player.getItemBySlot(EquipmentSlotType.FEET).getItem() instanceof AmethystRidingBoots) {
					event.setCanceled(true);
				}
			}
		}
	}
}
