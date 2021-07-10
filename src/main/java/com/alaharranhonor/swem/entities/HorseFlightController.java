package com.alaharranhonor.swem.entities;

import com.alaharranhonor.swem.network.HorseFlightPacket;
import com.alaharranhonor.swem.network.SWEMPacketHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.MoverType;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;

import java.util.Scanner;

public class HorseFlightController {

	private SWEMHorseEntityBase horse;

	private BlockPos launchPos;

	public static DataParameter<Boolean> isFloating = EntityDataManager.defineId(SWEMHorseEntityBase.class, DataSerializers.BOOLEAN);
	public static DataParameter<Boolean> isAccelerating = EntityDataManager.defineId(SWEMHorseEntityBase.class, DataSerializers.BOOLEAN);
	public static DataParameter<Boolean> isSlowingDown = EntityDataManager.defineId(SWEMHorseEntityBase.class, DataSerializers.BOOLEAN);
	private int slowingDownCounter;

	public static DataParameter<Boolean> isTurningLeft = EntityDataManager.defineId(SWEMHorseEntityBase.class, DataSerializers.BOOLEAN); // Identifier for playing either turning left or turning right animation
	public static DataParameter<Boolean> isTurning = EntityDataManager.defineId(SWEMHorseEntityBase.class, DataSerializers.BOOLEAN); // if isTurning is set to true, increment the counter.
	public static DataParameter<Boolean> isStillTurning = EntityDataManager.defineId(SWEMHorseEntityBase.class, DataSerializers.BOOLEAN);
	private int turningCounter;


	// Flap cycle
	public static DataParameter<Boolean> didFlap = EntityDataManager.defineId(SWEMHorseEntityBase.class, DataSerializers.BOOLEAN); // Was space pressed after flapCounter reached 20 ticks, reset to false
	private int flapCounter; // first 10 counts add more upwards movement than the last 10 counts;

	public static DataParameter<Boolean> isDiving = EntityDataManager.defineId(SWEMHorseEntityBase.class, DataSerializers.BOOLEAN);




	public HorseFlightController(SWEMHorseEntityBase horse) {
		this.horse = horse;
	}

	public void travel() {
		System.out.println("yRot: " + horse.yRot + " - Look Angle: " + horse.getLookAngle());
		//System.out.println("isClientSide: " + horse.level.isClientSide + " | Movement: " + horse.getDeltaMovement() + " | Look Angle Vec: " + horse.getLookAngle() + " | isFloating: " + horse.getEntityData().get(isFloating));
		if (this.horse.level.isClientSide) {
			this.clientTravel();
		} else {

			if (horse.isOnGround()) {
				horse.setFlying(false);
			}
			//System.out.println("Position: " + horse.blockPosition());
			if (horse.getEntityData().get(isFloating)) {
				horse.setDeltaMovement(horse.getLookAngle().x * 0.25, -0.05, horse.getLookAngle().z * 0.25);
			}

			if (horse.getEntityData().get(isAccelerating)) {
				horse.setDeltaMovement(horse.getLookAngle().x * 0.75, 0, horse.getLookAngle().z * 0.75);
			}

			if (horse.getEntityData().get(isSlowingDown)) {
				slowingDownCounter++;
				double xMove = Math.max(horse.getLookAngle().x * (0.75 / slowingDownCounter * 3.5), horse.getLookAngle().x * 0.25);
				double zMove = Math.max(horse.getLookAngle().z * (0.75 / slowingDownCounter * 3.5), horse.getLookAngle().z * 0.25);
				horse.setDeltaMovement(xMove, 0, zMove);

				if (slowingDownCounter == 20) {
					horse.getEntityData().set(isSlowingDown, false);
				}
			}

			if (horse.getEntityData().get(didFlap)) {
				flapCounter++;

				Vector3d moveVec = horse.getDeltaMovement();



				if (flapCounter == 20) {
					flapCounter = 0;
					horse.getEntityData().set(didFlap, false);
					horse.setDeltaMovement(moveVec.x, 0, moveVec.z);
				} else {
					horse.setDeltaMovement(moveVec.x, 0.5 / flapCounter, moveVec.z);
				}


			}







			// If isFloating is true, add small downards movement, and slight forward movement.
			// For every 5 blocks forward 1 block down.

			// If isTurning, set the horse rotation based on the turning counter; and the direction based on isTurningLeft;

			// If isAccelerating cancel out the downwards movement, and add forward movement.

			// If didFlap, add upwards movement, the first 10 counts adds slightly more upwards movement than the last 10 counts.
			// Increment flapCounter after 20 is reached, reset it.

			// if isDiving and not isAccelerating dive more aggressive and more at a 60 degree angle.

			// if isDiving and isAccelerating dive less aggressive and at 30-45 degree angle



		}
		if (horse.getEntityData().get(isTurning)) {

			int rotInc = horse.getEntityData().get(isTurningLeft) ? -2 : 2;

			horse.setRot(horse.yRot + rotInc, horse.xRot);
			horse.setYBodyRot(horse.yRot);
			horse.setYHeadRot(horse.yBodyRot);

			if (!horse.level.isClientSide) {
				turningCounter++;
				if (turningCounter == 20) {
					horse.getEntityData().set(isTurning, false);
					turningCounter = 0;
				}
			}
		}

		horse.move(MoverType.SELF, horse.getDeltaMovement());

		horse.baseTick();
	};

	private void clientTravel() {

		if (Minecraft.getInstance().options.keyUp.isDown()) { // Move forward

			SWEMPacketHandler.INSTANCE.sendToServer(new HorseFlightPacket(1, horse.getId()));

		} else if (!Minecraft.getInstance().options.keyUp.isDown() && horse.getEntityData().get(isAccelerating)) { // If move forward is checked, but the w key is not held anymore, start the slowing down.
			SWEMPacketHandler.INSTANCE.sendToServer(new HorseFlightPacket(2, horse.getId()));


		}

		if (Minecraft.getInstance().options.keyLeft.isDown()) {
			SWEMPacketHandler.INSTANCE.sendToServer(new HorseFlightPacket(3, horse.getId()));


		} else if (Minecraft.getInstance().options.keyRight.isDown()) {
			SWEMPacketHandler.INSTANCE.sendToServer(new HorseFlightPacket(4, horse.getId()));


		}

		if (Minecraft.getInstance().options.keyJump.consumeClick()) {
			SWEMPacketHandler.INSTANCE.sendToServer(new HorseFlightPacket(5, horse.getId()));


		}

		if (!Minecraft.getInstance().options.keyUp.isDown() && !Minecraft.getInstance().options.keyLeft.isDown() && !Minecraft.getInstance().options.keyRight.isDown() && !horse.getEntityData().get(didFlap) && !horse.getEntityData().get(isSlowingDown)) {
			SWEMPacketHandler.INSTANCE.sendToServer(new HorseFlightPacket(0, horse.getId()));

		}

		// If KEY WE SAY is pressed, set isDiving = true.


	};


	public void launchFlight() {
		this.launchPos = horse.blockPosition();
		horse.setPos(launchPos.getX(), launchPos.getY() + 10, launchPos.getZ());
	}

	public void land() {
		horse.setPos(launchPos.getX(), launchPos.getY(), launchPos.getZ());
	}


}
