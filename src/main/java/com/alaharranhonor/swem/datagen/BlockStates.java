package com.alaharranhonor.swem.datagen;


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

import com.alaharranhonor.swem.SWEM;
import com.alaharranhonor.swem.blocks.GrainFeederBlock;
import com.alaharranhonor.swem.blocks.NonParallelBlock;
import com.alaharranhonor.swem.blocks.SlowFeederBlock;
import com.alaharranhonor.swem.blocks.WheelBarrowBlock;
import com.alaharranhonor.swem.util.registry.SWEMBlocks;
import net.minecraft.data.DataGenerator;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.generators.*;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.fml.RegistryObject;

import java.util.List;


public class BlockStates extends BlockStateProvider {


	public BlockStates(DataGenerator gen, ExistingFileHelper exFileHelper) {
		super(gen, SWEM.MOD_ID, exFileHelper);
	}

	@Override
	protected void registerStatesAndModels() {
		this.registerWheelBarrows();
		this.registerSlowFeeders();
		this.registerNonParallelBlock(SWEMBlocks.SEPARATORS);
		this.registerGrainFeeders();
	}

	protected void registerGrainFeeders() {
		String[] models = new String[2];
		models[0] = "block/grain_feeder";
		models[1] = "block/grain_feeder_corner";

		for (RegistryObject<GrainFeederBlock> gf : SWEMBlocks.GRAIN_FEEDERS) {
			GrainFeederBlock feeder = gf.get();

			//itemModels().withExistingParent("item/" + feeder.getName().split("\\.")[2], "item/generated")
			//		.texture("layer0", new ResourceLocation(SWEM.MOD_ID, "items/" + feeder.getName().split("\\.")[2]));

			getVariantBuilder(feeder)
					.forAllStates((state) -> {
						Direction dir = state.getValue(GrainFeederBlock.FACING);
						boolean corner = state.getValue(GrainFeederBlock.LEFT) || state.getValue(GrainFeederBlock.RIGHT);
						ModelFile model = models().getBuilder("grain_feeder_" + (corner ? "corner_" : "") + feeder.getColour().getName())
								.texture("0", new ResourceLocation(SWEM.MOD_ID, "blocks/grain_feeder_" + (corner ? "corner_" : "") + feeder.getColour().getName()))
								.texture("particle", new ResourceLocation(SWEM.MOD_ID, "blocks/grain_feeder_" + (corner ? "corner_" : "") + feeder.getColour().getName()))
								.parent(models().getBuilder(models[corner ? 1 : 0]));

						int originalRotation = dir.getAxis() != Direction.Axis.Y ? ((dir.get2DDataValue() + 2) % 4) * 90 : 0;
						if (state.getValue(GrainFeederBlock.RIGHT)) {
							originalRotation += 90;
							if (originalRotation == 360) originalRotation = 0;
						}
						return ConfiguredModel.builder()
								.modelFile(model)
								.rotationY(originalRotation)
								.build();
					});
		}
	}

	protected void registerNonParallelBlock(List<RegistryObject<NonParallelBlock>> blockArray) {
		String[] models = new String[4];
		models[0] = "block/separator_single";
		models[1] = "block/separator_left";
		models[2] = "block/separator_middle";
		models[3] = "block/separator_right";

		for (RegistryObject<NonParallelBlock> sep : blockArray) {
			NonParallelBlock sepBlock = sep.get();

			itemModels().withExistingParent("item/" + sepBlock.getName().getString().split("\\.")[2], "item/generated")
					.texture("layer0", new ResourceLocation(SWEM.MOD_ID, "items/" + sepBlock.getName().getString().split("\\.")[2]));

			getVariantBuilder(sepBlock)
					.forAllStates((state) -> {
						Direction dir = state.getValue(WheelBarrowBlock.FACING);
						ModelFile model = models().getBuilder( "separator_" + state.getValue(NonParallelBlock.PART).getSerializedName() + "_" + sepBlock.getColour().getName())
								.texture("0", new ResourceLocation(SWEM.MOD_ID, "blocks/separator_" + state.getValue(NonParallelBlock.PART).getSerializedName() + "_" + sepBlock.getColour().getName()))
								.texture("particle", new ResourceLocation(SWEM.MOD_ID, "blocks/separator_" + state.getValue(NonParallelBlock.PART).getSerializedName() + "_" + sepBlock.getColour().getName()))
								.parent(models().getBuilder(models[state.getValue(NonParallelBlock.PART).getId()]));

						return ConfiguredModel.builder()
								.modelFile(model)
								.rotationY(dir.getAxis() != Direction.Axis.Y ? ((dir.get2DDataValue() + 2) % 4) * 90 : 0)
								.build();
					});
		}
	}

