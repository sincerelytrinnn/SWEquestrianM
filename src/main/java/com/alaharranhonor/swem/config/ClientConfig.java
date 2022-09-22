package com.alaharranhonor.swem.config;

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

import net.minecraftforge.common.ForgeConfigSpec;

public final class ClientConfig {

    public final ForgeConfigSpec.IntValue wingsTransparency;

    /**
     * Instantiates a new Client config.
     *
     * @param builder the builder
     */
    ClientConfig(final ForgeConfigSpec.Builder builder) {
        builder.push("Wings transparency");

        this.wingsTransparency =
                builder
                        .comment("Set how visible the horse wings should be! 0 = off, 1 = 50%, 2 = 100%")
                        .defineInRange("WingsTransparency", 2, 0, 2);
    }
}
