package com.alaharranhonor.swem.client.model;


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
import com.alaharranhonor.swem.tileentity.OneSaddleRackTE;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class OneSaddleRackModel extends AnimatedGeoModel<OneSaddleRackTE> {
	@Override
	public ResourceLocation getModelLocation(OneSaddleRackTE oneSaddleRackTE) {
		if (oneSaddleRackTE.getBlockState().getValue(BlockStateProperties.HANGING)) {
			return new ResourceLocation(SWEM.MOD_ID, "geo/tile/saddle_rack_wall.geo.json");
		}
		return new ResourceLocation(SWEM.MOD_ID, "geo/tile/saddle_rack_floor.geo.json");
	}

	@Override
	public ResourceLocation getTextureLocation(OneSaddleRackTE oneSaddleRackTE) {
		if (oneSaddleRackTE.getBlockState().getValue(BlockStateProperties.HANGING)) {
			return new ResourceLocation(SWEM.MOD_ID, "textures/tile/saddle_rack_wall.png");
		}
		return new ResourceLocation(SWEM.MOD_ID, "textures/tile/saddle_rack_floor.png");
	}

	@Override
	public ResourceLocation getAnimationFileLocation(OneSaddleRackTE oneSaddleRackTE) {
		return new ResourceLocation(SWEM.MOD_ID, "animations/tackbox.json");
	}
}
