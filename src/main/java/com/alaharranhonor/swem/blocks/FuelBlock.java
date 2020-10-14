package com.alaharranhonor.swem.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorldReader;
import net.minecraftforge.common.ToolType;

public class FuelBlock extends Block {
    public FuelBlock() {
        super(Block.Properties.create(Material.IRON)
                .hardnessAndResistance(2.0f, 6.0f)
                .sound(SoundType.STONE)
                .harvestLevel(1)
                .harvestTool(ToolType.PICKAXE)
        );
    }

    /**
     * Currently only called by fire when it is on top of this block.
     * Returning true will prevent the fire from naturally dying during updating.
     * Also prevents firing from dying from rain.
     *
     * @param state The current state
     * @param world The current world
     * @param pos   Block position in world
     * @param side  The face that the fire is coming from
     * @return True if this block sustains fire, meaning it will never go out.
     */
    @Override
    public boolean isFireSource(BlockState state, IWorldReader world, BlockPos pos, Direction side) {
        return true;
    }
}