	protected void registerWheelBarrows() {
		String[] models = new String[5];
		models[0] = "block/wheel_barrow";
		models[1] = "block/wheel_barrow_level_1";
		models[2] = "block/wheel_barrow_level_2";
		models[3] = "block/wheel_barrow_level_3";
		models[4] = "block/wheel_barrow_full";


		for (RegistryObject<WheelBarrowBlock> wb : SWEMBlocks.WHEEL_BARROWS) {

			WheelBarrowBlock wheel = wb.get();

			itemModels().withExistingParent("item/" + wheel.getName().getString().split("\\.")[2], "item/generated")
					.texture("layer0", new ResourceLocation(SWEM.MOD_ID, "items/" + wheel.getName().getString().split("\\.")[2]));


			getVariantBuilder(wheel)
					.forAllStates((state) -> {
						Direction dir = state.getValue(WheelBarrowBlock.FACING);
						ModelFile model = models().getBuilder(models[state.getValue(WheelBarrowBlock.LEVEL)] + "_" + wheel.getColour().getName())
								.texture("0", new ResourceLocation(SWEM.MOD_ID, "blocks/wheel_barrow_" + wheel.getColour().getName()))
								.texture("particle", new ResourceLocation(SWEM.MOD_ID, "blocks/wheel_barrow_" + wheel.getColour().getName()))
								.parent(models().getBuilder(models[state.getValue(WheelBarrowBlock.LEVEL)]));
						return ConfiguredModel.builder()
								.modelFile(model)
								.rotationY(dir.getAxis() != Direction.Axis.Y ? ((dir.get2DDataValue() + 2) % 4) * 90 : 0)
								.build();
					});
		}


	}

