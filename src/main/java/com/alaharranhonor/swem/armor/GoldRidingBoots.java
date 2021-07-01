package com.alaharranhonor.swem.armor;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.FlowingFluidBlock;
import net.minecraft.block.material.Material;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.world.World;


import net.minecraft.item.Item.Properties;

public class GoldRidingBoots extends IronRidingBoots {
	public GoldRidingBoots(String path, IArmorMaterial materialIn, EquipmentSlotType slot, Properties builderIn) {
		super(path, materialIn, slot, builderIn);
	}

	@Override
	public void onCraftedBy(ItemStack stack, World worldIn, PlayerEntity playerIn) {
		super.onCraftedBy(stack, worldIn, playerIn);
	}

	@Override
	public boolean makesPiglinsNeutral(ItemStack stack, LivingEntity wearer) {
		return true;
	}

	/**
	 * Called to tick armor in the armor slot. Override to do something
	 *
	 * @param stack
	 * @param world
	 * @param player
	 */
	@Override
	public void onArmorTick(ItemStack stack, World world, PlayerEntity player) {
		if (player.isOnGround()) {
			BlockState blockstate = Blocks.FROSTED_ICE.defaultBlockState();
			float f = (float)Math.min(16, 3);
			BlockPos.Mutable blockpos$mutable = new BlockPos.Mutable();

			BlockPos pos = player.blockPosition();

			for(BlockPos blockpos : BlockPos.betweenClosed(pos.offset(-f, -1.0D, -f), pos.offset(f, -1.0D, f))) {
				if (blockpos.closerThan(player.position(), (double)f)) {
					blockpos$mutable.set(blockpos.getX(), blockpos.getY() + 1, blockpos.getZ());
					BlockState blockstate1 = world.getBlockState(blockpos$mutable);
					if (blockstate1.isAir(world, blockpos$mutable)) {
						BlockState blockstate2 = world.getBlockState(blockpos);
						boolean isFull = blockstate2.getBlock() == Blocks.WATER && blockstate2.getValue(FlowingFluidBlock.LEVEL) == 0; //TODO: Forge, modded waters?
						if (blockstate2.getMaterial() == Material.WATER && isFull && blockstate.canSurvive(world, blockpos) && world.isUnobstructed(blockstate, blockpos, ISelectionContext.empty()) && !net.minecraftforge.event.ForgeEventFactory.onBlockPlace(player, net.minecraftforge.common.util.BlockSnapshot.create(world.dimension(), world, blockpos), net.minecraft.util.Direction.UP)) {
							world.setBlock(blockpos, blockstate, 3);
							world.getBlockTicks().scheduleTick(blockpos, Blocks.FROSTED_ICE, 20);
						}
					}
				}
			}

		}
	}
}
