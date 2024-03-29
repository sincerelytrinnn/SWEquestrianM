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
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.ArrayList;

public class HorseDoorHalfBlock extends Block {
    public static final DirectionProperty FACING = HorizontalBlock.FACING;
    public static final BooleanProperty OPEN = BlockStateProperties.OPEN;
    public static final EnumProperty<DoorHingeSide> HINGE = BlockStateProperties.DOOR_HINGE;
    public static final EnumProperty<SWEMBlockStateProperties.DoubleBlockSide> SIDE = SWEMBlockStateProperties.D_SIDE;
    protected static final VoxelShape SOUTH_AABB = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 3.0D);
    protected static final VoxelShape NORTH_AABB = Block.box(0.0D, 0.0D, 13.0D, 16.0D, 16.0D, 16.0D);
    protected static final VoxelShape WEST_AABB = Block.box(13.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D);
    protected static final VoxelShape EAST_AABB = Block.box(0.0D, 0.0D, 0.0D, 3.0D, 16.0D, 16.0D);

    private final DyeColor colour;

    /**
     * Instantiates a new Horse door half block.
     *
     * @param builder the builder
     * @param colour  the colour
     */
    public HorseDoorHalfBlock(AbstractBlock.Properties builder, DyeColor colour) {
        super(builder);
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(OPEN, Boolean.valueOf(false)).setValue(HINGE, DoorHingeSide.LEFT));
        this.colour = colour;
    }

    /**
     * Is wooden door boolean.
     *
     * @param world the world
     * @param pos   the pos
     * @return the boolean
     */
    public static boolean isWoodenDoor(World world, BlockPos pos) {
        return isWooden(world.getBlockState(pos));
    }

    /**
     * Is wooden boolean.
     *
     * @param state the state
     * @return the boolean
     */
    public static boolean isWooden(BlockState state) {
        return state.getBlock() instanceof HorseDoorBlock && (state.getMaterial() == Material.WOOD || state.getMaterial() == Material.NETHER_WOOD);
    }

    /**
     * Gets colour.
     *
     * @return the colour
     */
    public DyeColor getColour() {
        return this.colour;
    }

    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        Direction direction = state.getValue(FACING);
        boolean flag = !state.getValue(OPEN);
        boolean flag1 = state.getValue(HINGE) == DoorHingeSide.RIGHT;
        switch (direction) {
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
        switch (type) {
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

    /**
     * Gets close sound.
     *
     * @return the close sound
     */
    private int getCloseSound() {
        return this.material == Material.METAL ? 1011 : 1012;
    }

    /**
     * Gets open sound.
     *
     * @return the open sound
     */
    private int getOpenSound() {
        return this.material == Material.METAL ? 1005 : 1006;
    }

    @Nullable
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        BlockPos blockpos = context.getClickedPos();
        Direction direction = context.getHorizontalDirection();
        DoorHingeSide hinge = this.getHingeSide(context);

        if (hinge == DoorHingeSide.LEFT) {
            // Check right
            return checkAndGetRightSide(blockpos, direction, context, hinge, false);
        } else {
            // Check left.
            return checkAndGetLeftSide(blockpos, direction, context, hinge, false);
        }
    }

    /**
     * Check and get right side block state.
     *
     * @param blockpos   the blockpos
     * @param direction  the direction
     * @param context    the context
     * @param hinge      the hinge
     * @param secondTime the second time
     * @return the block state
     */
    public BlockState checkAndGetRightSide(BlockPos blockpos, Direction direction, BlockItemUseContext context, DoorHingeSide hinge, boolean secondTime) {

        ArrayList<Boolean> blockChecks = new ArrayList<>();
        BlockPos.betweenClosed(blockpos, blockpos.relative(direction.getCounterClockWise().getAxis(), direction == Direction.EAST ? 1 : direction == Direction.NORTH ? 1 : -1)).forEach(blockPos1 -> {
            blockChecks.add(context.getLevel().getBlockState(blockPos1).canBeReplaced(context));
        });

        if (blockpos.getY() < 254 && blockChecks.stream().allMatch((bool) -> bool)) {
            World world = context.getLevel();

            boolean flag = world.hasNeighborSignal(blockpos) || world.hasNeighborSignal(blockpos.above());
            return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection()).setValue(HINGE, hinge).setValue(OPEN, Boolean.valueOf(flag)).setValue(SIDE, SWEMBlockStateProperties.DoubleBlockSide.LEFT);
        } else {
            if (!secondTime) {
                return checkAndGetLeftSide(blockpos, direction, context, hinge == DoorHingeSide.LEFT ? DoorHingeSide.RIGHT : hinge, true);
            }

            return null;
        }
    }

    /**
     * Check and get left side block state.
     *
     * @param blockpos   the blockpos
     * @param direction  the direction
     * @param context    the context
     * @param hinge      the hinge
     * @param secondTime the second time
     * @return the block state
     */
    public BlockState checkAndGetLeftSide(BlockPos blockpos, Direction direction, BlockItemUseContext context, DoorHingeSide hinge, boolean secondTime) {
        ArrayList<Boolean> blockChecks = new ArrayList<>();
        BlockPos.betweenClosed(blockpos, blockpos.relative(direction.getCounterClockWise().getAxis(), direction == Direction.EAST ? -1 : direction == Direction.NORTH ? -1 : 1)).forEach(blockPos1 -> {
            blockChecks.add(context.getLevel().getBlockState(blockPos1).canBeReplaced(context));
        });

        if (blockpos.getY() < 254 && blockChecks.stream().allMatch((bool) -> bool)) {
            World world = context.getLevel();

            boolean flag = world.hasNeighborSignal(blockpos) || world.hasNeighborSignal(blockpos.above());
            return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection()).setValue(HINGE, hinge).setValue(OPEN, Boolean.valueOf(flag)).setValue(SIDE, SWEMBlockStateProperties.DoubleBlockSide.RIGHT);
        } else {
            if (!secondTime) {
                return checkAndGetRightSide(blockpos, direction, context, hinge == DoorHingeSide.RIGHT ? DoorHingeSide.LEFT : hinge, true);
            }
            return null;
        }
    }

    /**
     * Determines if this block should drop loot when exploded.
     *
     * @param state
     * @param world
     * @param pos
     * @param explosion
     */
    @Override
    public boolean canDropFromExplosion(BlockState state, IBlockReader world, BlockPos pos, Explosion explosion) {
        return state.getValue(SIDE) == SWEMBlockStateProperties.DoubleBlockSide.LEFT;
    }

    @Override
    public void playerWillDestroy(World worldIn, BlockPos pos, BlockState state, PlayerEntity player) {
        if (!worldIn.isClientSide) {

            this.getAllDoorParts(state, pos, worldIn, !state.getValue(OPEN)).stream().forEach((blockPos) -> {
                worldIn.setBlock(blockPos, Blocks.AIR.defaultBlockState(), 35, 3);
            });
        }

        if (!player.abilities.instabuild) dropResources(state, worldIn, pos);

        super.playerWillDestroy(worldIn, pos, state, player);
    }

    /**
     * Called by ItemBlocks after a block is set in the world, to allow post-place logic
     */
    public void setPlacedBy(World worldIn, BlockPos pos, BlockState state, LivingEntity placer, ItemStack stack) {

        // Z-Axis is Norht/South
        // X-Axis is East/West
        if (state.getValue(SIDE) == SWEMBlockStateProperties.DoubleBlockSide.LEFT) {
            if (placer.getDirection().getAxis() == Direction.Axis.Z) {
                switch (placer.getDirection()) {
                    case NORTH: {
                        worldIn.setBlock(pos.east(), state.setValue(SIDE, SWEMBlockStateProperties.DoubleBlockSide.RIGHT), 3, 3);
                        break;
                    }
                    case SOUTH: {
                        worldIn.setBlock(pos.west(), state.setValue(SIDE, SWEMBlockStateProperties.DoubleBlockSide.RIGHT), 3, 3);
                        break;
                    }
                }
            } else if (placer.getDirection().getAxis() == Direction.Axis.X) {
                switch (placer.getDirection()) {
                    case EAST: {
                        worldIn.setBlock(pos.south(), state.setValue(SIDE, SWEMBlockStateProperties.DoubleBlockSide.RIGHT), 3, 3);
                        break;
                    }
                    case WEST: {
                        worldIn.setBlock(pos.north(), state.setValue(SIDE, SWEMBlockStateProperties.DoubleBlockSide.RIGHT), 3, 3);
                        break;
                    }
                }
            }
        } else {
            if (placer.getDirection().getAxis() == Direction.Axis.Z) {
                switch (placer.getDirection()) {
                    case NORTH: {
                        worldIn.setBlock(pos.west(), state.setValue(SIDE, SWEMBlockStateProperties.DoubleBlockSide.LEFT), 3, 3);
                        break;
                    }
                    case SOUTH: {
                        worldIn.setBlock(pos.east(), state.setValue(SIDE, SWEMBlockStateProperties.DoubleBlockSide.LEFT), 3, 3);
                        break;
                    }
                }
            } else if (placer.getDirection().getAxis() == Direction.Axis.X) {
                switch (placer.getDirection()) {
                    case EAST: {
                        worldIn.setBlock(pos.north(), state.setValue(SIDE, SWEMBlockStateProperties.DoubleBlockSide.LEFT), 3, 3);
                        break;
                    }
                    case WEST: {
                        worldIn.setBlock(pos.south(), state.setValue(SIDE, SWEMBlockStateProperties.DoubleBlockSide.LEFT), 3, 3);
                        break;
                    }
                }
            }
        }
    }

    /**
     * Gets hinge side.
     *
     * @param context the context
     * @return the hinge side
     */
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
                double d0 = vector3d.x - (double) blockpos.getX();
                double d1 = vector3d.z - (double) blockpos.getZ();
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
            ArrayList<BlockPos> openPositions = this.getAllDoorParts(state, this.getInvertedOpenPos(state, pos), worldIn, state.getValue(OPEN));
            switch (state.getValue(SIDE)) {
                case RIGHT: {
                    if (state.getValue(HINGE) == DoorHingeSide.LEFT) {
                        openPositions.remove(1);
                    } else {
                        openPositions.remove(0);
                    }
                    break;
                }
                default: {
                    if (state.getValue(HINGE) == DoorHingeSide.LEFT) {
                        openPositions.remove(0);
                    } else {
                        openPositions.remove(1);
                    }
                    break;
                }
            }
            boolean shouldOpen = openPositions.stream().allMatch((pos1) -> worldIn.getBlockState(pos1).getMaterial().isReplaceable());
            if (shouldOpen) {
                state = state.cycle(OPEN);
                boolean open = state.getValue(OPEN);
                ArrayList<BlockState> states = new ArrayList<>();
                ArrayList<BlockPos> positions = this.getAllDoorParts(state, pos, worldIn, open);
                positions.forEach((pos1) -> states.add(worldIn.getBlockState(pos1)));
                for (int i = 0; i < 2; i++) {
                    this.openDoor(worldIn, states.get(i), positions.get(i), open);
                }
                worldIn.levelEvent(player, state.getValue(OPEN) ? this.getOpenSound() : this.getCloseSound(), pos, 0);
            } else {
                return ActionResultType.FAIL;
            }
        }
        return ActionResultType.sidedSuccess(worldIn.isClientSide);
    }

    /**
     * Is open boolean.
     *
     * @param state the state
     * @return the boolean
     */
    public boolean isOpen(BlockState state) {
        return state.getValue(OPEN);
    }

    /**
     * Gets inverted open pos.
     *
     * @param state the state
     * @param pos   the pos
     * @return the inverted open pos
     */
    public BlockPos getInvertedOpenPos(BlockState state, BlockPos pos) {
        if (state.getValue(SIDE).toString().equals(state.getValue(HINGE).toString())) return pos;
        boolean open = !state.getValue(OPEN);
        if (open) {
            switch (state.getValue(FACING)) {
                case NORTH: {
                    if (state.getValue(HINGE) == DoorHingeSide.LEFT) {
                        return pos.offset(-1, 0, -1);

                    } else {
                        return pos.offset(1, 0, -1);
                    }
                }
                case EAST: {
                    if (state.getValue(HINGE) == DoorHingeSide.LEFT) {
                        return pos.offset(1, 0, -1);

                    } else {
                        return pos.offset(1, 0, 1);
                    }
                }
                case SOUTH: {
                    if (state.getValue(HINGE) == DoorHingeSide.LEFT) {
                        return pos.offset(1, 0, 1);

                    } else {
                        return pos.offset(-1, 0, 1);
                    }
                }
                case WEST: {
                    if (state.getValue(HINGE) == DoorHingeSide.LEFT) {
                        return pos.offset(-1, 0, 1);

                    } else {
                        return pos.offset(-1, 0, -1);
                    }
                }
            }
            return pos;
        } else {
            switch (state.getValue(FACING)) {
                case NORTH: {
                    if (state.getValue(HINGE) == DoorHingeSide.LEFT) {
                        return pos.offset(1, 0, 1);

                    } else {
                        return pos.offset(-1, 0, 1);
                    }
                }
                case EAST: {
                    if (state.getValue(HINGE) == DoorHingeSide.LEFT) {
                        return pos.offset(-1, 0, 1);

                    } else {
                        return pos.offset(-1, 0, -1);
                    }
                }
                case SOUTH: {
                    if (state.getValue(HINGE) == DoorHingeSide.LEFT) {
                        return pos.offset(-1, 0, -1);

                    } else {
                        return pos.offset(1, 0, -1);
                    }
                }
                case WEST: {
                    if (state.getValue(HINGE) == DoorHingeSide.LEFT) {
                        return pos.offset(1, 0, -1);

                    } else {
                        return pos.offset(1, 0, 1);
                    }
                }
            }
            return pos;
        }
    }

    /**
     * Open door.
     *
     * @param worldIn the world in
     * @param state   the state
     * @param pos     the pos
     * @param open    the open
     */
    public void openDoor(World worldIn, BlockState state, BlockPos pos, boolean open) {
        if (state.is(this)) {
            if (state.getValue(HINGE).toString().equals(state.getValue(SIDE).toString())) {
                // Open normally
                worldIn.setBlock(pos, state.setValue(OPEN, open), 10, 3);
                this.playSound(worldIn, pos, open);
            } else {
                // Else offset the block.
                if (open) {
                    switch (state.getValue(FACING)) {
                        case NORTH: {
                            if (state.getValue(HINGE) == DoorHingeSide.LEFT) {
                                worldIn.setBlock(pos.offset(-1, 0, -1), state.setValue(OPEN, Boolean.valueOf(open)), 3);
                                break;
                            } else {
                                worldIn.setBlock(pos.offset(1, 0, -1), state.setValue(OPEN, Boolean.valueOf(open)), 3);
                                break;
                            }
                        }
                        case EAST: {
                            if (state.getValue(HINGE) == DoorHingeSide.LEFT) {
                                worldIn.setBlock(pos.offset(1, 0, -1), state.setValue(OPEN, Boolean.valueOf(open)), 10, 3);
                                break;
                            } else {
                                worldIn.setBlock(pos.offset(1, 0, 1), state.setValue(OPEN, Boolean.valueOf(open)), 10, 3);
                                break;
                            }
                        }
                        case SOUTH: {
                            if (state.getValue(HINGE) == DoorHingeSide.LEFT) {
                                worldIn.setBlock(pos.offset(1, 0, 1), state.setValue(OPEN, Boolean.valueOf(open)), 10, 3);
                                break;
                            } else {
                                worldIn.setBlock(pos.offset(-1, 0, 1), state.setValue(OPEN, Boolean.valueOf(open)), 10, 3);
                                break;
                            }
                        }
                        case WEST: {
                            if (state.getValue(HINGE) == DoorHingeSide.LEFT) {
                                worldIn.setBlock(pos.offset(-1, 0, 1), state.setValue(OPEN, Boolean.valueOf(open)), 10, 3);
                                break;
                            } else {
                                worldIn.setBlock(pos.offset(-1, 0, -1), state.setValue(OPEN, Boolean.valueOf(open)), 10, 3);
                                break;
                            }
                        }
                    }
                    worldIn.setBlock(pos, Blocks.AIR.defaultBlockState(), 3);
                } else {
                    switch (state.getValue(FACING)) {
                        case NORTH: {
                            if (state.getValue(HINGE) == DoorHingeSide.LEFT) {
                                worldIn.setBlock(pos.offset(1, 0, 1), state.setValue(OPEN, Boolean.valueOf(open)), 3);
                                break;
                            } else {
                                worldIn.setBlock(pos.offset(-1, 0, 1), state.setValue(OPEN, Boolean.valueOf(open)), 3);
                                break;
                            }
                        }
                        case EAST: {
                            if (state.getValue(HINGE) == DoorHingeSide.LEFT) {
                                worldIn.setBlock(pos.offset(-1, 0, 1), state.setValue(OPEN, Boolean.valueOf(open)), 10, 3);
                                break;
                            } else {
                                worldIn.setBlock(pos.offset(-1, 0, -1), state.setValue(OPEN, Boolean.valueOf(open)), 10, 3);
                                break;
                            }
                        }
                        case SOUTH: {
                            if (state.getValue(HINGE) == DoorHingeSide.LEFT) {
                                worldIn.setBlock(pos.offset(-1, 0, -1), state.setValue(OPEN, Boolean.valueOf(open)), 10, 3);
                                break;
                            } else {
                                worldIn.setBlock(pos.offset(1, 0, -1), state.setValue(OPEN, Boolean.valueOf(open)), 10, 3);
                                break;
                            }
                        }
                        case WEST: {
                            if (state.getValue(HINGE) == DoorHingeSide.LEFT) {
                                worldIn.setBlock(pos.offset(1, 0, -1), state.setValue(OPEN, Boolean.valueOf(open)), 10, 3);
                                break;
                            } else {
                                worldIn.setBlock(pos.offset(1, 0, 1), state.setValue(OPEN, Boolean.valueOf(open)), 10, 3);
                                break;
                            }
                        }
                    }
                    worldIn.setBlock(pos, Blocks.AIR.defaultBlockState(), 3);
                }
            }
        }
    }

    /**
     * Gets all door parts.
     *
     * @param state   the state
     * @param pos     the pos
     * @param worldIn the world in
     * @param opened  the opened
     * @return the all door parts
     */
    public ArrayList<BlockPos> getAllDoorParts(BlockState state, BlockPos pos, World worldIn, boolean opened) {
        Direction direction = state.getValue(FACING);
        ArrayList<BlockPos> positions = new ArrayList<>();
        if (state.getValue(HINGE) == DoorHingeSide.LEFT && state.getValue(SIDE) == SWEMBlockStateProperties.DoubleBlockSide.LEFT) {
            // Check right and above.

            positions.add(pos);
            positions.add(pos.relative(opened ? direction.getClockWise() : direction));

        } else if (state.getValue(HINGE) == DoorHingeSide.LEFT && state.getValue(SIDE) == SWEMBlockStateProperties.DoubleBlockSide.RIGHT) {
            // Check Left and above.

            positions.add(pos);
            positions.add(pos.relative(opened ? direction.getCounterClockWise() : direction.getOpposite()));

        } else if (state.getValue(HINGE) == DoorHingeSide.RIGHT && state.getValue(SIDE) == SWEMBlockStateProperties.DoubleBlockSide.LEFT) {

            positions.add(pos);
            positions.add(pos.relative(opened ? direction.getClockWise() : direction.getOpposite()));

            // Check Right and above;
        } else if (state.getValue(HINGE) == DoorHingeSide.RIGHT && state.getValue(SIDE) == SWEMBlockStateProperties.DoubleBlockSide.RIGHT) {
            // Check Left and above.

            positions.add(pos);
            positions.add(pos.relative(opened ? direction.getCounterClockWise() : direction));
        }

        return positions;
    }

    public void neighborChanged(BlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos, boolean isMoving) {
    }

    public boolean canSurvive(BlockState state, IWorldReader worldIn, BlockPos pos) {
        return this.getAllDoorParts(state, pos, (World) worldIn, true).stream().allMatch((pos1) -> {
            BlockState state1 = worldIn.getBlockState(pos1);
            return state1 == Blocks.AIR.defaultBlockState();
        });
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public boolean addDestroyEffects(BlockState state, World world, BlockPos pos, ParticleManager manager) {
        return true;
    }

    /**
     * Play sound.
     *
     * @param worldIn   the world in
     * @param pos       the pos
     * @param isOpening the is opening
     */
    private void playSound(World worldIn, BlockPos pos, boolean isOpening) {
        worldIn.levelEvent(null, isOpening ? this.getOpenSound() : this.getCloseSound(), pos, 0);
    }

    public PushReaction getPistonPushReaction(BlockState state) {
        return PushReaction.BLOCK;
    }

    public BlockState rotate(BlockState state, Rotation rot) {
        return state.setValue(FACING, rot.rotate(state.getValue(FACING)));
    }

    public BlockState mirror(BlockState state, Mirror mirrorIn) {
        return mirrorIn == Mirror.NONE ? state : state.rotate(mirrorIn.getRotation(state.getValue(FACING))).cycle(HINGE);
    }

    @OnlyIn(Dist.CLIENT)
    public long getSeed(BlockState state, BlockPos pos) {
        return MathHelper.getSeed(pos.getX(), pos.getY(), pos.getZ());
    }

    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(FACING, OPEN, HINGE, SIDE);
    }
}
