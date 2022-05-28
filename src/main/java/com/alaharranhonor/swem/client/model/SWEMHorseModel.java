
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
import com.alaharranhonor.swem.entities.SWEMHorseEntity;
import com.alaharranhonor.swem.client.coats.SWEMCoatColor;
import com.google.common.collect.Maps;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Util;
import software.bernie.geckolib3.model.AnimatedGeoModel;

import java.util.Map;

public class SWEMHorseModel extends AnimatedGeoModel<SWEMHorseEntity> {

    public static final Map<SWEMCoatColor, ResourceLocation> VARIANTS = Util.make(Maps.newEnumMap(SWEMCoatColor.class), (iter) -> {
        iter.put(SWEMCoatColor.LADY_JENNY, new ResourceLocation(SWEM.MOD_ID, "textures/entity/horse/coats/lady_jenny.png"));
        iter.put(SWEMCoatColor.WHITE, new ResourceLocation(SWEM.MOD_ID, "textures/entity/horse/coats/1_v_white.png"));
        iter.put(SWEMCoatColor.GRAY, new ResourceLocation(SWEM.MOD_ID, "textures/entity/horse/coats/2_v_gray.png"));
        iter.put(SWEMCoatColor.BLACK, new ResourceLocation(SWEM.MOD_ID, "textures/entity/horse/coats/3_v_black.png"));
        iter.put(SWEMCoatColor.CHESTNUT, new ResourceLocation(SWEM.MOD_ID, "textures/entity/horse/coats/4_v_chestnut.png"));
        iter.put(SWEMCoatColor.BROWN, new ResourceLocation(SWEM.MOD_ID, "textures/entity/horse/coats/5_v_brown.png"));
        iter.put(SWEMCoatColor.ROAN, new ResourceLocation(SWEM.MOD_ID, "textures/entity/horse/coats/6_v_roan.png"));
        iter.put(SWEMCoatColor.BUCKSKIN, new ResourceLocation(SWEM.MOD_ID, "textures/entity/horse/coats/7_v_buckskin.png"));
        iter.put(SWEMCoatColor.PAINT, new ResourceLocation(SWEM.MOD_ID, "textures/entity/horse/coats/8_v_paint.png"));
        iter.put(SWEMCoatColor.PALOMINO, new ResourceLocation(SWEM.MOD_ID, "textures/entity/horse/coats/9_v_palomino.png"));
        iter.put(SWEMCoatColor.NOBUCKLE, new ResourceLocation(SWEM.MOD_ID, "textures/entity/horse/coats/10_m_nobuckle.png"));
        iter.put(SWEMCoatColor.WILDANDFREE, new ResourceLocation(SWEM.MOD_ID, "textures/entity/horse/coats/11_m_wildandfree.png"));
        iter.put(SWEMCoatColor.TALLDARKHANDSOME, new ResourceLocation(SWEM.MOD_ID, "textures/entity/horse/coats/12_m_talldarkandhandsome.png"));
        iter.put(SWEMCoatColor.SWEETBOI, new ResourceLocation(SWEM.MOD_ID, "textures/entity/horse/coats/13_m_sweetboi.png"));
        iter.put(SWEMCoatColor.APPY, new ResourceLocation(SWEM.MOD_ID, "textures/entity/horse/coats/14_m_appy.png"));
        iter.put(SWEMCoatColor.GOLDEN, new ResourceLocation(SWEM.MOD_ID, "textures/entity/horse/coats/15_m_golden.png"));
        iter.put(SWEMCoatColor.LEOPARD, new ResourceLocation(SWEM.MOD_ID, "textures/entity/horse/coats/16_m_leopard.png"));
        iter.put(SWEMCoatColor.GALAXY, new ResourceLocation(SWEM.MOD_ID, "textures/entity/horse/coats/17_m_galaxy.png"));
        iter.put(SWEMCoatColor.RAINBOW, new ResourceLocation(SWEM.MOD_ID, "textures/entity/horse/coats/18_m_rainbow.png"));
        iter.put(SWEMCoatColor.MAN_O_WAR, new ResourceLocation(SWEM.MOD_ID, "textures/entity/horse/coats/man_o_war.png"));
        iter.put(SWEMCoatColor.SECRETARIAT, new ResourceLocation(SWEM.MOD_ID, "textures/entity/horse/coats/secretariat.png"));
        iter.put(SWEMCoatColor.SERGEANT_RECKLESS, new ResourceLocation(SWEM.MOD_ID, "textures/entity/horse/coats/sergeant_reckless.png"));
        iter.put(SWEMCoatColor.VALEGRO, new ResourceLocation(SWEM.MOD_ID, "textures/entity/horse/coats/valegro.png"));
        iter.put(SWEMCoatColor.DOLLAR_JOHN_WAYNE, new ResourceLocation(SWEM.MOD_ID, "textures/entity/horse/coats/dollar_john_wayne.png"));
        iter.put(SWEMCoatColor.TRIGGER_ROY_ROGERS, new ResourceLocation(SWEM.MOD_ID, "textures/entity/horse/coats/trigger_roy_rogers.png"));
        iter.put(SWEMCoatColor.MR_ED, new ResourceLocation(SWEM.MOD_ID, "textures/entity/horse/coats/mr_ed.png"));
        iter.put(SWEMCoatColor.JOERGEN_PEWDIEPIE, new ResourceLocation(SWEM.MOD_ID, "textures/entity/horse/coats/joergen_pewdiepie.png"));
        iter.put(SWEMCoatColor.SHWOOMPL_MARKIPLIER, new ResourceLocation(SWEM.MOD_ID, "textures/entity/horse/coats/shwoompl_markiplier.png"));
        iter.put(SWEMCoatColor.FINBAR_FOALEY_JACKSEPTICEYE, new ResourceLocation(SWEM.MOD_ID, "textures/entity/horse/coats/finbar_foaley_jacksepticeye.png"));
        iter.put(SWEMCoatColor.THIS_ESME_JOEY, new ResourceLocation(SWEM.MOD_ID, "textures/entity/horse/coats/this_esme_joey.png"));
        iter.put(SWEMCoatColor.GOOSEBERRY_JUSTPEACHY, new ResourceLocation(SWEM.MOD_ID, "textures/entity/horse/coats/gooseberry_justpeachy.png"));
        iter.put(SWEMCoatColor.EPONA_ZELDA, new ResourceLocation(SWEM.MOD_ID, "textures/entity/horse/coats/epona_zelda.png"));
        iter.put(SWEMCoatColor.ROACH_WITCHER, new ResourceLocation(SWEM.MOD_ID, "textures/entity/horse/coats/roach_witcher.png"));
        iter.put(SWEMCoatColor.AGRO_SOC, new ResourceLocation(SWEM.MOD_ID, "textures/entity/horse/coats/agro_soc.png"));
        iter.put(SWEMCoatColor.SHADOWMERE_OBLIVION, new ResourceLocation(SWEM.MOD_ID, "textures/entity/horse/coats/shadowmere_oblivion.png"));
        iter.put(SWEMCoatColor.RAPIDASH_POKEMON, new ResourceLocation(SWEM.MOD_ID, "textures/entity/horse/coats/rapidash_pokemon.png"));
        iter.put(SWEMCoatColor.SWIFT_WIND_SHE_RA, new ResourceLocation(SWEM.MOD_ID, "textures/entity/horse/coats/swift_wind_she_ra.png"));
        iter.put(SWEMCoatColor.BOB_FREE_REIN, new ResourceLocation(SWEM.MOD_ID, "textures/entity/horse/coats/bob_free_rein.png"));
        iter.put(SWEMCoatColor.ZORSE_PAINT, new ResourceLocation(SWEM.MOD_ID, "textures/entity/horse/coats/zorse_paint.png"));
        iter.put(SWEMCoatColor.CHIMERA_CALICO, new ResourceLocation(SWEM.MOD_ID, "textures/entity/horse/coats/chimera_calico.png"));
        iter.put(SWEMCoatColor.CHIMERA_BAY_GRAY, new ResourceLocation(SWEM.MOD_ID, "textures/entity/horse/coats/chimera_bay_gray.png"));
        iter.put(SWEMCoatColor.CHIMERA_BRINDLE, new ResourceLocation(SWEM.MOD_ID, "textures/entity/horse/coats/chimera_brindle.png"));
        iter.put(SWEMCoatColor.PEACOCK_APPY, new ResourceLocation(SWEM.MOD_ID, "textures/entity/horse/coats/peacock_appy.png"));
        iter.put(SWEMCoatColor.ARIALS_MALLI, new ResourceLocation(SWEM.MOD_ID, "textures/entity/horse/coats/arials_malli.png"));
        iter.put(SWEMCoatColor.EL_CAZADOR_MALLI, new ResourceLocation(SWEM.MOD_ID, "textures/entity/horse/coats/el_cazador_malli.png"));
        iter.put(SWEMCoatColor.BLACK_BIRDCATCHER_MALLI, new ResourceLocation(SWEM.MOD_ID, "textures/entity/horse/coats/black_birdcatcher_malli.png"));
        iter.put(SWEMCoatColor.BUCKSKIN_OVERO_MALLI, new ResourceLocation(SWEM.MOD_ID, "textures/entity/horse/coats/buckskin_overo_malli.png"));
        iter.put(SWEMCoatColor.FLAXEN_LIVER_MALLI, new ResourceLocation(SWEM.MOD_ID, "textures/entity/horse/coats/flaxen_liver_malli.png"));
        iter.put(SWEMCoatColor.LUNAR_ARISHANT, new ResourceLocation(SWEM.MOD_ID, "textures/entity/horse/coats/lunar_arishant.png"));
        iter.put(SWEMCoatColor.NERO_STARDUST, new ResourceLocation(SWEM.MOD_ID, "textures/entity/horse/coats/nero_stardust.png"));
        iter.put(SWEMCoatColor.BLUE_ROAN_FORTUNE_STARDUST, new ResourceLocation(SWEM.MOD_ID, "textures/entity/horse/coats/blue_roan_fortune_stardust.png"));
        iter.put(SWEMCoatColor.MEDICINE_HAT_ARISHANT, new ResourceLocation(SWEM.MOD_ID, "textures/entity/horse/coats/medicine_hat_arishant.png"));
        iter.put(SWEMCoatColor.SILVER_BAY_STARDUST, new ResourceLocation(SWEM.MOD_ID, "textures/entity/horse/coats/silver_bay_stardust.png"));
        iter.put(SWEMCoatColor.SILVER_DAPPLE_SPLASH_MALLI, new ResourceLocation(SWEM.MOD_ID, "textures/entity/horse/coats/silver_dapple_splash_malli.png"));
        iter.put(SWEMCoatColor.SMUDGED_LEOPARD_ARISHANT, new ResourceLocation(SWEM.MOD_ID, "textures/entity/horse/coats/smudged_leopard_arishant.png"));
        iter.put(SWEMCoatColor.FRANK_STEVECV, new ResourceLocation(SWEM.MOD_ID, "textures/entity/horse/coats/frank_stevecv.png"));
        iter.put(SWEMCoatColor.TOOTHBRUSH_BOATY, new ResourceLocation(SWEM.MOD_ID, "textures/entity/horse/coats/toothbrush_boaty.png"));
        iter.put(SWEMCoatColor.KODIAK_DELPHI, new ResourceLocation(SWEM.MOD_ID, "textures/entity/horse/coats/kodiak_delphi.png"));
        iter.put(SWEMCoatColor.ANNIE_LACE, new ResourceLocation(SWEM.MOD_ID, "textures/entity/horse/coats/annie_lace.png"));
        iter.put(SWEMCoatColor.FOAL_BLACK, new ResourceLocation(SWEM.MOD_ID, "textures/entity/horse/coats/foal/black.png"));
        iter.put(SWEMCoatColor.FOAL_BROWN, new ResourceLocation(SWEM.MOD_ID, "textures/entity/horse/coats/foal/brown.png"));
        iter.put(SWEMCoatColor.FOAL_CHESTNUT, new ResourceLocation(SWEM.MOD_ID, "textures/entity/horse/coats/foal/chestnut.png"));
        iter.put(SWEMCoatColor.FOAL_CREAMY, new ResourceLocation(SWEM.MOD_ID, "textures/entity/horse/coats/foal/creamy.png"));
        iter.put(SWEMCoatColor.FOAL_DARK_BROWN, new ResourceLocation(SWEM.MOD_ID, "textures/entity/horse/coats/foal/dark_brown.png"));
        iter.put(SWEMCoatColor.FOAL_GRAY, new ResourceLocation(SWEM.MOD_ID, "textures/entity/horse/coats/foal/gray.png"));
        iter.put(SWEMCoatColor.FOAL_WHITE, new ResourceLocation(SWEM.MOD_ID, "textures/entity/horse/coats/foal/white.png"));
    });


    @Override
    public ResourceLocation getModelLocation(SWEMHorseEntity swemHorseEntity) {
        if (swemHorseEntity.isBaby()) {
            return new ResourceLocation(SWEM.MOD_ID, "geo/entity/horse/swem_horse_foal.geo.json");
        }
        return new ResourceLocation(SWEM.MOD_ID, "geo/entity/horse/swem_horse.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(SWEMHorseEntity swemHorseEntity) {
        return VARIANTS.get(swemHorseEntity.getCoatColor());
    }

    @Override
    public ResourceLocation getAnimationFileLocation(SWEMHorseEntity swemHorseEntity) {
        if (swemHorseEntity.isBaby()) {
            return new ResourceLocation(SWEM.MOD_ID, "animations/swem_horse_foal.json");
        }
        return new ResourceLocation(SWEM.MOD_ID, "animations/swem_horse.json");
    }
}