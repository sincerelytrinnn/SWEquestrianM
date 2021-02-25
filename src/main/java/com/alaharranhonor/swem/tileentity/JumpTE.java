package com.alaharranhonor.swem.tileentity;

import com.alaharranhonor.swem.blocks.jumps.JumpBlock;
import com.alaharranhonor.swem.blocks.jumps.JumpLayer;
import com.alaharranhonor.swem.blocks.jumps.JumpStandardBlock;
import com.alaharranhonor.swem.blocks.jumps.StandardLayer;
import com.alaharranhonor.swem.network.ChangeLayerBlockPacket;
import com.alaharranhonor.swem.network.ChangeStandardPacket;
import com.alaharranhonor.swem.network.SWEMPacketHandler;
import com.alaharranhonor.swem.util.initialization.SWEMBlocks;
import com.alaharranhonor.swem.util.initialization.SWEMTileEntities;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.item.DyeColor;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JumpTE extends TileEntity {

	private int layerAmount;
	private Map<Integer, ArrayList<BlockPos>> layerPositions = new HashMap<>();
	private Map<Integer, JumpLayer> layerTypes = new HashMap<>();
	private Map<Integer, Integer> layerColors = new HashMap<>();

	private StandardLayer currentStandard;

	public JumpTE() {
		super(SWEMTileEntities.JUMP_TILE_ENTITY.get());
	}

	public void setLayerAmount(int layerAmount) {
		this.layerAmount = layerAmount;
	}
	public int getLayerAmount() {
		return this.layerAmount;
	}

	public void assignJumpBlocks(Map<Integer, ArrayList<BlockPos>> blocks) {
		this.layerPositions = blocks;
	}

	public void placeStandards(StandardLayer standard) {
		this.currentStandard = standard;
		for (int i = 1; i <= layerAmount; i++) {
			if (standard != StandardLayer.NONE) {
				this.world.setBlockState(layerPositions.get(i).get(0), i == layerAmount ? standard.getTopState().with(JumpStandardBlock.HORIZONTAL_FACING, this.getBlockState().get(JumpStandardBlock.HORIZONTAL_FACING)) : i == 1 ? standard.getBottomState().with(JumpStandardBlock.HORIZONTAL_FACING, this.getBlockState().get(JumpStandardBlock.HORIZONTAL_FACING).rotateY().rotateY()) : standard.getMiddleState().with(JumpStandardBlock.HORIZONTAL_FACING, this.getBlockState().get(JumpStandardBlock.HORIZONTAL_FACING)), 3);
				this.world.setBlockState(layerPositions.get(i).get(6), i == layerAmount ? standard.getTopState().with(JumpStandardBlock.HORIZONTAL_FACING, this.getBlockState().get(JumpStandardBlock.HORIZONTAL_FACING).rotateY().rotateY()) : i == 1 ? standard.getBottomState().with(JumpStandardBlock.HORIZONTAL_FACING, this.getBlockState().get(JumpStandardBlock.HORIZONTAL_FACING).rotateY().rotateY()) : standard.getMiddleState().with(JumpStandardBlock.HORIZONTAL_FACING, this.getBlockState().get(JumpStandardBlock.HORIZONTAL_FACING).rotateY().rotateY()), 3);
			} else {
				this.world.setBlockState(layerPositions.get(i).get(0), standard.getBottomState(), 3);
				this.world.setBlockState(layerPositions.get(i).get(6), standard.getBottomState(), 3);
			}

		}
		JumpTE newTe = this;
		newTe.validate();
		this.world.addTileEntity(newTe);

	}

	public void initStandards(StandardLayer standard) {
		this.currentStandard = standard;
		for (int i = 1; i <= layerAmount; i++) {
			if (i != 1) {
				this.world.setBlockState(layerPositions.get(i).get(0), i == layerAmount ? standard.getTopState().with(JumpStandardBlock.HORIZONTAL_FACING, this.getBlockState().get(JumpStandardBlock.HORIZONTAL_FACING)) : standard.getMiddleState().with(JumpStandardBlock.HORIZONTAL_FACING, this.getBlockState().get(JumpStandardBlock.HORIZONTAL_FACING)), 3);
				this.world.removeTileEntity(layerPositions.get(i).get(0));
				JumpPasserTE passer = SWEMTileEntities.JUMP_PASSER_TILE_ENTITY.get().create();
				passer.setPos(layerPositions.get(i).get(0));
				this.world.addTileEntity(passer);
				passer.setControllerPos(this.getPos());

			}
			this.world.setBlockState(layerPositions.get(i).get(6), i == 1 ? standard.getBottomState().with(JumpStandardBlock.HORIZONTAL_FACING, this.getBlockState().get(JumpStandardBlock.HORIZONTAL_FACING).rotateY().rotateY()) : i == layerAmount ? standard.getTopState().with(JumpStandardBlock.HORIZONTAL_FACING, this.getBlockState().get(JumpStandardBlock.HORIZONTAL_FACING).rotateY().rotateY()) : standard.getMiddleState().with(JumpStandardBlock.HORIZONTAL_FACING, this.getBlockState().get(JumpStandardBlock.HORIZONTAL_FACING).rotateY().rotateY()), 3);
			this.world.removeTileEntity(layerPositions.get(i).get(6));
			JumpPasserTE passer = SWEMTileEntities.JUMP_PASSER_TILE_ENTITY.get().create();
			passer.setPos(layerPositions.get(i).get(6));
			this.world.addTileEntity(passer);
			passer.setControllerPos(this.getPos());
		}
	}

	public void placeLayer(int number, JumpLayer layer) {
		layerTypes.put(number, layer);
		if (!layerColors.containsKey(number)) {
			layerColors.put(number, 0);
		}
		for (int i = 1; i < 6; i++) {
			BlockState placeState = i % 3 == 0 ?
					layer.getMiddleState(this.layerColors.get(number)).with(JumpBlock.HORIZONTAL_FACING, this.getBlockState().get(JumpStandardBlock.HORIZONTAL_FACING))
					: i % 2 == 0 ? layer.getBetweenState(this.layerColors.get(number)).with(JumpBlock.HORIZONTAL_FACING, i == 2 ? this.getBlockState().get(JumpStandardBlock.HORIZONTAL_FACING) : this.getBlockState().get(JumpStandardBlock.HORIZONTAL_FACING).getOpposite())
					: layer.getEndState(this.layerColors.get(number)).with(JumpBlock.HORIZONTAL_FACING, i == 1 ? this.getBlockState().get(JumpStandardBlock.HORIZONTAL_FACING) : this.getBlockState().get(JumpStandardBlock.HORIZONTAL_FACING).getOpposite());

			Direction facing = i == 1 ? this.getBlockState().get(JumpStandardBlock.HORIZONTAL_FACING) : this.getBlockState().get(JumpStandardBlock.HORIZONTAL_FACING).getOpposite();
			this.world.setBlockState(layerPositions.get(number).get(i), placeState, 3);
			SWEMPacketHandler.INSTANCE.sendToServer(new ChangeLayerBlockPacket(layerPositions.get(number).get(i), this.getPos(), layer, this.layerColors.get(number), facing, i % 3 == 0 ? "middle" : i % 2 == 0 ? "right" : "left"));
			JumpPasserTE passer = (JumpPasserTE) this.getWorld().getTileEntity(layerPositions.get(number).get(i));
			passer.setControllerPos(this.getPos());
		}

	}

	public void changeColorVariant(int layerNumber) {
		if (!layerColors.containsKey(layerNumber)) {
			layerColors.put(layerNumber, 0);
		}

		int nextColor = layerColors.get(layerNumber) + 1;
		if (nextColor > DyeColor.values().length - 1) {
			nextColor = 0;
		}
		layerColors.put(layerNumber, nextColor);
	}

	public DyeColor getColorVariant(int layerNumber) {
		return DyeColor.values()[this.layerColors.getOrDefault(layerNumber, 0)];
	}

	public JumpLayer getLayer(int layerNumber) {
		return this.layerTypes.get(layerNumber);
	}

	public StandardLayer getCurrentStandard() {
		return this.currentStandard;
	}

	public void setCurrentStandard(StandardLayer standard) {
		this.currentStandard = standard;
	}

	public List<JumpLayer> getApplicableLayers(int layerNumber) {
		List<JumpLayer> layers = new ArrayList<>();

		outer:
		for (JumpLayer layer : JumpLayer.values()) {
			if (layer == JumpLayer.AIR) continue;

			if (layer == JumpLayer.NONE) {
				layers.add(layer);
				continue;
			}
			if (layer.getMinHeight() <= this.getLayerAmount() &&
					(layerNumber >= layer.getMinLayer() && layerNumber <= layer.getMaxLayer())
					||
					(layerNumber == this.getLayerAmount() && layer.getMaxLayer() == layerNumber + 1 && layerNumber + 1 == layer.getMinLayer()))
			{
				switch (layer) {

					case STAIR_DROP:
					case CAVALETTI: {
						if (this.getCurrentStandard() != StandardLayer.NONE) {
							continue;
						}
						if (!layerTypes.containsKey(2) && !layerTypes.containsKey(3) && !layerTypes.containsKey(4) && !layerTypes.containsKey(5)) {
							layers.add(layer);
							continue;
						}
					}


					case LOG:
					case POLE_ON_BOX_SMALL:
					case POLE_ON_BOX_LARGE:
					case HEDGE:
					case WALL: {
						if (!layerTypes.containsKey(2) && !layerTypes.containsKey(3) && !layerTypes.containsKey(4) && !layerTypes.containsKey(5)) {
							layers.add(layer);
							continue;
						}
					}

					//case BRUSH_BOX:
					case FLOWER_BOX:
					case COOP:
					case ROLL_TOP:
					case WALL_MINI:
					case GROUND_POLE: {
						if (layerTypes.containsKey(2)) {
							if (!JumpLayer.testForRail(layerTypes.get(2)) && !JumpLayer.testForNone(layerTypes.get(2))) {
								break;
							}
						}
						if (layerTypes.containsKey(3)) {
							if (!JumpLayer.testForRail(layerTypes.get(3)) && !JumpLayer.testForNone(layerTypes.get(2))) {
								break;
							}
						}
						if (layerTypes.containsKey(4)) {
							if (!JumpLayer.testForRail(layerTypes.get(4)) && !JumpLayer.testForNone(layerTypes.get(2))) {
								break;
							}
						}
						if (layerTypes.containsKey(5)) {
							if (!JumpLayer.testForRail(layerTypes.get(5)) && !JumpLayer.testForNone(layerTypes.get(2))) {
								break;
							}
						}
						layers.add(layer);
						continue;
					}

					case RAIL:
					case PLANK:
					case PLANK_FANCY: {
					//case NUMBERS:
					//case RED_FLAG:
					//case WHITE_FLAG:
					//case RED_WHITE_FLAG: {
						layers.add(layer);
						continue;
					}

					case PANEL_WAVE:
					case PANEL_ARROW:
					case PANEL_STRIPE: {
						if (layerTypes.containsKey(layerNumber + 1)) {
							if (JumpLayer.testForRail(layerTypes.get(layerNumber + 1))) {
								layers.add(layer);
								continue;
							}
						}
					}

					/*case CROSS_RAILS: {
						for (int i = 1; i < layerNumber; i++) {
							if (layerTypes.containsKey(i)) {
								continue outer;
							}
						}
						if (layerTypes.containsKey(layerNumber + 1)) {
							if (JumpLayer.testForRail(layerTypes.get(layerNumber + 1))) {
								layers.add(layer);
							} else {
								break;
							}
							layers.add(layer);
						}
					}

					case SWEDISH_RAILS: {
						for (int i = layerNumber + 1; i < this.getLayerAmount(); i++) {
							if (layerTypes.containsKey(i)) {
								continue outer;
							}
						}
						layers.add(layer);
					}
					 */
				}
			}
		}
		/*if (!layers.contains(this.layerTypes.get(layerNumber))) {
			layerTypes.remove(layerNumber);
			// Remove layer.
			this.removeLayer(layerNumber);
		}*/

		return layers;
	}

	public void deleteLayer(int layerNumber) {
		if (layerNumber < 2 ) return;
		for (int i = 0; i < 7; i++) {
			this.world.setBlockState(this.layerPositions.get(layerNumber).get(i), Blocks.AIR.getDefaultState(), 3);
			SWEMPacketHandler.INSTANCE.sendToServer(new ChangeLayerBlockPacket(layerPositions.get(layerNumber).get(i), this.getPos(), JumpLayer.AIR, 0, Direction.NORTH, "middle"));
		}

		if (layerTypes.containsKey(layerNumber)) {
			layerTypes.remove(layerNumber);
		}
		if (layerPositions.containsKey(layerNumber)) {
			layerPositions.remove(layerNumber);
		}

		this.layerAmount--;
	}

	public void addLayer(int layerNumber) {
		if (layerNumber > 5 || layerNumber < 2) return;


		layerTypes.put(layerNumber, JumpLayer.NONE);
		ArrayList<BlockPos> positions = this.layerPositions.getOrDefault(layerNumber, new ArrayList<>());

		for (int i = 0; i < 7; i++) {
			BlockPos newPos = this.layerPositions.get(layerNumber - 1).get(i);
			positions.add(newPos.add(0, 1, 0));
			if (i == 0 || i == 6) {
				// Standards
				Direction facing = i == 0 ? this.getBlockState().get(JumpStandardBlock.HORIZONTAL_FACING) : this.getBlockState().get(JumpStandardBlock.HORIZONTAL_FACING).rotateY().rotateY();
				// Replace the lower standards to a middle piece.
				if (layerNumber - 1 != 1) {
					this.world.setBlockState(newPos, this.currentStandard.getMiddleState().with(JumpStandardBlock.HORIZONTAL_FACING, facing), 3);
					SWEMPacketHandler.INSTANCE.sendToServer(new ChangeStandardPacket(newPos, this.getPos(), this.currentStandard, facing, "middle"));
					this.world.removeTileEntity(newPos);
					JumpPasserTE passer = SWEMTileEntities.JUMP_PASSER_TILE_ENTITY.get().create();
					passer.setPos(newPos);
					this.world.addTileEntity(passer);
					passer.setControllerPos(this.getPos());



					this.world.setBlockState(positions.get(i), this.currentStandard.getTopState().with(JumpStandardBlock.HORIZONTAL_FACING, facing), 3);
					SWEMPacketHandler.INSTANCE.sendToServer(new ChangeStandardPacket(positions.get(i), this.getPos(), this.currentStandard, facing, "top"));
					this.world.removeTileEntity(positions.get(i));
					JumpPasserTE passer1 = SWEMTileEntities.JUMP_PASSER_TILE_ENTITY.get().create();
					passer1.setPos(positions.get(i));
					this.world.addTileEntity(passer1);
					passer1.setControllerPos(this.getPos());
				}

			} else {
				this.world.setBlockState(positions.get(i), JumpLayer.NONE.getMiddleState(0), 3);
				SWEMPacketHandler.INSTANCE.sendToServer(new ChangeLayerBlockPacket(positions.get(i), this.getPos(), JumpLayer.NONE, 0, Direction.NORTH, "middle"));
				JumpPasserTE passer = (JumpPasserTE) this.world.getTileEntity(positions.get(i));
				passer.setControllerPos(this.getPos());
			}


		}

		layerPositions.put(layerNumber, positions);
		this.layerAmount++;
	}

	@Override
	public void remove() {

		for (ArrayList<BlockPos> positions : this.layerPositions.values()) {
			for (BlockPos pos : positions) {
				this.world.setBlockState(pos, Blocks.AIR.getDefaultState(), 3);
				SWEMPacketHandler.INSTANCE.sendToServer(new ChangeLayerBlockPacket(pos, this.getPos(), JumpLayer.AIR, 0, Direction.NORTH, "middle"));
			}
		}


		super.remove();
	}

	@Override
	public CompoundNBT write(CompoundNBT compound) {
		CompoundNBT positions = new CompoundNBT();

		layerPositions.forEach((layerNumber, blockPos) -> {
			CompoundNBT layer = new CompoundNBT();
			for (int i = 0; i < 7; i++) {
				BlockPos pos  = blockPos.get(i);
				layer.putIntArray(Integer.toString(i), new int[] {pos.getX(), pos.getY(), pos.getZ()});
			}

			positions.put(Integer.toString(layerNumber), layer);
		});

		compound.put("layerPositions", positions);

		CompoundNBT layerTypes = new CompoundNBT();
		this.layerTypes.forEach((layerNumber, jumpType) -> {
			layerTypes.putString(Integer.toString(layerNumber), jumpType.name());
		});

		compound.put("layerTypes", layerTypes);

		compound.putInt("layerAmount", layerAmount);

		compound.putString("standard", this.currentStandard != null ? this.currentStandard.name() : StandardLayer.SCHOOLING.name());


		return super.write(compound);
	}

	@Override
	public void read(BlockState state, CompoundNBT nbt) {
		this.setLayerAmount(nbt.getInt("layerAmount"));


		if (nbt.contains("layerPositions")) {
			Map<Integer, ArrayList<BlockPos>> layerPos = new HashMap<>();
			CompoundNBT positions = (CompoundNBT) nbt.get("layerPositions");

			for (int i = 1; i <= layerAmount; i++) {
				CompoundNBT layer = (CompoundNBT) positions.get(Integer.toString(i));
				ArrayList<BlockPos> blockPositions = new ArrayList<>();
				for (int j = 0; j < 7; j++) {
					int[] posCords = layer.getIntArray(Integer.toString(j));
					blockPositions.add(new BlockPos(posCords[0], posCords[1], posCords[2]));
				}
				layerPos.put(i, blockPositions);
			}
			this.assignJumpBlocks(layerPos);
		}

		if (nbt.contains("layerTypes")) {
			CompoundNBT layerTypes = (CompoundNBT) nbt.get("layerTypes");
			for (int i = 1; i <= layerAmount; i++) {
				String jumpName = layerTypes.getString(Integer.toString(i));
				if (!jumpName.isEmpty()) {
					this.placeLayer(i, JumpLayer.valueOf(jumpName));
				}
			}
		}

		this.currentStandard = StandardLayer.valueOf(nbt.getString("standard"));

		super.read(state, nbt);
	}

	@Override
	public CompoundNBT getUpdateTag() {
		return this.write(new CompoundNBT());
	}

	@Override
	public void handleUpdateTag(BlockState state, CompoundNBT tag) {
		this.read(state, tag);
	}

}
