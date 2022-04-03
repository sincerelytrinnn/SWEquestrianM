package com.alaharranhonor.swem.entities;


/*
 * All Rights Reserved
 *
 * Copyright (c) 2021, AlaharranHonor, Legenden.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

import com.alaharranhonor.swem.network.HorseFlightPacket;
import com.alaharranhonor.swem.network.SWEMPacketHandler;
import com.alaharranhonor.swem.util.ClientEventHandlers;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.MoverType;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;

import java.util.Scanner;


public class HorseFlightController {

	private final SWEMHorseEntityBase horse;

	private BlockPos launchPos;

	public static DataParameter<Boolean> isLaunching = EntityDataManager.defineId(SWEMHorseEntityBase.class, DataSerializers.BOOLEAN);
	private int launchCounter;
	public static DataParameter<Boolean> isFloating = EntityDataManager.defineId(SWEMHorseEntityBase.class, DataSerializers.BOOLEAN);
	public static DataParameter<Boolean> isAccelerating = EntityDataManager.defineId(SWEMHorseEntityBase.class, DataSerializers.BOOLEAN);
	public static DataParameter<Boolean> isSlowingDown = EntityDataManager.defineId(SWEMHorseEntityBase.class, DataSerializers.BOOLEAN);
	public static DataParameter<Boolean> isStillSlowingDown = EntityDataManager.defineId(SWEMHorseEntityBase.class, DataSerializers.BOOLEAN);
	private int slowingDownCounter;

	public static DataParameter<Boolean> isTurningLeft = EntityDataManager.defineId(SWEMHorseEntityBase.class, DataSerializers.BOOLEAN); // Identifier for playing either turning left or turning right animation
	public static DataParameter<Boolean> isTurning = EntityDataManager.defineId(SWEMHorseEntityBase.class, DataSerializers.BOOLEAN); // if isTurning is set to true, increment the counter.
	public static DataParameter<Boolean> isStillTurning = EntityDataManager.defineId(SWEMHorseEntityBase.class, DataSerializers.BOOLEAN);
	private int turningCounter;


	// Flap cycle
	public static DataParameter<Boolean> didFlap = EntityDataManager.defineId(SWEMHorseEntityBase.class, DataSerializers.BOOLEAN); // Was space pressed after flapCounter reached 20 ticks, reset to false
	private int flapCounter; // first 10 counts add more upwards movement than the last 10 counts;

	public static DataParameter<Boolean> isDiving = EntityDataManager.defineId(SWEMHorseEntityBase.class, DataSerializers.BOOLEAN);


	/**
	 * Instantiates a new Horse flight controller.
	 *
	 * @param horse the horse
	 */
	public HorseFlightController(SWEMHorseEntityBase horse) {
		this.horse = horse;
	}

	/**
	 * Travel.
	 */
	public void travel() {
		//System.out.println("yRot: " + horse.yRot + " - Look Angle: " + horse.getLookAngle());
		//System.out.println("isClientSide: " + horse.level.isClientSide + " | Movement: " + horse.getDeltaMovement() + " | Look Angle Vec: " + horse.getLookAngle() + " | isFloating: " + horse.getEntityData().get(isFloating));
		if (this.horse.level.isClientSide) {
			this.clientTravel();
		} else {


			/*String sb = new StringBuilder()
					.append("isFloating: " + horse.getEntityData().get(isFloating)).append("\n")
					.append("isAccelerating: " + horse.getEntityData().get(isAccelerating)).append("\n")
					.append("isSlowingDown: " + horse.getEntityData().get(isSlowingDown)).append("\n")
					.append("didFlap: " + horse.getEntityData().get(didFlap)).append("\n")
					.append("isTurning: " + horse.getEntityData().get(isTurning)).append("\n")
					.append("isTurningLeft: " + horse.getEntityData().get(isTurningLeft)).append("\n")
					.toString();*/

			if (horse.isOnGround() && !horse.getEntityData().get(isLaunching)) {
				horse.setFlying(false);
				horse.getEntityData().set(isFloating, false);
				horse.getEntityData().set(isAccelerating, false);
				horse.getEntityData().set(isSlowingDown, false);
				horse.getEntityData().set(isTurningLeft, false);
				horse.getEntityData().set(isTurning, false);
				horse.getEntityData().set(isStillTurning, false);
				horse.getEntityData().set(didFlap, false);
				horse.getEntityData().set(isDiving, false);
				horse.getEntityData().set(isLaunching, false);
			}
			if (horse.getEntityData().get(isLaunching)) {
				if (launchCounter < 4) {
					horse.setDeltaMovement(0, 10, 0);
				} else {
					horse.setDeltaMovement(0, 1, 0);
				}
				horse.move(MoverType.SELF, horse.getDeltaMovement());
				horse.hasImpulse = true;
				horse.baseTick();
				if (++launchCounter >= 37) {
					horse.hasImpulse = false;
					horse.getEntityData().set(isLaunching, false);
					launchCounter = 0;
				}
				return;
			}

			//System.out.println("Position: " + horse.blockPosition());
			if (horse.getEntityData().get(isFloating)) {
				horse.setDeltaMovement(horse.getLookAngle().x * 0.35, -0.05, horse.getLookAngle().z * 0.35);
			}

			if (horse.getEntityData().get(isAccelerating)) {
				horse.setDeltaMovement(horse.getLookAngle().x * 0.75, 0, horse.getLookAngle().z * 0.75);
			}

			if (horse.getEntityData().get(isSlowingDown)) {
				slowingDownCounter++;
				double xMove = Math.max(horse.getLookAngle().x * ((slowingDownCounter < 21 ? 21 - slowingDownCounter : 1) * 3.5) * 0.01, horse.getLookAngle().x * 0.10);
				double zMove = Math.max(horse.getLookAngle().z * ((slowingDownCounter < 21 ? 21 - slowingDownCounter: 1) * 3.5) * 0.01, horse.getLookAngle().z * 0.10);
				horse.setDeltaMovement(xMove, 0, zMove);

				if (slowingDownCounter >= 20 && !horse.getEntityData().get(isStillSlowingDown)) {
					slowingDownCounter = 0;
					horse.getEntityData().set(isSlowingDown, false);
					horse.getEntityData().set(isFloating, true);
				}
			}

			if (horse.getEntityData().get(didFlap)) {
				flapCounter++;

				Vector3d moveVec = horse.getDeltaMovement();


				if (flapCounter == 23) {
					flapCounter = 0;
					horse.getEntityData().set(didFlap, false);
					horse.setDeltaMovement(moveVec.x, 0, moveVec.z);
				} else {
					horse.setDeltaMovement(moveVec.x, (1.0 / (flapCounter * 0.75)) , moveVec.z);
				}
			}


			if (horse.getEntityData().get(isDiving)) {

				Vector3d moveVec = horse.getDeltaMovement();

				double downSpeed = horse.getEntityData().get(isAccelerating) ? -0.5d : -0.75d;

				horse.setDeltaMovement(moveVec.x, downSpeed, moveVec.z);
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

			float rotInc = horse.getEntityData().get(isTurningLeft) ? -0.75f : 0.75f;

			rotInc *= horse.getEntityData().get(isAccelerating) ? 2 : 3;

			horse.setRot(horse.yRot + rotInc, horse.xRot);
			horse.setYBodyRot(horse.yRot);
			horse.setYHeadRot(horse.yBodyRot);

			if (!horse.level.isClientSide) {
				turningCounter++;
				if (turningCounter >= 30 && !horse.getEntityData().get(isStillTurning)) {
					horse.getEntityData().set(isTurning, false);
					turningCounter = 0;
				}
			}
		}

		horse.move(MoverType.SELF, horse.getDeltaMovement());

		horse.baseTick();
	}

	/**
	 * Client travel.
	 */
	private void clientTravel() {

		if (Minecraft.getInstance().options.keyUp.isDown()) { // Move forward

			SWEMPacketHandler.INSTANCE.sendToServer(new HorseFlightPacket(1, horse.getId()));

		} else if (!Minecraft.getInstance().options.keyUp.isDown() && horse.getEntityData().get(isAccelerating)) { // If move forward is checked, but the w key is not held anymore, start the slowing down.
			SWEMPacketHandler.INSTANCE.sendToServer(new HorseFlightPacket(2, horse.getId()));
		}

		if (Minecraft.getInstance().options.keyLeft.isDown() && !horse.getEntityData().get(isDiving)) {
			SWEMPacketHandler.INSTANCE.sendToServer(new HorseFlightPacket(3, horse.getId()));
		} else if (Minecraft.getInstance().options.keyRight.isDown() & !horse.getEntityData().get(isDiving)) {
			SWEMPacketHandler.INSTANCE.sendToServer(new HorseFlightPacket(4, horse.getId()));
		} else if (horse.getEntityData().get(isStillTurning)) {
			SWEMPacketHandler.INSTANCE.sendToServer(new HorseFlightPacket(6, horse.getId()));
		}

		if (Minecraft.getInstance().options.keyJump.consumeClick() && !horse.getEntityData().get(isDiving)) {
			SWEMPacketHandler.INSTANCE.sendToServer(new HorseFlightPacket(5, horse.getId()));
		}

		if (Minecraft.getInstance().options.keyDown.isDown() && !horse.getEntityData().get(didFlap) && !horse.getEntityData().get(isTurning) && !horse.getEntityData().get(isAccelerating)) {
			SWEMPacketHandler.INSTANCE.sendToServer(new HorseFlightPacket(9, horse.getId()));
		} else if (horse.getEntityData().get(isStillSlowingDown) && !Minecraft.getInstance().options.keyDown.isDown()) {
			SWEMPacketHandler.INSTANCE.sendToServer(new HorseFlightPacket(10, horse.getId()));
		}

		if (ClientEventHandlers.keyBindings[5].isDown() && !horse.getEntityData().get(didFlap) && !horse.getEntityData().get(isTurning)) {
			SWEMPacketHandler.INSTANCE.sendToServer(new HorseFlightPacket(7, horse.getId()));
		} else if (horse.getEntityData().get(isDiving) && !ClientEventHandlers.keyBindings[5].isDown()) {
			SWEMPacketHandler.INSTANCE.sendToServer(new HorseFlightPacket(8, horse.getId()));
		}

		if (!Minecraft.getInstance().options.keyUp.isDown() && !Minecraft.getInstance().options.keyLeft.isDown() && !Minecraft.getInstance().options.keyRight.isDown() && !horse.getEntityData().get(didFlap) && !horse.getEntityData().get(isSlowingDown) && !horse.getEntityData().get(isLaunching)) {
			SWEMPacketHandler.INSTANCE.sendToServer(new HorseFlightPacket(0, horse.getId()));

		}

		// If KEY WE SAY is pressed, set isDiving = true.


	}


	/**
	 * Launch flight.
	 */
	public void launchFlight() {
		this.launchPos = horse.blockPosition();
	}

	/**
	 * Land.
	 */
	public void land() {
		horse.setPos(launchPos.getX(), launchPos.getY(), launchPos.getZ());
	}


}
