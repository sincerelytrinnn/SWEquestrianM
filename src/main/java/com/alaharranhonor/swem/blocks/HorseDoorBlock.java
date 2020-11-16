package com.alaharranhonor.swem.blocks;

import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.PushReaction;
import net.minecraft.client.particle.ParticleManager;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.pathfinding.PathType;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.state.properties.DoorHingeSide;
import net.minecraft.state.properties.DoubleBlockHalf;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.ArrayList;

public class HorseDoorBlock extends Block{
	public static final DirectionProperty FACING = HorizontalBlock.HORIZONTAL_FACING;
	public static final BooleanProperty OPEN = BlockStateProperties.OPEN;
	public static final EnumProperty<DoorHingeSide> HINGE = BlockStateProperties.DOOR_HINGE;
	public static final EnumProperty<DoubleBlockHalf> HALF = BlockStateProperties.DOUBLE_BLOCK_HALF;
	public static final EnumProperty<SWEMBlockStateProperties.DoubleBlockSide> SIDE = SWEMBlockStateProperties.D_SIDE;
	protected static final VoxelShape SOUTH_AABB = Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 3.0D);
	protected static final VoxelShape NORTH_AABB = Block.makeCuboidShape(0.0D, 0.0D, 13.0D, 16.0D, 16.0D, 16.0D);
	protected static final VoxelShape WEST_AABB = Block.makeCuboidShape(13.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D);
	protected static final VoxelShape EAST_AABB = Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 3.0D, 16.0D, 16.0D);

	public HorseDoorBlock(AbstractBlock.Properties builder) {
		super(builder);
		this.setDefaultState(this.stateContainer.getBaseState().with(FACING, Direction.NORTH).with(OPEN, Boolean.valueOf(false)).with(HINGE, DoorHingeSide.LEFT).with(HALF, DoubleBlockHalf.LOWER));
	}

	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
		Direction direction = state.get(FACING);
		boolean flag = !state.get(OPEN);
		boolean flag1 = state.get(HINGE) == DoorHingeSide.RIGHT;
		switch(direction) {
			case EAST:
			default:
				return flag ? EAST_AABB : (flag1 ? NORTH_AABB : SOUTH_AABB);
			case SOUTH:
				return flag ? SOUTH_AABB : (flag1 ? EAST_AABB : WEST_AABB);
			case WEST:
				return flag ? WEST_AABB : (flag1 ? SOUTH_AABB : NORTH_AABB);
			case NORTH:
				return flag ? NORTH_AABB : (flag1 ? WEST_AABB : EAST_AABB);
		}
	}

	/**
	 * Update the provided state given the provided neighbor facing and neighbor state, returning a new state.
	 * For example, fences make their connections to the passed in state if possible, and wet concrete powder immediately
	 * returns its solidified counterpart.
	 * Note that this method should ideally consider only the specific face passed in.
	 */
	public BlockState updatePostPlacement(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos) {
		DoubleBlockHalf doubleblockhalf = stateIn.get(HALF);
		if (facing.getAxis() == Direction.Axis.Y && doubleblockhalf == DoubleBlockHalf.LOWER == (facing == Direction.UP)) {
			return facingState.isIn(this) && facingState.get(HALF) != doubleblockhalf ? stateIn.with(FACING, facingState.get(FACING)).with(OPEN, facingState.get(OPEN)).with(HINGE, facingState.get(HINGE)) : Blocks.AIR.getDefaultState();
		} else {
			return doubleblockhalf == DoubleBlockHalf.LOWER && facing == Direction.DOWN && !stateIn.isValidPosition(worldIn, currentPos) ? Blocks.AIR.getDefaultState() : super.updatePostPlacement(stateIn, facing, facingState, worldIn, currentPos, facingPos);
		}
	}



	public boolean allowsMovement(BlockState state, IBlockReader worldIn, BlockPos pos, PathType type) {
		switch(type) {
			case LAND:
				return state.get(OPEN);
			case WATER:
				return false;
			case AIR:
				return state.get(OPEN);
			default:
				return false;
		}
	}

	private int getCloseSound() {
		return this.material == Material.IRON ? 1011 : 1012;
	}

	private int getOpenSound() {
		return this.material == Material.IRON ? 1005 : 1006;
	}

	@Nullable
	public BlockState getStateForPlacement(BlockItemUseContext context) {
		BlockPos blockpos = context.getPos();
		Direction direction = context.getPlacementHorizontalFacing();
		DoorHingeSide hinge = this.getHingeSide(context);


		if (hinge == DoorHingeSide.LEFT) {
			// Check right
			ArrayList<Boolean> blockChecks = new ArrayList<>();
			BlockPos.getAllInBox(blockpos, blockpos.up().func_241872_a(direction.getAxis(), 1)).forEach(blockPos1 -> {
				blockChecks.add(context.getWorld().getBlockState(blockPos1).isReplaceable(context));
			});

			if (blockpos.getY() < 254 && blockChecks.stream().allMatch((bool) -> bool)) {
				World world = context.getWorld();

				boolean flag = world.isBlockPowered(blockpos) || world.isBlockPowered(blockpos.up());
				return this.getDefaultState().with(FACING, context.getPlacementHorizontalFacing()).with(HINGE, this.getHingeSide(context)).with(OPEN, Boolean.valueOf(flag)).with(HALF, DoubleBlockHalf.LOWER).with(SIDE, SWEMBlockStateProperties.DoubleBlockSide.LEFT);
			} else {
				return null;
			}
		} else {
			// Check right.
			ArrayList<Boolean> blockChecks = new ArrayList<>();
			BlockPos.getAllInBox(blockpos, blockpos.up().func_241872_a(direction.getAxis(), -1)).forEach(blockPos1 -> {
				blockChecks.add(context.getWorld().getBlockState(blockPos1).isReplaceable(context));
			});

			if (blockpos.getY() < 254 && blockChecks.stream().allMatch((bool) -> bool)) {
				World world = context.getWorld();

				boolean flag = world.isBlockPowered(blockpos) || world.isBlockPowered(blockpos.up());
				return this.getDefaultState().with(FACING, context.getPlacementHorizontalFacing()).with(HINGE, this.getHingeSide(context)).with(OPEN, Boolean.valueOf(flag)).with(HALF, DoubleBlockHalf.LOWER).with(SIDE, SWEMBlockStateProperties.DoubleBlockSide.RIGHT);
			} else {
				return null;
			}
		}
	}

	@Override
	public void onBlockHarvested(World worldIn, BlockPos pos, BlockState state, PlayerEntity player) {
		if (!worldIn.isRemote && player.isCreative()) {
			worldIn.setBlockState(pos, Blocks.AIR.getDefaultState(), 35);
			worldIn.playEvent(player, 2001, pos, Block.getStateId(state));
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
		if (state.get(SIDE) == SWEMBlockStateProperties.DoubleBlockSide.LEFT) {
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
		} else {
			if (placer.getHorizontalFacing().getAxis() == Direction.Axis.Z) {
				switch (placer.getHorizontalFacing()) {
					case NORTH: {
						worldIn.setBlockState(pos.west(), state.with(SIDE, SWEMBlockStateProperties.DoubleBlockSide.LEFT), 3);
						worldIn.setBlockState(pos.west().up(), state.with(SIDE, SWEMBlockStateProperties.DoubleBlockSide.LEFT).with(HALF, DoubleBlockHalf.UPPER), 3);
						break;
					}
					case SOUTH: {
						worldIn.setBlockState(pos.east(), state.with(SIDE, SWEMBlockStateProperties.DoubleBlockSide.LEFT), 3);
						worldIn.setBlockState(pos.east().up(), state.with(SIDE, SWEMBlockStateProperties.DoubleBlockSide.LEFT).with(HALF, DoubleBlockHalf.UPPER), 3);
						break;
					}
				}
			} else if (placer.getHorizontalFacing().getAxis() == Direction.Axis.X) {
				switch (placer.getHorizontalFacing()) {
					case EAST: {
						worldIn.setBlockState(pos.north(), state.with(SIDE, SWEMBlockStateProperties.DoubleBlockSide.LEFT), 3);
						worldIn.setBlockState(pos.north().up(), state.with(SIDE, SWEMBlockStateProperties.DoubleBlockSide.LEFT).with(HALF, DoubleBlockHalf.UPPER), 3);
						break;
					}
					case WEST: {
						worldIn.setBlockState(pos.south(), state.with(SIDE, SWEMBlockStateProperties.DoubleBlockSide.LEFT), 3);
						worldIn.setBlockState(pos.south().up(), state.with(SIDE, SWEMBlockStateProperties.DoubleBlockSide.LEFT).with(HALF, DoubleBlockHalf.UPPER), 3);
						break;
					}
				}
			}
		}

	}

	private DoorHingeSide getHingeSide(BlockItemUseContext context) {
		IBlockReader iblockreader = context.getWorld();
		BlockPos blockpos = context.getPos();
		Direction direction = context.getPlacementHorizontalFacing();
		BlockPos blockpos1 = blockpos.up();
		Direction direction1 = direction.rotateYCCW();
		BlockPos blockpos2 = blockpos.offset(direction1);
		BlockState blockstate = iblockreader.getBlockState(blockpos2);
		BlockPos blockpos3 = blockpos1.offset(direction1);
		BlockState blockstate1 = iblockreader.getBlockState(blockpos3);
		Direction direction2 = direction.rotateY();
		BlockPos blockpos4 = blockpos.offset(direction2);
		BlockState blockstate2 = iblockreader.getBlockState(blockpos4);
		BlockPos blockpos5 = blockpos1.offset(direction2);
		BlockState blockstate3 = iblockreader.getBlockState(blockpos5);
		int i = (blockstate.hasOpaqueCollisionShape(iblockreader, blockpos2) ? -1 : 0) + (blockstate1.hasOpaqueCollisionShape(iblockreader, blockpos3) ? -1 : 0) + (blockstate2.hasOpaqueCollisionShape(iblockreader, blockpos4) ? 1 : 0) + (blockstate3.hasOpaqueCollisionShape(iblockreader, blockpos5) ? 1 : 0);
		boolean flag = blockstate.isIn(this) && blockstate.get(HALF) == DoubleBlockHalf.LOWER;
		boolean flag1 = blockstate2.isIn(this) && blockstate2.get(HALF) == DoubleBlockHalf.LOWER;
		if ((!flag || flag1) && i <= 0) {
			if ((!flag1 || flag) && i >= 0) {
				int j = direction.getXOffset();
				int k = direction.getZOffset();
				Vector3d vector3d = context.getHitVec();
				double d0 = vector3d.x - (double)blockpos.getX();
				double d1 = vector3d.z - (double)blockpos.getZ();
				return (j >= 0 || !(d1 < 0.5D)) && (j <= 0 || !(d1 > 0.5D)) && (k >= 0 || !(d0 > 0.5D)) && (k <= 0 || !(d0 < 0.5D)) ? DoorHingeSide.LEFT : DoorHingeSide.RIGHT;
			} else {
				return DoorHingeSide.LEFT;
			}
		} else {
			return DoorHingeSide.RIGHT;
		}
	}

	public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {

		if (this.material == Material.IRON) {
			return ActionResultType.PASS;
		} else {
			if (!worldIn.isRemote) {
				state = state.func_235896_a_(OPEN);
				boolean open = state.get(OPEN);
				ArrayList<BlockState> states = new ArrayList<>();
				ArrayList<BlockPos> positions = this.getAllDoorParts(state, pos, worldIn);
				positions.forEach((pos1) -> states.add(worldIn.getBlockState(pos1)));
				for (int i = 0; i < 4; i++) {
					this.openDoor(worldIn, states.get(i), positions.get(i), open);
				}
				worldIn.playEvent(player, state.get(OPEN) ? this.getOpenSound() : this.getCloseSound(), pos, 0);
			}
			return ActionResultType.func_233537_a_(worldIn.isRemote);
		}
	}

	public boolean isOpen(BlockState state) {
		return state.get(OPEN);
	}

	public void openDoor(World worldIn, BlockState state, BlockPos pos, boolean open) {
		if (state.isIn(this)) {
			if (state.get(HINGE).toString().equals(state.get(SIDE).toString())) {
				// Open normally
				worldIn.setBlockState(pos, state.with(OPEN, open), 10);
				this.playSound(worldIn, pos, open);
			} else {
				// Else offset the block.
				if (open) {
					switch (state.get(FACING)) {
						case NORTH: {
							if (state.get(HINGE) == DoorHingeSide.LEFT) {
								worldIn.setBlockState(pos.add(-1, 0, -1), state.with(OPEN, Boolean.valueOf(open)));
								break;
							} else {
								worldIn.setBlockState(pos.add(1, 0, -1), state.with(OPEN, Boolean.valueOf(open)));
								break;
							}
						}
						case EAST: {
							if (state.get(HINGE) == DoorHingeSide.LEFT) {
								worldIn.setBlockState(pos.add(1, 0, -1), state.with(OPEN, Boolean.valueOf(open)), 10);
								break;
							} else {
								worldIn.setBlockState(pos.add(1, 0, 1), state.with(OPEN, Boolean.valueOf(open)), 10);
								break;
							}
						}
						case SOUTH: {
							if (state.get(HINGE) == DoorHingeSide.LEFT) {
								worldIn.setBlockState(pos.add(1, 0, 1), state.with(OPEN, Boolean.valueOf(open)), 10);
								break;
							} else {
								worldIn.setBlockState(pos.add(-1, 0, 1), state.with(OPEN, Boolean.valueOf(open)), 10);
								break;
							}
						}
						case WEST: {
							if (state.get(HINGE) == DoorHingeSide.LEFT) {
								worldIn.setBlockState(pos.add(-1, 0, 1), state.with(OPEN, Boolean.valueOf(open)), 10);
								break;
							} else {
								worldIn.setBlockState(pos.add(-1, 0, -1), state.with(OPEN, Boolean.valueOf(open)), 10);
								break;
							}
						}
					}
					worldIn.setBlockState(pos, Blocks.AIR.getDefaultState());
				} else {
					switch (state.get(FACING)) {
						case NORTH: {
							if (state.get(HINGE) == DoorHingeSide.LEFT) {
								worldIn.setBlockState(pos.add(1, 0, 1), state.with(OPEN, Boolean.valueOf(open)));
								break;
							} else {
								worldIn.setBlockState(pos.add(-1, 0, 1), state.with(OPEN, Boolean.valueOf(open)));
								break;
							}
						}
						case EAST: {
							if (state.get(HINGE) == DoorHingeSide.LEFT) {
								worldIn.setBlockState(pos.add(-1, 0, 1), state.with(OPEN, Boolean.valueOf(open)), 10);
								break;
							} else {
								worldIn.setBlockState(pos.add(-1, 0, -1), state.with(OPEN, Boolean.valueOf(open)), 10);
								break;
							}
						}
						case SOUTH: {
							if (state.get(HINGE) == DoorHingeSide.LEFT) {
								worldIn.setBlockState(pos.add(-1, 0, -1), state.with(OPEN, Boolean.valueOf(open)), 10);
								break;
							} else {
								worldIn.setBlockState(pos.add(1, 0, -1), state.with(OPEN, Boolean.valueOf(open)), 10);
								break;
							}
						}
						case WEST: {
							if (state.get(HINGE) == DoorHingeSide.LEFT) {
								worldIn.setBlockState(pos.add(1, 0, -1), state.with(OPEN, Boolean.valueOf(open)), 10);
								break;
							} else {
								worldIn.setBlockState(pos.add(1, 0, 1), state.with(OPEN, Boolean.valueOf(open)), 10);
								break;
							}
						}
					}
					worldIn.setBlockState(pos, Blocks.AIR.getDefaultState());
				}

			}

		}
	}

	public ArrayList<BlockPos> getAllDoorParts(BlockState state, BlockPos pos, World worldIn) {
		boolean opened = state.get(OPEN);
		Direction direction = state.get(FACING);
		ArrayList<BlockPos> positions = new ArrayList<>();
		if (state.get(HINGE) == DoorHingeSide.LEFT && state.get(SIDE) == SWEMBlockStateProperties.DoubleBlockSide.LEFT) {
			// Check right and above.
			if (state.get(HALF) == DoubleBlockHalf.LOWER) {
				positions.add(pos);
				positions.add(pos.up());
				positions.add(pos.offset(opened ? direction.rotateY() : direction));
				positions.add(pos.offset(opened ? direction.rotateY() : direction).up());
			} else {
				positions.add(pos);
				positions.add(pos.down());
				positions.add(pos.offset(opened ? direction.rotateY() : direction));
				positions.add(pos.offset(opened ? direction.rotateY() : direction).down());
			}
		} else if (state.get(HINGE) == DoorHingeSide.LEFT && state.get(SIDE) == SWEMBlockStateProperties.DoubleBlockSide.RIGHT) {
			// Check Left and above.
			if (state.get(HALF) == DoubleBlockHalf.LOWER) {
				positions.add(pos);
				positions.add(pos.up());
				positions.add(pos.offset(opened ? direction.rotateYCCW() : direction.getOpposite()));
				positions.add(pos.offset(opened ? direction.rotateYCCW() : direction.getOpposite()).up());
			} else {
				positions.add(pos);
				positions.add(pos.down());
				positions.add(pos.offset(opened ? direction.rotateYCCW() : direction.getOpposite()));
				positions.add(pos.offset(opened ? direction.rotateYCCW() : direction.getOpposite()).down());
			}

		} else if (state.get(HINGE) == DoorHingeSide.RIGHT && state.get(SIDE) == SWEMBlockStateProperties.DoubleBlockSide.LEFT) {
			if (state.get(HALF) == DoubleBlockHalf.LOWER) {
				positions.add(pos);
				positions.add(pos.up());
				positions.add(pos.offset(opened ? direction.rotateY() : direction.getOpposite()));
				positions.add(pos.offset(opened ? direction.rotateY() : direction.getOpposite()).up());
			} else {
				positions.add(pos);
				positions.add(pos.down());
				positions.add(pos.offset(opened ? direction.rotateY() : direction.getOpposite()));
				positions.add(pos.offset(opened ? direction.rotateY() : direction.getOpposite()).down());
			}
			// Check Right and above;
		} else if (state.get(HINGE) == DoorHingeSide.RIGHT && state.get(SIDE) == SWEMBlockStateProperties.DoubleBlockSide.RIGHT) {
			// Check Left and above.
			if (state.get(HALF) == DoubleBlockHalf.LOWER) {
				positions.add(pos);
				positions.add(pos.up());
				positions.add(pos.offset(opened ? direction.rotateYCCW() : direction));
				positions.add(pos.offset(opened ? direction.rotateYCCW() : direction).up());
			} else {
				positions.add(pos);
				positions.add(pos.down());
				positions.add(pos.offset(opened ? direction.rotateYCCW() : direction));
				positions.add(pos.offset(opened ? direction.rotateYCCW() : direction).down());
			}
		}

		return positions;
	}

	public void neighborChanged(BlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos, boolean isMoving) {

	}

	public boolean isValidPosition(BlockState state, IWorldReader worldIn, BlockPos pos) {
		return this.getAllDoorParts(state, pos, (World) worldIn).stream().allMatch((pos1) ->  {
			BlockState state1 = worldIn.getBlockState(pos1);
			return state1 == Blocks.AIR.getDefaultState();
		});
	}

	@Override
	public boolean addDestroyEffects(BlockState state, World world, BlockPos pos, ParticleManager manager) {
		return true;
	}

	private void playSound(World worldIn, BlockPos pos, boolean isOpening) {
		worldIn.playEvent((PlayerEntity)null, isOpening ? this.getOpenSound() : this.getCloseSound(), pos, 0);
	}

	/**
	 * @deprecated call via {@link IBlockState#getMobilityFlag()} whenever possible. Implementing/overriding is fine.
	 */
	public PushReaction getPushReaction(BlockState state) {
		return PushReaction.DESTROY;
	}

	/**
	 * Returns the blockstate with the given rotation from the passed blockstate. If inapplicable, returns the passed
	 * blockstate.
	 * @deprecated call via {@link IBlockState#withRotation(Rotation)} whenever possible. Implementing/overriding is
	 * fine.
	 */
	public BlockState rotate(BlockState state, Rotation rot) {
		return state.with(FACING, rot.rotate(state.get(FACING)));
	}

	/**
	 * Returns the blockstate with the given mirror of the passed blockstate. If inapplicable, returns the passed
	 * blockstate.
	 * @deprecated call via {@link IBlockState#withMirror(Mirror)} whenever possible. Implementing/overriding is fine.
	 */
	public BlockState mirror(BlockState state, Mirror mirrorIn) {
		return mirrorIn == Mirror.NONE ? state : state.rotate(mirrorIn.toRotation(state.get(FACING))).func_235896_a_(HINGE);
	}

	/**
	 * Return a random long to be passed to {@link IBakedModel#getQuads}, used for random model rotations
	 */
	@OnlyIn(Dist.CLIENT)
	public long getPositionRandom(BlockState state, BlockPos pos) {
		return MathHelper.getCoordinateRandom(pos.getX(), pos.down(state.get(HALF) == DoubleBlockHalf.LOWER ? 0 : 1).getY(), pos.getZ());
	}

	protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
		builder.add(HALF, FACING, OPEN, HINGE, SIDE);
	}

	public static boolean isWooden(World world, BlockPos pos) {
		return isWooden(world.getBlockState(pos));
	}

	public static boolean isWooden(BlockState state) {
		return state.getBlock() instanceof HorseDoorBlock && (state.getMaterial() == Material.WOOD || state.getMaterial() == Material.NETHER_WOOD);
	}
}
