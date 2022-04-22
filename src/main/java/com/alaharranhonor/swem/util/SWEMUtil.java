package com.alaharranhonor.swem.util;


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

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BucketItem;
import net.minecraft.item.DyeColor;
import net.minecraft.item.ItemStack;

import java.util.Arrays;

public class SWEMUtil {


	public static void damageOrShrink(ItemStack stack, PlayerEntity player) {
		if (stack.getItem() instanceof BucketItem) {
			player.setItemInHand(player.getUsedItemHand(), ((BucketItem) stack.getItem()).getEmptySuccessItem(stack, player));
		}
		if (stack.isDamageableItem()) {
			stack.hurtAndBreak(1, player, (playerEntity) -> {
				playerEntity.broadcastBreakEvent(playerEntity.getUsedItemHand());
			});
		} else {
			if (!player.isCreative())
				stack.shrink(1);
			player.setItemInHand(player.getUsedItemHand(), stack);
		}
	}


	/**
	 * Check text overflow string.
	 *
	 * @param text     the text
	 * @param maxLimit the max limit
	 * @return the string
	 */
	public static String checkTextOverflow(String text, int maxLimit) {
		if (text.length() > maxLimit - 2) {
			return text.substring(0, maxLimit - 2) + "...";
		} else {
			return text;
		}
	}


	public static DyeColor[] COLOURS = {
		DyeColor.WHITE,
		DyeColor.LIGHT_BLUE,
		DyeColor.CYAN,
		DyeColor.BLUE,
		DyeColor.PINK,
		DyeColor.MAGENTA,
		DyeColor.PURPLE,
		DyeColor.YELLOW,
		DyeColor.ORANGE,
		DyeColor.RED,
		DyeColor.LIME,
		DyeColor.GREEN,
		DyeColor.LIGHT_GRAY,
		DyeColor.GRAY,
		DyeColor.BLACK,
		DyeColor.BROWN,
	};

	private static final DyeColor[] BY_INDEX = Arrays.stream(COLOURS).toArray(DyeColor[]::new);

	/**
	 * Logical by id dye color.
	 *
	 * @param pColorId the p color id
	 * @return the dye color
	 */
	public static DyeColor logicalByIndex(int pColorId) {
		if (pColorId < 0 || pColorId >= BY_INDEX.length) {
			pColorId = 0;
		}

		return BY_INDEX[pColorId];
	}
}
