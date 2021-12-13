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

import java.util.*;

public enum SWEMCoatColors {
	WHITE(0),
	GRAY(1),
	BLACK(2),
	CHESTNUT(3),
	BROWN(4),
	REDROAN(5),
	BUCKSKIN(6),
	PAINT(7),
	PALOMINO(8),
	NOBUCKLE(9),
	WILDANDFREE(10),
	TALLDARKHANDSOME(11),
	SWEETBOY(12),
	APPY(13),
	GOLDEN(14),
	LEOPARD(15),
	LADY(16),
	GALAXY(17),
	RAINBOW(18);

	private static final SWEMCoatColors[] VALUES = Arrays.stream(values()).sorted(Comparator.comparingInt(SWEMCoatColors::getId)).toArray(SWEMCoatColors[]::new);
	private final int id;
	SWEMCoatColors(int id) {
		this.id = id;
	}

	public int getId() {
		return this.id;
	}

	public static SWEMCoatColors getById(int id) {
		return VALUES[id % VALUES.length];
	}



}
