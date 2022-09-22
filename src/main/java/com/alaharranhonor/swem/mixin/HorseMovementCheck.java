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

package com.alaharranhonor.swem.mixin;

import com.alaharranhonor.swem.entities.SWEMHorseEntityBase;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.play.ServerPlayNetHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

/**
 * Disable movement checks for SWEM horses, fixing <a
 * href="https://bugs.mojang.com/browse/MC-100830">MC-100830</a>
 *
 * <p>Thanks to <a href="https://github.com/CodeF53/Horse-Buff">Horse Buff</a>
 */
@Mixin(ServerPlayNetHandler.class)
public class HorseMovementCheck {
    @Shadow
    public ServerPlayerEntity player;

    // 1.17+ first instance of 0.0625
    // 1.16- second instance of 0.0625
    @ModifyConstant(
            method = "handleMoveVehicle",
            constant = @Constant(doubleValue = 0.0625D, ordinal = 1))
    private double horseNoMovementCheck(double value) {
        if (this.player.getRootVehicle() instanceof SWEMHorseEntityBase)
            return Double.POSITIVE_INFINITY;
        return value;
    }
}
