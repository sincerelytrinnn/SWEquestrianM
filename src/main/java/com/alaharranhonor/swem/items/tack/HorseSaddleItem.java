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
import com.alaharranhonor.swem.entities.SWEMHorseEntityBase;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.text.StringTextComponent;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

public class HorseSaddleItem extends HorseTackItem implements IAnimatable {

    private final AnimationFactory factory = new AnimationFactory(this);
    private ResourceLocation texture;
    private ResourceLocation saddleRackTexture;

    /**
     * Instantiates a new Horse saddle item.
     *
     * @param textureName the texture name
     * @param properties  the properties
     */
    public HorseSaddleItem(String textureName, Properties properties) {
        super(properties);
        this.texture =
                new ResourceLocation(SWEM.MOD_ID, "textures/entity/horse/saddle/" + textureName + ".png");
        this.saddleRackTexture =
                new ResourceLocation(SWEM.MOD_ID, "textures/tile/saddle_rack/" + textureName + ".png");
    }

    public ActionResultType interactLivingEntity(
            ItemStack stack, PlayerEntity playerIn, LivingEntity target, Hand hand) {
        if (target instanceof SWEMHorseEntityBase && target.isAlive()) {
            SWEMHorseEntityBase iequipable = (SWEMHorseEntityBase) target;
            if (iequipable.getSWEMArmor().getItem() instanceof PastureBlanketItem) {
                playerIn.displayClientMessage(new StringTextComponent("You can't use this on a horse with a pasture blanket!"), true);
                return ActionResultType.FAIL;
            }
            if (playerIn.level.isClientSide && !iequipable.canEquipSaddle()) {
                playerIn.displayClientMessage(
                        new StringTextComponent("You need to equip a Blanket first!"), true);
                return ActionResultType.FAIL;
            }
            if ((!iequipable.isHorseSaddled() || playerIn.isSecondaryUseActive())
                    && iequipable.isSaddleable(playerIn)
                    && iequipable.canEquipSaddle()) {
                if (!playerIn.level.isClientSide) {
                    iequipable.equipSaddle(SoundCategory.NEUTRAL, stack, playerIn);
                    if (!playerIn.abilities.instabuild) stack.shrink(1);
                }

                return ActionResultType.sidedSuccess(playerIn.level.isClientSide);
            }
        }

        return ActionResultType.PASS;
    }

    /**
     * Gets texture.
     *
     * @return the texture
     */
    public ResourceLocation getTexture() {
        return this.texture;
    }

    /**
     * Gets saddle rack texture.
     *
     * @return the saddle rack texture
     */
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
