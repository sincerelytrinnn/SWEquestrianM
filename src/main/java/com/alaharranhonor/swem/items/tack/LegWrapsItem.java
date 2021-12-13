package com.alaharranhonor.swem.items.tack;


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
import com.alaharranhonor.swem.entities.ISWEMEquipable;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.IDyeableArmorItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import net.minecraft.item.Item.Properties;

public class LegWrapsItem extends HorseTackItem implements IDyeableArmorItem {

	private final ResourceLocation texture;
	private final ResourceLocation textureOverlay;
	public LegWrapsItem(String textureName, Properties properties) {
		this(new ResourceLocation(SWEM.MOD_ID, "textures/entity/horse/leg_wraps/" + textureName + ".png"), new ResourceLocation(SWEM.MOD_ID, "textures/entity/horse/leg_wraps/" + textureName + "_overlay.png"), properties);

	}
	public LegWrapsItem(ResourceLocation texture, ResourceLocation textureOverlay, Properties properties) {
		super(properties);
		this.texture = texture;
		this.textureOverlay = textureOverlay;
	}

	public ActionResultType interactLivingEntity(ItemStack stack, PlayerEntity playerIn, LivingEntity target, Hand hand) {
		if (target instanceof ISWEMEquipable && target.isAlive()) {
			ISWEMEquipable iequipable = (ISWEMEquipable)target;
			if (playerIn.level.isClientSide && !iequipable.hasHalter()) {
				playerIn.displayClientMessage(new StringTextComponent("You need to equip a Halter/Bridle first."), true);
				return ActionResultType.CONSUME;
			}
			if (!iequipable.hasLegWraps() && iequipable.isSaddleable(playerIn) && iequipable.hasHalter()) {
				if (!playerIn.level.isClientSide) {
					iequipable.equipSaddle(SoundCategory.NEUTRAL, stack, playerIn);
					if (!playerIn.abilities.instabuild)
						stack.shrink(1);
				}

				return ActionResultType.sidedSuccess(playerIn.level.isClientSide);
			}
		}
		return ActionResultType.PASS;
	}

	@OnlyIn(Dist.CLIENT)
	public ResourceLocation getArmorTexture() {
		return texture;
	}

	@OnlyIn(Dist.CLIENT)
	public ResourceLocation getArmorTextureOverlay() {
		return this.textureOverlay;
	}
}
