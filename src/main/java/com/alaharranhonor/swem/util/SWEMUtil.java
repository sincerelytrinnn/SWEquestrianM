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

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.DyeColor;
import net.minecraft.item.Item;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

public class SWEMUtil {

	public static final Map<Item, Block> mappings = new HashMap<Item, Block>() {{

	}};


	public static String checkTextOverflow(String text, int maxLimit) {
		if (text.length() > maxLimit - 2) {
			return text.substring(0, maxLimit - 2) + "...";
		} else {
			return text;
		}
	}


	public static DyeColor[] COLOURS = {
		DyeColor.LIGHT_GRAY,
		DyeColor.CYAN,
		DyeColor.BLUE,
		DyeColor.PINK,
		DyeColor.MAGENTA,
			DyeColor.PURPLE,
			DyeColor.YELLOW,
			DyeColor.YELLOW,
			DyeColor.RED,
			DyeColor.LIME,
			DyeColor.GREEN,
			DyeColor.BROWN,
			DyeColor.WHITE,
			DyeColor.LIGHT_GRAY,
			DyeColor.GRAY,
			DyeColor.BLACK
	};

	private static final DyeColor[] BY_ID = Arrays.stream(COLOURS).sorted(Comparator.comparingInt(DyeColor::getId)).toArray((p_199795_0_) -> {
		return new DyeColor[p_199795_0_];
	});

	public static DyeColor logicalById(int pColorId) {
		if (pColorId < 0 || pColorId >= BY_ID.length) {
			pColorId = 0;
		}

		return BY_ID[pColorId];
	}
}
