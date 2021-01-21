package com.alaharranhonor.swem.tools;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.material.Material;
import net.minecraft.client.gui.screen.inventory.InventoryScreen;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.CreatureAttribute;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.item.ArmorStandEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.HoeItem;
import net.minecraft.item.IItemTier;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Effects;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class AmethystScythe extends HoeItem {

	private final float attackDamage;
	/** Modifiers applied when the item is in the mainhand of a user. */
	private final Multimap<Attribute, AttributeModifier> attributeModifiers;

	public AmethystScythe(IItemTier tier, int attackDamageIn, float attackSpeedIn, Properties builderIn) {
		super(tier, attackDamageIn, attackSpeedIn, builderIn);
		this.attackDamage = (float)attackDamageIn + tier.getAttackDamage();
		ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
		builder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(ATTACK_DAMAGE_MODIFIER, "Weapon modifier", (double)this.attackDamage, AttributeModifier.Operation.ADDITION));
		builder.put(Attributes.ATTACK_SPEED, new AttributeModifier(ATTACK_SPEED_MODIFIER, "Weapon modifier", (double)attackSpeedIn, AttributeModifier.Operation.ADDITION));
		this.attributeModifiers = builder.build();
	}

	public float getAttackDamage() {
		return this.attackDamage;
	}

	/**
	 * Current implementations of this method in child classes do not use the entry argument beside ev. They just raise
	 * the damage on the stack.
	 */
	public boolean hitEntity(ItemStack stack, LivingEntity target, LivingEntity attacker) {

		if (!(attacker instanceof PlayerEntity)) return false;
		PlayerEntity player = (PlayerEntity) attacker;

		float f = (float)player.getAttributeValue(Attributes.ATTACK_DAMAGE);
		float f1;
		if (target instanceof LivingEntity) {
			f1 = EnchantmentHelper.getModifierForCreature(player.getHeldItemMainhand(), ((LivingEntity)target).getCreatureAttribute());
		} else {
			f1 = EnchantmentHelper.getModifierForCreature(player.getHeldItemMainhand(), CreatureAttribute.UNDEFINED);
		}

		float f2 = player.getCooledAttackStrength(0.5F);
		f = f * (0.2F + f2 * f2 * 0.8F);
		f1 = f1 * f2;

		boolean flag = f2 > 0.9F;

		boolean flag2 = flag && player.fallDistance > 0.0F && !player.isOnGround() && !player.isOnLadder() && !player.isInWater() && !player.isPotionActive(Effects.BLINDNESS) && !player.isPassenger() && target instanceof LivingEntity;
		flag2 = flag2 && !player.isSprinting();
		net.minecraftforge.event.entity.player.CriticalHitEvent hitResult = net.minecraftforge.common.ForgeHooks.getCriticalHit(player, target, flag2, flag2 ? 1.5F : 1.0F);
		flag2 = hitResult != null;
		if (flag2) {
			f *= hitResult.getDamageModifier();
		}

		float f3 = 1.0F + (1.0F - 1.0F / (float)(2 + 1)) * f;

		for(LivingEntity livingentity : player.world.getEntitiesWithinAABB(LivingEntity.class, target.getBoundingBox().grow(1.0D, 0.25D, 1.0D))) {
			if (livingentity != player && livingentity != target && !player.isOnSameTeam(livingentity) && (!(livingentity instanceof ArmorStandEntity) || !((ArmorStandEntity)livingentity).hasMarker()) && player.getDistanceSq(livingentity) < 9.0D) {
				livingentity.applyKnockback(0.4F, (double) MathHelper.sin(player.rotationYaw * ((float)Math.PI / 180F)), (double)(-MathHelper.cos(player.rotationYaw * ((float)Math.PI / 180F))));
				livingentity.attackEntityFrom(DamageSource.causePlayerDamage(player), f3);
			}
		}

		player.world.playSound((PlayerEntity)null, player.getPosX(), player.getPosY(), player.getPosZ(), SoundEvents.ENTITY_PLAYER_ATTACK_SWEEP, player.getSoundCategory(), 1.0F, 1.0F);
		player.spawnSweepParticles();
		stack.damageItem(1, attacker, (entity) -> {
			entity.sendBreakAnimation(EquipmentSlotType.MAINHAND);
		});
		return true;
	}

	/**
	 * Called when a Block is destroyed using this Item. Return true to trigger the "Use Item" statistic.
	 */
	public boolean onBlockDestroyed(ItemStack stack, World worldIn, BlockState state, BlockPos pos, LivingEntity entityLiving) {
		if (!worldIn.isRemote && state.getBlockHardness(worldIn, pos) != 0.0F) {
			stack.damageItem(1, entityLiving, (entity) -> {
				entity.sendBreakAnimation(EquipmentSlotType.MAINHAND);
			});
		}

		return true;
	}

	/**
	 * Gets a map of item attribute modifiers, used by ItemSword to increase hit damage.
	 */
	public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlotType equipmentSlot) {
		return equipmentSlot == EquipmentSlotType.MAINHAND ? this.attributeModifiers : super.getAttributeModifiers(equipmentSlot);
	}


}
