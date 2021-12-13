
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
import com.alaharranhonor.swem.entities.SWEMHorseEntity;
import com.alaharranhonor.swem.entity.coats.SWEMCoatColors;
import com.google.common.collect.Maps;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Util;
import software.bernie.geckolib3.model.AnimatedGeoModel;

import java.util.Map;

public class SWEMHorseModel extends AnimatedGeoModel<SWEMHorseEntity> {

    public static final Map<SWEMCoatColors, ResourceLocation> VARIANTS = Util.make(Maps.newEnumMap(SWEMCoatColors.class), (iter) -> {
        iter.put(SWEMCoatColors.WHITE, new ResourceLocation(SWEM.MOD_ID, "textures/entity/horse/coats/white_coat.png"));
        iter.put(SWEMCoatColors.GRAY, new ResourceLocation(SWEM.MOD_ID, "textures/entity/horse/coats/gray_coat.png"));
        iter.put(SWEMCoatColors.BLACK, new ResourceLocation(SWEM.MOD_ID, "textures/entity/horse/coats/black_coat.png"));
        iter.put(SWEMCoatColors.CHESTNUT, new ResourceLocation(SWEM.MOD_ID, "textures/entity/horse/coats/chestnut_coat.png"));
        iter.put(SWEMCoatColors.BROWN, new ResourceLocation(SWEM.MOD_ID, "textures/entity/horse/coats/brown_coat.png"));
        iter.put(SWEMCoatColors.REDROAN, new ResourceLocation(SWEM.MOD_ID, "textures/entity/horse/coats/red_roan_coat.png"));
        iter.put(SWEMCoatColors.BUCKSKIN, new ResourceLocation(SWEM.MOD_ID, "textures/entity/horse/coats/buckskin_coat.png"));
        iter.put(SWEMCoatColors.PAINT, new ResourceLocation(SWEM.MOD_ID, "textures/entity/horse/coats/paint_coat.png"));
        iter.put(SWEMCoatColors.PALOMINO, new ResourceLocation(SWEM.MOD_ID, "textures/entity/horse/coats/palomino_coat.png"));
        iter.put(SWEMCoatColors.NOBUCKLE, new ResourceLocation(SWEM.MOD_ID, "textures/entity/horse/coats/nobuckle_coat.png"));
        iter.put(SWEMCoatColors.WILDANDFREE, new ResourceLocation(SWEM.MOD_ID, "textures/entity/horse/coats/wild_and_free_coat.png"));
        iter.put(SWEMCoatColors.TALLDARKHANDSOME, new ResourceLocation(SWEM.MOD_ID, "textures/entity/horse/coats/tall_dark_handsome_coat.png"));
        iter.put(SWEMCoatColors.SWEETBOY, new ResourceLocation(SWEM.MOD_ID, "textures/entity/horse/coats/sweet_boy_coat.png"));
        iter.put(SWEMCoatColors.APPY, new ResourceLocation(SWEM.MOD_ID, "textures/entity/horse/coats/appy_coat.png"));
        iter.put(SWEMCoatColors.GOLDEN, new ResourceLocation(SWEM.MOD_ID, "textures/entity/horse/coats/golden_coat.png"));
        iter.put(SWEMCoatColors.LEOPARD, new ResourceLocation(SWEM.MOD_ID, "textures/entity/horse/coats/leopard_coat.png"));
        iter.put(SWEMCoatColors.LADY, new ResourceLocation(SWEM.MOD_ID, "textures/entity/horse/coats/lady_coat.png"));
        iter.put(SWEMCoatColors.GALAXY, new ResourceLocation(SWEM.MOD_ID, "textures/entity/horse/coats/galaxy_coat.png"));
        iter.put(SWEMCoatColors.RAINBOW, new ResourceLocation(SWEM.MOD_ID, "textures/entity/horse/coats/rainbow_coat.png"));
    });


    @Override
    public ResourceLocation getModelLocation(SWEMHorseEntity swemHorseEntity) {
        return new ResourceLocation(SWEM.MOD_ID, "geo/entity/horse/swem_horse.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(SWEMHorseEntity swemHorseEntity) {
        return VARIANTS.get(swemHorseEntity.getCoatColor());
    }

    @Override
    public ResourceLocation getAnimationFileLocation(SWEMHorseEntity swemHorseEntity) {
        return new ResourceLocation(SWEM.MOD_ID, "animations/swem_horse.json");
    }
}