package com.alaharranhonor.swem.entity.coats;


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
	SERGEANT_RECKLESS(19, true),
	VALEGRO(20, true),
	MR_ED(23, true),
	THIS_ESME_JOEY(27, true),
	GALAXY(29, false),
	RAINBOW(30, false),
	EPONA_ZELDA(31, false),
	ROACH_WITCHER(32, false),
	AGRO_SOC(33, false),
	SHADOWMERE_OBLIVION(34, false),
	RAPIDASH_POKEMON(35, false),
	SWIFT_WIND_SHE_RA(36, false),
	BOB_FREE_REIN(37, false),
	ARIALS_MALLI(45, true),
	EL_CAZADOR_MALLI(46, true),
	NERO_STARDUST(49, true),
	FRANK_STEVECV(51, true),
	KODIAK_DELPHI(53, true);

	private static final SWEMCoatColor[] VALUES = Arrays.stream(values()).sorted(Comparator.comparingInt(SWEMCoatColor::getId)).toArray(SWEMCoatColor[]::new);
	private final int id;
	private final boolean lapisObtainable;
	SWEMCoatColor(int id, boolean lapisObtainable) {
		this.id = id;
		this.lapisObtainable = lapisObtainable;
	}

	public int getId() {
		return this.id;
	}

	public boolean isLapisObtainable() {
		return lapisObtainable;
	}

	private static int getIndexFromId(int id) {
		for (int i = 0; i < VALUES.length; i++) {
			if (id == VALUES[i].getId()) {
				return i;
			}
		}
		SWEM.LOGGER.error("Could not get any index from id: " + id + " - Returning index for white.");
		return 1;
	}

	public static SWEMCoatColor getById(int id) {
		for (SWEMCoatColor color : VALUES) {
			if (color.getId() == id) {
				return color;
			}
		}
		SWEM.LOGGER.error("Could not find any coat with id of: " + id + " - Returning white coat.");
		return SWEMCoatColor.WHITE;
	}

	public static SWEMCoatColor getNextCoat(int prevId) {
		return VALUES[(prevId + 1) % VALUES.length];
	}

	public static SWEMCoatColor getPreviousCoat(int prevId) {
		int index = prevId - 1;
		if (index < 0) {
			index += VALUES.length;
		}
		return VALUES[index % VALUES.length];
	}

	public static SWEMCoatColor getNextCyclableCoat(int prevId) {
		SWEMCoatColor color = getNextCoat(getIndexFromId(prevId));
		while (!color.isLapisObtainable()) {
			color = getNextCoat(getIndexFromId(color.getId()));
		}
		return color;
	}

	public static SWEMCoatColor getPreviousCyclableCoat(int prevId) {
		SWEMCoatColor color = getPreviousCoat(getIndexFromId(prevId));
		while (!color.isLapisObtainable()) {
			color = getPreviousCoat(getIndexFromId(color.getId()));
		}

		return color;
	}

	public static SWEMCoatColor getRandomCoat() {
		Random random = new Random();
		return VALUES[random.nextInt(VALUES.length)];
	}

	public static SWEMCoatColor getRandomLapisObtainableCoat() {
		SWEMCoatColor color = getRandomCoat();
		while (!color.isLapisObtainable()) {
			color = getNextCyclableCoat(getIndexFromId(color.getId()));
		}

		return color;
	}



}
