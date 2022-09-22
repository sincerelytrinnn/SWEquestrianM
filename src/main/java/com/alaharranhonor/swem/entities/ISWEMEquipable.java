package com.alaharranhonor.swem.entities;

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

import net.minecraft.entity.IEquipable;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.SoundCategory;

import javax.annotation.Nullable;

public interface ISWEMEquipable extends IEquipable {
    /**
     * Is saddleable boolean.
     *
     * @param player the player
     * @return the boolean
     */
    boolean isSaddleable(PlayerEntity player);

    /**
     * Equip saddle.
     *
     * @param p_230266_1_ the p 230266 1
     * @param stack       the stack
     * @param player      the player
     */
    void equipSaddle(@Nullable SoundCategory p_230266_1_, ItemStack stack, PlayerEntity player);

    /**
     * Is horse saddled boolean.
     *
     * @return the boolean
     */
    boolean isHorseSaddled();

    /**
     * Has adventure saddle boolean.
     *
     * @return the boolean
     */
    boolean hasAdventureSaddle();

    /**
     * Has blanket boolean.
     *
     * @return the boolean
     */
    boolean hasBlanket();

    /**
     * Has breast collar boolean.
     *
     * @return the boolean
     */
    boolean hasBreastCollar();

    /**
     * Has halter boolean.
     *
     * @return the boolean
     */
    boolean hasHalter();

    /**
     * Has girth strap boolean.
     *
     * @return the boolean
     */
    boolean hasGirthStrap();

    /**
     * Has leg wraps boolean.
     *
     * @return the boolean
     */
    boolean hasLegWraps();

    /**
     * Can equip saddle boolean.
     *
     * @return the boolean
     */
    boolean canEquipSaddle();

    /**
     * Can equip girth strap boolean.
     *
     * @return the boolean
     */
    boolean canEquipGirthStrap();

    /**
     * Can equip armor boolean.
     *
     * @return the boolean
     */
    boolean canEquipArmor();

    boolean canEquipPastureBlanket();
}
