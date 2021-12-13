package com.alaharranhonor.swem.datagen;


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

import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.LanguageProvider;


public class Languages extends LanguageProvider {

	private String[][] translations;
	private int localeIndex;

	public Languages(DataGenerator gen, String modid, String locale, String[][] translations, int localeIndex) {
		this(gen, modid, locale);
		this.translations = translations;
		this.localeIndex = localeIndex;
	}
	public Languages(DataGenerator gen, String modid, String locale) {
		super(gen, modid, locale);
	}

	@Override
	protected void addTranslations() {
		for (String[] set : this.translations) {
			String key = set[0];
			if (set.length - 1 < this.localeIndex)
				continue;
			String translation = set[this.localeIndex];
			this.add(key, translation);

		}
	}
}
