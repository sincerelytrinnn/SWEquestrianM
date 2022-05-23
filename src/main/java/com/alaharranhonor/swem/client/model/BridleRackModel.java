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
import com.alaharranhonor.swem.tileentity.BridleRackTE;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class BridleRackModel extends AnimatedGeoModel<BridleRackTE> {
@Override
	public ResourceLocation getModelLocation(BridleRackTE bridleRackTE) {
			return new ResourceLocation(SWEM.MOD_ID, "geo/tile/bridle_rack/bridle_rack.geo.json");
	}

	@Override
	public ResourceLocation getTextureLocation(BridleRackTE bridleRackTE) {
		return new ResourceLocation(SWEM.MOD_ID, "textures/tile/bridle_rack/bridle_rack.png");
	}

	@Override
	public ResourceLocation getAnimationFileLocation(BridleRackTE bridleRackTE) {
		return new ResourceLocation(SWEM.MOD_ID, "animations/tackbox.json");
	}
}
