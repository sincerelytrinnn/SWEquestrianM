package com.alaharranhonor.swem.datagen;

import com.alaharranhonor.swem.SWEM;
import com.alaharranhonor.swem.blocks.GrainFeederBlock;
import com.alaharranhonor.swem.blocks.NonParallelBlock;
import com.alaharranhonor.swem.blocks.SlowFeederBlock;
import com.alaharranhonor.swem.blocks.WheelBarrowBlock;
import com.alaharranhonor.swem.util.initialization.SWEMBlocks;
import net.minecraft.data.DataGenerator;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.generators.*;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.fml.RegistryObject;

import java.util.ArrayList;
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

			//itemModels().withExistingParent("item/" + feeder.getTranslationKey().split("\\.")[2], "item/generated")
			//		.texture("layer0", new ResourceLocation(SWEM.MOD_ID, "items/" + feeder.getTranslationKey().split("\\.")[2]));

			getVariantBuilder(feeder)
					.forAllStates((state) -> {
						Direction dir = state.get(GrainFeederBlock.HORIZONTAL_FACING);
						boolean corner = state.get(GrainFeederBlock.LEFT) || state.get(GrainFeederBlock.RIGHT);
						ModelFile model = models().getBuilder("grain_feeder_" + (corner ? "corner_" : "") + feeder.getColour().getTranslationKey())
								.texture("0", new ResourceLocation(SWEM.MOD_ID, "blocks/grain_feeder_" + (corner ? "corner_" : "") + feeder.getColour().getTranslationKey()))
								.texture("particle", new ResourceLocation(SWEM.MOD_ID, "blocks/grain_feeder_" + (corner ? "corner_" : "") + feeder.getColour().getTranslationKey()))
								.parent(models().getBuilder(models[corner ? 1 : 0]));

						int originalRotation = dir.getAxis() != Direction.Axis.Y ? ((dir.getHorizontalIndex() + 2) % 4) * 90 : 0;
						return ConfiguredModel.builder()
								.modelFile(model)
								.rotationY(state.get(GrainFeederBlock.RIGHT) ? 90 + originalRotation : originalRotation)
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

			itemModels().withExistingParent("item/" + sepBlock.getTranslationKey().split("\\.")[2], "item/generated")
					.texture("layer0", new ResourceLocation(SWEM.MOD_ID, "items/" + sepBlock.getTranslationKey().split("\\.")[2]));

			getVariantBuilder(sepBlock)
					.forAllStates((state) -> {
						Direction dir = state.get(WheelBarrowBlock.HORIZONTAL_FACING);
						ModelFile model = models().getBuilder( "separator_" + state.get(NonParallelBlock.PART).getString() + "_" + sepBlock.getColour().getTranslationKey())
								.texture("0", new ResourceLocation(SWEM.MOD_ID, "blocks/separator_" + state.get(NonParallelBlock.PART).getString() + "_" + sepBlock.getColour().getTranslationKey()))
								.texture("particle", new ResourceLocation(SWEM.MOD_ID, "blocks/separator_" + state.get(NonParallelBlock.PART).getString() + "_" + sepBlock.getColour().getTranslationKey()))
								.parent(models().getBuilder(models[state.get(NonParallelBlock.PART).getId()]));

						return ConfiguredModel.builder()
								.modelFile(model)
								.rotationY(dir.getAxis() != Direction.Axis.Y ? ((dir.getHorizontalIndex() + 2) % 4) * 90 : 0)
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

			itemModels().withExistingParent("item/" + wheel.getTranslationKey().split("\\.")[2], "item/generated")
					.texture("layer0", new ResourceLocation(SWEM.MOD_ID, "items/" + wheel.getTranslationKey().split("\\.")[2]));


			getVariantBuilder(wheel)
					.forAllStates((state) -> {
						Direction dir = state.get(WheelBarrowBlock.HORIZONTAL_FACING);
						ModelFile model = models().getBuilder(models[state.get(WheelBarrowBlock.LEVEL)] + "_" + wheel.getColour().getTranslationKey())
								.texture("0", new ResourceLocation(SWEM.MOD_ID, "blocks/wheel_barrow_" + wheel.getColour().getTranslationKey()))
								.texture("particle", new ResourceLocation(SWEM.MOD_ID, "blocks/wheel_barrow_" + wheel.getColour().getTranslationKey()))
								.parent(models().getBuilder(models[state.get(WheelBarrowBlock.LEVEL)]));
						return ConfiguredModel.builder()
								.modelFile(model)
								.rotationY(dir.getAxis() != Direction.Axis.Y ? ((dir.getHorizontalIndex() + 2) % 4) * 90 : 0)
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

			itemModels().withExistingParent("item/" + slow.getTranslationKey().split("\\.")[2], "item/generated")
					.texture("layer0", new ResourceLocation(SWEM.MOD_ID, "items/" + slow.getTranslationKey().split("\\.")[2]));

			ModelFile sfModel = models().getBuilder(models[0]+ "_" + slow.getColour().getTranslationKey())
					.texture("0", new ResourceLocation(SWEM.MOD_ID, "blocks/" + models[0].split("/")[1] + "_"+slow.getColour().getTranslationKey()))
					.texture("particle", new ResourceLocation(SWEM.MOD_ID, "blocks/" + models[0].split("/")[1] + "_"+slow.getColour().getTranslationKey()))
					.parent(models().getBuilder(models[0]));


			ModelFile sfCornerModel = models().getBuilder(models[1]+ "_" + slow.getColour().getTranslationKey())
					.texture("0", new ResourceLocation(SWEM.MOD_ID, "blocks/" + models[1].split("/")[1] + "_"+slow.getColour().getTranslationKey()))
					.texture("particle", new ResourceLocation(SWEM.MOD_ID, "blocks/" + models[1].split("/")[1] + "_"+slow.getColour().getTranslationKey()))
					.parent(models().getBuilder(models[1]));

			MultiPartBlockStateBuilder mp = getMultipartBuilder(slow);
			mp.part().modelFile(models().getExistingFile(new ResourceLocation(SWEM.MOD_ID, "block/full_feeder"))).addModel().condition(SlowFeederBlock.LEVEL, 2);
			mp.part().modelFile(models().getExistingFile(new ResourceLocation(SWEM.MOD_ID, "block/half_feeder"))).addModel().condition(SlowFeederBlock.LEVEL, 1);
			mp.part().modelFile(models().getExistingFile(new ResourceLocation(SWEM.MOD_ID, "block/full_feeder_vanilla"))).addModel().condition(SlowFeederBlock.LEVEL_VANILLA, 2);
			mp.part().modelFile(models().getExistingFile(new ResourceLocation(SWEM.MOD_ID, "block/half_feeder_vanilla"))).addModel().condition(SlowFeederBlock.LEVEL_VANILLA, 1);

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
