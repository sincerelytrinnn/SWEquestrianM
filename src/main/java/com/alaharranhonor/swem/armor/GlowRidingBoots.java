package com.alaharranhonor.swem.armor;

import com.alaharranhonor.swem.SWEM;
import com.alaharranhonor.swem.util.initialization.SWEMBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingEquipmentChangeEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;


public class GlowRidingBoots extends LeatherRidingBoots {
	public GlowRidingBoots(String path, IArmorMaterial materialIn, EquipmentSlotType slot, Properties builderIn) {
		super(path, materialIn, slot, builderIn);
	}

	@Override
	public void onCreated(ItemStack stack, World worldIn, PlayerEntity playerIn) {
		super.onCreated(stack, worldIn, playerIn);
	}

	@Mod.EventBusSubscriber(modid = SWEM.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
	public static class GlowRidingBootsEquipped {

		@SubscribeEvent
		public static void onJump(LivingEvent.LivingJumpEvent event) {
			if (!(event.getEntityLiving() instanceof PlayerEntity)) return;
			ItemStack stack = event.getEntityLiving().getItemStackFromSlot(EquipmentSlotType.FEET);

			if (stack.getItem() instanceof GlowRidingBoots) {
				PlayerEntity player = (PlayerEntity) event.getEntityLiving();
				World world = player.getEntityWorld();
				CompoundNBT stackData = stack.getOrCreateTag();
				BlockPos glowBlockPos = player.getPosition().up();
				if (!world.getBlockState(glowBlockPos).isAir(world, glowBlockPos)) {
					return;
				}

				if (!stackData.contains("glowBlockPos")) {
					CompoundNBT glowBlockData = new CompoundNBT();
					glowBlockData.putInt("x", glowBlockPos.getX());
					glowBlockData.putInt("y", glowBlockPos.getY());
					glowBlockData.putInt("z", glowBlockPos.getZ());
					stackData.put("glowBlockPos", glowBlockData);
					world.setBlockState(glowBlockPos, SWEMBlocks.INVISIBLE_GLOW_BLOCK.get().getDefaultState(), 3);
				} else {
					CompoundNBT glowBlockData = (CompoundNBT) stackData.get("glowBlockPos");
					BlockPos oldGlowBlockPos = new BlockPos(glowBlockData.getInt("x"), glowBlockData.getInt("y"), glowBlockData.getInt("z"));
					if (!glowBlockPos.equals(oldGlowBlockPos)) {
						world.setBlockState(oldGlowBlockPos, Blocks.AIR.getDefaultState(), 3);
						glowBlockData.putInt("x", glowBlockPos.getX());
						glowBlockData.putInt("y", glowBlockPos.getY());
						glowBlockData.putInt("z", glowBlockPos.getZ());
						world.setBlockState(glowBlockPos, SWEMBlocks.INVISIBLE_GLOW_BLOCK.get().getDefaultState(), 3);
					}

				}
			}
		}


		@SubscribeEvent
		public static void onInventoryChange(LivingEquipmentChangeEvent event) {
			if (!(event.getEntityLiving() instanceof PlayerEntity)) return;

			if ((event.getFrom().getItem() instanceof GlowRidingBoots)) {
				ItemStack stack = event.getFrom();
				CompoundNBT stackData = stack.getTag();
				if (stackData != null) {
					if (stackData.contains("glowBlockPos")) {
						CompoundNBT glowBlockPos = stackData.getCompound("glowBlockPos");
						BlockPos oldGlowBlockPos = new BlockPos(glowBlockPos.getInt("x"), glowBlockPos.getInt("y"), glowBlockPos.getInt("z"));
						event.getEntityLiving().getEntityWorld().setBlockState(oldGlowBlockPos, Blocks.AIR.getDefaultState(), 3);
					}
				}
			}
		}

	}
}
