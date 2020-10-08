package com.alaharranhonor.swem.blocks;

import com.alaharranhonor.swem.SWEM;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraftforge.common.ToolType;

public class RubberMatBase extends Block{
    public RubberMatBase() {
        super(Block.Properties.create(Material.IRON)
                .hardnessAndResistance(1.0f, 3.0f)
                .sound(SoundType.STONE)
                .harvestLevel(0)
        );
    }
}
