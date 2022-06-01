package com.alaharranhonor.swem.armor;

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

import com.alaharranhonor.swem.items.SWEMArmorItem;
import software.bernie.geckolib3.renderers.geo.GeoArmorRenderer;

public class AmethystArmorModelRenderer extends GeoArmorRenderer<SWEMArmorItem> {
  /** Instantiates a new Amethyst armor model renderer. */
  public AmethystArmorModelRenderer() {
    super(new AmethystArmorModel());

    this.headBone = "Head";
    this.bodyBone = "Body";
    this.rightArmBone = "RightArm";
    this.leftArmBone = "LeftArm";
    this.rightLegBone = "LeftLeg";
    this.leftLegBone = "RightLeg";
    this.rightBootBone = "LeftBoot";
    this.leftBootBone = "RightBoot";
  }
}
