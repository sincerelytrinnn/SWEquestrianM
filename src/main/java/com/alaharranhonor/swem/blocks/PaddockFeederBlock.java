package com.alaharranhonor.swem.blocks;

import com.alaharranhonor.swem.util.initialization.SWEMBlocks;
import net.minecraft.block.*;
import net.minecraft.block.material.PushReaction;
import net.minecraft.client.particle.ParticleManager;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.state.properties.DoubleBlockHalf;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.ArrayList;

public class PaddockFeederBlock extends Block {
	public static final DirectionProperty FACING = HorizontalBlock.HORIZONTAL_FACING;
	public static final EnumProperty<DoubleBlockHalf> HALF = BlockStateProperties.DOUBLE_BLOCK_HALF;
	public static final IntegerProperty LEVEL = BlockStateProperties.LEVEL_0_3;
	public static final EnumProperty<SWEMBlockStateProperties.DoubleBlockSide> SIDE = SWEMBlockStateProperties.D_SIDE;


	public PaddockFeederBlock(AbstractBlock.Properties builder) {
		super(builder);
		this.setDefaultState(this.stateContainer.getBaseState().with(FACING, Direction.NORTH).with(HALF, DoubleBlockHalf.LOWER));
	}


	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
		Direction direction = state.get(FACING);
		DoubleBlockHalf half = state.get(HALF);
		SWEMBlockStateProperties.DoubleBlockSide side = state.get(SIDE);

		switch (direction) {
			case NORTH: {
				if (side == SWEMBlockStateProperties.DoubleBlockSide.LEFT) {
					return Block.makeCuboidShape(0, 0, 1, 14, 16, 15);
				} else {
					return Block.makeCuboidShape(2, 0, 1, 16, 16, 15);
				}
			}
			case EAST: {
				if (side == SWEMBlockStateProperties.DoubleBlockSide.LEFT) {
					return Block.makeCuboidShape(1, 0, 0, 15, 16, 14);
				} else {
					return Block.makeCuboidShape(1, 0, 2, 15, 16, 16);
				}
			}
			case SOUTH: {
				if (side == SWEMBlockStateProperties.DoubleBlockSide.LEFT) {
					return Block.makeCuboidShape(2, 0, 1, 16, 16, 15);
				} else {
					return Block.makeCuboidShape(0, 0, 1, 14, 16, 15);
				}
			}
			case WEST: {
				if (side == SWEMBlockStateProperties.DoubleBlockSide.LEFT) {
					return Block.makeCuboidShape(1, 0, 2, 15, 16, 16);
				} else {
					return Block.makeCuboidShape(1, 0, 0, 15, 16, 14);
				}
			}
		}

