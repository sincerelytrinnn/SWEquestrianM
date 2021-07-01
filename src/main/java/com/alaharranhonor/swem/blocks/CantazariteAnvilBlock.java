package com.alaharranhonor.swem.blocks;

import com.alaharranhonor.swem.tileentity.CantazariteAnvilTE;
import com.alaharranhonor.swem.util.initialization.SWEMTileEntities;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.StateContainer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

import javax.annotation.Nullable;

import net.minecraft.block.AbstractBlock.Properties;

public class CantazariteAnvilBlock extends HorizontalBlock {

	public CantazariteAnvilBlock(Properties properties) {
		super(properties);
		this.registerDefaultState(
			this.stateDefinition.any()
				.setValue(FACING, Direction.NORTH)
		);
	}

	@Override
	public ActionResultType use(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
		if (!worldIn.isClientSide) {
			TileEntity tile = worldIn.getBlockEntity(pos);
			if (tile instanceof CantazariteAnvilTE) {
				NetworkHooks.openGui((ServerPlayerEntity) player, (CantazariteAnvilTE) tile, (buffer) -> {
					buffer.writeBlockPos(pos);
				});

			}
		}
		return ActionResultType.SUCCESS;
	}

	@Override
	public boolean hasTileEntity(BlockState state) {
		return true;
	}

	@Nullable
	@Override
	public TileEntity createTileEntity(BlockState state, IBlockReader world) {
		return SWEMTileEntities.CANTAZARITE_ANVIL_TILE_ENTITY.get().create();
	}

	@Override
	public boolean canSurvive(BlockState state, IWorldReader worldIn, BlockPos pos) {
		if (worldIn.getBlockState(pos.below()).canOcclude()) {
			return true;
		}
		return false;
	}

	@Override
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
		double xStart = state.getValue(FACING).getAxis() == Direction.Axis.Z ? 0.125D : 0.3125D;
		double zStart = state.getValue(FACING).getAxis() == Direction.Axis.X ? 0.125D : 0.3125D;
		return VoxelShapes.box(xStart, 0.0d, zStart, 1.0D - xStart, 0.5d, 1.0D - zStart);
	}

	@Override
	public BlockState getStateForPlacement(BlockItemUseContext context) {
		return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection());
	}

	@Override
	protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
		builder.add(FACING);
	}
}
