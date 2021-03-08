package com.alaharranhonor.swem.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.renderer.color.BlockColors;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.potion.PotionUtils;
import net.minecraft.potion.Potions;
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

import java.util.stream.Stream;

public class HalfBarrelBlock extends Block {
	public static final IntegerProperty LEVEL = BlockStateProperties.LEVEL_0_3;
	private static final VoxelShape SHAPE = Stream.of(
			Block.makeCuboidShape(4.686291501015241, 11, 0, 11.31370849898476, 12, 1),
			Block.makeCuboidShape(4.686291501015241, 11, 0, 11.31370849898476, 12, 1),
			Block.makeCuboidShape(4.686291501015241, 11, 15, 11.31370849898476, 12, 16),
			Block.makeCuboidShape(4.686291501015241, 11, 15, 11.31370849898476, 12, 16),
			Block.makeCuboidShape(0, 11, 4.686291501015241, 1, 12, 11.31370849898476),
			Block.makeCuboidShape(0, 11, 4.686291501015241, 1, 12, 11.31370849898476),
			Block.makeCuboidShape(15, 11, 4.686291501015241, 16, 12, 11.31370849898476),
			Block.makeCuboidShape(15, 11, 4.686291501015241, 16, 12, 11.31370849898476),
			Block.makeCuboidShape(4.893398282201788, 1, 0.5, 11.106601717798213, 12, 1.5),
			Block.makeCuboidShape(4.893398282201788, 1, 0.5, 11.106601717798213, 12, 1.5),
			Block.makeCuboidShape(4.893398282201788, 1, 14.5, 11.106601717798213, 12, 15.5),
			Block.makeCuboidShape(4.893398282201788, 1, 14.5, 11.106601717798213, 12, 15.5),
			Block.makeCuboidShape(0.5, 1, 4.893398282201788, 1.5, 12, 11.106601717798213),
			Block.makeCuboidShape(0.5, 1, 4.893398282201788, 1.5, 12, 11.106601717798213),
			Block.makeCuboidShape(14.5, 1, 4.893398282201788, 15.5, 12, 11.106601717798213),
			Block.makeCuboidShape(14.5, 1, 4.893398282201788, 15.5, 12, 11.106601717798213),
			Block.makeCuboidShape(4.686291501015241, 0, 0, 11.31370849898476, 1, 16),
			Block.makeCuboidShape(4.686291501015241, 0, 0, 11.31370849898476, 1, 16),
			Block.makeCuboidShape(0, 0, 4.686291501015241, 16, 1, 11.31370849898476),
			Block.makeCuboidShape(0, 0, 4.686291501015241, 16, 1, 11.31370849898476)
	).reduce((v1, v2) -> {return VoxelShapes.combineAndSimplify(v1, v2, IBooleanFunction.OR);}).get();

	public HalfBarrelBlock(Properties properties) {
		super(properties);
		this.setDefaultState(this.stateContainer.getBaseState().with(LEVEL, Integer.valueOf(0)));
	}

	@Override
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
		return SHAPE;
	}


	@Override
	public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
		ItemStack itemstack = player.getHeldItem(handIn);
		if (itemstack.isEmpty()) {
			return ActionResultType.PASS;
		} else {
			int i = state.get(LEVEL);
			Item item = itemstack.getItem();
			if (item == Items.WATER_BUCKET) {
				if (i < 3 && !worldIn.isRemote) {
					if (!player.abilities.isCreativeMode) {
						player.setHeldItem(handIn, new ItemStack(Items.BUCKET));
					}

					player.addStat(Stats.FILL_CAULDRON);
					this.setWaterLevel(worldIn, pos, state, i +1);
					worldIn.playSound((PlayerEntity)null, pos, SoundEvents.ITEM_BUCKET_EMPTY, SoundCategory.BLOCKS, 1.0F, 1.0F);
				}

				return ActionResultType.func_233537_a_(worldIn.isRemote);


			} else if (item == Items.BUCKET) {
				if (i >= 1 && !worldIn.isRemote) {
					if (!player.abilities.isCreativeMode) {
						itemstack.shrink(1);
						if (itemstack.isEmpty()) {
							player.setHeldItem(handIn, new ItemStack(Items.WATER_BUCKET));
						} else if (!player.inventory.addItemStackToInventory(new ItemStack(Items.WATER_BUCKET))) {
							player.dropItem(new ItemStack(Items.WATER_BUCKET), false);
						}
					}

					player.addStat(Stats.USE_CAULDRON);
					this.setWaterLevel(worldIn, pos, state, i - 1);
					worldIn.playSound((PlayerEntity) null, pos, SoundEvents.ITEM_BUCKET_FILL, SoundCategory.BLOCKS, 1.0F, 1.0F);
				}
			} else {
				return ActionResultType.PASS;
			}
			return ActionResultType.PASS;
		}
	}

	public void fillWithRain(World worldIn, BlockPos pos) {
		if (worldIn.rand.nextInt(1) == 1) {
			float f = worldIn.getBiome(pos).getTemperature(pos);
			if (!(f < 0.15F)) {
				BlockState blockstate = worldIn.getBlockState(pos);
				if (blockstate.get(LEVEL) < 3) {
					worldIn.setBlockState(pos, blockstate.func_235896_a_(LEVEL), 2);
				}

			}
		}
	}

	public void setWaterLevel(World worldIn, BlockPos pos, BlockState state, int level) {
		worldIn.setBlockState(pos, state.with(LEVEL, Integer.valueOf(MathHelper.clamp(level, 0, 3))), 2);
		worldIn.updateComparatorOutputLevel(pos, this);
	}

	protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
		builder.add(LEVEL);
	}
}
