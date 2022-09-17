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

import com.alaharranhonor.swem.util.ClientEventHandlers;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.MoverType;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;

public class HorseFlightController {

    public static DataParameter<Boolean> isLaunching = EntityDataManager.defineId(SWEMHorseEntityBase.class, DataSerializers.BOOLEAN);

    private boolean isFloating;
    private boolean isAccelerating;
    private boolean isSlowingDown;
    private boolean isStillSlowingDown;
    private boolean isTurningLeft; // Identifier for playing either turning left or turning right animation
    private boolean isTurning; // if isTurning is set to true, increment the counter.
    private boolean isStillTurning;
    private boolean didFlap; // Was space pressed after flapCounter reached 20 ticks, reset to false
    private boolean isDiving;
    private final SWEMHorseEntityBase horse;
    private BlockPos launchPos;
    private int launchCounter;
    private int slowingDownCounter;
    private int turningCounter;
    private int flapCounter; // first 10 counts add more upwards movement than the last 10 counts;

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
        // System.out.println("yRot: " + horse.yRot + " - Look Angle: " + horse.getLookAngle());
        // System.out.println("isClientSide: " + horse.level.isClientSide + " | Movement: " +
        // horse.getDeltaMovement() + " | Look Angle Vec: " + horse.getLookAngle() + " | isFloating: " +
        // horse.getEntityData().get(isFloating));


