package com.alaharranhonor.swem.items;

import net.minecraft.block.SoundType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class PendantItem extends Item {

	ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(1);

	public PendantItem() {
		super(new Item.Properties());
	}

	@Override
	public void inventoryTick(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
		if (entityIn.tickCount % 200 == 0) {
			if (worldIn.getRandom().nextInt(5) == 4) {
				BlockPos posToPlay1 = entityIn.blockPosition().relative(entityIn.getDirection().getOpposite(), 3);
				BlockPos posToPlay2 = entityIn.blockPosition().relative(entityIn.getDirection().getOpposite(), 2);
				BlockPos posToPlay3 = entityIn.blockPosition().relative(entityIn.getDirection().getOpposite(), 1);
				SoundType soundType = worldIn.getBlockState(entityIn.blockPosition().below()).getSoundType(worldIn, entityIn.blockPosition().below(), entityIn);

				executor.schedule(new Runnable() {
					public void run() {
						worldIn.playSound((PlayerEntity) entityIn, posToPlay1, soundType.getStepSound(), SoundCategory.PLAYERS, 0.20f, soundType.getPitch());
					}
				}, 1, TimeUnit.SECONDS);

				executor.schedule(new Runnable() {
					public void run() {
						worldIn.playSound((PlayerEntity) entityIn, posToPlay2, soundType.getStepSound(), SoundCategory.PLAYERS, 0.20f, soundType.getPitch());
					}
				}, 2, TimeUnit.SECONDS);

				executor.schedule(new Runnable() {
					public void run() {
						worldIn.playSound((PlayerEntity) entityIn, posToPlay3, soundType.getStepSound(), SoundCategory.PLAYERS, 0.20f, soundType.getPitch());
					}
				}, 3, TimeUnit.SECONDS);
			}
		}

		super.inventoryTick(stack, worldIn, entityIn, itemSlot, isSelected);
	}
}
