package com.alaharranhonor.swem.tools;

import com.alaharranhonor.swem.blocks.jumps.JumpControllerBlock;
import com.alaharranhonor.swem.blocks.jumps.JumpLayer;
import com.alaharranhonor.swem.blocks.jumps.StandardLayer;
import com.alaharranhonor.swem.container.JumpContainer;
import com.alaharranhonor.swem.items.ItemBase;
import com.alaharranhonor.swem.tileentity.JumpPasserTE;
import com.alaharranhonor.swem.tileentity.JumpTE;
import com.alaharranhonor.swem.util.registry.SWEMBlocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.fml.network.NetworkHooks;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MeasurementTool extends ItemBase {

	@Override
	public ActionResultType useOn(ItemUseContext context) {

		if (context.getLevel().isClientSide) return ActionResultType.PASS;

		TileEntity te = context.getLevel().getBlockEntity(context.getClickedPos());
		if (te != null) {
			if (te instanceof JumpPasserTE) {
				JumpPasserTE jumpPasser = (JumpPasserTE) te;
				if (jumpPasser.getControllerPos() != null ) {
					JumpTE controller = (JumpTE) context.getLevel().getBlockEntity(jumpPasser.getControllerPos());
					INamedContainerProvider provider = new INamedContainerProvider() {
						@Override
						public ITextComponent getDisplayName() {
							return new TranslationTextComponent("screen.swem.jump_builder");
						}

						@Nullable
						@Override
						public Container createMenu(int p_createMenu_1_, PlayerInventory p_createMenu_2_, PlayerEntity p_createMenu_3_) {
							return new JumpContainer(p_createMenu_1_, p_createMenu_2_, controller);
						}
					};
					NetworkHooks.openGui((ServerPlayerEntity) context.getPlayer(), provider, buffer ->
							buffer
									.writeBlockPos(controller.getBlockPos())
					);
					//SWEMPacketHandler.INSTANCE.send(PacketDistributor.PLAYER.with(() -> (ServerPlayerEntity) context.getPlayer()), new OpenGuiPacket(controller.getPos(), controller.getLayerAmount(), controller.getCurrentStandard()));

				}
			}
			if (te instanceof JumpTE) {
				JumpTE jumpController = (JumpTE) te;
				if (jumpController.layerAmount == 0) return ActionResultType.PASS;
				INamedContainerProvider provider = new INamedContainerProvider() {
					@Override
					public ITextComponent getDisplayName() {
						return new TranslationTextComponent("screen.swem.jump_builder");
					}

					@Nullable
					@Override
					public Container createMenu(int p_createMenu_1_, PlayerInventory p_createMenu_2_, PlayerEntity p_createMenu_3_) {
						return new JumpContainer(p_createMenu_1_, p_createMenu_2_, jumpController);
					}
				};
				NetworkHooks.openGui((ServerPlayerEntity) context.getPlayer(), provider, buffer ->
						buffer
								.writeBlockPos(jumpController.getBlockPos())
				);
				//SWEMPacketHandler.INSTANCE.send(PacketDistributor.PLAYER.with(() -> (ServerPlayerEntity) context.getPlayer()), new OpenGuiPacket(jumpController.getPos(), jumpController.getLayerAmount(), jumpController.getCurrentStandard()));
			}
			return ActionResultType.CONSUME;
		}


		ItemStack stack = context.getItemInHand();
		CompoundNBT nbt = stack.getOrCreateTag();

		if (nbt.contains("pos1")) {
			int[] posCords = nbt.getIntArray("pos1");
			BlockPos firstPos = new BlockPos(posCords[0], posCords[1], posCords[2]);
			BlockPos pos = context.getClickedPos();
			int direction = isValidPos(firstPos, pos);
			if (direction == -1) {
				nbt.remove("pos1");
				stack.setTag(nbt);
				return ActionResultType.CONSUME;
			}



			System.out.println(firstPos.toString() + " | " + pos.toString());
			ArrayList<BlockPos> blockPositions = new ArrayList<>();
			BlockPos.betweenClosed(firstPos, pos).forEach((ps) -> {
				BlockPos pos1 = ps.immutable();
				blockPositions.add(pos1);
			});
			int layerAmount = blockPositions.size() / 7;
			nbt.remove("pos1");
			int lowestYValue = Math.min(firstPos.getY(), pos.getY());
			Map<Integer, ArrayList<BlockPos>> layers = this.rearrangeLayers(lowestYValue, blockPositions);


			// Should default to either the player facing north/east

			Direction facing = context.getHorizontalDirection().getAxis() == Direction.Axis.Z ? Direction.SOUTH : Direction.WEST;

			context.getLevel().setBlock(layers.get(1).get(0).relative(Direction.UP, 5), SWEMBlocks.JUMP_CONTROLLER.get().defaultBlockState().setValue(JumpControllerBlock.FACING, facing), 3);

			JumpTE jumpController = (JumpTE) context.getLevel().getBlockEntity(layers.get(1).get(0).relative(Direction.UP, 5));
			jumpController.setLayerAmount(layerAmount);
			jumpController.assignJumpBlocks(layers);
			jumpController.initStandards(StandardLayer.SCHOOLING);
			for (int i = 1; i <= layerAmount; i++) {
				jumpController.placeLayer(i, JumpLayer.NONE);
			}



			INamedContainerProvider provider = new INamedContainerProvider() {
				@Override
				public ITextComponent getDisplayName() {
					return new TranslationTextComponent("screen.swem.jump_builder");
				}

				@Nullable
				@Override
				public Container createMenu(int p_createMenu_1_, PlayerInventory p_createMenu_2_, PlayerEntity p_createMenu_3_) {
					return new JumpContainer(p_createMenu_1_, p_createMenu_2_, jumpController);
				}
			};
			NetworkHooks.openGui((ServerPlayerEntity) context.getPlayer(), provider, buffer ->
					buffer
							.writeBlockPos(jumpController.getBlockPos())
			);

			//SWEMPacketHandler.INSTANCE.send(PacketDistributor.PLAYER.with(() -> (ServerPlayerEntity) context.getPlayer()), new OpenGuiPacket(jumpController.getPos(), jumpController.getLayerAmount(), jumpController.getCurrentStandard()));



			stack.setTag(new CompoundNBT());

			return ActionResultType.CONSUME;
		} else {
			BlockPos pos = context.getClickedPos();
			nbt.putIntArray("pos1", new int[] {pos.getX(), pos.getY(), pos.getZ()});
			stack.setTag(nbt);
			return ActionResultType.CONSUME;
		}


	}

	private Map<Integer, ArrayList<BlockPos>> rearrangeLayers(int lowestYValue, ArrayList<BlockPos> positions) {
		Map<Integer, ArrayList<BlockPos>> layers = new HashMap<>();
		for (int i = 1; i <= positions.size(); i++) {
			BlockPos pos = positions.get(i - 1);
			int layerNumber = (pos.getY() - lowestYValue) + 1;
			ArrayList<BlockPos> layer = layers.getOrDefault(layerNumber, new ArrayList<>());
			layer.add(pos);
			layers.put(layerNumber, layer);
		}

		return layers;
	}

	private int isValidPos(BlockPos first, BlockPos second) {
		if (first.getX() == second.getX()) {
			if (second.getZ() == first.getZ() - 6) {
				return 1; // Negative Z
			} else if (second.getZ() == first.getZ() + 6) {
				return 2; // Positive Z
			}
			//check < or > 5 on z
		} else if (first.getZ() == second.getZ()) {
			if (second.getX() == first.getX() - 6) {
				return 3; // Negative X
			} else if (second.getX() == first.getX() + 6) {
				return 4; // Positive X
			}
		}

		return -1;

	}


	@Override
	public void readShareTag(ItemStack stack, @Nullable CompoundNBT nbt) {
		stack.setTag(new CompoundNBT());
	}
}
