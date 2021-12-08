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
import com.alaharranhonor.swem.entities.SWEMHorseEntityBase;
import com.alaharranhonor.swem.items.tack.BridleItem;
import com.alaharranhonor.swem.items.tack.WesternBridleItem;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class BridleRackWesternModel extends AnimatedGeoModel<BridleItem> {

	@Override
	public ResourceLocation getModelLocation(BridleItem westernBridleItem) {
		return new ResourceLocation(SWEM.MOD_ID, "geo/tile/bridle_rack/bridle_rack_western.geo.json");
	}

	@Override
	public ResourceLocation getTextureLocation(BridleItem westernBridleItem) {
		return westernBridleItem.getBridleRackTexture();
	}

	@Override
	public ResourceLocation getAnimationFileLocation(BridleItem westernBridleItem) {
		return null;
	}
}
