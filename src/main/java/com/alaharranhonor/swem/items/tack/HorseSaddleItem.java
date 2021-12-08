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
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.text.StringTextComponent;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;



import net.minecraft.item.Item.Properties;

public class HorseSaddleItem extends Item implements IAnimatable {

	private ResourceLocation texture;
	private ResourceLocation saddleRackTexture;
	private final AnimationFactory factory = new AnimationFactory(this);


	public HorseSaddleItem(String textureName, Properties properties) {
		super(properties);
		this.texture = new ResourceLocation(SWEM.MOD_ID, "textures/entity/horse/saddle/" + textureName + ".png");
		this.saddleRackTexture = new ResourceLocation(SWEM.MOD_ID, "textures/tile/saddle_rack/" + textureName + ".png");
	}

	public ActionResultType interactLivingEntity(ItemStack stack, PlayerEntity playerIn, LivingEntity target, Hand hand) {
		if (target instanceof ISWEMEquipable && target.isAlive()) {
			ISWEMEquipable iequipable = (ISWEMEquipable)target;
			if (playerIn.level.isClientSide && !iequipable.canEquipSaddle()) {
				playerIn.displayClientMessage(new StringTextComponent("You need to equip a Blanket first!"), true);
				return ActionResultType.FAIL;
			}
			if (!iequipable.isHorseSaddled() && iequipable.isSaddleable() && iequipable.canEquipSaddle()) {
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

	public ResourceLocation getTexture() {
		return this.texture;
	}
	public ResourceLocation getSaddleRackTexture() {
		return this.saddleRackTexture;
	}

	@Override
	public void registerControllers(AnimationData animationData) {

	}

	@Override
	public AnimationFactory getFactory() {
		return this.factory;
	}
}
