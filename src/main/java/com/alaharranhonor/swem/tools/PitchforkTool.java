package com.alaharranhonor.swem.tools;


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

import com.alaharranhonor.swem.blocks.Shavings;
import com.alaharranhonor.swem.items.ItemBase;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemStack;

public class PitchforkTool extends ItemBase {

	@Override
	public float getDestroySpeed(ItemStack stack, BlockState state) {
		if (state.getBlock() instanceof Shavings) {
			return 5;
		}
		return super.getDestroySpeed(stack, state);
	}


}