		return VoxelShapes.fullCube();
	}

	@Nullable
	public BlockState getStateForPlacement(BlockItemUseContext context) {
		Direction direction = context.getPlacementHorizontalFacing();

		return this.getDefaultState().with(FACING, direction.getOpposite()).with(SIDE, SWEMBlockStateProperties.DoubleBlockSide.LEFT);
	}

	@Override
	public void onBlockHarvested(World worldIn, BlockPos pos, BlockState state, PlayerEntity player) {
		if (!worldIn.isRemote) {

			this.getAllParts(state, pos, worldIn).stream().forEach((blockPos) -> {
				worldIn.setBlockState(blockPos, Blocks.AIR.getDefaultState(), 35);
			});

			BlockState state1 = state;
			if (state.get(HALF) == DoubleBlockHalf.LOWER) {
				state1 = worldIn.getBlockState(pos.up());
			}
			int level = state1.get(LEVEL);
			if (level > 0) {
				ItemEntity itemEntity = new ItemEntity(worldIn, pos.getX(), pos.getY(), pos.getZ(), new ItemStack(SWEMBlocks.QUALITY_BALE_ITEM.get(), level));
				worldIn.addEntity(itemEntity);
			}
		}

		super.onBlockHarvested(worldIn, pos, state, player);
	}

	/**
	 * Called by ItemBlocks after a block is set in the world, to allow post-place logic
	 */
	public void onBlockPlacedBy(World worldIn, BlockPos pos, BlockState state, LivingEntity placer, ItemStack stack) {

		// Z-Axis is Norht/South
		// X-Axis is East/West
		worldIn.setBlockState(pos.up(), state.with(HALF, DoubleBlockHalf.UPPER), 3);
		if (placer.getHorizontalFacing().getAxis() == Direction.Axis.Z) {
			switch (placer.getHorizontalFacing()) {
				case NORTH: {
					worldIn.setBlockState(pos.east(), state.with(SIDE, SWEMBlockStateProperties.DoubleBlockSide.RIGHT), 3);
					worldIn.setBlockState(pos.east().up(), state.with(SIDE, SWEMBlockStateProperties.DoubleBlockSide.RIGHT).with(HALF, DoubleBlockHalf.UPPER), 3);
					break;
				}
				case SOUTH: {
					worldIn.setBlockState(pos.west(), state.with(SIDE, SWEMBlockStateProperties.DoubleBlockSide.RIGHT), 3);
					worldIn.setBlockState(pos.west().up(), state.with(SIDE, SWEMBlockStateProperties.DoubleBlockSide.RIGHT).with(HALF, DoubleBlockHalf.UPPER), 3);
					break;
				}
			}
		} else if (placer.getHorizontalFacing().getAxis() == Direction.Axis.X) {
			switch (placer.getHorizontalFacing()) {
				case EAST: {
					worldIn.setBlockState(pos.south(), state.with(SIDE, SWEMBlockStateProperties.DoubleBlockSide.RIGHT), 3);
					worldIn.setBlockState(pos.south().up(), state.with(SIDE, SWEMBlockStateProperties.DoubleBlockSide.RIGHT).with(HALF, DoubleBlockHalf.UPPER), 3);
					break;
				}
				case WEST: {
					worldIn.setBlockState(pos.north(), state.with(SIDE, SWEMBlockStateProperties.DoubleBlockSide.RIGHT), 3);
					worldIn.setBlockState(pos.north().up(), state.with(SIDE, SWEMBlockStateProperties.DoubleBlockSide.RIGHT).with(HALF, DoubleBlockHalf.UPPER), 3);
					break;
				}
			}
		}

	}


	public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
		ItemStack stack = player.getHeldItem(handIn);
		if (stack.isEmpty()) {
			return ActionResultType.PASS;
		} else {
			BlockState state1 = state;
			BlockPos pos1 = pos;
			if (state.get(HALF) == DoubleBlockHalf.LOWER) {
				state1 = worldIn.getBlockState(pos.up());
				pos1 = pos.up();
			}

			int level = state1.get(LEVEL);
			BlockState stateOther;
			BlockPos posOther;

			Direction facing = state.get(FACING);

			if (state.get(SIDE) == SWEMBlockStateProperties.DoubleBlockSide.LEFT) {
				posOther = pos1.offset(facing.rotateYCCW());
				stateOther = worldIn.getBlockState(posOther);
			} else {
				posOther = pos1.offset(facing.rotateY());
				stateOther = worldIn.getBlockState(posOther);
			}


			Item item = stack.getItem();
			if (item == SWEMBlocks.QUALITY_BALE_ITEM.get()) {
				level++;
				worldIn.setBlockState(pos1, state1.with(LEVEL, MathHelper.clamp(level, 0, 3)));
				worldIn.setBlockState(posOther, stateOther.with(LEVEL, MathHelper.clamp(level, 0, 3)));
				return ActionResultType.CONSUME;
			} else {
				return ActionResultType.FAIL;
			}
		}
	}

	public ArrayList<BlockPos> getAllParts(BlockState state, BlockPos pos, World worldIn) {
		Direction direction = state.get(FACING);
		ArrayList<BlockPos> positions = new ArrayList<>();
		if (state.get(SIDE) == SWEMBlockStateProperties.DoubleBlockSide.LEFT) {
			// Check right and above.
			if (state.get(HALF) == DoubleBlockHalf.LOWER) {
				positions.add(pos);
				positions.add(pos.up());
				positions.add(pos.offset(direction.rotateYCCW()));
				positions.add(pos.offset(direction.rotateYCCW()).up());
			} else {
				positions.add(pos.down());
				positions.add(pos);
				positions.add(pos.offset(direction.rotateYCCW()).down());
				positions.add(pos.offset(direction.rotateYCCW()));
			}
		} else if (state.get(SIDE) == SWEMBlockStateProperties.DoubleBlockSide.RIGHT) {
			// Check Left and above.
			if (state.get(HALF) == DoubleBlockHalf.LOWER) {
				positions.add(pos);
				positions.add(pos.up());
				positions.add(pos.offset(direction.rotateY()));
				positions.add(pos.offset(direction.rotateY()).up());
			} else {
				positions.add(pos.down());
				positions.add(pos);
				positions.add(pos.offset(direction.rotateY()).down());
				positions.add(pos.offset(direction.rotateY()));
			}

		}

		return positions;
	}

	public void neighborChanged(BlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos, boolean isMoving) {

	}

	public boolean isValidPosition(BlockState state, IWorldReader worldIn, BlockPos pos) {
		return this.getAllParts(state, pos, (World) worldIn).stream().allMatch((pos1) ->  {
			BlockState state1 = worldIn.getBlockState(pos1);
			return state1 == Blocks.AIR.getDefaultState();
		});
	}

	@Override
	public boolean addDestroyEffects(BlockState state, World world, BlockPos pos, ParticleManager manager) {
		return true;
	}


	public PushReaction getPushReaction(BlockState state) {
		return PushReaction.DESTROY;
	}


	public BlockState rotate(BlockState state, Rotation rot) {
		return state.with(FACING, rot.rotate(state.get(FACING)));
	}


	public BlockState mirror(BlockState state, Mirror mirrorIn) {
		return mirrorIn == Mirror.NONE ? state : state.rotate(mirrorIn.toRotation(state.get(FACING)));
	}

	protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
		builder.add(HALF, FACING, SIDE, LEVEL);
	}
}
