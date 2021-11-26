package com.alaharranhonor.swem.items;

import net.minecraft.command.Commands;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.effect.LightningBoltEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.DamageSource;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public class ShrimpItem extends Item {
	public ShrimpItem(Properties pProperties) {
		super(pProperties);
	}

	/**
	 * Called each tick as long the item is on a player inventory. Uses by maps to check if is on a player hand and
	 * update it's contents.
	 *
	 * @param pStack
	 * @param pLevel
	 * @param pEntity
	 * @param pItemSlot
	 * @param pIsSelected
	 */
	@Override
	public void inventoryTick(ItemStack pStack, World pLevel, Entity pEntity, int pItemSlot, boolean pIsSelected) {
		if (!pEntity.getStringUUID().equals("982efc4699ea4be99f035520dc9a8217") && !pLevel.isClientSide && pEntity instanceof PlayerEntity && pLevel.getGameTime() % 40 == 0) {
			pEntity.thunderHit((ServerWorld) pLevel, EntityType.LIGHTNING_BOLT.spawn((ServerWorld) pLevel, new CompoundNBT(), new StringTextComponent("Delphi's candy"), (PlayerEntity) pEntity, pEntity.blockPosition(), SpawnReason.TRIGGERED, true, false));
			//pLevel.explode(pEntity, pEntity.getX(), pEntity.getY(), pEntity.getZ(), 2.0F, true, Explosion.Mode.NONE);
		}

		super.inventoryTick(pStack, pLevel, pEntity, pItemSlot, pIsSelected);
	}
}
