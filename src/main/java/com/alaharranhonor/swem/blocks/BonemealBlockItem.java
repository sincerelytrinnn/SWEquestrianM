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
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biomes;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.Objects;
import java.util.Optional;

public class BonemealBlockItem extends BlockItemBase {
    /**
     * Instantiates a new Bonemeal block item.
     *
     * @param block the block
     */
    public BonemealBlockItem(Block block) {
        super(block);
    }

    /**
     * Apply bonemeal boolean.
     *
     * @param stack   the stack
     * @param worldIn the world in
     * @param pos     the pos
     * @return the boolean
     */
    @Deprecated // Forge: Use Player/Hand version
    public static boolean applyBonemeal(ItemStack stack, World worldIn, BlockPos pos) {
        if (worldIn instanceof net.minecraft.world.server.ServerWorld)
            return applyBonemeal(
                    stack,
                    worldIn,
                    pos,
                    net.minecraftforge.common.util.FakePlayerFactory.getMinecraft(
                            (net.minecraft.world.server.ServerWorld) worldIn));
        return false;
    }

    /**
     * Apply bonemeal boolean.
     *
     * @param stack   the stack
     * @param worldIn the world in
     * @param pos     the pos
     * @param player  the player
     * @return the boolean
     */
    public static boolean applyBonemeal(
            ItemStack stack,
            World worldIn,
            BlockPos pos,
            net.minecraft.entity.player.PlayerEntity player) {
        BlockState blockstate = worldIn.getBlockState(pos);
        int hook =
                net.minecraftforge.event.ForgeEventFactory.onApplyBonemeal(
                        player, worldIn, pos, blockstate, stack);
        if (hook != 0) return hook > 0;
        if (blockstate.getBlock() instanceof IGrowable) {
            IGrowable igrowable = (IGrowable) blockstate.getBlock();
            if (igrowable.isValidBonemealTarget(worldIn, pos, blockstate, worldIn.isClientSide)) {
                if (worldIn instanceof ServerWorld) {
                    if (igrowable.isBonemealSuccess(worldIn, worldIn.random, pos, blockstate)) {
                        igrowable.performBonemeal((ServerWorld) worldIn, worldIn.random, pos, blockstate);
                    }

                    stack.shrink(1);
                }

                return true;
            }
        }

        return false;
    }

    /**
     * Grow seagrass boolean.
     *
     * @param stack   the stack
     * @param worldIn the world in
     * @param pos     the pos
     * @param side    the side
     * @return the boolean
     */
    public static boolean growSeagrass(
            ItemStack stack, World worldIn, BlockPos pos, @Nullable Direction side) {
        if (worldIn.getBlockState(pos).is(Blocks.WATER)
                && worldIn.getFluidState(pos).getAmount() == 8) {
            if (!(worldIn instanceof ServerWorld)) {
                return true;
            } else {
                label80:
                for (int i = 0; i < 128; ++i) {
                    BlockPos blockpos = pos;
                    BlockState blockstate = Blocks.SEAGRASS.defaultBlockState();

                    for (int j = 0; j < i / 16; ++j) {
                        blockpos =
                                blockpos.offset(
                                        random.nextInt(3) - 1,
                                        (random.nextInt(3) - 1) * random.nextInt(3) / 2,
                                        random.nextInt(3) - 1);
                        if (worldIn.getBlockState(blockpos).hasPostProcess(worldIn, blockpos)) {
                            continue label80;
                        }
                    }

                    Optional<RegistryKey<Biome>> optional = worldIn.getBiomeName(blockpos);
                    if (Objects.equals(optional, Optional.of(Biomes.WARM_OCEAN))
                            || Objects.equals(optional, Optional.of(Biomes.DEEP_WARM_OCEAN))) {
                        if (i == 0 && side != null && side.getAxis().isHorizontal()) {
                            blockstate =
                                    BlockTags.WALL_CORALS
                                            .getRandomElement(worldIn.random)
                                            .defaultBlockState()
                                            .setValue(DeadCoralWallFanBlock.FACING, side);
                        } else if (random.nextInt(4) == 0) {
                            blockstate =
                                    BlockTags.UNDERWATER_BONEMEALS.getRandomElement(random).defaultBlockState();
                        }
                    }

                    if (blockstate.getBlock().is(BlockTags.WALL_CORALS)) {
                        for (int k = 0; !blockstate.canSurvive(worldIn, blockpos) && k < 4; ++k) {
                            blockstate =
                                    blockstate.setValue(
                                            DeadCoralWallFanBlock.FACING,
                                            Direction.Plane.HORIZONTAL.getRandomDirection(random));
                        }
                    }

                    if (blockstate.canSurvive(worldIn, blockpos)) {
                        BlockState blockstate1 = worldIn.getBlockState(blockpos);
                        if (blockstate1.is(Blocks.WATER) && worldIn.getFluidState(blockpos).getAmount() == 8) {
                            worldIn.setBlock(blockpos, blockstate, 3);
                        } else if (blockstate1.is(Blocks.SEAGRASS) && random.nextInt(10) == 0) {
                            ((IGrowable) Blocks.SEAGRASS)
                                    .performBonemeal((ServerWorld) worldIn, random, blockpos, blockstate1);
                        }
                    }
                }

                stack.shrink(1);
                return true;
            }
        } else {
            return false;
        }
    }

