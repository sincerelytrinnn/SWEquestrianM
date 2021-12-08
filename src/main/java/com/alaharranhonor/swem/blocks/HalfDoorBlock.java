package com.alaharranhonor.swem.blocks;


/*
 * All Rights Reserved
 *
 * Copyright (c) 2021, AlaharranHonor, Legenden.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.PushReaction;
import net.minecraft.client.particle.ParticleManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItemUseContext;
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

public class HalfDoorBlock extends Block {
	public static final DirectionProperty FACING = HorizontalBlock.FACING;
	public static final BooleanProperty OPEN = BlockStateProperties.OPEN;
	public static final EnumProperty<DoorHingeSide> HINGE = BlockStateProperties.DOOR_HINGE;
	protected static final VoxelShape SOUTH_AABB = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 3.0D);
	protected static final VoxelShape NORTH_AABB = Block.box(0.0D, 0.0D, 13.0D, 16.0D, 16.0D, 16.0D);
	protected static final VoxelShape WEST_AABB = Block.box(13.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D);
	protected static final VoxelShape EAST_AABB = Block.box(0.0D, 0.0D, 0.0D, 3.0D, 16.0D, 16.0D);


	public HalfDoorBlock(AbstractBlock.Properties builder) {
		super(builder);
		this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(OPEN, Boolean.valueOf(false)).setValue(HINGE, DoorHingeSide.LEFT));
	}

	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
		Direction direction = state.getValue(FACING);
		boolean flag = !state.getValue(OPEN);
		boolean flag1 = state.getValue(HINGE) == DoorHingeSide.RIGHT;
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



	public boolean isPathfindable(BlockState state, IBlockReader worldIn, BlockPos pos, PathType type) {
		switch(type) {
			case LAND:
				return state.getValue(OPEN);
			case WATER:
				return false;
			case AIR:
				return state.getValue(OPEN);
			default:
				return false;
		}
	}

	private int getCloseSound() {
		return this.material == Material.METAL ? 1011 : 1012;
	}

	private int getOpenSound() {
		return this.material == Material.METAL ? 1005 : 1006;
	}

	@Nullable
	public BlockState getStateForPlacement(BlockItemUseContext context) {
		return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection()).setValue(HINGE, this.getHingeSide(context));
	}


	private DoorHingeSide getHingeSide(BlockItemUseContext context) {
		IBlockReader iblockreader = context.getLevel();
		BlockPos blockpos = context.getClickedPos();
		Direction direction = context.getHorizontalDirection();
		Direction direction1 = direction.getCounterClockWise();
		BlockPos blockpos2 = blockpos.relative(direction1);
		BlockState blockstate = iblockreader.getBlockState(blockpos2);
		Direction direction2 = direction.getClockWise();
		BlockPos blockpos4 = blockpos.relative(direction2);
		BlockState blockstate2 = iblockreader.getBlockState(blockpos4);
		int i = (blockstate.isCollisionShapeFullBlock(iblockreader, blockpos2) ? -1 : 0) + (blockstate2.isCollisionShapeFullBlock(iblockreader, blockpos4) ? 1 : 0);
		boolean flag = blockstate.is(this);
		boolean flag1 = blockstate2.is(this);
		if ((!flag || flag1) && i <= 0) {
			if ((!flag1 || flag) && i >= 0) {
				int j = direction.getStepX();
				int k = direction.getStepZ();
				Vector3d vector3d = context.getClickLocation();
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

	public ActionResultType use(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {

		if (!worldIn.isClientSide) {
			worldIn.setBlock(pos, state.setValue(OPEN, !state.getValue(OPEN)), 10);
			worldIn.levelEvent(player, state.getValue(OPEN) ? this.getOpenSound() : this.getCloseSound(), pos, 0);
		}
		return ActionResultType.sidedSuccess(worldIn.isClientSide);
	}

	public boolean isOpen(BlockState state) {
		return state.getValue(OPEN);
	}


	public void openDoor(World worldIn, BlockState state, BlockPos pos, boolean open) {
		if (state.is(this)) {
			// Open normally
			worldIn.setBlock(pos, state.setValue(OPEN, open), 10);
		}
	}

	public void neighborChanged(BlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos, boolean isMoving) {

	}

	public boolean isValidPosition(BlockState state, IWorldReader worldIn, BlockPos pos) {
		return true;
	}

	@Override
	public boolean addDestroyEffects(BlockState state, World world, BlockPos pos, ParticleManager manager) {
		return true;
	}

	private void playSound(World worldIn, BlockPos pos, boolean isOpening) {
		worldIn.levelEvent((PlayerEntity)null, isOpening ? this.getOpenSound() : this.getCloseSound(), pos, 0);
	}


	@Override
	public PushReaction getPistonPushReaction(BlockState pState) {
		return PushReaction.BLOCK;
	}


	public BlockState rotate(BlockState state, Rotation rot) {
		return state.setValue(FACING, rot.rotate(state.getValue(FACING)));
	}


	public BlockState mirror(BlockState state, Mirror mirrorIn) {
		return mirrorIn == Mirror.NONE ? state : state.rotate(mirrorIn.getRotation(state.getValue(FACING))).cycle(HINGE);
	}


	@OnlyIn(Dist.CLIENT)
	public long getPositionRandom(BlockState state, BlockPos pos) {
		return MathHelper.getSeed(pos.getX(), pos.getY(), pos.getZ());
	}

	protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
		builder.add(FACING, OPEN, HINGE);
	}

	public static boolean isWooden(World world, BlockPos pos) {
		return isWooden(world.getBlockState(pos));
	}

	public static boolean isWooden(BlockState state) {
		return state.getBlock() instanceof HorseDoorBlock && (state.getMaterial() == Material.WOOD || state.getMaterial() == Material.NETHER_WOOD);
	}
}
