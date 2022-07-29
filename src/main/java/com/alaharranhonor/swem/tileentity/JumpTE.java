package com.alaharranhonor.swem.tileentity;

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

import com.alaharranhonor.swem.blocks.jumps.*;
import com.alaharranhonor.swem.util.SWEMUtil;
import com.alaharranhonor.swem.util.registry.SWEMTileEntities;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.item.DyeColor;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JumpTE extends TileEntity {

    public int layerAmount;
    public Map<Integer, JumpLayer> layerTypes = new HashMap<>();
    public Map<Integer, Integer> layerColors = new HashMap<>();
    public StandardLayer currentStandard;
    private Map<Integer, ArrayList<BlockPos>> layerPositions = new HashMap<>();

    /**
     * Instantiates a new Jump te.
     */
    public JumpTE() {
        super(SWEMTileEntities.JUMP_TILE_ENTITY.get());
    }

    /**
     * Gets layer amount.
     *
     * @return the layer amount
     */
    public int getLayerAmount() {
        return this.layerAmount;
    }

    /**
     * Sets layer amount.
     *
     * @param layerAmount the layer amount
     */
    public void setLayerAmount(int layerAmount) {
        this.layerAmount = layerAmount;
    }

    /**
     * Assign jump blocks.
     *
     * @param blocks the blocks
     */
    public void assignJumpBlocks(Map<Integer, ArrayList<BlockPos>> blocks) {
        this.layerPositions = blocks;
    }

    /**
     * Place standards.
     *
     * @param layerNumber the layer number
     * @param standard    the standard
     */
    public void placeStandards(int layerNumber, StandardLayer standard) {
        this.currentStandard = standard;

        if (standard != StandardLayer.NONE) {
            this.level.setBlock(
                    layerPositions.get(layerNumber).get(0),
                    layerNumber == layerAmount
                            ? standard
                            .getTopState()
                            .setValue(
                                    JumpStandardBlock.FACING,
                                    this.getBlockState().getValue(JumpStandardBlock.FACING))
                            : layerNumber == 1
                            ? standard
                            .getBottomState()
                            .setValue(
                                    JumpStandardBlock.FACING,
                                    this.getBlockState()
                                            .getValue(JumpStandardBlock.FACING)
                                            .getClockWise()
                                            .getClockWise())
                            : standard
                            .getMiddleState()
                            .setValue(
                                    JumpStandardBlock.FACING,
                                    this.getBlockState().getValue(JumpStandardBlock.FACING)),
                    3);
            this.level.setBlock(
                    layerPositions.get(layerNumber).get(6),
                    layerNumber == layerAmount
                            ? standard
                            .getTopState()
                            .setValue(
                                    JumpStandardBlock.FACING,
                                    this.getBlockState()
                                            .getValue(JumpStandardBlock.FACING)
                                            .getClockWise()
                                            .getClockWise())
                            : layerNumber == 1
                            ? standard
                            .getBottomState()
                            .setValue(
                                    JumpStandardBlock.FACING,
                                    this.getBlockState()
                                            .getValue(JumpStandardBlock.FACING)
                                            .getClockWise()
                                            .getClockWise())
                            : standard
                            .getMiddleState()
                            .setValue(
                                    JumpStandardBlock.FACING,
                                    this.getBlockState()
                                            .getValue(JumpStandardBlock.FACING)
                                            .getClockWise()
                                            .getClockWise()),
                    3);
        } else {
            this.level.setBlock(layerPositions.get(layerNumber).get(0), standard.getBottomState(), 3);
            this.level.setBlock(layerPositions.get(layerNumber).get(6), standard.getBottomState(), 3);
        }

        JumpTE newTe = this;
        newTe.clearRemoved();
        this.level.addBlockEntity(newTe);
    }

    /**
     * Init standards.
     *
     * @param standard the standard
     */
    public void initStandards(StandardLayer standard) {
        this.currentStandard = standard;
        for (int i = 1; i <= layerAmount; i++) {

            this.level.setBlock(
                    layerPositions.get(i).get(0),
                    i == 1
                            ? standard
                            .getBottomState()
                            .setValue(
                                    JumpStandardBlock.FACING,
                                    this.getBlockState().getValue(JumpControllerBlock.FACING))
                            : i == layerAmount
                            ? standard
                            .getTopState()
                            .setValue(
                                    JumpStandardBlock.FACING,
                                    this.getBlockState().getValue(JumpControllerBlock.FACING))
                            : standard
                            .getMiddleState()
                            .setValue(
                                    JumpStandardBlock.FACING,
                                    this.getBlockState().getValue(JumpControllerBlock.FACING)),
                    3);
            ((JumpPasserTE) this.level.getBlockEntity(layerPositions.get(i).get(0)))
                    .setControllerPos(this.getBlockPos());

            this.level.setBlock(
                    layerPositions.get(i).get(6),
                    i == 1
                            ? standard
                            .getBottomState()
                            .setValue(
                                    JumpStandardBlock.FACING,
                                    this.getBlockState()
                                            .getValue(JumpControllerBlock.FACING)
                                            .getClockWise()
                                            .getClockWise())
                            : i == layerAmount
                            ? standard
                            .getTopState()
                            .setValue(
                                    JumpStandardBlock.FACING,
                                    this.getBlockState()
                                            .getValue(JumpControllerBlock.FACING)
                                            .getClockWise()
                                            .getClockWise())
                            : standard
                            .getMiddleState()
                            .setValue(
                                    JumpStandardBlock.FACING,
                                    this.getBlockState()
                                            .getValue(JumpControllerBlock.FACING)
                                            .getClockWise()
                                            .getClockWise()),
                    3);
            ((JumpPasserTE) this.level.getBlockEntity(layerPositions.get(i).get(6)))
                    .setControllerPos(this.getBlockPos());
        }
    }

    /**
     * Place layer.
     *
     * @param number the number
     * @param layer  the layer
     */
    public void placeLayer(int number, JumpLayer layer) {
        layerTypes.put(number, layer);
        if (!layerColors.containsKey(number)) {
            this.resetColor(number);
        }
        if (this.level != null) {
            for (int i = 1; i < 6; i++) {
                BlockState placeState =
                        i % 3 == 0
                                ? layer
                                .getMiddleState(this.layerColors.get(number))
                                .setValue(
                                        JumpBlock.FACING, this.getBlockState().getValue(JumpStandardBlock.FACING))
                                : i % 2 == 0
                                ? layer
                                .getBetweenState(this.layerColors.get(number))
                                .setValue(
                                        JumpBlock.FACING,
                                        i == 2
                                                ? this.getBlockState().getValue(JumpStandardBlock.FACING)
                                                : this.getBlockState()
                                                .getValue(JumpStandardBlock.FACING)
                                                .getOpposite())
                                : layer
                                .getEndState(this.layerColors.get(number))
                                .setValue(
                                        JumpBlock.FACING,
                                        i == 1
                                                ? this.getBlockState().getValue(JumpStandardBlock.FACING)
                                                : this.getBlockState()
                                                .getValue(JumpStandardBlock.FACING)
                                                .getOpposite());

                // Direction facing = i == 1 ? this.getBlockState().get(JumpStandardBlock.FACING) :
                // this.getBlockState().get(JumpStandardBlock.FACING).getOpposite();
                this.level.setBlock(layerPositions.get(number).get(i), placeState, 1 | 2);
                JumpPasserTE passer =
                        (JumpPasserTE) this.level.getBlockEntity(layerPositions.get(number).get(i));
                if (passer != null) {
                    passer.setControllerPos(this.getBlockPos());
                    passer.setChanged();
                }
            }
        }
    }

    /**
     * Change layer forward.
     *
     * @param layerNumber the layer number
     */
    public void changeLayerForward(int layerNumber) {
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

    /**
     * Change layer backwards.
     *
     * @param layerNumber the layer number
     */
    public void changeLayerBackwards(int layerNumber) {
        List<JumpLayer> applicableLayers = this.getApplicableLayers(layerNumber);
        if (applicableLayers.contains(this.layerTypes.get(layerNumber))) {
            int indexToPick = applicableLayers.indexOf(this.layerTypes.get(layerNumber)) - 1;
            if (indexToPick < 0) {
                indexToPick = applicableLayers.size() - 1;
            }
            this.placeLayer(layerNumber, applicableLayers.get(indexToPick));
        } else {
            this.placeLayer(layerNumber, applicableLayers.get(0));
        }
    }

    /**
     * Change standard forward.
     */
    public void changeStandardForward() {
        int enumId = this.currentStandard.ordinal();
        if (++enumId > StandardLayer.values().length - 1) {
            enumId = 0;
        }
        this.initStandards(StandardLayer.values()[enumId]);
    }

    /**
     * Change standard backwards.
     */
    public void changeStandardBackwards() {
        int enumId = this.currentStandard.ordinal();
        if (--enumId < 0) {
            enumId = StandardLayer.values().length - 1;
        }
        this.initStandards(StandardLayer.values()[enumId]);
    }

    /**
     * Increment color variant.
     *
     * @param layerNumber the layer number
     */
    public void incrementColorVariant(int layerNumber) {
        if (!layerColors.containsKey(layerNumber)) {
            layerColors.put(layerNumber, 0);
        }

        int nextColor = layerColors.get(layerNumber) + 1;
        if (nextColor > SWEMUtil.COLOURS.length - 1) {
            nextColor = 0;
        }
        layerColors.put(layerNumber, nextColor);

        this.placeLayer(layerNumber, this.getLayer(layerNumber));
    }

    /**
     * Decrement color variant.
     *
     * @param layerNumber the layer number
     */
    public void decrementColorVariant(int layerNumber) {
        if (!layerColors.containsKey(layerNumber)) {
            layerColors.put(layerNumber, 0);
        }

        int prevColor = layerColors.get(layerNumber) - 1;
        if (prevColor < 0) {
            prevColor = SWEMUtil.COLOURS.length - 1;
        }
        layerColors.put(layerNumber, prevColor);

        this.placeLayer(layerNumber, this.getLayer(layerNumber));
    }

    /**
     * Reset color.
     *
     * @param layerNumber the layer number
     */
    private void resetColor(int layerNumber) {
        layerColors.put(layerNumber, SWEMUtil.COLOURS[0].getId());
    }

    /**
     * Gets color variant.
     *
     * @param layerNumber the layer number
     * @return the color variant
     */
    public DyeColor getColorVariant(int layerNumber) {
        return SWEMUtil.COLOURS[
                this.layerColors.getOrDefault(layerNumber, SWEMUtil.COLOURS[0].getId())];
    }

    /**
     * Gets layer.
     *
     * @param layerNumber the layer number
     * @return the layer
     */
    public JumpLayer getLayer(int layerNumber) {
        return this.layerTypes.get(layerNumber);
    }

    /**
     * Gets current standard.
     *
     * @return the current standard
     */
    public StandardLayer getCurrentStandard() {
        return this.currentStandard;
    }

    /**
     * Sets current standard.
     *
     * @param standard the standard
     */
    public void setCurrentStandard(StandardLayer standard) {
        this.currentStandard = standard;
    }

    /**
     * Gets applicable layers.
     *
     * @param layerNumber the layer number
     * @return the applicable layers
     */
    public List<JumpLayer> getApplicableLayers(int layerNumber) {
        List<JumpLayer> layers = new ArrayList<>();

        outer:
        for (JumpLayer layer : JumpLayer.values()) {
            if (layer == JumpLayer.AIR) continue;

            if (layer == JumpLayer.NONE) {
                layers.add(layer);
                continue;
            }
            if (layer.getMinHeight() <= this.getLayerAmount()
                    && (layerNumber >= layer.getMinLayer() && layerNumber <= layer.getMaxLayer())
                    || (layerNumber == this.getLayerAmount()
                    && layer.getMaxLayer() == layerNumber + 1
                    && layerNumber + 1 == layer.getMinLayer())) {
                switch (layer) {
                    case STAIR_DROP:
                    case CAVALETTI: {
                        if (this.getCurrentStandard() != StandardLayer.NONE) {
                            continue;
                        }
                        if (!layerTypes.containsKey(2)
                                && !layerTypes.containsKey(3)
                                && !layerTypes.containsKey(4)
                                && !layerTypes.containsKey(5)) {
                            layers.add(layer);
                            continue;
                        }
                    }

                    case LOG:
                    case POLE_ON_BOX_SMALL:
                    case POLE_ON_BOX_LARGE:
                    case HEDGE: {
                        if (!layerTypes.containsKey(2)
                                && !layerTypes.containsKey(3)
                                && !layerTypes.containsKey(4)
                                && !layerTypes.containsKey(5)) {
                            layers.add(layer);
                            continue;
                        }
                    }

                    case WALL:
                    case WALL_MINI: {
                        boolean shouldAdd = true;
                        for (int i = 1; i < layerNumber; i++) {
                            if (layerTypes.get(i) != JumpLayer.WALL) {
                                shouldAdd = false;
                                break;
                            }
                        }
                        if (shouldAdd) {
                            layers.add(layer);
                        }
                        continue;
                    }

                    case BRUSH_BOX:
                    case FLOWER_BOX:
                    case COOP:
                    case ROLL_TOP:

                    case GROUND_POLE: {
                        if (layerTypes.containsKey(2)) {
                            if (!JumpLayer.testForRail(layerTypes.get(2))
                                    && !JumpLayer.testForNone(layerTypes.get(2))) {
                                break;
                            }
                        }
                        if (layerTypes.containsKey(3)) {
                            if (!JumpLayer.testForRail(layerTypes.get(3))
                                    && !JumpLayer.testForNone(layerTypes.get(2))) {
                                break;
                            }
                        }
                        if (layerTypes.containsKey(4)) {
                            if (!JumpLayer.testForRail(layerTypes.get(4))
                                    && !JumpLayer.testForNone(layerTypes.get(2))) {
                                break;
                            }
                        }
                        if (layerTypes.containsKey(5)) {
                            if (!JumpLayer.testForRail(layerTypes.get(5))
                                    && !JumpLayer.testForNone(layerTypes.get(2))) {
                                break;
                            }
                        }
                        layers.add(layer);
                        continue;
                    }

                    case RAIL:
                    case PLANK:
                    case PLANK_FANCY: {
                        // case NUMBERS:
                        // case RED_FLAG:
                        // case WHITE_FLAG:
                        // case RED_WHITE_FLAG: {
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

    /**
     * Delete layer.
     *
     * @param layerNumber the layer number
     */
    public void deleteLayer(int layerNumber) {
        if (layerNumber < 2) return;
        for (int i = 0; i < 7; i++) {
            this.level.setBlock(
                    this.layerPositions.get(layerNumber).get(i), Blocks.AIR.defaultBlockState(), 3);
        }

        if (layerTypes.containsKey(layerNumber)) {
            layerTypes.remove(layerNumber);
        }
        if (layerPositions.containsKey(layerNumber)) {
            layerPositions.remove(layerNumber);
        }
        if (layerColors.containsKey(layerNumber)) {
            layerColors.remove(layerNumber);
        }

        this.layerAmount--;

        if (this.layerAmount > 1) {
            this.placeStandards(this.layerAmount, this.currentStandard);
        }
    }

    /**
     * Add layer.
     *
     * @param layerNumber the layer number
     */
    public void addLayer(int layerNumber) {
        if (layerNumber > 5 || layerNumber < 2) return;

        layerTypes.put(layerNumber, JumpLayer.NONE);
        ArrayList<BlockPos> positions =
                this.layerPositions.getOrDefault(layerNumber, new ArrayList<>());

        for (int i = 0; i < 7; i++) {

            BlockPos newPos = this.layerPositions.get(layerNumber - 1).get(i).offset(0, 1, 0);
            positions.add(newPos);

            if (i == 0 || i == 6) {
                // Standards
                Direction facing =
                        i == 0
                                ? this.getBlockState().getValue(JumpControllerBlock.FACING)
                                : this.getBlockState()
                                .getValue(JumpControllerBlock.FACING)
                                .getClockWise()
                                .getClockWise();

                // Replace the lower standards to a middle piece. If the 2nd layer is added, keep the bottom
                // as the bottom piece.
                if (layerNumber - 1 != 1) {
                    this.level.setBlock(
                            this.layerPositions.get(layerNumber - 1).get(i),
                            this.currentStandard.getMiddleState().setValue(JumpStandardBlock.FACING, facing),
                            1 | 2);
                    ((JumpPasserTE)
                            this.level.getBlockEntity(this.layerPositions.get(layerNumber - 1).get(i)))
                            .setControllerPos(this.getBlockPos());
                }
                // Set the state at the new pos, to the top piece.
                this.level.setBlock(
                        newPos,
                        this.currentStandard.getTopState().setValue(JumpStandardBlock.FACING, facing),
                        1 | 2);
                ((JumpPasserTE) this.level.getBlockEntity(newPos)).setControllerPos(this.getBlockPos());
            }
        }

        layerPositions.put(layerNumber, positions);
        this.placeLayer(layerNumber, JumpLayer.NONE);
        this.layerAmount++;
    }

    @Override
    public void setRemoved() {

        if (this.level != null && !this.level.isClientSide) {
            this.level.setBlock(
                    this.layerPositions.get(1).get(0).relative(Direction.UP, 5),
                    Blocks.AIR.defaultBlockState(),
                    3);
            for (ArrayList<BlockPos> positions : this.layerPositions.values()) {
                for (BlockPos pos : positions) {
                    this.level.setBlock(pos, Blocks.AIR.defaultBlockState(), 3);
                }
            }
            super.setRemoved();
        }
    }

    @Override
    public CompoundNBT save(CompoundNBT compound) {
        CompoundNBT positions = new CompoundNBT();

        layerPositions.forEach(
                (layerNumber, blockPos) -> {
                    CompoundNBT layer = new CompoundNBT();
                    for (int i = 0; i < 7; i++) {
                        BlockPos pos = blockPos.get(i);
                        layer.putIntArray(Integer.toString(i), new int[]{pos.getX(), pos.getY(), pos.getZ()});
                    }

                    positions.put(Integer.toString(layerNumber), layer);
                });

        compound.put("layerPositions", positions);

        CompoundNBT layerTypes = new CompoundNBT();
        this.layerTypes.forEach(
                (layerNumber, jumpType) -> {
                    layerTypes.putString(Integer.toString(layerNumber), jumpType.name());
                });

        compound.put("layerTypes", layerTypes);

        CompoundNBT layerColors = new CompoundNBT();
        this.layerColors.forEach(
                (layerNumber, colorId) -> {
                    layerColors.putInt(Integer.toString(layerNumber), colorId);
                });

        compound.put("layerColors", layerColors);

        compound.putInt("layerAmount", layerAmount);

        compound.putString(
                "standard",
                this.currentStandard != null
                        ? this.currentStandard.name()
                        : StandardLayer.SCHOOLING.name());

        return super.save(compound);
    }

    @Override
    public void load(BlockState state, CompoundNBT nbt) {
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

        if (nbt.contains("standard")) {
            this.currentStandard = StandardLayer.valueOf(nbt.getString("standard"));
        } else {
            this.currentStandard = StandardLayer.SCHOOLING;
        }

        super.load(state, nbt);
    }
}
