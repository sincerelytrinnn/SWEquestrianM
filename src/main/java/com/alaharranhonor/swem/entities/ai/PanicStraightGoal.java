package com.alaharranhonor.swem.entities.ai;

import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.ai.goal.PanicGoal;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3i;

public class PanicStraightGoal extends PanicGoal {
	public PanicStraightGoal(CreatureEntity creature, double speedIn) {
		super(creature, speedIn);
	}

	@Override
	protected boolean findRandomPosition() {
		Direction direction = this.mob.getDirection();
		Vector3i vector3i = direction.getNormal();
		vector3i = vector3i.relative(direction, 5);
		BlockPos currentPos = this.mob.blockPosition();
		BlockPos pos = currentPos.offset(vector3i);
		this.posX = pos.getX();
		this.posY = pos.getY();
		this.posZ = pos.getZ();
		return true;

	}
}
