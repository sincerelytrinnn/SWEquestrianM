package com.alaharranhonor.swem.blocks;

import com.alaharranhonor.swem.util.initialization.SWEMBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
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
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;

import javax.annotation.Nullable;

import net.minecraft.block.AbstractBlock.Properties;

public class BarrelBlock extends Block {
	public static final EnumProperty<HitchingPostBase.PostPart> PART = SWEMBlockStateProperties.POST_PART;

	public BarrelBlock(Properties properties) {
		super(properties);
		this.setDefaultState(
				this.stateContainer.getBaseState()
						.with(PART, HitchingPostBase.PostPart.LOWER)
		);
	}

	@Override
	public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
		ItemStack itemstack = player.getHeldItem(handIn);
		if (itemstack.getItem() == Items.SHEARS) {
			itemstack.damageItem(1, player, (entity) -> entity.sendBreakAnimation(handIn));
			worldIn.setBlockState(pos, Blocks.AIR.getDefaultState());
			ItemEntity entity = new ItemEntity(worldIn, pos.getX(), pos.getY(), pos.getZ(), new ItemStack(SWEMBlocks.HALF_BARRELS.get(DyeColor.WHITE.getId()).get()));
			ItemEntity entity1 = new ItemEntity(worldIn, pos.getX(), pos.getY(), pos.getZ(), new ItemStack(SWEMBlocks.HALF_BARRELS.get(DyeColor.WHITE.getId()).get()));

			worldIn.addEntity(entity);
			worldIn.addEntity(entity1);
			return ActionResultType.SUCCESS;
		} else {
			return ActionResultType.PASS;
		}
	}

	@Override
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
		if (state.get(PART) == HitchingPostBase.PostPart.UPPER) {
			return VoxelShapes.create(0.01D, 0.01D, 0.01D, 0.99D, 1.45d, 0.99D).withOffset(0.0D, -1.0D, 0.0D);
		} else {
			return VoxelShapes.create(0.01D, 0.01D, 0.01D, 0.99D, 1.45d, 0.99D);
		}
	}

	@Override
	protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
		builder.add(PART);
	}

	@Override
	public void onBlockPlacedBy(World worldIn, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
		super.onBlockPlacedBy(worldIn, pos, state, placer, stack);
		if (!worldIn.isRemote) {
			BlockPos blockpos = pos.offset(Direction.UP);
			worldIn.setBlockState(blockpos, state.with(PART, HitchingPostBase.PostPart.UPPER), 3);
			state.updateNeighbours(worldIn, pos, 3);
		}
	}

	public BlockState updatePostPlacement(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos) {
		if (stateIn.get(PART) == HitchingPostBase.PostPart.LOWER && facing == Direction.UP && facingState.getBlock() == Blocks.AIR) {
			return Blocks.AIR.getDefaultState();
		} else if (stateIn.get(PART) == HitchingPostBase.PostPart.UPPER && facing == Direction.DOWN && facingState.getBlock() == Blocks.AIR) {
			return Blocks.AIR.getDefaultState();
		}
		return super.updatePostPlacement(stateIn, facing, facingState, worldIn, currentPos, facingPos);
	}
}
