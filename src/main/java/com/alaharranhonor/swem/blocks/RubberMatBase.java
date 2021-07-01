package com.alaharranhonor.swem.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

import net.minecraft.block.AbstractBlock;

public class RubberMatBase extends Block{
    public RubberMatBase() {
        super(AbstractBlock.Properties.of(Material.METAL)
                .strength(1.0f, 3.0f)
                .sound(SoundType.STONE)
                .harvestLevel(0)
        );
    }
}
