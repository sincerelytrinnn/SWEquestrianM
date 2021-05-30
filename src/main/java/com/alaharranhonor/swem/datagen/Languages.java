package com.alaharranhonor.swem.datagen;

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
