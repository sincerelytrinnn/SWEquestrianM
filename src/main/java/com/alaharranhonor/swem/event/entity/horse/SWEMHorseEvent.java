
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
import net.minecraftforge.event.entity.living.LivingEvent;

public class SWEMHorseEvent extends LivingEvent {
    private SWEMHorseEntityBase entityHorse;
    public SWEMHorseEvent(SWEMHorseEntityBase entity) {
        super(entity);
    }

    public SWEMHorseEntityBase getEntityHorse() {
        return entityHorse;
    }
}