package com.alaharranhonor.swem.entity.coats;
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
		put(SWEMCoatColor.ARIALS_MALLI, SWEMCoatColor.FOAL_WHITE);
		put(SWEMCoatColor.EL_CAZADOR_MALLI, SWEMCoatColor.FOAL_DARK_BROWN);
		put(SWEMCoatColor.LUNAR_ARISHANT, SWEMCoatColor.FOAL_BLACK);
		put(SWEMCoatColor.NERO_STARDUST, SWEMCoatColor.FOAL_WHITE);
		put(SWEMCoatColor.FRANK_STEVECV, SWEMCoatColor.FOAL_BROWN);
		put(SWEMCoatColor.KODIAK_DELPHI, SWEMCoatColor.FOAL_CREAMY);
		put(SWEMCoatColor.ANNIE_LACE, SWEMCoatColor.FOAL_WHITE);
		put(SWEMCoatColor.THIS_ESME_JOEY, SWEMCoatColor.FOAL_GRAY);
	}};


	/**
	 * Parent to foal coat swem coat color.
	 *
	 * @param parent the parent
	 * @return the swem coat color
	 */
	protected static SWEMCoatColor parentToFoalCoat(SWEMCoatColor parent) {
		return coatMap.get(parent);
	}

	/**
	 * This returns a random coat of which the foal coat maps to based on the coat map above.
	 * @param foal The coat the foal has.
	 * @return The coat the new grown parent should have.
	 */
	protected static SWEMCoatColor foalToParentCoat(SWEMCoatColor foal) {
		List<SWEMCoatColor> applicableParentCoats = new ArrayList<>();
		for (Map.Entry<SWEMCoatColor, SWEMCoatColor> entry : coatMap.entrySet()) {
			if (entry.getValue() == foal) {
				applicableParentCoats.add(entry.getKey());
			}
		}
		Collections.shuffle(applicableParentCoats);
		return applicableParentCoats.get(0);
	}
}
