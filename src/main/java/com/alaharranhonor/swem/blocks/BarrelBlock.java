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

import com.alaharranhonor.swem.util.registry.SWEMBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.material.PushReaction;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.DyeColor;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class BarrelBlock extends Block {
    public static final EnumProperty<HitchingPostBase.PostPart> PART =
            SWEMBlockStateProperties.POST_PART;

    /**
     * Instantiates a new Barrel block.
     *
     * @param properties the properties
     */
    public BarrelBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(
                this.stateDefinition.any().setValue(PART, HitchingPostBase.PostPart.LOWER));
    }

    @Override
    public ActionResultType use(
            BlockState state,
            World worldIn,
            BlockPos pos,
            PlayerEntity player,
            Hand handIn,
            BlockRayTraceResult hit) {
        ItemStack itemstack = player.getItemInHand(handIn);
        if (itemstack.getItem() == Items.SHEARS) {
            itemstack.hurtAndBreak(1, player, (entity) -> entity.broadcastBreakEvent(handIn));

            // Destroy both parts of the barrel.
            worldIn.setBlock(pos, Blocks.AIR.defaultBlockState(), 3);
            if (state.getValue(PART) == HitchingPostBase.PostPart.LOWER) {
                worldIn.setBlock(pos.above(), Blocks.AIR.defaultBlockState(), 3);
            } else if (state.getValue(PART) == HitchingPostBase.PostPart.UPPER) {
                worldIn.setBlock(pos.below(), Blocks.AIR.defaultBlockState(), 3);
            }

            ItemEntity entity =
                    new ItemEntity(
                            worldIn,
                            pos.getX(),
                            pos.getY(),
                            pos.getZ(),
                            new ItemStack(SWEMBlocks.HALF_BARRELS.get(DyeColor.WHITE.getId()).get()));
            ItemEntity entity1 =
                    new ItemEntity(
                            worldIn,
                            pos.getX(),
                            pos.getY(),
                            pos.getZ(),
                            new ItemStack(SWEMBlocks.HALF_BARRELS.get(DyeColor.WHITE.getId()).get()));

            worldIn.addFreshEntity(entity);
            worldIn.addFreshEntity(entity1);
            return ActionResultType.SUCCESS;
        } else {
            return ActionResultType.PASS;
        }
    }

    @Override
    public VoxelShape getShape(
            BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        if (state.getValue(PART) == HitchingPostBase.PostPart.UPPER) {
            return VoxelShapes.box(0.01D, 0.01D, 0.01D, 0.99D, 1.45d, 0.99D).move(0.0D, -1.0D, 0.0D);
        } else {
            return VoxelShapes.box(0.01D, 0.01D, 0.01D, 0.99D, 1.45d, 0.99D);
        }
    }

    @Override
    public void playerWillDestroy(
            World p_176208_1_, BlockPos p_176208_2_, BlockState p_176208_3_, PlayerEntity p_176208_4_) {
        super.playerWillDestroy(p_176208_1_, p_176208_2_, p_176208_3_, p_176208_4_);

        // Destroy the other part of the barrel.
        if (p_176208_3_.getValue(PART) == HitchingPostBase.PostPart.LOWER) {
            p_176208_1_.setBlock(p_176208_2_.above(), Blocks.AIR.defaultBlockState(), 3);
        } else if (p_176208_3_.getValue(PART) == HitchingPostBase.PostPart.UPPER) {
            p_176208_1_.setBlock(p_176208_2_.below(), Blocks.AIR.defaultBlockState(), 3);
        }
    }

    /**
     * Determines if this block should drop loot when exploded.
     */
    @Override
    public boolean canDropFromExplosion(
            BlockState state, IBlockReader world, BlockPos pos, Explosion explosion) {
        return state.getValue(PART) == HitchingPostBase.PostPart.LOWER;
    }

    @Override
    public PushReaction getPistonPushReaction(BlockState pState) {
        return PushReaction.BLOCK;
    }

    @Override
    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(PART);
    }

    @Override
    public void setPlacedBy(
            World worldIn,
            BlockPos pos,
            BlockState state,
            @Nullable LivingEntity placer,
            ItemStack stack) {
        super.setPlacedBy(worldIn, pos, state, placer, stack);
        if (!worldIn.isClientSide) {
            BlockPos blockpos = pos.relative(Direction.UP);
            worldIn.setBlock(blockpos, state.setValue(PART, HitchingPostBase.PostPart.UPPER), 3);
        }
    }

    @Override
    public boolean canSurvive(BlockState pState, IWorldReader pLevel, BlockPos pPos) {
        return pLevel.getBlockState(pPos).isAir() && pLevel.getBlockState(pPos.above()).isAir();
    }
}
