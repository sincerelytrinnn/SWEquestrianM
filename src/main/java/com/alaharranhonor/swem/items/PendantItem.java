package com.alaharranhonor.swem.items;

import net.minecraft.block.SoundType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class PendantItem extends ItemBase {

	public PendantItem() {
		super();
	}

	@Override
	public void inventoryTick(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
		if (entityIn.ticksExisted % 200 == 0) {
			if (worldIn.getRandom().nextInt(5) == 4) {
				BlockPos posToPlay1 = entityIn.getPosition().offset(entityIn.getHorizontalFacing().getOpposite(), 3);
				BlockPos posToPlay2 = entityIn.getPosition().offset(entityIn.getHorizontalFacing().getOpposite(), 2);
				BlockPos posToPlay3 = entityIn.getPosition().offset(entityIn.getHorizontalFacing().getOpposite(), 1);
				SoundType soundType = worldIn.getBlockState(entityIn.getPosition().down()).getSoundType(worldIn, entityIn.getPosition().down(), entityIn);
				worldIn.playSound((PlayerEntity) entityIn, posToPlay1, soundType.getStepSound(), SoundCategory.PLAYERS, 0.20f, soundType.getPitch());

				worldIn.playSound((PlayerEntity) entityIn, posToPlay2, soundType.getStepSound(), SoundCategory.PLAYERS, 0.20f, soundType.getPitch());
				worldIn.playSound((PlayerEntity) entityIn, posToPlay3, soundType.getStepSound(), SoundCategory.PLAYERS, 0.20f, soundType.getPitch());
			}
		}

		super.inventoryTick(stack, worldIn, entityIn, itemSlot, isSelected);
	}
}
