package com.alaharranhonor.swem.entities.ai;


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

import com.alaharranhonor.swem.entities.SWEMHorseEntityBase;
import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.ai.goal.PanicGoal;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3i;

public class PanicStraightGoal extends PanicGoal {
	private SWEMHorseEntityBase horse;
	public PanicStraightGoal(SWEMHorseEntityBase creature, double speedIn) {
		super(creature, speedIn);
		this.horse = creature;
	}

	@Override
	public void start() {
		super.start();
		SWEMHorseEntityBase.HorseSpeed oldSpeed = this.horse.currentSpeed;
		this.horse.currentSpeed = SWEMHorseEntityBase.HorseSpeed.WALK;
		this.horse.updateSelectedSpeed(oldSpeed);
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
