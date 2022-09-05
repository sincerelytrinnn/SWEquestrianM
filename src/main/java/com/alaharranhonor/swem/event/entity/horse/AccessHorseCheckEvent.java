
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

package com.alaharranhonor.swem.event.entity.horse;

import com.alaharranhonor.swem.entities.SWEMHorseEntityBase;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.eventbus.api.Event;

/**
 * This event has a result<br>
 * Default uses the default canAccess value.<br>
 * Deny denies the access check.<br>
 * Allow allows the access check.
 */
@Event.HasResult
public class AccessHorseCheckEvent extends SWEMHorseEvent {

    private final boolean canAccess;
    private final PlayerEntity accessor;

    public AccessHorseCheckEvent(SWEMHorseEntityBase entity, PlayerEntity accessor, boolean canAccess) {
        super(entity);
        this.canAccess = canAccess;
        this.accessor = accessor;
    }

    public boolean canAccess() {
        return canAccess;
    }

    public PlayerEntity getAccessor() {
        return accessor;
    }
}
