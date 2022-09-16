package com.alaharranhonor.swem.client.coats;

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

import java.util.*;
import java.util.stream.Collectors;

public enum SWEMCoatColor {
    WHITE(1, true),
    GRAY(2, true),
    BLACK(3, true),
    CHESTNUT(4, true),
    BROWN(5, true),
    ROAN(6, true),
    BUCKSKIN(7, true),
    PAINT(8, true),
    PALOMINO(9, true),
    NOBUCKLE(10, true),
    WILDANDFREE(11, true),
    TALLDARKHANDSOME(12, true),
    SWEETBOI(13, true),
    APPY(14, true),
    GOLDEN(15, true),
    LEOPARD(16, true),
    GALAXY(17, false),
    RAINBOW(18, false),
    MAN_O_WAR(19, true),
    SECRETARIAT(20, true),
    SERGEANT_RECKLESS(21, true),
    VALEGRO(22, true),
    DOLLAR_JOHN_WAYNE(23, true),
    TRIGGER_ROY_ROGERS(24, true),
    MR_ED(25, true),
    JOERGEN_PEWDIEPIE(26, true),
    SHWOOMPL_MARKIPLIER(27, true),
    FINBAR_FOALEY_JACKSEPTICEYE(28, true),
    JOEY_THIS_ESME(29, true),
    GOOSEBERRY_JUSTPEACHY(30, true),
    EPONA_ZELDA(31, false),
    ROACH_WITCHER(32, false),
    AGRO_SOC(33, false),
    SHADOWMERE_OBLIVION(34, false),
    RAPIDASH_POKEMON(35, false),
    SWIFT_WIND_SHE_RA(36, false),
    BOB_FREE_REIN(37, false),
    FARCAH_ZORSE_TAN_HAUKET(38, false),
    SARINE_ZORSE_PAINT_HAUKET(39, false),
    CALIHOPE_ZORSE_DARK_HAUKET(40, false),
    COURIER_CALICO_HAUKET(41, false),
    GUARDIAN_BAY_GRAY_HAUKET(42, false),
    ROYAL_BRINDLE_HAUKET(43, false),
    RIPTIDE_PEACOCK_HAUKET(44, false),
    ARIELS_MALLI(45, true),
    EL_CAZADOR_MALLI(46, true),
    POLARIS_MALLI(47, true),
    DUSTAR_MALLI(48, true),
    CALIHAN_MALLI(49, true),
    VULCAN_ARISHANT(50, true),
    AVALANCHE_ARISHANT(51, true),
    ORION_ARISHANT(52, true),
    PARIS_ARISHANT(53, true),
    ROSITA_ARISHANT(54, true),
    IBIS_ARISHANT(55, true),
    MONTY_ARISHANT(56, true),
    HELIOS_ARISHANT(57, true),
    TYRA_CYTRIS(58, true),
    PHAROAH_ARISHANT(59, true),
    POPPY_ARISHANT(60, true),
    PARAMOUNT_CYTRIS(61, true),
    //62(62, true),
    //63(63, true),
    DOMINO_ARISHANT(64, true),
    DAHLIA_ARISHANT(65, true),
    HIBISCUS_ARISHANT(66, true),
    TITUS_ARISHANT(67, true),
    ROMEO_ARISHANT(68, true),
    PRIMROSE_ARISHANT(69, true),
    LUNAR_ARISHANT(70, true),
    NERO_STARDUST(71, true),
    BLUE_ROAN_FORTUNE_STARDUST(72, true),
    MASQUERADE_ARISHANT(73, true),
    MIKA_STARDUST(74, true),
    PANAMA_MALLI(75, true),
    ASTERIA_ARISHANT(76, true),
    FREYJA_ARISHANT(77, true),
    SYMPHONY_ARISHANT(78, true),
    SONATA_ARISHANT(79, true),
    HEARTBREAKER_ARISHANT(80, true),
    FREIGHTER_MALLI(81, true),
    DELTA_MALLI(82, true),
    BIRDIE_ARISHANT(83, true),
    CARNELIAN_ARISHANT(84, true),
    CHAMPION_ARISHANT(85, true),
    APHRODITE_ARISHANT(86, true),
    INFERNO_ARISHANT(87, true),
    MYSTIC_ARISHANT(88, true),
    MARZAPA_ARISHANT(89, true),
    BANDIT_ARISHANT(90, true),
    CAROUSEL_ARISHANT(91, true),
    ANTIQUE_ARISHANT(92, true),
    WREN_ARISHANT(93, true),
    CITRINE_ARISHANT(94, true),
    HURRICANE_ARISHANT(95, true),
    PHANTOM_ARISHANT(96, true),
    RUMBLE_ARISHANT(97, true),
    MAPLE_ARISHANT(98, true),
    //99(99, true),
    US_MARSHALL(100, true),
    FRANK_STEVECV(101, true),
    TOOTHBRUSH_BOATY(102, true),
    KODIAK_DELPHI(103, true),
    ANNIE_LACE(104, true),
    LADY_GUINEVERE_ARISHANT(105, true),
    //106(106, false),
    MIA_ERIC(107, false),
    //108(108,true),
    KHAREMA_CYTRIS(109, true),
    ANGEL_RILEY(110, true),
    LADY_JENNY(17351, false),
    LUCY_HANNAH(62118, false),
    FOAL_BLACK(70000, false),
    FOAL_BROWN(70001, false),
    FOAL_CHESTNUT(70002, false),
    FOAL_CREAMY(70003, false),
    FOAL_DARK_BROWN(70004, false),
    FOAL_GRAY(70005, false),
    FOAL_WHITE(70006, false);

