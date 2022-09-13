package com.alaharranhonor.swem.client.coats;
/*
 * All Rights Reserved
 *
 * Copyright (c) 2022, AlaharranHonor, Legenden.
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

import java.util.*;

public class CoatMapper {
    private static HashMap<SWEMCoatColor, SWEMCoatColor> coatMap =
            new HashMap() {
                {
                    //COAT #0-* put(SWEMCoatColor.SALAMARTY_ARISHANT, SWEMCoatColor.FOAL_BROWN);
                    put(SWEMCoatColor.WHITE, SWEMCoatColor.FOAL_WHITE);
                    put(SWEMCoatColor.GRAY, SWEMCoatColor.FOAL_GRAY);
                    put(SWEMCoatColor.BLACK, SWEMCoatColor.FOAL_BLACK);
                    put(SWEMCoatColor.CHESTNUT, SWEMCoatColor.FOAL_CHESTNUT);
                    put(SWEMCoatColor.BROWN, SWEMCoatColor.FOAL_BROWN);
                    put(SWEMCoatColor.ROAN, SWEMCoatColor.FOAL_CHESTNUT);
                    put(SWEMCoatColor.BUCKSKIN, SWEMCoatColor.FOAL_CREAMY);
                    put(SWEMCoatColor.PAINT, SWEMCoatColor.FOAL_CHESTNUT);
                    put(SWEMCoatColor.PALOMINO, SWEMCoatColor.FOAL_CREAMY);
                    put(SWEMCoatColor.NOBUCKLE, SWEMCoatColor.FOAL_CREAMY);
                    put(SWEMCoatColor.WILDANDFREE, SWEMCoatColor.FOAL_CHESTNUT);
                    put(SWEMCoatColor.TALLDARKHANDSOME, SWEMCoatColor.FOAL_BLACK);
                    put(SWEMCoatColor.SWEETBOI, SWEMCoatColor.FOAL_GRAY);
                    put(SWEMCoatColor.APPY, SWEMCoatColor.FOAL_BROWN);
                    put(SWEMCoatColor.GOLDEN, SWEMCoatColor.FOAL_CREAMY);
                    put(SWEMCoatColor.LEOPARD, SWEMCoatColor.FOAL_WHITE);
                    put(SWEMCoatColor.MAN_O_WAR, SWEMCoatColor.FOAL_CHESTNUT);
                    put(SWEMCoatColor.SECRETARIAT, SWEMCoatColor.FOAL_CHESTNUT);
                    put(SWEMCoatColor.SERGEANT_RECKLESS, SWEMCoatColor.FOAL_CHESTNUT);
                    put(SWEMCoatColor.VALEGRO, SWEMCoatColor.FOAL_DARK_BROWN);
                    put(SWEMCoatColor.DOLLAR_JOHN_WAYNE, SWEMCoatColor.FOAL_BROWN);
                    put(SWEMCoatColor.TRIGGER_ROY_ROGERS, SWEMCoatColor.FOAL_CREAMY);
                    put(SWEMCoatColor.MR_ED, SWEMCoatColor.FOAL_CREAMY);
                    put(SWEMCoatColor.JOERGEN_PEWDIEPIE, SWEMCoatColor.FOAL_DARK_BROWN);
                    put(SWEMCoatColor.SHWOOMPL_MARKIPLIER, SWEMCoatColor.FOAL_GRAY);
                    put(SWEMCoatColor.FINBAR_FOALEY_JACKSEPTICEYE, SWEMCoatColor.FOAL_GRAY);
                    put(SWEMCoatColor.JOEY_THIS_ESME, SWEMCoatColor.FOAL_GRAY);
                    put(SWEMCoatColor.GOOSEBERRY_JUSTPEACHY, SWEMCoatColor.FOAL_GRAY);
                    put(SWEMCoatColor.ARIELS_MALLI, SWEMCoatColor.FOAL_WHITE);
                    put(SWEMCoatColor.EL_CAZADOR_MALLI, SWEMCoatColor.FOAL_DARK_BROWN);
                    put(SWEMCoatColor.POLARIS_MALLI, SWEMCoatColor.FOAL_BLACK);
                    put(SWEMCoatColor.DUSTAR_MALLI, SWEMCoatColor.FOAL_CREAMY);
                    put(SWEMCoatColor.CALIHAN_MALLI, SWEMCoatColor.FOAL_BROWN);
                    put(SWEMCoatColor.VULCAN_ARISHANT, SWEMCoatColor.FOAL_CHESTNUT);
                    put(SWEMCoatColor.AVALANCHE_ARISHANT, SWEMCoatColor.FOAL_WHITE);
                    put(SWEMCoatColor.ORION_ARISHANT, SWEMCoatColor.FOAL_GRAY);
                    put(SWEMCoatColor.PARIS_ARISHANT, SWEMCoatColor.FOAL_GRAY);
                    put(SWEMCoatColor.ROSITA_ARISHANT, SWEMCoatColor.FOAL_GRAY);
                    put(SWEMCoatColor.IBIS_ARISHANT, SWEMCoatColor.FOAL_GRAY);
                    put(SWEMCoatColor.MONTY_ARISHANT, SWEMCoatColor.FOAL_BROWN);
                    put(SWEMCoatColor.HELIOS_ARISHANT, SWEMCoatColor.FOAL_BROWN);
                    //COAT #58-EarlyBay* put(SWEMCoatColor.TOBEADDED, SWEMCoatColor.FOAL_BROWN);
                    //COAT #59-RedBay* put(SWEMCoatColor.TOBEADDED, SWEMCoatColor.FOAL_BROWN);
                    put(SWEMCoatColor.POPPY_ARISHANT, SWEMCoatColor.FOAL_BROWN);
                    //COAT #61-LightSootyBay* put(SWEMCoatColor.TOBEADDED, SWEMCoatColor.FOAL_BROWN);
                    //COAT #62-DeepBay* put(SWEMCoatColor.TOBEADDED, SWEMCoatColor.FOAL_DARK_BROWN);
                    //COAT #63-SealBrownBay* put(SWEMCoatColor.TOBEADDED, SWEMCoatColor.FOAL_DARK_BROWN);
                    put(SWEMCoatColor.DOMINO_ARISHANT, SWEMCoatColor.FOAL_BLACK);
                    put(SWEMCoatColor.DAHLIA_ARISHANT, SWEMCoatColor.FOAL_CREAMY);
                    put(SWEMCoatColor.HIBISCUS_ARISHANT, SWEMCoatColor.FOAL_CREAMY);
                    put(SWEMCoatColor.TITUS_ARISHANT, SWEMCoatColor.FOAL_CREAMY);
                    put(SWEMCoatColor.ROMEO_ARISHANT, SWEMCoatColor.FOAL_DARK_BROWN);
                    put(SWEMCoatColor.PRIMROSE_ARISHANT, SWEMCoatColor.FOAL_CHESTNUT);
                    put(SWEMCoatColor.LUNAR_ARISHANT, SWEMCoatColor.FOAL_BLACK);
                    put(SWEMCoatColor.NERO_STARDUST, SWEMCoatColor.FOAL_WHITE);
                    put(SWEMCoatColor.BLUE_ROAN_FORTUNE_STARDUST, SWEMCoatColor.FOAL_GRAY);
                    put(SWEMCoatColor.MASQUERADE_ARISHANT, SWEMCoatColor.FOAL_WHITE);
                    put(SWEMCoatColor.MIKA_STARDUST, SWEMCoatColor.FOAL_BROWN);
                    put(SWEMCoatColor.PANAMA_MALLI, SWEMCoatColor.FOAL_GRAY);
                    put(SWEMCoatColor.ASTERIA_ARISHANT, SWEMCoatColor.FOAL_WHITE);
                    put(SWEMCoatColor.FREYJA_ARISHANT, SWEMCoatColor.FOAL_CREAMY);
                    put(SWEMCoatColor.SYMPHONY_ARISHANT, SWEMCoatColor.FOAL_CREAMY);
                    put(SWEMCoatColor.SONATA_ARISHANT, SWEMCoatColor.FOAL_CREAMY);
                    put(SWEMCoatColor.HEARTBREAKER_ARISHANT, SWEMCoatColor.FOAL_CHESTNUT);
                    put(SWEMCoatColor.FREIGHTER_MALLI, SWEMCoatColor.FOAL_DARK_BROWN);
                    put(SWEMCoatColor.DELTA_MALLI, SWEMCoatColor.FOAL_DARK_BROWN);
                    put(SWEMCoatColor.CARNELIAN_ARISHANT, SWEMCoatColor.FOAL_BROWN);
                    put(SWEMCoatColor.CHAMPION_ARISHANT, SWEMCoatColor.FOAL_CREAMY);
                    put(SWEMCoatColor.APHRODITE_ARISHANT, SWEMCoatColor.FOAL_CREAMY);
                    put(SWEMCoatColor.INFERNO_ARISHANT, SWEMCoatColor.FOAL_CHESTNUT);
                    put(SWEMCoatColor.MYSTIC_ARISHANT, SWEMCoatColor.FOAL_BLACK);
                    put(SWEMCoatColor.MARZAPA_ARISHANT, SWEMCoatColor.FOAL_BROWN);
                    put(SWEMCoatColor.BANDIT_ARISHANT, SWEMCoatColor.FOAL_CHESTNUT);
                    put(SWEMCoatColor.CAROUSEL_ARISHANT, SWEMCoatColor.FOAL_CREAMY);
                    put(SWEMCoatColor.ANTIQUE_ARISHANT, SWEMCoatColor.FOAL_CREAMY);
                    put(SWEMCoatColor.WREN_ARISHANT, SWEMCoatColor.FOAL_BROWN);
                    put(SWEMCoatColor.CITRINE_ARISHANT, SWEMCoatColor.FOAL_CREAMY);
                    put(SWEMCoatColor.HURRICANE_ARISHANT, SWEMCoatColor.FOAL_GRAY);
                    put(SWEMCoatColor.PHANTOM_ARISHANT, SWEMCoatColor.FOAL_BLACK);
                    put(SWEMCoatColor.RUMBLE_ARISHANT, SWEMCoatColor.FOAL_CHESTNUT);
                    put(SWEMCoatColor.MAPLE_ARISHANT, SWEMCoatColor.FOAL_CHESTNUT);
                    //COAT #99-BaldSabino* put(SWEMCoatColor.TOBEADDED, SWEMCoatColor.FOAL_BROWN);

                    //**APPRECIATION COATS (BREEDABLE)**
                    put(SWEMCoatColor.US_MARSHALL, SWEMCoatColor.FOAL_BROWN);
                    put(SWEMCoatColor.FRANK_STEVECV, SWEMCoatColor.FOAL_BROWN);
                    put(SWEMCoatColor.TOOTHBRUSH_BOATY, SWEMCoatColor.FOAL_GRAY);
                    put(SWEMCoatColor.KODIAK_DELPHI, SWEMCoatColor.FOAL_CREAMY);
                    put(SWEMCoatColor.ANNIE_LACE, SWEMCoatColor.FOAL_WHITE);
                    put (SWEMCoatColor.LADY_GUINEVERE_ARISHANT, SWEMCoatColor.FOAL_BLACK);
                    //COAT #106-* put(SWEMCoatColor.RIPPLE_BROOKE, SWEMCoatColor.FOAL_GRAY);
                    put(SWEMCoatColor.KHAREMA_CYTRIS, SWEMCoatColor.FOAL_BROWN);
                }
            };

    /**
     * Parent to foal coat swem coat color.
     *
     * @param parent the parent
     * @return the swem coat color
     */
    protected static SWEMCoatColor parentToFoalCoat(SWEMCoatColor parent) {
        SWEMCoatColor color = coatMap.get(parent);
        if (color == null) {
            SWEM.LOGGER.debug("Using white, since a non-mapped coat was used for breeding.");
            color = coatMap.get(SWEMCoatColor.WHITE);
        }
        return color;
    }

    /**
     * This returns a random coat of which the foal coat maps to based on the coat map above.
     *
     * @param foal The coat the foal has.
     * @return The coat the new grown parent should have.
     */
    protected static SWEMCoatColor foalToParentCoat(SWEMCoatColor foal) {
        List<SWEMCoatColor> applicableParentCoats = new ArrayList<>();
        for (Map.Entry<SWEMCoatColor, SWEMCoatColor> entry : coatMap.entrySet()) {
            if (entry.getValue() == foal && entry.getKey().isLapisObtainable()) {
                applicableParentCoats.add(entry.getKey());
            }
        }
        Collections.shuffle(applicableParentCoats);
        if (applicableParentCoats.isEmpty()) {
            SWEM.LOGGER.error(
                    "Something went wrong mapping foal coat: "
                            + foal
                            + " to any parent coat! Returning Parent White Coat.");
            return SWEMCoatColor.WHITE;
        }
        return applicableParentCoats.get(0);
    }
}
