package com.alaharranhonor.swem.entity.render;


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
import com.alaharranhonor.swem.entities.PoopEntity;
import com.alaharranhonor.swem.entity.model.PoopModel;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class PoopRender extends GeoEntityRenderer<PoopEntity> {
	/**
	 * Instantiates a new Poop render.
	 *
	 * @param renderManager the render manager
	 */
	public PoopRender(EntityRendererManager renderManager) {
		super(renderManager, new PoopModel());
	}

	@Override
	public ResourceLocation getTextureLocation(PoopEntity entity) {
		return new ResourceLocation(SWEM.MOD_ID, "textures/entity/horse_poop.png");
	}


}