	private void registerSlowFeeders() {
		String[] models = new String[2];
		models[0] = "block/slow_feeder";
		models[1] = "block/slow_feeder_corner";

		for(RegistryObject<SlowFeederBlock> sf : SWEMBlocks.SLOW_FEEDERS) {
			SlowFeederBlock slow = sf.get();

			itemModels().withExistingParent("item/" + slow.getName().getString().split("\\.")[2], "item/generated")
					.texture("layer0", new ResourceLocation(SWEM.MOD_ID, "items/" + slow.getName().getString().split("\\.")[2]));

			ModelFile sfModel = models().getBuilder(models[0]+ "_" + slow.getColour().getName())
					.texture("0", new ResourceLocation(SWEM.MOD_ID, "blocks/" + models[0].split("/")[1] + "_"+slow.getColour().getName()))
					.texture("particle", new ResourceLocation(SWEM.MOD_ID, "blocks/" + models[0].split("/")[1] + "_"+slow.getColour().getName()))
					.parent(models().getBuilder(models[0]));


			ModelFile sfCornerModel = models().getBuilder(models[1]+ "_" + slow.getColour().getName())
					.texture("0", new ResourceLocation(SWEM.MOD_ID, "blocks/" + models[1].split("/")[1] + "_"+slow.getColour().getName()))
					.texture("particle", new ResourceLocation(SWEM.MOD_ID, "blocks/" + models[1].split("/")[1] + "_"+slow.getColour().getName()))
					.parent(models().getBuilder(models[1]));

			MultiPartBlockStateBuilder mp = getMultipartBuilder(slow);
			mp.part().modelFile(models().getExistingFile(new ResourceLocation(SWEM.MOD_ID, "block/full_feeder"))).addModel().condition(SlowFeederBlock.LEVEL, 2);
			mp.part().modelFile(models().getExistingFile(new ResourceLocation(SWEM.MOD_ID, "block/half_feeder"))).addModel().condition(SlowFeederBlock.LEVEL, 1);

			mp.part().modelFile(sfCornerModel).addModel()
					.condition(SlowFeederBlock.NORTH, true)
					.condition(SlowFeederBlock.WEST, true)
					.condition(SlowFeederBlock.SOUTH, false)
					.condition(SlowFeederBlock.EAST, false);

			mp.part().modelFile(sfCornerModel).rotationY(90).addModel()
					.condition(SlowFeederBlock.NORTH, true)
					.condition(SlowFeederBlock.WEST, false)
					.condition(SlowFeederBlock.SOUTH, false)
					.condition(SlowFeederBlock.EAST, true);

			mp.part().modelFile(sfCornerModel).rotationY(180).addModel()
					.condition(SlowFeederBlock.NORTH, false)
					.condition(SlowFeederBlock.WEST, false)
					.condition(SlowFeederBlock.SOUTH, true)
					.condition(SlowFeederBlock.EAST, true);

			mp.part().modelFile(sfCornerModel).rotationY(270).addModel()
					.condition(SlowFeederBlock.NORTH, false)
					.condition(SlowFeederBlock.WEST, true)
					.condition(SlowFeederBlock.SOUTH, true)
					.condition(SlowFeederBlock.EAST, false);

			mp.part().modelFile(sfModel).addModel()
					.condition(SlowFeederBlock.NORTH, false)
					.condition(SlowFeederBlock.WEST, false, true)
					.condition(SlowFeederBlock.SOUTH, false)
					.condition(SlowFeederBlock.EAST, true, false);

			mp.part().modelFile(sfModel).addModel()
					.condition(SlowFeederBlock.NORTH, true, false)
					.condition(SlowFeederBlock.WEST, false)
					.condition(SlowFeederBlock.SOUTH, true, false)
					.condition(SlowFeederBlock.EAST, false);

			mp.part().modelFile(sfModel).addModel()
					.condition(SlowFeederBlock.NORTH, false)
					.condition(SlowFeederBlock.WEST, true, false)
					.condition(SlowFeederBlock.SOUTH, false)
					.condition(SlowFeederBlock.EAST, false);

			mp.part().modelFile(sfModel).addModel()
					.condition(SlowFeederBlock.NORTH, true)
					.condition(SlowFeederBlock.WEST, false)
					.condition(SlowFeederBlock.SOUTH, true)
					.condition(SlowFeederBlock.EAST, true);

			mp.part().modelFile(sfModel).addModel()
					.condition(SlowFeederBlock.NORTH, false)
					.condition(SlowFeederBlock.WEST, true)
					.condition(SlowFeederBlock.SOUTH, true)
					.condition(SlowFeederBlock.EAST, true);

			mp.part().modelFile(sfModel).addModel()
					.condition(SlowFeederBlock.NORTH, true)
					.condition(SlowFeederBlock.WEST, true)
					.condition(SlowFeederBlock.SOUTH, true)
					.condition(SlowFeederBlock.EAST, false);

			mp.part().modelFile(sfModel).addModel()
					.condition(SlowFeederBlock.NORTH, true)
					.condition(SlowFeederBlock.WEST, true)
					.condition(SlowFeederBlock.SOUTH, false)
					.condition(SlowFeederBlock.EAST, true);

			mp.part().modelFile(sfModel).addModel()
					.condition(SlowFeederBlock.NORTH, true)
					.condition(SlowFeederBlock.WEST, true)
					.condition(SlowFeederBlock.SOUTH, true)
					.condition(SlowFeederBlock.EAST, true);

		}

	}
}
