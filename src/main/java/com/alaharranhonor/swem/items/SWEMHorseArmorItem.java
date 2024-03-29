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

import com.alaharranhonor.swem.SWEM;
import com.alaharranhonor.swem.entities.ISWEMEquipable;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.HorseArmorItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

public class SWEMHorseArmorItem extends HorseArmorItem implements IAnimatable {
    public final String type;
    public final HorseArmorTier tier;
    private final AnimationFactory factory = new AnimationFactory(this);
    private final ResourceLocation rackTexture;

    /**
     * Instantiates a new Swem horse armor item.
     *
     * @param tier       the tier
     * @param armorValue the armor value
     * @param texture    the texture
     * @param builder    the builder
     */
    public SWEMHorseArmorItem(HorseArmorTier tier, int armorValue, String texture, Properties builder) {
        super(armorValue, new ResourceLocation(SWEM.MOD_ID, "textures/entity/horse/armor/" + texture + ".png"), builder);
        this.type = texture;
        this.tier = tier;
        this.rackTexture = new ResourceLocation(SWEM.MOD_ID, "textures/entity/horse/armor/" + texture + "_rack.png");
    }

    public ActionResultType interactLivingEntity(ItemStack stack, PlayerEntity playerIn, LivingEntity target, Hand hand) {
        if (target instanceof ISWEMEquipable && target.isAlive()) {
            ISWEMEquipable iequipable = (ISWEMEquipable) target;
            if (iequipable.isSaddleable(playerIn) && iequipable.canEquipArmor()) {
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
     * Gets rack texture.
     *
     * @return the rack texture
     */
    public ResourceLocation getRackTexture() {
        return this.rackTexture;
    }

    /**
     * Predicate play state.
     *
     * @param <E>   the type parameter
     * @param event the event
     * @return the play state
     */
    public <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
        return PlayState.CONTINUE;
    }

    @Override
    public void registerControllers(AnimationData animationData) {
        animationData.addAnimationController(new AnimationController(this, "controller", 0, this::predicate));
    }

    @Override
    public AnimationFactory getFactory() {
        return this.factory;
    }

    public enum HorseArmorTier {
        NONE(-1), CLOTH(0), IRON(1), GOLD(2), DIAMOND(3), AMETHYST(4);

        private int id;

        /**
         * Instantiates a new Horse armor tier.
         *
         * @param id the id
         */
        HorseArmorTier(int id) {
            this.id = id;
        }

        /**
         * Gets id.
         *
         * @return the id
         */
        public int getId() {
            return this.id;
        }
    }
}
