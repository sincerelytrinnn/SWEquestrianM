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
	LADY_JENNY(0, false),
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
	THIS_ESME_JOEY(29, true),
	GOOSEBERRY_JUSTPEACHY(30, true),
	EPONA_ZELDA(31, false),
	ROACH_WITCHER(32, false),
	AGRO_SOC(33, false),
	SHADOWMERE_OBLIVION(34, false),
	RAPIDASH_POKEMON(35, false),
	SWIFT_WIND_SHE_RA(36, false),
	BOB_FREE_REIN(37, false),
	ZORSE_PAINT(39, false),
	CHIMERA_CALICO(41, false),
	CHIMERA_BAY_GRAY(42, false),
	CHIMERA_BRINDLE(43, false),
	PEACOCK_APPY(44, false),
	ARIALS_MALLI(45, true),
	EL_CAZADOR_MALLI(46, true),
	BLACK_BIRDCATCHER_MALLI(47, true),
	BUCKSKIN_OVERO_MALLI(48, true),
	FLAXEN_LIVER_MALLI(49, true),

	LUNAR_ARISHANT(70, true),
	NERO_STARDUST(71, true),
	BLUE_ROAN_FORTUNE_STARDUST(72, true),
	MEDICINE_HAT_ARISHANT(73, true),
	SILVER_BAY_STARDUST(74, true),
	SILVER_DAPPLE_SPLASH_MALLI(75, true),
	SMUDGED_LEOPARD_ARISHANT(76, true),
	FRANK_STEVECV(101, true),
	TOOTHBRUSH_BOATY(102, true),
	KODIAK_DELPHI(103, true),
	ANNIE_LACE(104, true),
	FOAL_BLACK(70000, false),
	FOAL_BROWN(70001, false),
	FOAL_CHESTNUT(70002, false),
	FOAL_CREAMY(70003, false),
	FOAL_DARK_BROWN(70004, false),
	FOAL_GRAY(70005, false),
	FOAL_WHITE(70006, false);

	private static final SWEMCoatColor[] VALUES = Arrays.stream(values()).sorted(Comparator.comparingInt(SWEMCoatColor::getId)).toArray(SWEMCoatColor[]::new);
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
		SWEM.LOGGER.error("Could not get any index from id: " + id + " - Returning index for white.");
		return 1;
	}

	/**
	 * Gets by id.
	 *
	 * @param id the id
	 * @return the by id
	 */
	public static SWEMCoatColor getById(int id) {
		// Block the GALAXY AND RAINBOW coats.
		while (id == 17 || id == 18) {
			id += 1;
		}
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
	 * Retrieves a random foal coat.
	 * Foal coats are defined as having id's of 70000 or above.
	 * @return The random foal coat to use.
	 */
	public static SWEMCoatColor getRandomFoalCoat() {
		List<SWEMCoatColor> foalCoats = Arrays.stream(VALUES).filter((coat) -> coat.getId() >= 70000).collect(Collectors.toList());
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



}