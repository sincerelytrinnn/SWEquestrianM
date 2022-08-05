
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

package com.alaharranhonor.swem.items.tack;

import com.alaharranhonor.swem.items.SWEMHorseArmorItem;

public class PastureBlanketItem extends SWEMHorseArmorItem {

    /**
     * Instantiates a new Swem horse armor item.
     *
     * @param tier       the tier
     * @param armorValue the armor value
     * @param texture    the texture
     * @param builder    the builder
     */
    public PastureBlanketItem(HorseArmorTier tier, int armorValue, String texture, Properties builder) {
        super(tier, armorValue, "pasture_blanket/" + texture, builder);
    }
}
