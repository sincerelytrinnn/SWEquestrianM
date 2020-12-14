package com.alaharranhonor.swem.datagen;

import com.alaharranhonor.swem.blocks.NonParallelBlock;
import com.alaharranhonor.swem.util.initialization.SWEMBlocks;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.LanguageProvider;
import net.minecraftforge.fml.RegistryObject;

public class Languages extends LanguageProvider {
	public Languages(DataGenerator gen, String modid, String locale) {
		super(gen, modid, locale);
	}

	@Override
	protected void addTranslations() {
		for (RegistryObject<NonParallelBlock> block : SWEMBlocks.SEPARATORS) {
			// capital colour letter.
			String first = block.get().getColour().getString().substring(0, 1).toUpperCase();
			String colour = first + block.get().getColour().getString().substring(1);
			this.add(block.get().asItem(), "Separator " + colour);
		}
	}
}
