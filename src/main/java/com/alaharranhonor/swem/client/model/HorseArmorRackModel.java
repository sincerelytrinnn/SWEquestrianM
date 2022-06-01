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
import com.alaharranhonor.swem.blocks.SWEMBlockStateProperties;
import com.alaharranhonor.swem.tileentity.HorseArmorRackTE;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class HorseArmorRackModel extends AnimatedGeoModel<HorseArmorRackTE> {

  @Override
  public ResourceLocation getModelLocation(HorseArmorRackTE horseArmorRackTE) {
    if (horseArmorRackTE.getBlockState().getValue(SWEMBlockStateProperties.D_SIDE)
        == SWEMBlockStateProperties.DoubleBlockSide.LEFT) {
      return new ResourceLocation(SWEM.MOD_ID, "geo/tile/horse_armor_rack_front.geo.json");
    }
    return new ResourceLocation(SWEM.MOD_ID, "geo/tile/horse_armor_rack_back.geo.json");
  }

  @Override
  public ResourceLocation getTextureLocation(HorseArmorRackTE horseArmorRackTE) {
    if (horseArmorRackTE.getBlockState().getValue(SWEMBlockStateProperties.D_SIDE)
        == SWEMBlockStateProperties.DoubleBlockSide.LEFT) {
      return new ResourceLocation(SWEM.MOD_ID, "textures/blocks/horse_armor_rack_front.png");
    }
    return new ResourceLocation(SWEM.MOD_ID, "textures/blocks/horse_armor_rack_back.png");
  }

  @Override
  public ResourceLocation getAnimationFileLocation(HorseArmorRackTE horseArmorRackTE) {
    return new ResourceLocation(SWEM.MOD_ID, "animations/tackbox.json");
  }
}