    /**
     * Spawn bonemeal particles.
     *
     * @param worldIn the world in
     * @param posIn   the pos in
     * @param data    the data
     */
    @OnlyIn(Dist.CLIENT)
    public static void spawnBonemealParticles(IWorld worldIn, BlockPos posIn, int data) {
        if (data == 0) {
            data = 15;
        }

        BlockState blockstate = worldIn.getBlockState(posIn);
        if (!blockstate.isAir(worldIn, posIn)) {
            double d0 = 0.5D;
            double d1;
            if (blockstate.is(Blocks.WATER)) {
                data *= 3;
                d1 = 1.0D;
                d0 = 3.0D;
            } else if (blockstate.isSolidRender(worldIn, posIn)) {
                posIn = posIn.above();
                data *= 3;
                d0 = 3.0D;
                d1 = 1.0D;
            } else {
                d1 = blockstate.getShape(worldIn, posIn).max(Direction.Axis.Y);
            }

            worldIn.addParticle(
                    ParticleTypes.HAPPY_VILLAGER,
                    (double) posIn.getX() + 0.5D,
                    (double) posIn.getY() + 0.5D,
                    (double) posIn.getZ() + 0.5D,
                    0.0D,
                    0.0D,
                    0.0D);

            for (int i = 0; i < data; ++i) {
                double d2 = random.nextGaussian() * 0.02D;
                double d3 = random.nextGaussian() * 0.02D;
                double d4 = random.nextGaussian() * 0.02D;
                double d5 = 0.5D - d0;
                double d6 = (double) posIn.getX() + d5 + random.nextDouble() * d0 * 2.0D;
                double d7 = (double) posIn.getY() + random.nextDouble() * d1;
                double d8 = (double) posIn.getZ() + d5 + random.nextDouble() * d0 * 2.0D;
                if (!worldIn.getBlockState((new BlockPos(d6, d7, d8)).below()).isAir()) {
                    worldIn.addParticle(ParticleTypes.HAPPY_VILLAGER, d6, d7, d8, d2, d3, d4);
                }
            }
        }
    }

    public ActionResultType useOn(ItemUseContext context) {
        World world = context.getLevel();
        BlockPos blockpos = context.getClickedPos();
        BlockPos blockpos1 = blockpos.relative(context.getClickedFace());
        if (applyBonemeal(context.getItemInHand(), world, blockpos, context.getPlayer())) {
            if (!world.isClientSide) {
                world.levelEvent(2005, blockpos, 0);
            }

            return ActionResultType.sidedSuccess(world.isClientSide);
        } else {
            BlockState blockstate = world.getBlockState(blockpos);
            boolean flag = blockstate.isFaceSturdy(world, blockpos, context.getClickedFace());
            if (flag
                    && growSeagrass(context.getItemInHand(), world, blockpos1, context.getClickedFace())) {
                if (!world.isClientSide) {
                    world.levelEvent(2005, blockpos1, 0);
                }

                return ActionResultType.sidedSuccess(world.isClientSide);
            } else {
                return super.useOn(context);
            }
        }
    }
}
