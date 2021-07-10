package com.alaharranhonor.swem.blocks;

import com.alaharranhonor.swem.util.registry.SWEMBlocks;
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

public class BarrelBlock extends Block {
	public static final EnumProperty<HitchingPostBase.PostPart> PART = SWEMBlockStateProperties.POST_PART;

	public BarrelBlock(Properties properties) {
		super(properties);
		this.registerDefaultState(
				this.stateDefinition.any()
						.setValue(PART, HitchingPostBase.PostPart.LOWER)
		);
	}

	@Override
	public ActionResultType use(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
		ItemStack itemstack = player.getItemInHand(handIn);
		if (itemstack.getItem() == Items.SHEARS) {
			itemstack.hurtAndBreak(1, player, (entity) -> entity.broadcastBreakEvent(handIn));
			worldIn.setBlock(pos, Blocks.AIR.defaultBlockState(), 3);
			ItemEntity entity = new ItemEntity(worldIn, pos.getX(), pos.getY(), pos.getZ(), new ItemStack(SWEMBlocks.HALF_BARRELS.get(DyeColor.WHITE.getId()).get()));
			ItemEntity entity1 = new ItemEntity(worldIn, pos.getX(), pos.getY(), pos.getZ(), new ItemStack(SWEMBlocks.HALF_BARRELS.get(DyeColor.WHITE.getId()).get()));

			worldIn.addFreshEntity(entity);
			worldIn.addFreshEntity(entity1);
			return ActionResultType.SUCCESS;
		} else {
			return ActionResultType.PASS;
		}
	}

	@Override
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
		if (state.getValue(PART) == HitchingPostBase.PostPart.UPPER) {
			return VoxelShapes.box(0.01D, 0.01D, 0.01D, 0.99D, 1.45d, 0.99D).move(0.0D, -1.0D, 0.0D);
		} else {
			return VoxelShapes.box(0.01D, 0.01D, 0.01D, 0.99D, 1.45d, 0.99D);
		}
	}

	@Override
	protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
		builder.add(PART);
	}

	@Override
	public void setPlacedBy(World worldIn, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
		super.setPlacedBy(worldIn, pos, state, placer, stack);
		if (!worldIn.isClientSide) {
			BlockPos blockpos = pos.relative(Direction.UP);
			worldIn.setBlock(blockpos, state.setValue(PART, HitchingPostBase.PostPart.UPPER), 3);
			state.updateNeighbourShapes(worldIn, pos, 3);
		}
	}

	public BlockState updateShape(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos) {
		if (stateIn.getValue(PART) == HitchingPostBase.PostPart.LOWER && facing == Direction.UP && facingState.getBlock() == Blocks.AIR) {
			return Blocks.AIR.defaultBlockState();
		} else if (stateIn.getValue(PART) == HitchingPostBase.PostPart.UPPER && facing == Direction.DOWN && facingState.getBlock() == Blocks.AIR) {
			return Blocks.AIR.defaultBlockState();
		}
		return super.updateShape(stateIn, facing, facingState, worldIn, currentPos, facingPos);
	}
}
