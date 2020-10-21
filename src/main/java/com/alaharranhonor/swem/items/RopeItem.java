package com.alaharranhonor.swem.items;

import com.alaharranhonor.swem.entities.RopeKnotEntity;
import net.minecraft.block.Block;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.item.LeashKnotEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemUseContext;
import net.minecraft.item.LeadItem;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class RopeItem extends LeadItem {
	public RopeItem(Properties builder) {
		super(builder);
	}

	public ActionResultType onItemUse(ItemUseContext context) {
		World world = context.getWorld();
		BlockPos blockpos = context.getPos();
		Block block = world.getBlockState(blockpos).getBlock();
		if (block.isIn(BlockTags.FENCES)) {
			PlayerEntity playerentity = context.getPlayer();
			if (!world.isRemote && playerentity != null) {
				bindPlayerMobs(playerentity, world, blockpos);
			}

			return ActionResultType.func_233537_a_(world.isRemote);
		} else {
			return ActionResultType.PASS;
		}
	}

	public static ActionResultType bindPlayerMobs(PlayerEntity player, World world, BlockPos pos) {
		RopeKnotEntity ropeKnotEntity = null;
		boolean flag = false;
		double d0 = 7.0D;
		int i = pos.getX();
		int j = pos.getY();
		int k = pos.getZ();

		for(MobEntity mobentity : world.getEntitiesWithinAABB(MobEntity.class, new AxisAlignedBB((double)i - 7.0D, (double)j - 7.0D, (double)k - 7.0D, (double)i + 7.0D, (double)j + 7.0D, (double)k + 7.0D))) {
			if (mobentity.getLeashHolder() == player) {
				if (ropeKnotEntity == null) {
					ropeKnotEntity = RopeKnotEntity.create(world, pos);
				}

				mobentity.setLeashHolder(ropeKnotEntity, true);
				flag = true;
			}
		}

		return flag ? ActionResultType.SUCCESS : ActionResultType.PASS;
	}
}
