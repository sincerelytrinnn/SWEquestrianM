package com.alaharranhonor.swem.armor;

import com.alaharranhonor.swem.SWEM;
import com.alaharranhonor.swem.enchantments.UpstepEnchantment;
import com.alaharranhonor.swem.util.RegistryHandler;
import net.minecraft.block.Blocks;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingEquipmentChangeEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.function.Supplier;

public class GlowRidingBoots extends LeatherRidingBoots {

	private static BlockPos glowBlockPos;
	private static LivingEntity player;
	public GlowRidingBoots(IArmorMaterial materialIn, EquipmentSlotType slot, Properties builderIn, Supplier<Supplier<ArmorBaseModel>> armorModel) {
		super(materialIn, slot, builderIn, armorModel);
	}

	@Override
	public void onCreated(ItemStack stack, World worldIn, PlayerEntity playerIn) {
		super.onCreated(stack, worldIn, playerIn);
	}

	@Mod.EventBusSubscriber(modid = SWEM.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
	public static class GlowRidingBootsEquipped {

		@SubscribeEvent
		public static void onJump(LivingEvent.LivingJumpEvent event) {
			if (player == null) {
				player = event.getEntityLiving();
			}
			if (event.getEntityLiving().getItemStackFromSlot(EquipmentSlotType.FEET).getItem() instanceof GlowRidingBoots) {
				LivingEntity player = event.getEntityLiving();
				World world = player.getEntityWorld();
				if (glowBlockPos == null) {
					glowBlockPos = player.getPosition().up();
					world.setBlockState(glowBlockPos, RegistryHandler.INVISIBLE_GLOW_BLOCK.get().getDefaultState());
				} else if (glowBlockPos != player.getPosition().up()) {
					world.setBlockState(glowBlockPos, Blocks.AIR.getDefaultState());
					glowBlockPos = player.getPosition().up();
					world.setBlockState(glowBlockPos, RegistryHandler.INVISIBLE_GLOW_BLOCK.get().getDefaultState());
				}

			}
		}


		@SubscribeEvent
		public static void onInventoryChange(LivingEquipmentChangeEvent event) {
			if (event.getTo().getItem() instanceof GlowRidingBoots && event.getEntityLiving().equals(player)) {
				glowBlockPos = event.getEntityLiving().getPosition().up();
			}
			if (!(event.getTo().getItem() instanceof GlowRidingBoots) && glowBlockPos != null && event.getEntityLiving().equals(player)) {
				player.getEntityWorld().setBlockState(glowBlockPos, Blocks.AIR.getDefaultState());
				glowBlockPos = null;
			}
		}

	}
}
