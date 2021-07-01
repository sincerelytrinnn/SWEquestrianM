package com.alaharranhonor.swem.event;

import com.alaharranhonor.swem.event.entity.player.FillHoseEvent;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.Event;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class EventFactory {
	@Nullable
	public static ActionResult<ItemStack> onHoseUse(@Nonnull PlayerEntity player, @Nonnull World world, @Nonnull ItemStack stack, @Nullable RayTraceResult target)
	{
		FillHoseEvent event = new FillHoseEvent(player, stack, world, target);
		if (MinecraftForge.EVENT_BUS.post(event)) return new ActionResult<ItemStack>(ActionResultType.FAIL, stack);

		if (event.getResult() == Event.Result.ALLOW)
		{
			if (player.abilities.instabuild)
				return new ActionResult<ItemStack>(ActionResultType.SUCCESS, stack);

			stack.shrink(1);
			if (stack.isEmpty())
				return new ActionResult<ItemStack>(ActionResultType.SUCCESS, event.getFilledHose());

			if (!player.inventory.add(event.getFilledHose()))
				player.drop(event.getFilledHose(), false);

			return new ActionResult<ItemStack>(ActionResultType.SUCCESS, stack);
		}
		return null;
	}
}
