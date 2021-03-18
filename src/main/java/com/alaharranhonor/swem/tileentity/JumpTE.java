package com.alaharranhonor.swem.tileentity;

import com.alaharranhonor.swem.blocks.jumps.JumpBlock;
import com.alaharranhonor.swem.blocks.jumps.JumpLayer;
import com.alaharranhonor.swem.blocks.jumps.JumpStandardBlock;
import com.alaharranhonor.swem.blocks.jumps.StandardLayer;
import com.alaharranhonor.swem.network.*;
import com.alaharranhonor.swem.util.initialization.SWEMTileEntities;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.Minecraft;
import net.minecraft.item.DyeColor;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.network.PacketDistributor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JumpTE extends TileEntity {

	public int layerAmount;
	private Map<Integer, ArrayList<BlockPos>> layerPositions = new HashMap<>();
	public Map<Integer, JumpLayer> layerTypes = new HashMap<>();
	public Map<Integer, Integer> layerColors = new HashMap<>();

	public StandardLayer currentStandard;

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

	public void placeStandards(int layerNumber, StandardLayer standard) {
		this.currentStandard = standard;

		if (standard != StandardLayer.NONE) {
			this.world.setBlockState(layerPositions.get(layerNumber).get(0), layerNumber == layerAmount ? standard.getTopState().with(JumpStandardBlock.HORIZONTAL_FACING, this.getBlockState().get(JumpStandardBlock.HORIZONTAL_FACING)) : layerNumber == 1 ? standard.getBottomState().with(JumpStandardBlock.HORIZONTAL_FACING, this.getBlockState().get(JumpStandardBlock.HORIZONTAL_FACING).rotateY().rotateY()) : standard.getMiddleState().with(JumpStandardBlock.HORIZONTAL_FACING, this.getBlockState().get(JumpStandardBlock.HORIZONTAL_FACING)), 3);
			this.world.setBlockState(layerPositions.get(layerNumber).get(6), layerNumber == layerAmount ? standard.getTopState().with(JumpStandardBlock.HORIZONTAL_FACING, this.getBlockState().get(JumpStandardBlock.HORIZONTAL_FACING).rotateY().rotateY()) : layerNumber == 1 ? standard.getBottomState().with(JumpStandardBlock.HORIZONTAL_FACING, this.getBlockState().get(JumpStandardBlock.HORIZONTAL_FACING).rotateY().rotateY()) : standard.getMiddleState().with(JumpStandardBlock.HORIZONTAL_FACING, this.getBlockState().get(JumpStandardBlock.HORIZONTAL_FACING).rotateY().rotateY()), 3);
		} else {
			this.world.setBlockState(layerPositions.get(layerNumber).get(0), standard.getBottomState(), 3);
			this.world.setBlockState(layerPositions.get(layerNumber).get(6), standard.getBottomState(), 3);
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
			this.resetColor(number);
		}
		if (this.world != null) {
			for (int i = 1; i < 6; i++) {
				BlockState placeState = i % 3 == 0 ?
						layer.getMiddleState(this.layerColors.get(number)).with(JumpBlock.HORIZONTAL_FACING, this.getBlockState().get(JumpStandardBlock.HORIZONTAL_FACING))
						: i % 2 == 0 ? layer.getBetweenState(this.layerColors.get(number)).with(JumpBlock.HORIZONTAL_FACING, i == 2 ? this.getBlockState().get(JumpStandardBlock.HORIZONTAL_FACING) : this.getBlockState().get(JumpStandardBlock.HORIZONTAL_FACING).getOpposite())
						: layer.getEndState(this.layerColors.get(number)).with(JumpBlock.HORIZONTAL_FACING, i == 1 ? this.getBlockState().get(JumpStandardBlock.HORIZONTAL_FACING) : this.getBlockState().get(JumpStandardBlock.HORIZONTAL_FACING).getOpposite());

				//Direction facing = i == 1 ? this.getBlockState().get(JumpStandardBlock.HORIZONTAL_FACING) : this.getBlockState().get(JumpStandardBlock.HORIZONTAL_FACING).getOpposite();
				this.world.setBlockState(layerPositions.get(number).get(i), placeState, 1 | 2);
				JumpPasserTE passer = (JumpPasserTE) this.world.getTileEntity(layerPositions.get(number).get(i));
				if (passer != null) {
					passer.setControllerPos(this.getPos());
					passer.markDirty();
				}
			}

		}
	}

	public void changeLayer(int layerNumber) {
		List<JumpLayer> applicableLayers = this.getApplicableLayers(layerNumber);
		if (applicableLayers.contains(this.layerTypes.get(layerNumber))) {
			int indexToPick = applicableLayers.indexOf(this.layerTypes.get(layerNumber)) + 1;
			if (indexToPick >= applicableLayers.size()) {
				indexToPick = 0;
			}
			this.placeLayer(layerNumber, applicableLayers.get(indexToPick));
		} else {
			this.placeLayer(layerNumber, applicableLayers.get(0));
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

		this.placeLayer(layerNumber, this.getLayer(layerNumber));
	}

	private void resetColor(int layerNumber) {
		layerColors.put(layerNumber, 0);
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

					case BRUSH_BOX:
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

			BlockPos newPos = this.layerPositions.get(layerNumber - 1).get(i).add(0, 1, 0);
			positions.add(newPos);

			if (i == 0 || i == 6) {
				// Standards
				Direction facing = i == 0 ? this.getBlockState().get(JumpStandardBlock.HORIZONTAL_FACING) : this.getBlockState().get(JumpStandardBlock.HORIZONTAL_FACING).rotateY().rotateY();

				// Replace the lower standards to a middle piece. If the 2nd layer is added, keep the bottom as the bottom piece.
				if (layerNumber - 1 != 1) {
					this.world.removeTileEntity(newPos);
					this.world.setBlockState(this.layerPositions.get(layerNumber - 1).get(i), this.currentStandard.getMiddleState().with(JumpStandardBlock.HORIZONTAL_FACING, facing), 1 | 2 );
					JumpPasserTE passer = SWEMTileEntities.JUMP_PASSER_TILE_ENTITY.get().create();
					passer.setPos(newPos);
					this.world.addTileEntity(passer);
					passer.setControllerPos(this.getPos());

				}
				// Set the state at the new pos, to the top piece.
				this.world.removeTileEntity(newPos);
				this.world.setBlockState(newPos, this.currentStandard.getTopState().with(JumpStandardBlock.HORIZONTAL_FACING, facing), 1 | 2 );
				JumpPasserTE passer1 = SWEMTileEntities.JUMP_PASSER_TILE_ENTITY.get().create();
				passer1.setPos(newPos);
				this.world.addTileEntity(passer1);
				passer1.setControllerPos(this.getPos());


			}
		}

		layerPositions.put(layerNumber, positions);
		this.placeLayer(layerNumber, JumpLayer.NONE);
		this.layerAmount++;
	}

	@Override
	public void remove() {

		for (ArrayList<BlockPos> positions : this.layerPositions.values()) {
			for (BlockPos pos : positions) {
				this.world.setBlockState(pos, Blocks.AIR.getDefaultState(), 3);
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

		CompoundNBT layerColors = new CompoundNBT();
		this.layerColors.forEach((layerNumber, colorId) -> {
			layerColors.putInt(Integer.toString(layerNumber), colorId);
		});

		compound.put("layerColors", layerColors);


		compound.putInt("layerAmount", layerAmount);

		compound.putString("standard", this.currentStandard != null ? this.currentStandard.name() : StandardLayer.SCHOOLING.name());


		return super.write(compound);
	}

	@Override
	public void read(BlockState state, CompoundNBT nbt) {
		this.setLayerAmount(nbt.getInt("layerAmount"));


		if (nbt.contains("layerPositions")) {
			Map<Integer, ArrayList<BlockPos>> layerPos = new HashMap<>();
			CompoundNBT positions = nbt.getCompound("layerPositions");

			for (int i = 1; i <= layerAmount; i++) {
				CompoundNBT layer = positions.getCompound(Integer.toString(i));
				ArrayList<BlockPos> blockPositions = new ArrayList<>();
				for (int j = 0; j < 7; j++) {
					int[] posCords = layer.getIntArray(Integer.toString(j));
					blockPositions.add(new BlockPos(posCords[0], posCords[1], posCords[2]));
				}
				layerPos.put(i, blockPositions);
			}
			this.assignJumpBlocks(layerPos);
		}

		if (nbt.contains("layerColors")) {
			CompoundNBT layerColors = nbt.getCompound("layerColors");
			for (int i = 1; i <= layerAmount; i++) {
				this.layerColors.put(i, layerColors.getInt(Integer.toString(i)));
			}
		}

		if (nbt.contains("layerTypes")) {
			CompoundNBT layerTypes = nbt.getCompound("layerTypes");
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

}
