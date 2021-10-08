package com.alaharranhonor.swem.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.loot.LootContext;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.stats.Stats;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.shapes.IBooleanFunction;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

import java.util.List;
import java.util.stream.Stream;

import net.minecraft.block.AbstractBlock.Properties;

public class HalfBarrelBlock extends Block {
	public static final IntegerProperty LEVEL = BlockStateProperties.LEVEL_CAULDRON;

	public HalfBarrelBlock(Properties properties) {
		super(properties);
		this.registerDefaultState(this.stateDefinition.any().setValue(LEVEL, Integer.valueOf(0)));
	}

	@Override
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
		return VoxelShapes.box(0.01d, 0.01d, 0.01d, 0.99d, 0.75d, 0.99d);
	}


	@Override
	public ActionResultType use(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
		ItemStack itemstack = player.getItemInHand(handIn);
		if (itemstack.isEmpty()) {
			return ActionResultType.PASS;
		} else {
			int i = state.getValue(LEVEL);
			Item item = itemstack.getItem();
			if (item == Items.WATER_BUCKET) {
				if (i < 3 && !worldIn.isClientSide) {
					if (!player.abilities.instabuild) {
						player.setItemInHand(handIn, new ItemStack(Items.BUCKET));
					}

					player.awardStat(Stats.FILL_CAULDRON);
					this.setWaterLevel(worldIn, pos, state, i +1);
					worldIn.playSound((PlayerEntity)null, pos, SoundEvents.BUCKET_EMPTY, SoundCategory.BLOCKS, 1.0F, 1.0F);
				}

				return ActionResultType.sidedSuccess(worldIn.isClientSide);


			} else if (item == Items.BUCKET) {
				if (i >= 1 && !worldIn.isClientSide) {
					if (!player.abilities.instabuild) {
						itemstack.shrink(1);
						if (itemstack.isEmpty()) {
							player.setItemInHand(handIn, new ItemStack(Items.WATER_BUCKET));
						} else if (!player.inventory.add(new ItemStack(Items.WATER_BUCKET))) {
							player.drop(new ItemStack(Items.WATER_BUCKET), false);
						}
					}

					player.awardStat(Stats.USE_CAULDRON);
					this.setWaterLevel(worldIn, pos, state, i - 1);
					worldIn.playSound((PlayerEntity) null, pos, SoundEvents.BUCKET_FILL, SoundCategory.BLOCKS, 1.0F, 1.0F);
				}

				return ActionResultType.sidedSuccess(worldIn.isClientSide);

			} else {
				return ActionResultType.PASS;
			}
		}
	}

	public void fillWithRain(World worldIn, BlockPos pos) {
		if (worldIn.random.nextInt(1) == 1) {
			float f = worldIn.getBiome(pos).getTemperature(pos);
			if (!(f < 0.15F)) {
				BlockState blockstate = worldIn.getBlockState(pos);
				if (blockstate.getValue(LEVEL) < 3) {
					worldIn.setBlock(pos, blockstate.cycle(LEVEL), 2);
				}

			}
		}
	}

	public void setWaterLevel(World worldIn, BlockPos pos, BlockState state, int level) {
		worldIn.setBlock(pos, state.setValue(LEVEL, Integer.valueOf(MathHelper.clamp(level, 0, 3))), 2);
		worldIn.updateNeighbourForOutputSignal(pos, this);
	}

	protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
		builder.add(LEVEL);
	}
}
