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
		Direction direction = this.creature.getHorizontalFacing();
		Vector3i vector3i = direction.getDirectionVec();
		vector3i = vector3i.offset(direction, 5);
		BlockPos currentPos = this.creature.getPosition();
		BlockPos pos = currentPos.add(vector3i);
		this.randPosX = pos.getX();
		this.randPosY = pos.getY();
		this.randPosZ = pos.getZ();
		return true;

	}
}
