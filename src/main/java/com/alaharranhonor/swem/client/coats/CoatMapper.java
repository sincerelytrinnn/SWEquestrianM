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
	private static HashMap<SWEMCoatColor, SWEMCoatColor> coatMap = new HashMap() {{
		put(SWEMCoatColor.WHITE, SWEMCoatColor.FOAL_WHITE);
		put(SWEMCoatColor.GRAY, SWEMCoatColor.FOAL_GRAY);
		put(SWEMCoatColor.BLACK, SWEMCoatColor.FOAL_BLACK);
		put(SWEMCoatColor.CHESTNUT, SWEMCoatColor.FOAL_CHESTNUT);
		put(SWEMCoatColor.BROWN, SWEMCoatColor.FOAL_BROWN);
		put(SWEMCoatColor.ROAN, SWEMCoatColor.FOAL_CHESTNUT);
		put(SWEMCoatColor.BUCKSKIN, SWEMCoatColor.FOAL_CREAMY);
		put(SWEMCoatColor.PAINT, SWEMCoatColor.FOAL_BROWN);
		put(SWEMCoatColor.PALOMINO, SWEMCoatColor.FOAL_CREAMY);
		put(SWEMCoatColor.NOBUCKLE, SWEMCoatColor.FOAL_BROWN);
		put(SWEMCoatColor.WILDANDFREE, SWEMCoatColor.FOAL_CHESTNUT);
		put(SWEMCoatColor.TALLDARKHANDSOME, SWEMCoatColor.FOAL_BLACK);
		put(SWEMCoatColor.SWEETBOI, SWEMCoatColor.FOAL_GRAY);
		put(SWEMCoatColor.APPY, SWEMCoatColor.FOAL_BROWN);
		put(SWEMCoatColor.GOLDEN, SWEMCoatColor.FOAL_CREAMY);
		put(SWEMCoatColor.LEOPARD, SWEMCoatColor.FOAL_WHITE);
		put(SWEMCoatColor.SECRETARIAT, SWEMCoatColor.FOAL_CHESTNUT);
		put(SWEMCoatColor.SERGEANT_RECKLESS, SWEMCoatColor.FOAL_CHESTNUT);
		put(SWEMCoatColor.VALEGRO, SWEMCoatColor.FOAL_GRAY);
		put(SWEMCoatColor.TRIGGER_ROY_ROGERS, SWEMCoatColor.FOAL_CREAMY);
		put(SWEMCoatColor.MR_ED, SWEMCoatColor.FOAL_CREAMY);
		put(SWEMCoatColor.JOERGEN_PEWDIEPIE, SWEMCoatColor.FOAL_DARK_BROWN);
		put(SWEMCoatColor.SHWOOMPL_MARKIPLIER, SWEMCoatColor.FOAL_GRAY);
		put(SWEMCoatColor.GOOSEBERRY_JUSTPEACHY, SWEMCoatColor.FOAL_GRAY);
		put(SWEMCoatColor.ARIELS_MALLI, SWEMCoatColor.FOAL_WHITE);
		put(SWEMCoatColor.EL_CAZADOR_MALLI, SWEMCoatColor.FOAL_DARK_BROWN);
		put(SWEMCoatColor.LUNAR_ARISHANT, SWEMCoatColor.FOAL_BLACK);
		put(SWEMCoatColor.NERO_STARDUST, SWEMCoatColor.FOAL_WHITE);
		put(SWEMCoatColor.FRANK_STEVECV, SWEMCoatColor.FOAL_BROWN);
		put(SWEMCoatColor.KODIAK_DELPHI, SWEMCoatColor.FOAL_CREAMY);
		put(SWEMCoatColor.ANNIE_LACE, SWEMCoatColor.FOAL_WHITE);
		put(SWEMCoatColor.JOEY_THIS_ESME, SWEMCoatColor.FOAL_GRAY);
		put(SWEMCoatColor.POLARIS_MALLI, SWEMCoatColor.FOAL_WHITE);
		put(SWEMCoatColor.DUSTAR_MALLI, SWEMCoatColor.FOAL_CREAMY);
		put(SWEMCoatColor.CALIHAN_MALLI, SWEMCoatColor.FOAL_CHESTNUT);
		put(SWEMCoatColor.MASQUERADE_ARISHANT, SWEMCoatColor.FOAL_WHITE);
		put(SWEMCoatColor.PANAMA_MALLI, SWEMCoatColor.FOAL_GRAY);
		put(SWEMCoatColor.ASTERIA_ARISHANT, SWEMCoatColor.FOAL_BLACK);
		put(SWEMCoatColor.BLUE_ROAN_FORTUNE_STARDUST, SWEMCoatColor.FOAL_GRAY);
		put(SWEMCoatColor.GUARDIAN_BAY_GRAY_HAUKET, SWEMCoatColor.FOAL_BROWN);
		put(SWEMCoatColor.COURIER_CALICO_HAUKET, SWEMCoatColor.FOAL_WHITE);
		put(SWEMCoatColor.ROYAL_BRINDLE_HAUKET, SWEMCoatColor.FOAL_DARK_BROWN);
		put(SWEMCoatColor.DOLLAR_JOHN_WAYNE, SWEMCoatColor.FOAL_CHESTNUT);
		put(SWEMCoatColor.FINBAR_FOALEY_JACKSEPTICEYE, SWEMCoatColor.FOAL_GRAY);
		put(SWEMCoatColor.MAN_O_WAR, SWEMCoatColor.FOAL_CHESTNUT);
		put(SWEMCoatColor.MIKA_STARDUST, SWEMCoatColor.FOAL_CREAMY);
		put(SWEMCoatColor.SARINE_ZORSE_PAINT_HAUKET, SWEMCoatColor.FOAL_BROWN);
		put(SWEMCoatColor.TOOTHBRUSH_BOATY, SWEMCoatColor.FOAL_GRAY);
		put(SWEMCoatColor.RIPTIDE_PEACOCK_HAUKET, SWEMCoatColor.FOAL_GRAY);
	}};


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
			SWEM.LOGGER.error("Something went wrong mapping foal coat: " + foal + " to any parent coat! Returning Parent White Coat.");
			return SWEMCoatColor.WHITE;
		}
		return applicableParentCoats.get(0);
	}
}
