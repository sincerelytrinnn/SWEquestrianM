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
import com.alaharranhonor.swem.items.tack.WesternSaddleItem;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class WesternSaddleModel extends AnimatedGeoModel<WesternSaddleItem> {


	@Override
	public ResourceLocation getModelLocation(WesternSaddleItem westernSaddleItem) {
		return new ResourceLocation(SWEM.MOD_ID, "geo/entity/horse/saddles/western_saddle.geo.json");
	}

	@Override
	public ResourceLocation getTextureLocation(WesternSaddleItem westernSaddleItem) {
		return westernSaddleItem.getTexture();
	}

	@Override
	public ResourceLocation getAnimationFileLocation(WesternSaddleItem westernSaddleItem) {
		return new ResourceLocation(SWEM.MOD_ID, "animations/swem_horse.json");
	}
}