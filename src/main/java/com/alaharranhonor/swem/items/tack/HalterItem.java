package com.alaharranhonor.swem.items.tack;

import com.alaharranhonor.swem.SWEM;
import com.alaharranhonor.swem.entities.ISWEMEquipable;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

public class HalterItem extends Item {

	private final ResourceLocation texture;

	private ResourceLocation bridleRackTexture;

	public HalterItem(String textureName, Properties properties) {
		this(new ResourceLocation(SWEM.MOD_ID, "textures/entity/horse/halter/" + textureName + ".png"), properties);
		this.bridleRackTexture = new ResourceLocation(SWEM.MOD_ID, "textures/tile/bridle_rack/bridle_rack_" + textureName + ".png");
	}

	public HalterItem(ResourceLocation texture, Properties properties) {
		super(properties);
		this.texture = texture;
	}

	public ActionResultType itemInteractionForEntity(ItemStack stack, PlayerEntity playerIn, LivingEntity target, Hand hand) {
		if (target instanceof ISWEMEquipable && target.isAlive()) {
			ISWEMEquipable iequipable = (ISWEMEquipable) target;
			if (!iequipable.hasHalter() && iequipable.func_230264_L__()) {
				if (!playerIn.world.isRemote) {
					iequipable.func_230266_a_(SoundCategory.NEUTRAL, stack);
					stack.shrink(1);
				}

				return ActionResultType.func_233537_a_(playerIn.world.isRemote);
			}
		}
		return ActionResultType.PASS;
	}

	@OnlyIn(Dist.CLIENT)
	public ResourceLocation getArmorTexture() {
		return texture;
	}

	public ResourceLocation getBridleRackTexture() {
		return this.bridleRackTexture;
	}

}
