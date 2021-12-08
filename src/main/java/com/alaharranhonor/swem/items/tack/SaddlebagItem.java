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
import com.alaharranhonor.swem.items.ItemBase;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;


public class SaddlebagItem extends ItemBase implements IAnimatable {

	private AnimationFactory factory = new AnimationFactory(this);
	private ResourceLocation texture;

	public SaddlebagItem(String texturePath) {
		super();
		this.texture = new ResourceLocation(SWEM.MOD_ID, "textures/entity/horse/saddlebags/" + texturePath + ".png");
	}

	public ActionResultType interactLivingEntity(ItemStack stack, PlayerEntity playerIn, LivingEntity target, Hand hand) {
		if (target instanceof ISWEMEquipable && target.isAlive()) {
			ISWEMEquipable iequipable = (ISWEMEquipable)target;
			if (iequipable.isSaddleable()) {
				if (!playerIn.level.isClientSide) {
					iequipable.equipSaddle(SoundCategory.NEUTRAL, stack);
					if (!playerIn.abilities.instabuild)
						stack.shrink(1);
				}

				return ActionResultType.sidedSuccess(playerIn.level.isClientSide);
			}
		}
		return ActionResultType.PASS;
	}


	@Override
	public void registerControllers(AnimationData animationData) {

	}

	@Override
	public AnimationFactory getFactory() {
		return this.factory;
	}

	public ResourceLocation getArmorTexture() {
		return this.texture;
	}
}
