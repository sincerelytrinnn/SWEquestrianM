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
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import javax.annotation.Nullable;
import java.util.Random;

public class TearingMagma extends Block {


	public static final IntegerProperty AGE = BlockStateProperties.AGE_3;

	/**
	 * Instantiates a new Tearing magma.
	 *
	 * @param properties the properties
	 */
	public TearingMagma(AbstractBlock.Properties properties) {
		super(properties);
		this.registerDefaultState(this.stateDefinition.any().setValue(AGE, Integer.valueOf(0)));
	}

	public void playerDestroy(World worldIn, PlayerEntity player, BlockPos pos, BlockState state, @Nullable TileEntity te, ItemStack stack) {
		super.playerDestroy(worldIn, player, pos, state, te, stack);
		if (EnchantmentHelper.getItemEnchantmentLevel(Enchantments.SILK_TOUCH, stack) == 0) {
			Material material = worldIn.getBlockState(pos.below()).getMaterial();
			if (material.blocksMotion() || material.isLiquid()) {
				worldIn.setBlock(pos, Blocks.LAVA.defaultBlockState(), 3);
			}
		}

	}

	/**
	 * Performs a random tick on a block.
	 */
	public void randomTick(BlockState state, ServerWorld worldIn, BlockPos pos, Random random) {
		this.tick(state, worldIn, pos, random);
	}

	public void tick(BlockState state, ServerWorld worldIn, BlockPos pos, Random rand) {
		if ((rand.nextInt(3) == 0 || this.shouldMelt(worldIn, pos, 4)) && worldIn.getMaxLocalRawBrightness(pos) > 11 - state.getValue(AGE) - state.getLightBlock(worldIn, pos) && this.slightlyMelt(state, worldIn, pos)) {
			BlockPos.Mutable blockpos$mutable = new BlockPos.Mutable();

			for(Direction direction : Direction.values()) {
				blockpos$mutable.setWithOffset(pos, direction);
				BlockState blockstate = worldIn.getBlockState(blockpos$mutable);
				if (blockstate.is(this) && !this.slightlyMelt(blockstate, worldIn, blockpos$mutable)) {
					worldIn.getBlockTicks().scheduleTick(blockpos$mutable, this, MathHelper.nextInt(rand, 20, 40));
				}
			}

		} else {
			worldIn.getBlockTicks().scheduleTick(pos, this, MathHelper.nextInt(rand, 20, 40));
		}
	}

	/**
	 * Slightly melt boolean.
	 *
	 * @param state   the state
	 * @param worldIn the world in
	 * @param pos     the pos
	 * @return the boolean
	 */
	private boolean slightlyMelt(BlockState state, World worldIn, BlockPos pos) {
		int i = state.getValue(AGE);
		if (i < 3) {
			worldIn.setBlock(pos, state.setValue(AGE, Integer.valueOf(i + 1)), 3);
			return false;
		} else {
			this.turnIntoLava(state, worldIn, pos);
			return true;
		}
	}

	public void neighborChanged(BlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos, boolean isMoving) {
		if (blockIn == this && this.shouldMelt(worldIn, pos, 2)) {
			this.turnIntoLava(state, worldIn, pos);
		}

		super.neighborChanged(state, worldIn, pos, blockIn, fromPos, isMoving);
	}

	/**
	 * Should melt boolean.
	 *
	 * @param worldIn           the world in
	 * @param pos               the pos
	 * @param neighborsRequired the neighbors required
	 * @return the boolean
	 */
	private boolean shouldMelt(IBlockReader worldIn, BlockPos pos, int neighborsRequired) {
		int i = 0;
		BlockPos.Mutable blockpos$mutable = new BlockPos.Mutable();

		for(Direction direction : Direction.values()) {
			blockpos$mutable.setWithOffset(pos, direction);
			if (worldIn.getBlockState(blockpos$mutable).is(this)) {
				++i;
				if (i >= neighborsRequired) {
					return false;
				}
			}
		}

		return true;
	}

	/**
	 * Turn into lava.
	 *
	 * @param state the state
	 * @param world the world
	 * @param pos   the pos
	 */
	protected void turnIntoLava(BlockState state, World world, BlockPos pos) {
		world.setBlock(pos, Blocks.LAVA.defaultBlockState(), 3);
		world.neighborChanged(pos, Blocks.LAVA, pos);

	}



	protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
		builder.add(AGE);
	}

	/**
	 * Gets item.
	 *
	 * @param worldIn the world in
	 * @param pos     the pos
	 * @param state   the state
	 * @return the item
	 */
	public ItemStack getItem(IBlockReader worldIn, BlockPos pos, BlockState state) {
		return ItemStack.EMPTY;
	}
}
