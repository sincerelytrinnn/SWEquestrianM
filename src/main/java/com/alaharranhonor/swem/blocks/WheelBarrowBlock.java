package com.alaharranhonor.swem.blocks;

import com.alaharranhonor.swem.items.PoopItem;
import com.alaharranhonor.swem.tileentity.WheelBarrowTE;
import com.alaharranhonor.swem.util.initialization.SWEMBlocks;
import com.alaharranhonor.swem.util.initialization.SWEMTileEntities;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.DyeColor;
import net.minecraft.item.ItemStack;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.PacketDistributor;

import javax.annotation.Nullable;

import static com.alaharranhonor.swem.util.initialization.SWEMBlocks.WET_COMPOST_ITEM;

import net.minecraft.block.AbstractBlock.Properties;

public class WheelBarrowBlock extends HorizontalBlock {

	public static final IntegerProperty LEVEL = IntegerProperty.create("level", 0, 4);

	private DyeColor colour;

	public WheelBarrowBlock(Properties properties, DyeColor colour) {
		super(properties);
		this.registerDefaultState(
				this.stateDefinition.any()
				.setValue(FACING, Direction.NORTH)
				.setValue(LEVEL, 0)
		);

		this.colour = colour;
	}

	@Override
	public ActionResultType use(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
		if (!worldIn.isClientSide && handIn == Hand.MAIN_HAND) {
			TileEntity tile = worldIn.getBlockEntity(pos);
			WheelBarrowTE te = (WheelBarrowTE) tile;
			if ((player.getItemInHand(handIn).getItem() instanceof ShavingsItem.SoiledShavingsItem || player.getItemInHand(handIn).getItem() instanceof PoopItem) && te.itemHandler.getStackInSlot(0).getCount() < 8) {

				if (!player.isCreative())
					player.getItemInHand(handIn).split(1);

				ItemStack layer = new ItemStack(SWEMBlocks.SOILED_SHAVINGS_ITEM.get(), 1);


				PacketDistributor.TRACKING_CHUNK.with(() -> te.getLevel().getChunkAt(te.getBlockPos())).send(te.getUpdatePacket());


				if (te.itemHandler.getStackInSlot(0) == ItemStack.EMPTY) {
					te.itemHandler.setStackInSlot(0, layer);
				} else {
					te.itemHandler.insertItem(0, layer, false);
				}
				worldIn.setBlock(pos, state.setValue(LEVEL, (int) Math.floor(te.itemHandler.getStackInSlot(0).getCount() / 2) ), 3);
				if (te.itemHandler.getStackInSlot(0).getCount() == 8)
					te.startTicking();
				
				return ActionResultType.CONSUME;
			}
		}

		return super.use(state, worldIn, pos, player, handIn, hit);
	}

	public DyeColor getColour() {
		return this.colour;
	}

	@Override
	public boolean hasTileEntity(BlockState state) {
		return true;
	}

	@Nullable
	@Override
	public TileEntity createTileEntity(BlockState state, IBlockReader world) {
		return SWEMTileEntities.WHEEL_BARROW_TILE_ENTITY.get().create();
	}

	@Nullable
	@Override
	public BlockState getStateForPlacement(BlockItemUseContext context) {
		return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection()).setValue(LEVEL, 0);
	}

	@Override
	protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
		builder.add(FACING, LEVEL);
	}
}
