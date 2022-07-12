package com.alaharranhonor.swem.blocks;

import com.alaharranhonor.swem.util.registry.SWEMTileEntities;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.common.ToolType;

import javax.annotation.Nullable;


public class BlockOWater extends Block {
    /** Instantiates a Block O' Water. */
    public BlockOWater() {
        super(
                AbstractBlock.Properties.of(Material.WATER)
                        .strength(2.0f, 6.0f)
                        .sound(SoundType.WET_GRASS)
                        .harvestLevel(1)
                        .harvestTool(ToolType.PICKAXE));
    }

    public ActionResultType onBlockActivated(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand) {
        ItemStack itemHeld = player.getItemInHand(hand);
        TileEntity tank = world.getBlockEntity(pos);
        //do stuff here
        return null;
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return SWEMTileEntities.BLOCK_O_WATER_TILE_ENTITY.get().create();
    }

}