        if (!this.horse.level.isClientSide) {
            if (horse.isOnGround() && !horse.getEntityData().get(isLaunching)) {
                horse.setFlying(false);
                isFloating = false;
                isAccelerating = false;
                isSlowingDown = false;
                isTurningLeft = false;
                isTurning = false;
                isStillTurning = false;
                didFlap = false;
                isDiving = false;
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
            return;
        }

        this.clientTravel();

      /*String sb = new StringBuilder()
      .append("isFloating: " + horse.getEntityData().get(isFloating)).append("\n")
      .append("isAccelerating: " + horse.getEntityData().get(isAccelerating)).append("\n")
      .append("isSlowingDown: " + horse.getEntityData().get(isSlowingDown)).append("\n")
      .append("didFlap: " + horse.getEntityData().get(didFlap)).append("\n")
      .append("isTurning: " + horse.getEntityData().get(isTurning)).append("\n")
      .append("isTurningLeft: " + horse.getEntityData().get(isTurningLeft)).append("\n")
      .toString();*/


        // System.out.println("Position: " + horse.blockPosition());
        if (isFloating) {
            horse.setDeltaMovement(horse.getLookAngle().x * 0.35, -0.05, horse.getLookAngle().z * 0.35);
        }

        if (isAccelerating) {
            horse.setDeltaMovement(horse.getLookAngle().x * 0.75, 0, horse.getLookAngle().z * 0.75);
        }

        if (isSlowingDown) {
            slowingDownCounter++;
            double xMove = Math.max(horse.getLookAngle().x * ((slowingDownCounter < 21 ? 21 - slowingDownCounter : 1) * 3.5) * 0.01, horse.getLookAngle().x * 0.10);
            double zMove = Math.max(horse.getLookAngle().z * ((slowingDownCounter < 21 ? 21 - slowingDownCounter : 1) * 3.5) * 0.01, horse.getLookAngle().z * 0.10);
            horse.setDeltaMovement(xMove, 0, zMove);

            if (slowingDownCounter >= 20 && !isStillSlowingDown) {
                slowingDownCounter = 0;
                isSlowingDown = false;
                isFloating = true;
            }
        }

        if (didFlap) {
            flapCounter++;

            Vector3d moveVec = horse.getDeltaMovement();

            if (flapCounter == 23) {
                flapCounter = 0;
                didFlap = false;
                horse.setDeltaMovement(moveVec.x, 0, moveVec.z);
            } else {
                horse.setDeltaMovement(moveVec.x, (1.0 / (flapCounter * 0.75)), moveVec.z);
            }
        }

        if (isDiving) {

            Vector3d moveVec = horse.getDeltaMovement();

            double downSpeed = isAccelerating ? -0.5d : -0.75d;

            horse.setDeltaMovement(moveVec.x, downSpeed, moveVec.z);
        }

        // If isFloating is true, add small downards movement, and slight forward movement.
        // For every 5 blocks forward 1 block down.

        // If isTurning, set the horse rotation based on the turning counter; and the direction based
        // on isTurningLeft;

        // If isAccelerating cancel out the downwards movement, and add forward movement.

        // If didFlap, add upwards movement, the first 10 counts adds slightly more upwards movement
        // than the last 10 counts.
        // Increment flapCounter after 20 is reached, reset it.

        // if isDiving and not isAccelerating dive more aggressive and more at a 60 degree angle.

        // if isDiving and isAccelerating dive less aggressive and at 30-45 degree angle

        if (isTurning) {

            float rotInc = isTurningLeft ? -0.75f : 0.75f;

            rotInc *= isAccelerating ? 2 : 3;

            horse.setRot(horse.yRot + rotInc, horse.xRot);
            horse.setYBodyRot(horse.yRot);
            horse.setYHeadRot(horse.yBodyRot);


            turningCounter++;
            if (turningCounter >= 30 && !isStillTurning) {
                isTurning = false;
                turningCounter = 0;
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

            isFloating = false;
            isAccelerating = true;

        } else if (!Minecraft.getInstance().options.keyUp.isDown() && isAccelerating) { // If move forward is checked, but the w key is not held anymore,
            // start the slowing down.
            isFloating = false;
            isAccelerating = false;
            isSlowingDown = true;
        }

        if (Minecraft.getInstance().options.keyLeft.isDown() && !isDiving) {
            isTurningLeft = true;
            isTurning = true;
            isStillTurning = true;
        } else if (Minecraft.getInstance().options.keyRight.isDown() & !isDiving) {
            isTurningLeft = false;
            isTurning = true;
            isStillTurning = true;
        } else if (isStillTurning) {
            isStillTurning = false;
        }

        if (Minecraft.getInstance().options.keyJump.consumeClick() && !isDiving) {
            isFloating = false;
            didFlap = true;
        }

        if (Minecraft.getInstance().options.keyDown.isDown() && !didFlap && !isTurning && !isAccelerating) {
            isFloating = false;
            isAccelerating = false;
            isSlowingDown = true;
            isStillSlowingDown = true;
        } else if (isStillSlowingDown && !Minecraft.getInstance().options.keyDown.isDown()) {
            isStillSlowingDown = false;
        }

        if (ClientEventHandlers.keyBindings[5].isDown() && !didFlap && !isTurning) {
            isTurning = false;
            isDiving = true;
        } else if (isDiving && !ClientEventHandlers.keyBindings[5].isDown()) {
            isDiving = false;
        }

        if (!Minecraft.getInstance().options.keyUp.isDown() && !Minecraft.getInstance().options.keyLeft.isDown() && !Minecraft.getInstance().options.keyRight.isDown() && !didFlap && !isSlowingDown && !horse.getEntityData().get(isLaunching)) {
            isFloating = true;
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
    }

    public boolean isFloating() {
        return isFloating;
    }

    public boolean isAccelerating() {
        return isAccelerating;
    }

    public boolean isSlowingDown() {
        return isSlowingDown;
    }

    public boolean isStillSlowingDown() {
        return isStillSlowingDown;
    }

    public boolean isTurningLeft() {
        return isTurningLeft;
    }

    public boolean isTurning() {
        return isTurning;
    }

    public boolean isStillTurning() {
        return isStillTurning;
    }

    public boolean isDidFlap() {
        return didFlap;
    }

    public boolean isDiving() {
        return isDiving;
    }
}
