package com.alaharranhonor.swem.blocks;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.block.material.PushReaction;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.StateContainer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;

public class SWLMGlazeTerracottaBlock extends HorizontalBlock {
    public SWLMGlazeTerracottaBlock(AbstractBlock.Properties properties) {
        super(properties);
    }

    public void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(HORIZONTAL_FACING);
    }

    public BlockState getStateForPlacement(BlockItemUseContext context) {
        return this.getDefaultState().with(HORIZONTAL_FACING, context.getPlacementHorizontalFacing().getOpposite());
    }

    /**
     * @deprecated call via {@link //IBlockState#getMobilityFlag()} whenever possible. Implementing/overriding is fine.
     */
    public PushReaction getPushReaction(BlockState state) {
        return PushReaction.PUSH_ONLY;
    }

    @Override
    public int getLightValue(BlockState state, IBlockReader world, BlockPos pos) {
        return 15;
    }
}
