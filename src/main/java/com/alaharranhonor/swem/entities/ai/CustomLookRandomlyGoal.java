package com.alaharranhonor.swem.entities.ai;
/*
 * All Rights Reserved
 *
 * Copyright (c) 2022, AlaharranHonor, Legenden.
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
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.item.LeashKnotEntity;
import net.minecraft.entity.player.PlayerEntity;

import java.util.EnumSet;

public class CustomLookRandomlyGoal extends Goal {
    private final SWEMHorseEntityBase mob;
    private double relX;
    private double relZ;
    private int lookTime;

    /**
     * Instantiates a new Custom look randomly goal.
     *
     * @param p_i1647_1_ the p i 1647 1
     */
    public CustomLookRandomlyGoal(SWEMHorseEntityBase p_i1647_1_) {
        this.mob = p_i1647_1_;
        this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
    }

    /**
     * Returns whether execution should begin. You can also read and cache any state necessary for
     * execution in this method as well.
     */
    public boolean canUse() {
        return this.mob.getRandom().nextFloat() < 0.02F
                && !(this.mob.getLeashHolder() instanceof PlayerEntity)
                && !(this.mob.leashHolder2 instanceof LeashKnotEntity);
    }

    /**
     * Returns whether an in-progress EntityAIBase should continue executing
     */
    public boolean canContinueToUse() {
        return this.lookTime >= 0;
    }

    /**
     * Execute a one shot task or start executing a continuous task
     */
    public void start() {
        double d0 = (Math.PI * 2D) * this.mob.getRandom().nextDouble();
        this.relX = Math.cos(d0);
        this.relZ = Math.sin(d0);
        this.lookTime = 20 + this.mob.getRandom().nextInt(20);
    }

    /**
     * Keep ticking a continuous task that has already been started
     */
    public void tick() {
        --this.lookTime;
        this.mob
                .getLookControl()
                .setLookAt(
                        this.mob.getX() + this.relX,
                        this.mob.getEyeY(),
                        this.mob.getZ() + this.relZ,
                        this.mob.getMaxHeadYRot(),
                        (float) this.mob.getMaxHeadXRot());
    }
}
