
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

package com.alaharranhonor.swem.client.animation;

import com.alaharranhonor.swem.SWEM;
import com.alaharranhonor.swem.entities.SWEMHorseEntity;
import dev.kosmx.playerAnim.api.layered.AnimationStack;
import dev.kosmx.playerAnim.api.layered.KeyframeAnimationPlayer;
import dev.kosmx.playerAnim.api.layered.ModifierLayer;
import dev.kosmx.playerAnim.api.layered.modifier.AbstractFadeModifier;
import dev.kosmx.playerAnim.core.data.KeyframeAnimation;
import dev.kosmx.playerAnim.core.util.Ease;
import dev.kosmx.playerAnim.impl.IAnimatedPlayer;
import net.minecraft.entity.player.PlayerEntity;
import software.bernie.geckolib3.core.controller.AnimationController;

public class PlayerAnimationController {
    private final PlayerEntity player;
    private final ModifierLayer<KeyframeAnimationPlayer> poseContainer = new ModifierLayer(null);

    public PlayerAnimationController(PlayerEntity player) {
        this.player = player;
        AnimationStack stack = ((IAnimatedPlayer) player).getAnimationStack();
        stack.addAnimLayer(1, poseContainer);
    }


    public void tick() {
        if (!(this.player.getVehicle() instanceof SWEMHorseEntity)) {
            poseContainer.setAnimation(null);
            return;
        }
        SWEMHorseEntity horse = (SWEMHorseEntity) this.player.getVehicle();

        if (horse.getFactory() == null) {
            return;
        }

        AnimationController controller = ((SWEMHorseEntity) this.player.getVehicle()).getFactory().getOrCreateAnimationData(this.player.getVehicle().getUUID().hashCode()).getAnimationControllers().get("controller");

        if (controller == null) {
            SWEM.LOGGER.debug("Horse controller is null");
            return;
        }

        if (controller.getCurrentAnimation() == null) {
            SWEM.LOGGER.error("Horse animation is null");
            return;
        }

        if (poseContainer.getAnimation() == null || (poseContainer.getAnimation() != null && !poseContainer.getAnimation().getData().extraData.get("name").equals(controller.getCurrentAnimation().animationName + "Player"))) {
            KeyframeAnimation animation = AnimationRegistry.animations.get(controller.getCurrentAnimation().animationName + "Player");
            if (animation != null) {

                poseContainer.replaceAnimationWithFade(AbstractFadeModifier.standardFadeIn(2, Ease.LINEAR), new KeyframeAnimationPlayer(animation));
            }

        }

    }
}
