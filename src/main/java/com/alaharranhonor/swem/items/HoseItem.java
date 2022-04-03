
package com.alaharranhonor.swem.items;

import com.alaharranhonor.swem.event.EventFactory;
import com.alaharranhonor.swem.util.registry.SWEMBlocks;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.fluid.FlowingFluid;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.*;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.stats.Stats;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.*;
import net.minecraft.util.math.*;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import javax.annotation.Nullable;

public class HoseItem extends Item {

	/**
	 * Instantiates a new Hose item.
	 *
	 * @param pProperties the p properties
	 */
	public HoseItem(Properties pProperties) {
		super(pProperties);
	}

	/**
	 * Called when this item is used when targetting a Block
	 *
	 * @param pContext
	 */
	@Override
	public ActionResultType useOn(ItemUseContext pContext) {
		BlockState checkState = pContext.getPlayer().level.getBlockState(pContext.getClickedPos().above());
		if (!pContext.getLevel().isClientSide && checkState.getBlock() == SWEMBlocks.HORSE_PEE.get()) {
			for (int i = 0; i < 5; i++) {
				((ServerWorld)pContext.getLevel()).sendParticles(ParticleTypes.SPLASH, pContext.getClickedPos().getX() + Math.random(), pContext.getClickedPos().getY() + 1, pContext.getClickedPos().getZ() + Math.random(), 1, 0, 0, 0, 1);
			}
			pContext.getPlayer().level.setBlock(pContext.getClickedPos().above(), Blocks.AIR.defaultBlockState(), 3);
			return ActionResultType.CONSUME;
		}
		return super.useOn(pContext);
	}
}

