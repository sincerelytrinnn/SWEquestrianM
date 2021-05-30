package com.alaharranhonor.swem.items;

import com.alaharranhonor.swem.event.EventFactory;
import com.alaharranhonor.swem.util.initialization.SWEMItems;
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

import javax.annotation.Nullable;

public class HoseItem extends Item {
	private final Fluid containedBlock;

	/**
	 * @param supplier A fluid supplier such as {@link net.minecraftforge.fml.RegistryObject<Fluid>}
	 */
	public HoseItem(java.util.function.Supplier<? extends Fluid> supplier, Item.Properties builder) {
		super(builder);
		this.containedBlock = null;
		this.fluidSupplier = supplier;
	}

	/**
	 * Called to trigger the item's "innate" right click behavior. To handle when this item is used on a Block, see
	 * {@link #onItemUse}.
	 */
	public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
		ItemStack itemstack = playerIn.getHeldItem(handIn);
		RayTraceResult raytraceresult = rayTrace(worldIn, playerIn, this.containedBlock == Fluids.EMPTY ? RayTraceContext.FluidMode.SOURCE_ONLY : RayTraceContext.FluidMode.NONE);
		ActionResult<ItemStack> ret = EventFactory.onHoseUse(playerIn, worldIn, itemstack, raytraceresult);
		if (ret != null) return ret;
		if (raytraceresult.getType() == RayTraceResult.Type.MISS) {
			return ActionResult.resultPass(itemstack);
		} else if (raytraceresult.getType() != RayTraceResult.Type.BLOCK) {
			return ActionResult.resultPass(itemstack);
		} else {
			BlockRayTraceResult blockraytraceresult = (BlockRayTraceResult)raytraceresult;
			BlockPos blockpos = blockraytraceresult.getPos();
			Direction direction = blockraytraceresult.getFace();
			BlockPos blockpos1 = blockpos.offset(direction);
			if (worldIn.isBlockModifiable(playerIn, blockpos) && playerIn.canPlayerEdit(blockpos1, direction, itemstack)) {
				if (this.containedBlock == Fluids.EMPTY) {
					BlockState blockstate1 = worldIn.getBlockState(blockpos);
					if (blockstate1.getBlock() instanceof IBucketPickupHandler) {
						Fluid fluid = ((IBucketPickupHandler)blockstate1.getBlock()).pickupFluid(worldIn, blockpos, blockstate1);
						if (fluid != Fluids.EMPTY) {
							playerIn.addStat(Stats.ITEM_USED.get(this));

							SoundEvent soundevent = this.containedBlock.getAttributes().getFillSound();
							if (soundevent == null) soundevent = SoundEvents.ITEM_BUCKET_FILL;
							playerIn.playSound(soundevent, 1.0F, 1.0F);
							ItemStack itemstack1 = DrinkHelper.fill(itemstack, playerIn, new ItemStack(this.getFilledHose()));
							if (!worldIn.isRemote) {
								CriteriaTriggers.FILLED_BUCKET.trigger((ServerPlayerEntity)playerIn, new ItemStack(this.getFilledHose()));
							}

							return ActionResult.func_233538_a_(itemstack1, worldIn.isRemote());
						}
					}

					return ActionResult.resultFail(itemstack);
				} else {
					BlockState blockstate = worldIn.getBlockState(blockpos);
					BlockPos blockpos2 = canBlockContainFluid(worldIn, blockpos, blockstate) ? blockpos : blockpos1;
					if (this.tryPlaceContainedLiquid(playerIn, worldIn, blockpos2, blockraytraceresult)) {
						this.onLiquidPlaced(worldIn, itemstack, blockpos2);
						if (playerIn instanceof ServerPlayerEntity) {
							CriteriaTriggers.PLACED_BLOCK.trigger((ServerPlayerEntity)playerIn, blockpos2, itemstack);
						}

						playerIn.addStat(Stats.ITEM_USED.get(this));
						return ActionResult.func_233538_a_(this.emptyBucket(itemstack, playerIn), worldIn.isRemote());
					} else {
						return ActionResult.resultFail(itemstack);
					}
				}
			} else {
				return ActionResult.resultFail(itemstack);
			}
		}
	}

	@Override
	public void inventoryTick(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
		super.inventoryTick(stack, worldIn, entityIn, itemSlot, isSelected);
	}

	public ItemStack emptyBucket(ItemStack stack, PlayerEntity player) {
		return !player.abilities.isCreativeMode ? new ItemStack(SWEMItems.HOSE.get()) : stack;
	}

	public void onLiquidPlaced(World worldIn, ItemStack p_203792_2_, BlockPos pos) {
	}

	private Item getFilledHose() {
		return SWEMItems.HOSE_WATER.get();
	}

	public boolean tryPlaceContainedLiquid(@Nullable PlayerEntity player, World worldIn, BlockPos posIn, @Nullable BlockRayTraceResult rayTrace) {
		if (!(this.containedBlock instanceof FlowingFluid)) {
			return false;
		} else {
			BlockState blockstate = worldIn.getBlockState(posIn);
			Block block = blockstate.getBlock();
			Material material = blockstate.getMaterial();
			boolean flag = blockstate.isReplaceable(this.containedBlock);
			boolean flag1 = blockstate.isAir() || flag || block instanceof ILiquidContainer && ((ILiquidContainer)block).canContainFluid(worldIn, posIn, blockstate, this.containedBlock);
			if (!flag1) {
				return rayTrace != null && this.tryPlaceContainedLiquid(player, worldIn, rayTrace.getPos().offset(rayTrace.getFace()), (BlockRayTraceResult)null);
			} else if (worldIn.getDimensionType().isUltrawarm() && this.containedBlock.isIn(FluidTags.WATER)) {
				int i = posIn.getX();
				int j = posIn.getY();
				int k = posIn.getZ();
				worldIn.playSound(player, posIn, SoundEvents.BLOCK_FIRE_EXTINGUISH, SoundCategory.BLOCKS, 0.5F, 2.6F + (worldIn.rand.nextFloat() - worldIn.rand.nextFloat()) * 0.8F);

				for(int l = 0; l < 8; ++l) {
					worldIn.addParticle(ParticleTypes.LARGE_SMOKE, (double)i + Math.random(), (double)j + Math.random(), (double)k + Math.random(), 0.0D, 0.0D, 0.0D);
				}

				return true;
			} else if (block instanceof ILiquidContainer && ((ILiquidContainer)block).canContainFluid(worldIn,posIn,blockstate,containedBlock)) {
				((ILiquidContainer)block).receiveFluid(worldIn, posIn, blockstate, ((FlowingFluid)this.containedBlock).getStillFluidState(false));
				this.playEmptySound(player, worldIn, posIn);
				return true;
			} else {
				if (!worldIn.isRemote && flag && !material.isLiquid()) {
					worldIn.destroyBlock(posIn, true);
				}

				if (!worldIn.setBlockState(posIn, this.containedBlock.getDefaultState().getBlockState(), 11) && !blockstate.getFluidState().isSource()) {
					return false;
				} else {
					this.playEmptySound(player, worldIn, posIn);
					return true;
				}
			}
		}
	}

	protected void playEmptySound(@Nullable PlayerEntity player, IWorld worldIn, BlockPos pos) {
		SoundEvent soundevent = this.containedBlock.getAttributes().getEmptySound();
		if(soundevent == null) soundevent = SoundEvents.ITEM_BUCKET_EMPTY;
		worldIn.playSound(player, pos, soundevent, SoundCategory.BLOCKS, 1.0F, 1.0F);
	}

	@Override
	public net.minecraftforge.common.capabilities.ICapabilityProvider initCapabilities(ItemStack stack, @Nullable net.minecraft.nbt.CompoundNBT nbt) {
		if (this.getClass() == HoseItem.class)
			return new net.minecraftforge.fluids.capability.wrappers.FluidBucketWrapper(stack);
		else
			return super.initCapabilities(stack, nbt);
	}

	private final java.util.function.Supplier<? extends Fluid> fluidSupplier;
	public Fluid getFluid() { return fluidSupplier.get(); }

	private boolean canBlockContainFluid(World worldIn, BlockPos posIn, BlockState blockstate)
	{
		return blockstate.getBlock() instanceof ILiquidContainer && ((ILiquidContainer)blockstate.getBlock()).canContainFluid(worldIn, posIn, blockstate, this.containedBlock);
	}
}
