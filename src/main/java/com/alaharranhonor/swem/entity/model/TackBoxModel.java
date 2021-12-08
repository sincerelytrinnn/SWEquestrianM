package com.alaharranhonor.swem.entity.model;


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
import com.alaharranhonor.swem.blocks.SWEMBlockStateProperties;
import com.alaharranhonor.swem.tileentity.TackBoxTE;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class TackBoxModel extends AnimatedGeoModel<TackBoxTE> {

	@Override
	public ResourceLocation getModelLocation(TackBoxTE tackBoxTE) {
		if (tackBoxTE.getBlockState().getValue(SWEMBlockStateProperties.D_SIDE) == SWEMBlockStateProperties.DoubleBlockSide.LEFT) {
			return new ResourceLocation(SWEM.MOD_ID, "geo/tile/tackbox_left.geo.json");
		} else {
			return new ResourceLocation(SWEM.MOD_ID, "geo/tile/tackbox_right.geo.json");
		}
	}

	@Override
	public ResourceLocation getTextureLocation(TackBoxTE tackBoxTE) {
		return new ResourceLocation(SWEM.MOD_ID, "textures/tile/tackbox_white.png");
	}

	@Override
	public ResourceLocation getAnimationFileLocation(TackBoxTE tackBoxTE) {
		return new ResourceLocation(SWEM.MOD_ID, "animations/tackbox.json");
	}
}

