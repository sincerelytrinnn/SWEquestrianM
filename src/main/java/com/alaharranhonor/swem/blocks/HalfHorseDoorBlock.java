package com.alaharranhonor.swem.blocks;

import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.PushReaction;
import net.minecraft.client.particle.ParticleManager;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.DyeColor;
import net.minecraft.item.ItemStack;
import net.minecraft.pathfinding.PathType;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.state.properties.DoorHingeSide;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.ArrayList;

public class HalfHorseDoorBlock extends Block {
	public static final DirectionProperty FACING = HorizontalBlock.HORIZONTAL_FACING;
	public static final BooleanProperty OPEN = BlockStateProperties.OPEN;
	public static final EnumProperty<DoorHingeSide> HINGE = BlockStateProperties.DOOR_HINGE;
	public static final EnumProperty<SWEMBlockStateProperties.DoubleBlockSide> SIDE = SWEMBlockStateProperties.D_SIDE;
	protected static final VoxelShape SOUTH_AABB = Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 3.0D);
	protected static final VoxelShape NORTH_AABB = Block.makeCuboidShape(0.0D, 0.0D, 13.0D, 16.0D, 16.0D, 16.0D);
	protected static final VoxelShape WEST_AABB = Block.makeCuboidShape(13.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D);
	protected static final VoxelShape EAST_AABB = Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 3.0D, 16.0D, 16.0D);

	private DyeColor colour;

	public HalfHorseDoorBlock(AbstractBlock.Properties builder, DyeColor colour) {
		super(builder);
		this.setDefaultState(this.stateContainer.getBaseState().with(FACING, Direction.NORTH).with(OPEN, Boolean.valueOf(false)).with(HINGE, DoorHingeSide.LEFT));
		this.colour = colour;
	}

	public DyeColor getColour() {
		return this.colour;
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
			BlockPos.getAllInBox(blockpos, blockpos.func_241872_a(direction.getAxis(), 1)).forEach(blockPos1 -> {
				blockChecks.add(context.getWorld().getBlockState(blockPos1).isReplaceable(context));
			});

			if (blockpos.getY() < 254 && blockChecks.stream().allMatch((bool) -> bool)) {
				World world = context.getWorld();

				boolean flag = world.isBlockPowered(blockpos) || world.isBlockPowered(blockpos.up());
				return this.getDefaultState().with(FACING, context.getPlacementHorizontalFacing()).with(HINGE, this.getHingeSide(context)).with(OPEN, Boolean.valueOf(flag)).with(SIDE, SWEMBlockStateProperties.DoubleBlockSide.LEFT);
			} else {
				return null;
			}
		} else {
			// Check right.
			ArrayList<Boolean> blockChecks = new ArrayList<>();
			BlockPos.getAllInBox(blockpos, blockpos.func_241872_a(direction.getAxis(), -1)).forEach(blockPos1 -> {
				blockChecks.add(context.getWorld().getBlockState(blockPos1).isReplaceable(context));
			});

			if (blockpos.getY() < 254 && blockChecks.stream().allMatch((bool) -> bool)) {
				World world = context.getWorld();

				boolean flag = world.isBlockPowered(blockpos) || world.isBlockPowered(blockpos.up());
				return this.getDefaultState().with(FACING, context.getPlacementHorizontalFacing()).with(HINGE, this.getHingeSide(context)).with(OPEN, Boolean.valueOf(flag)).with(SIDE, SWEMBlockStateProperties.DoubleBlockSide.RIGHT);
			} else {
				return null;
			}
		}
	}

	@Override
	public void onBlockHarvested(World worldIn, BlockPos pos, BlockState state, PlayerEntity player) {
		if (!worldIn.isRemote) {

			this.getAllDoorParts(state, pos, worldIn, !state.get(OPEN)).stream().forEach((blockPos) -> {
				worldIn.setBlockState(blockPos, Blocks.AIR.getDefaultState(), 35);
			});
		}

		super.onBlockHarvested(worldIn, pos, state, player);
	}

	/**
	 * Called by ItemBlocks after a block is set in the world, to allow post-place logic
	 */
	public void onBlockPlacedBy(World worldIn, BlockPos pos, BlockState state, LivingEntity placer, ItemStack stack) {

		// Z-Axis is Norht/South
		// X-Axis is East/West
		if (state.get(SIDE) == SWEMBlockStateProperties.DoubleBlockSide.LEFT) {
			if (placer.getHorizontalFacing().getAxis() == Direction.Axis.Z) {
				switch (placer.getHorizontalFacing()) {
					case NORTH: {
						worldIn.setBlockState(pos.east(), state.with(SIDE, SWEMBlockStateProperties.DoubleBlockSide.RIGHT), 3);
						break;
					}
					case SOUTH: {
						worldIn.setBlockState(pos.west(), state.with(SIDE, SWEMBlockStateProperties.DoubleBlockSide.RIGHT), 3);
						break;
					}
				}
			} else if (placer.getHorizontalFacing().getAxis() == Direction.Axis.X) {
				switch (placer.getHorizontalFacing()) {
					case EAST: {
						worldIn.setBlockState(pos.south(), state.with(SIDE, SWEMBlockStateProperties.DoubleBlockSide.RIGHT), 3);
						break;
					}
					case WEST: {
						worldIn.setBlockState(pos.north(), state.with(SIDE, SWEMBlockStateProperties.DoubleBlockSide.RIGHT), 3);
						break;
					}
				}
			}
		} else {
			if (placer.getHorizontalFacing().getAxis() == Direction.Axis.Z) {
				switch (placer.getHorizontalFacing()) {
					case NORTH: {
						worldIn.setBlockState(pos.west(), state.with(SIDE, SWEMBlockStateProperties.DoubleBlockSide.LEFT), 3);
						break;
					}
					case SOUTH: {
						worldIn.setBlockState(pos.east(), state.with(SIDE, SWEMBlockStateProperties.DoubleBlockSide.LEFT), 3);
						break;
					}
				}
			} else if (placer.getHorizontalFacing().getAxis() == Direction.Axis.X) {
				switch (placer.getHorizontalFacing()) {
					case EAST: {
						worldIn.setBlockState(pos.north(), state.with(SIDE, SWEMBlockStateProperties.DoubleBlockSide.LEFT), 3);
						break;
					}
					case WEST: {
						worldIn.setBlockState(pos.south(), state.with(SIDE, SWEMBlockStateProperties.DoubleBlockSide.LEFT), 3);
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
		Direction direction1 = direction.rotateYCCW();
		BlockPos blockpos2 = blockpos.offset(direction1);
		BlockState blockstate = iblockreader.getBlockState(blockpos2);
		Direction direction2 = direction.rotateY();
		BlockPos blockpos4 = blockpos.offset(direction2);
		BlockState blockstate2 = iblockreader.getBlockState(blockpos4);
		int i = (blockstate.hasOpaqueCollisionShape(iblockreader, blockpos2) ? -1 : 0) + (blockstate2.hasOpaqueCollisionShape(iblockreader, blockpos4) ? 1 : 0);
		boolean flag = blockstate.matchesBlock(this);
		boolean flag1 = blockstate2.matchesBlock(this);
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


		if (!worldIn.isRemote) {
			ArrayList<BlockPos> openPositions = this.getAllDoorParts(state, this.getInvertedOpenPos(state, pos), worldIn, state.get(OPEN));
			switch (state.get(SIDE)) {
				case RIGHT: {
					if (state.get(HINGE) == DoorHingeSide.LEFT) {
						openPositions.remove(1);
					} else {
						openPositions.remove(0);
					}
					break;
				}
				default: {
					if (state.get(HINGE) == DoorHingeSide.LEFT) {
						openPositions.remove(0);
					} else {
						openPositions.remove(1);
					}
					break;
				}
			}
			boolean shouldOpen = openPositions.stream().allMatch((pos1) -> worldIn.getBlockState(pos1) == Blocks.AIR.getDefaultState());
			if (shouldOpen) {
				state = state.cycleValue(OPEN);
				boolean open = state.get(OPEN);
				ArrayList<BlockState> states = new ArrayList<>();
				ArrayList<BlockPos> positions = this.getAllDoorParts(state, pos, worldIn, open);
				positions.forEach((pos1) -> states.add(worldIn.getBlockState(pos1)));
				for (int i = 0; i < 2; i++) {
					this.openDoor(worldIn, states.get(i), positions.get(i), open);
				}
				worldIn.playEvent(player, state.get(OPEN) ? this.getOpenSound() : this.getCloseSound(), pos, 0);
			} else {
				return ActionResultType.FAIL;
			}
		}
		return ActionResultType.func_233537_a_(worldIn.isRemote);
	}

	public boolean isOpen(BlockState state) {
		return state.get(OPEN);
	}

	public BlockPos getInvertedOpenPos(BlockState state, BlockPos pos) {
		if (state.get(SIDE).toString().equals(state.get(HINGE).toString())) return pos;
		boolean open = !state.get(OPEN);
		if (open) {
			switch (state.get(FACING)) {
				case NORTH: {

					if (state.get(HINGE) == DoorHingeSide.LEFT) {
						return pos.add(-1, 0, -1);

					} else {
						return pos.add(1, 0, -1);

					}
				}
				case EAST: {
					if (state.get(HINGE) == DoorHingeSide.LEFT) {
						return pos.add(1, 0, -1);

					} else {
						return pos.add(1, 0, 1);

					}
				}
				case SOUTH: {
					if (state.get(HINGE) == DoorHingeSide.LEFT) {
						return pos.add(1, 0, 1);

					} else {
						return pos.add(-1, 0, 1);

					}
				}
				case WEST: {
					if (state.get(HINGE) == DoorHingeSide.LEFT) {
						return pos.add(-1, 0, 1);

					} else {
						return pos.add(-1, 0, -1);

					}
				}
			}
			return pos;
		} else {
			switch (state.get(FACING)) {
				case NORTH: {
					if (state.get(HINGE) == DoorHingeSide.LEFT) {
						return pos.add(1, 0, 1);

					} else {
						return pos.add(-1, 0, 1);

					}
				}
				case EAST: {
					if (state.get(HINGE) == DoorHingeSide.LEFT) {
						return pos.add(-1, 0, 1);

					} else {
						return pos.add(-1, 0, -1);

					}
				}
				case SOUTH: {
					if (state.get(HINGE) == DoorHingeSide.LEFT) {
						return pos.add(-1, 0, -1);

					} else {
						return pos.add(1, 0, -1);

					}
				}
				case WEST: {
					if (state.get(HINGE) == DoorHingeSide.LEFT) {
						return pos.add(1, 0, -1);

					} else {
						return pos.add(1, 0, 1);

					}
				}
			}
			return pos;
		}
	};

	public void openDoor(World worldIn, BlockState state, BlockPos pos, boolean open) {
		if (state.matchesBlock(this)) {
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

	public ArrayList<BlockPos> getAllDoorParts(BlockState state, BlockPos pos, World worldIn, boolean opened) {
		Direction direction = state.get(FACING);
		ArrayList<BlockPos> positions = new ArrayList<>();
		if (state.get(HINGE) == DoorHingeSide.LEFT && state.get(SIDE) == SWEMBlockStateProperties.DoubleBlockSide.LEFT) {
			// Check right and above.

			positions.add(pos);
			positions.add(pos.offset(opened ? direction.rotateY() : direction));

		} else if (state.get(HINGE) == DoorHingeSide.LEFT && state.get(SIDE) == SWEMBlockStateProperties.DoubleBlockSide.RIGHT) {
			// Check Left and above.

			positions.add(pos);
			positions.add(pos.offset(opened ? direction.rotateYCCW() : direction.getOpposite()));


		} else if (state.get(HINGE) == DoorHingeSide.RIGHT && state.get(SIDE) == SWEMBlockStateProperties.DoubleBlockSide.LEFT) {

			positions.add(pos);
			positions.add(pos.offset(opened ? direction.rotateY() : direction.getOpposite()));

			// Check Right and above;
		} else if (state.get(HINGE) == DoorHingeSide.RIGHT && state.get(SIDE) == SWEMBlockStateProperties.DoubleBlockSide.RIGHT) {
			// Check Left and above.

			positions.add(pos);
			positions.add(pos.offset(opened ? direction.rotateYCCW() : direction));

		}

		return positions;
	}

	public void neighborChanged(BlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos, boolean isMoving) {

	}

	public boolean isValidPosition(BlockState state, IWorldReader worldIn, BlockPos pos) {
		return this.getAllDoorParts(state, pos, (World) worldIn, true).stream().allMatch((pos1) ->  {
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


	public PushReaction getPushReaction(BlockState state) {
		return PushReaction.DESTROY;
	}


	public BlockState rotate(BlockState state, Rotation rot) {
		return state.with(FACING, rot.rotate(state.get(FACING)));
	}


	public BlockState mirror(BlockState state, Mirror mirrorIn) {
		return mirrorIn == Mirror.NONE ? state : state.rotate(mirrorIn.toRotation(state.get(FACING))).cycleValue(HINGE);
	}


	@OnlyIn(Dist.CLIENT)
	public long getPositionRandom(BlockState state, BlockPos pos) {
		return MathHelper.getCoordinateRandom(pos.getX(), pos.getY(), pos.getZ());
	}

	protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
		builder.add(FACING, OPEN, HINGE, SIDE);
	}

	public static boolean isWooden(World world, BlockPos pos) {
		return isWooden(world.getBlockState(pos));
	}

	public static boolean isWooden(BlockState state) {
		return state.getBlock() instanceof HorseDoorBlock && (state.getMaterial() == Material.WOOD || state.getMaterial() == Material.NETHER_WOOD);
	}
}
