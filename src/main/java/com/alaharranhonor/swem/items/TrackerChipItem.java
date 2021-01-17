package com.alaharranhonor.swem.items;

import com.alaharranhonor.swem.SWEM;
import com.alaharranhonor.swem.entities.SWEMHorseEntityBase;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;

public class TrackerChipItem extends ItemBase {
    @Override
    public ActionResultType itemInteractionForEntity(ItemStack stack, PlayerEntity playerIn, LivingEntity target, Hand hand) {
        if(playerIn.getEntityWorld().isRemote) {
            return ActionResultType.PASS;
        }
        if(target instanceof SWEMHorseEntityBase) {
            if(target.addTag("TRACKED")) {
                SWEM.LOGGER.info("Added TRACKED tag to horse " + target);
                stack.shrink(1);
                return ActionResultType.CONSUME;
            } else {
                SWEM.LOGGER.info("TRACKED Tag already on horse " + target);
                playerIn.sendStatusMessage(ITextComponent.getTextComponentOrEmpty("Horse already has a tracker chip"), false);
                return ActionResultType.FAIL;
            }
        }
        return ActionResultType.FAIL;
    }
}
