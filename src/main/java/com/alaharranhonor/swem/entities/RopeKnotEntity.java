package com.alaharranhonor.swem.entities;

import com.alaharranhonor.swem.util.RegistryHandler;
import com.alaharranhonor.swem.util.initialization.SWEMEntities;
import net.minecraft.entity.*;
import net.minecraft.entity.item.HangingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.network.play.server.SSpawnObjectPacket;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.List;

public class RopeKnotEntity extends HangingEntity {

	public RopeKnotEntity(EntityType<? extends RopeKnotEntity> p_i50223_1_, World world) {
		super(p_i50223_1_, world);
	}

	public RopeKnotEntity(World worldIn, BlockPos hangingPositionIn) {
		super(SWEMEntities.ROPE_KNOT_ENTITY.get(), worldIn, hangingPositionIn);
		this.setPosition((double)hangingPositionIn.getX() + 0.5D, (double)hangingPositionIn.getY() + 0.5D, (double)hangingPositionIn.getZ() + 0.5D);
		float f = 0.125F;
		float f1 = 0.1875F;
		float f2 = 0.25F;
		this.setBoundingBox(new AxisAlignedBB(this.getPosX() - 0.1875D, this.getPosY() - 0.25D + 0.125D, this.getPosZ() - 0.1875D, this.getPosX() + 0.1875D, this.getPosY() + 0.25D + 0.125D, this.getPosZ() + 0.1875D));
		this.forceSpawn = true;
	}

	/**
	 * Sets the x,y,z of the entity from the given parameters. Also seems to set up a bounding box.
	 */
	public void setPosition(double x, double y, double z) {
		super.setPosition((double)MathHelper.floor(x) + 0.5D, (double)MathHelper.floor(y) + 0.5D, (double)MathHelper.floor(z) + 0.5D);
	}

	/**
	 * Updates the entity bounding box based on current facing
	 */
	protected void updateBoundingBox() {
		this.setRawPosition((double)this.hangingPosition.getX() + 0.5D, (double)this.hangingPosition.getY() + 0.5D, (double)this.hangingPosition.getZ() + 0.5D);
		if (this.isAddedToWorld() && this.world instanceof net.minecraft.world.server.ServerWorld) ((net.minecraft.world.server.ServerWorld)this.world).chunkCheck(this); // Forge - Process chunk registration after moving.
	}

	/**
	 * Updates facing and bounding box based on it
	 */
	public void updateFacingWithBoundingBox(Direction facingDirectionIn) {
	}

	public int getWidthPixels() {
		return 9;
	}

	public int getHeightPixels() {
		return 9;
	}

	protected float getEyeHeight(Pose poseIn, EntitySize sizeIn) {
		return -0.0625F;
	}

	/**
	 * Checks if the entity is in range to render.
	 */
	@OnlyIn(Dist.CLIENT)
	public boolean isInRangeToRenderDist(double distance) {
		return distance < 1024.0D;
	}

	/**
	 * Called when this entity is broken. Entity parameter may be null.
	 */
	public void onBroken(@Nullable Entity brokenEntity) {
		this.playSound(SoundEvents.ENTITY_LEASH_KNOT_BREAK, 1.0F, 1.0F);
	}

	public void writeAdditional(CompoundNBT compound) {
	}

	/**
	 * (abstract) Protected helper method to read subclass entity data from NBT.
	 */
	public void readAdditional(CompoundNBT compound) {
	}

	public ActionResultType processInitialInteract(PlayerEntity player, Hand hand) {
		if (this.world.isRemote) {
			return ActionResultType.SUCCESS;
		} else {
			boolean flag = false;
			double d0 = 7.0D;
			List<MobEntity> list = this.world.getEntitiesWithinAABB(MobEntity.class, new AxisAlignedBB(this.getPosX() - 7.0D, this.getPosY() - 7.0D, this.getPosZ() - 7.0D, this.getPosX() + 7.0D, this.getPosY() + 7.0D, this.getPosZ() + 7.0D));

			for(MobEntity mobentity : list) {
				if (mobentity.getLeashHolder() == player) {
					mobentity.setLeashHolder(this, true);
					flag = true;
				}
			}

			if (!flag) {
				this.remove();
				if (player.abilities.isCreativeMode) {
					for(MobEntity mobentity1 : list) {
						if (mobentity1.getLeashed() && mobentity1.getLeashHolder() == this) {
							mobentity1.clearLeashed(true, false);
						}
					}
				}
			}

			return ActionResultType.CONSUME;
		}
	}

	/**
	 * checks to make sure painting can be placed there
	 */
	public boolean onValidSurface() {
		return this.world.getBlockState(this.hangingPosition).getBlock().isIn(BlockTags.FENCES);
	}

	public static RopeKnotEntity create(World world, BlockPos pos) {
		int i = pos.getX();
		int j = pos.getY();
		int k = pos.getZ();

		for(RopeKnotEntity ropeKnotEntity : world.getEntitiesWithinAABB(RopeKnotEntity.class, new AxisAlignedBB((double)i - 1.0D, (double)j - 1.0D, (double)k - 1.0D, (double)i + 1.0D, (double)j + 1.0D, (double)k + 1.0D))) {
			if (ropeKnotEntity.getHangingPosition().equals(pos)) {
				return ropeKnotEntity;
			}
		}

		RopeKnotEntity ropeKnotEntity1 = new RopeKnotEntity(world, pos);
		world.addEntity(ropeKnotEntity1);
		ropeKnotEntity1.playPlaceSound();
		return ropeKnotEntity1;
	}

	public void playPlaceSound() {
		this.playSound(SoundEvents.ENTITY_LEASH_KNOT_PLACE, 1.0F, 1.0F);
	}

	public IPacket<?> createSpawnPacket() {
		return new SSpawnObjectPacket(this, this.getType(), 0, this.getHangingPosition());
	}

	@OnlyIn(Dist.CLIENT)
	public Vector3d getLeashPosition(float partialTicks) {
		return this.func_242282_l(partialTicks).add(0.0D, 0.2D, 0.0D);
	}
}
