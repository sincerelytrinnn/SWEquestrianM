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
	boolean isSaddleable(PlayerEntity player);

	void equipSaddle(@Nullable SoundCategory p_230266_1_, ItemStack stack, PlayerEntity player);

	boolean isHorseSaddled();

	boolean hasAdventureSaddle();

	boolean hasBlanket();

	boolean hasBreastCollar();

	boolean hasHalter();

	boolean hasGirthStrap();

	boolean hasLegWraps();

	boolean canEquipSaddle();
	boolean canEquipGirthStrap();

	boolean canEquipArmor();
}
