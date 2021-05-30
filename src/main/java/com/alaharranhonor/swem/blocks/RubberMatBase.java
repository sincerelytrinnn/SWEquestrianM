package com.alaharranhonor.swem.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

public class RubberMatBase extends Block{
    public RubberMatBase() {
        super(Block.Properties.create(Material.IRON)
                .hardnessAndResistance(1.0f, 3.0f)
                .sound(SoundType.STONE)
                .harvestLevel(0)
        );
    }
}