    private static final SWEMCoatColor[] VALUES =
        Arrays.stream(values())
            .sorted(Comparator.comparingInt(SWEMCoatColor::getId))
            .toArray(SWEMCoatColor[]::new);
    private final int id;
    private final boolean lapisObtainable;

    /**
     * Instantiates a new Swem coat color.
     *
     * @param id              the id
     * @param lapisObtainable the lapis obtainable
     */
    SWEMCoatColor(int id, boolean lapisObtainable) {
        this.id = id;
        this.lapisObtainable = lapisObtainable;
    }

    /**
     * Gets index from id.
     *
     * @param id the id
     * @return the index from id
     */
    private static int getIndexFromId(int id) {
        for (int i = 0; i < VALUES.length; i++) {
            if (id == VALUES[i].getId()) {
                return i;
            }
        }
        //SWEM.LOGGER.error("Could not get any index from id: " + id + " - Returning index for white."); **Hannah requested this logger error be removed to lower user confusion in tech help
        return 1;
    }

    /**
     * Gets by id.
     *
     * @param id the id
     * @return the by id
     */
    public static SWEMCoatColor getById(int id) {
        for (SWEMCoatColor color : VALUES) {
            if (color.getId() == id) {
                return color;
            }
        }
        SWEM.LOGGER.error("Could not find any coat with id of: " + id + " - Returning white coat.");
        return SWEMCoatColor.WHITE;
    }

    /**
     * Gets next coat.
     *
     * @param prevId the prev id
     * @return the next coat
     */
    private static SWEMCoatColor getNextCoat(int prevId) {
        return VALUES[(prevId + 1) % VALUES.length];
    }

    /**
     * Gets previous coat.
     *
     * @param prevId the prev id
     * @return the previous coat
     */
    private static SWEMCoatColor getPreviousCoat(int prevId) {
        int index = prevId - 1;
        if (index < 0) {
            index += VALUES.length;
        }
        return VALUES[index % VALUES.length];
    }

    /**
     * Gets next cyclable coat.
     *
     * @param prevId the prev id
     * @return the next cyclable coat
     */
    public static SWEMCoatColor getNextCyclableCoat(int prevId) {
        SWEMCoatColor color = getNextCoat(getIndexFromId(prevId));
        while (!color.isLapisObtainable()) {
            color = getNextCoat(getIndexFromId(color.getId()));
        }
        return color;
    }

    /**
     * Gets previous cyclable coat.
     *
     * @param prevId the prev id
     * @return the previous cyclable coat
     */
    public static SWEMCoatColor getPreviousCyclableCoat(int prevId) {
        SWEMCoatColor color = getPreviousCoat(getIndexFromId(prevId));
        while (!color.isLapisObtainable()) {
            color = getPreviousCoat(getIndexFromId(color.getId()));
        }

        return color;
    }

    /**
     * Gets random coat.
     *
     * @return the random coat
     */
    private static SWEMCoatColor getRandomCoat() {
        Random random = new Random();
        return VALUES[random.nextInt(VALUES.length)];
    }

    /**
     * Gets random lapis obtainable coat.
     *
     * @return the random lapis obtainable coat
     */
    public static SWEMCoatColor getRandomLapisObtainableCoat() {
        SWEMCoatColor color = getRandomCoat();
        while (!color.isLapisObtainable()) {
            color = getNextCyclableCoat(getIndexFromId(color.getId()));
        }

        return color;
    }

    /**
     * Retrieves a random foal coat. Foal coats are defined as having id's of 70000 or above.
     *
     * @return The random foal coat to use.
     */
    public static SWEMCoatColor getRandomFoalCoat() {
        List<SWEMCoatColor> foalCoats =
            Arrays.stream(VALUES).filter((coat) -> coat.getId() >= 70000).collect(Collectors.toList());
        Collections.shuffle(foalCoats);

        return foalCoats.get(0);
    }

    /**
     * Wrapper method for {@link CoatMapper#parentToFoalCoat(SWEMCoatColor)}
     */
    public static SWEMCoatColor parentToFoalCoat(SWEMCoatColor parent) {
        return CoatMapper.parentToFoalCoat(parent);
    }

    /**
     * Wrapper method for {@link CoatMapper#foalToParentCoat(SWEMCoatColor)}
     */
    public static SWEMCoatColor foalToParentCoat(SWEMCoatColor foal) {
        return CoatMapper.foalToParentCoat(foal);
    }

    /**
     * Gets id.
     *
     * @return the id
     */
    public int getId() {
        return this.id;
    }

    /**
     * Is lapis obtainable boolean.
     *
     * @return the boolean
     */
    public boolean isLapisObtainable() {
        return lapisObtainable;
    }
}
