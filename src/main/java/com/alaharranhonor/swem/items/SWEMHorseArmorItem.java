package com.alaharranhonor.swem.items;

import com.alaharranhonor.swem.SWEM;
import com.alaharranhonor.swem.entities.ISWEMEquipable;
import com.alaharranhonor.swem.util.initialization.SWEMEntities;
import net.minecraft.client.renderer.entity.layers.LeatherHorseArmorLayer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.HorseArmorItem;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import software.bernie.geckolib3.item.GeoArmorItem;

public class SWEMHorseArmorItem extends HorseArmorItem implements IAnimatable {
	public final String type;
	private final AnimationFactory factory = new AnimationFactory(this);

	public final HorseArmorTier tier;


	public SWEMHorseArmorItem(HorseArmorTier tier, int armorValue, String texture, Properties builder) {
		super(armorValue, new ResourceLocation(SWEM.MOD_ID, "textures/entity/horse/armor/" + texture + ".png"), builder);
		this.type = texture;
		this.tier = tier;
	}

	public ActionResultType itemInteractionForEntity(ItemStack stack, PlayerEntity playerIn, LivingEntity target, Hand hand) {
		if (target instanceof ISWEMEquipable && target.isAlive()) {
			ISWEMEquipable iequipable = (ISWEMEquipable)target;
			if (iequipable.func_230264_L__() && iequipable.hasAdventureSaddle()) {
				if (!playerIn.world.isRemote) {
					iequipable.func_230266_a_(SoundCategory.NEUTRAL, stack);
					if (!playerIn.abilities.isCreativeMode)
						stack.shrink(1);
				}

				return ActionResultType.func_233537_a_(playerIn.world.isRemote);
			}
		}
		return ActionResultType.PASS;
	}


	public <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
		System.out.println(event.getAnimatable().getClass().toString());
		return PlayState.CONTINUE;
	}

	@Override
	public void registerControllers(AnimationData animationData) {
		animationData.addAnimationController(new AnimationController(this, "controller", 0, this::predicate));
	}

	@Override
	public AnimationFactory getFactory() {
		return this.factory;
	}


	public static enum HorseArmorTier {
		CLOTH(0),
		IRON(1),
		GOLD(2),
		DIAMOND(3),
		AMETHYST(4);

		private int id;
		HorseArmorTier(int id) {
			this.id = id;
		}

		public int getId() {
			return this.id;
		}
	}
}
