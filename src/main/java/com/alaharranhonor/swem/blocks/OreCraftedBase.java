package com.alaharranhonor.swem.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraftforge.common.ToolType;

import net.minecraft.block.AbstractBlock;

public class OreCraftedBase extends Block{
    public OreCraftedBase() {
        super(AbstractBlock.Properties.create(Material.IRON)
                .hardnessAndResistance(2.0f, 6.0f)
                .sound(SoundType.STONE)
                .harvestLevel(2)
                .harvestTool(ToolType.PICKAXE)
        );
    }
}
