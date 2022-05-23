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
import com.alaharranhonor.swem.items.SWEMHorseArmorItem;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class HorseArmorModelGeo extends AnimatedGeoModel<SWEMHorseArmorItem> {
	@Override
	public ResourceLocation getModelLocation(SWEMHorseArmorItem swemHorseArmorItem) {
		return new ResourceLocation(SWEM.MOD_ID, "geo/entity/horse/armor/" + swemHorseArmorItem.type + ".geo.json");
	}

	@Override
	public ResourceLocation getTextureLocation(SWEMHorseArmorItem swemHorseArmorItem) {
		return new ResourceLocation(SWEM.MOD_ID, "textures/entity/horse/armor/" + swemHorseArmorItem.type + ".png");
	}

	@Override
	public ResourceLocation getAnimationFileLocation(SWEMHorseArmorItem swemHorseArmorItem) {
		return new ResourceLocation(SWEM.MOD_ID, "animations/swem_horse.json"); // Make it dynamic
	}


}
