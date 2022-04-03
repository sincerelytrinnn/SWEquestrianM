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

import com.alaharranhonor.swem.entity.model.TackBoxModel;
import com.alaharranhonor.swem.tileentity.TackBoxTE;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import software.bernie.geckolib3.renderers.geo.GeoBlockRenderer;

public class TackBoxRender extends GeoBlockRenderer<TackBoxTE> {
	/**
	 * Instantiates a new Tack box render.
	 *
	 * @param rendererDispatcherIn the renderer dispatcher in
	 */
	public TackBoxRender(TileEntityRendererDispatcher rendererDispatcherIn) {
		super(rendererDispatcherIn, new TackBoxModel());
	}


}
