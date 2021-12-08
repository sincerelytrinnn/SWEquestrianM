package com.alaharranhonor.swem.items.tack;


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
import net.minecraft.util.ResourceLocation;

import java.util.Arrays;
import java.util.stream.Collectors;

import net.minecraft.item.Item.Properties;

public class BridleItem extends HalterItem  {

	private ResourceLocation modelTexture;

	private ResourceLocation bridleRackTexture;


	public BridleItem(String textureName, Properties properties) {
		super(new ResourceLocation(SWEM.MOD_ID, "textures/entity/horse/bridle/" + textureName + ".png"), properties);
		this.modelTexture = new ResourceLocation(SWEM.MOD_ID, "textures/entity/horse/bridle/" + textureName + "_model.png");
		if (textureName.contains("bridle_")) {
			this.bridleRackTexture = new ResourceLocation(SWEM.MOD_ID, "textures/tile/bridle_rack/bridle_rack_" + Arrays.stream(textureName.split("bridle_")).collect(Collectors.joining("")) + ".png");
		}
	}

	public BridleItem(String textureName, String bridleRackTextureName, Properties properties) {
		super(new ResourceLocation(SWEM.MOD_ID, "textures/entity/horse/bridle/" + textureName + ".png"), properties);
		this.modelTexture = new ResourceLocation(SWEM.MOD_ID, "textures/entity/horse/bridle/" + textureName + "_model.png");
		this.bridleRackTexture = new ResourceLocation(SWEM.MOD_ID, "textures/tile/bridle_rack/bridle_rack_" + bridleRackTextureName + ".png");

	}

	public ResourceLocation getModelTexture() {
		return this.modelTexture;
	}

	@Override
	public ResourceLocation getBridleRackTexture() {
		return this.bridleRackTexture;
	}
}
