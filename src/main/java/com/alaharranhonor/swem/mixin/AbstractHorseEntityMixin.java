package com.alaharranhonor.swem.mixin;

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

import net.minecraft.entity.passive.horse.AbstractHorseEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Random;

@Mixin(AbstractHorseEntity.class)
public class AbstractHorseEntityMixin {

    // Lnet/minecraft/entity/passive/horse/AbstractHorseEntity;generateRandomJumpStrength()D

    /**
     * Generate random jump strength.
     *
     * @param callback the callback
     */
    @Inject(at = @At("HEAD"), method = "generateRandomJumpStrength()D", cancellable = true)
    public void generateRandomJumpStrength(CallbackInfoReturnable<Double> callback) {
        Random random = new Random();
        double jump =
                (double) 0.4F
                        + Math.min(random.nextDouble(), 0.7D) * 0.2D
                        + Math.min(random.nextDouble(), 0.7D) * 0.2D
                        + Math.min(random.nextDouble(), 0.7D) * 0.2D;
        callback.setReturnValue(jump);
        callback.cancel();
    }
}
