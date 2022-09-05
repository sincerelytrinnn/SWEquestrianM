
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

package com.alaharranhonor.swem.entities.ai;

import com.alaharranhonor.swem.entities.SWEMHorseEntityBase;
import net.minecraft.entity.ai.goal.WaterAvoidingRandomWalkingGoal;

public class HorseWaterAvoidingRandomWalkingGoal extends WaterAvoidingRandomWalkingGoal {
    private final SWEMHorseEntityBase horse;

    /**
     * Instantiates a new Horse water avoiding random walking goal.
     *
     * @param p_i47301_1_ the p i 47301 1
     * @param p_i47301_2_ the p i 47301 2
     */
    public HorseWaterAvoidingRandomWalkingGoal(SWEMHorseEntityBase p_i47301_1_, double p_i47301_2_) {
        super(p_i47301_1_, p_i47301_2_);
        this.horse = p_i47301_1_;
    }

    @Override
    public void start() {
        super.start();

        SWEMHorseEntityBase.HorseSpeed oldSpeed = this.horse.currentSpeed;
        this.horse.currentSpeed = SWEMHorseEntityBase.HorseSpeed.WALK;
        this.horse.updateSelectedSpeed(oldSpeed);
    }
}
