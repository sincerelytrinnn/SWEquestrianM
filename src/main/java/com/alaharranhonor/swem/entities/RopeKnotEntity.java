package com.alaharranhonor.swem.entities;

import com.alaharranhonor.swem.util.RegistryHandler;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.effect.LightningBoltEntity;
import net.minecraft.entity.item.HangingEntity;
import net.minecraft.entity.item.LeashKnotEntity;
import net.minecraft.network.IPacket;
import net.minecraft.network.play.server.SSpawnObjectPacket;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class RopeKnotEntity extends HangingEntity {

	public RopeKnotEntity(EntityType<? extends RopeKnotEntity> entityEntityType, World world) {
		super(entityEntityType, world);
	}

	public RopeKnotEntity(World worldIn, BlockPos hangingPositionIn) {
		super(RegistryHandler.ROPE_KNOT_ENTITY.get(), worldIn, hangingPositionIn);
		this.setPosition((double)hangingPositionIn.getX() + 0.5D, (double)hangingPositionIn.getY() + 0.5D, (double)hangingPositionIn.getZ() + 0.5D);
		float f = 0.125F;
		float f1 = 0.1875F;
		float f2 = 0.25F;
		this.setBoundingBox(new AxisAlignedBB(this.getPosX() - 0.1875D, this.getPosY() - 0.25D + 0.125D, this.getPosZ() - 0.1875D, this.getPosX() + 0.1875D, this.getPosY() + 0.25D + 0.125D, this.getPosZ() + 0.1875D));
		this.forceSpawn = true;
	}



	@Override
	public int getWidthPixels() {
		return 9;
	}

	@Override
	public int getHeightPixels() {
		return 9;
	}

	/**
	 * Called when this entity is broken. Entity parameter may be null.
	 *
	 * @param brokenEntity
	 */
	@Override
	public void onBroken(@Nullable Entity brokenEntity) {

	}

	@Override
	public void playPlaceSound() {

	}

	@Override
	public IPacket<?> createSpawnPacket() {
		return new SSpawnObjectPacket(this, this.getType(), 0, this.getHangingPosition());
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

		RopeKnotEntity ropeKnotEntity = new RopeKnotEntity(world, pos);
		world.addEntity(ropeKnotEntity);
		ropeKnotEntity.playPlaceSound();
		return ropeKnotEntity;
	}
}
