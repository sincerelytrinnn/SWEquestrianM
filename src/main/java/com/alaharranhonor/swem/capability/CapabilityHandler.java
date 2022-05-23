package com.alaharranhonor.swem.capability;
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
// Huge thanks to Mowzie's Mobs for making this custom player renderer
// https://www.curseforge.com/minecraft/mc-mods/mowzies-mobs

import net.minecraft.entity.Entity;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;

import javax.annotation.Nullable;

public class CapabilityHandler {
	public static void register() {
		CapabilityManager.INSTANCE.register(PlayerCapability.IPlayerCapability.class, new PlayerCapability.PlayerStorage(), PlayerCapability.PlayerCapabilityImp::new);
	}

	@Nullable
	public static <T> T getCapability(Entity entity, Capability<T> capability) {
		if (entity == null) return null;
		if (!entity.isAlive()) return null;
		return entity.getCapability(capability).isPresent() ? entity.getCapability(capability).orElseThrow(() -> new IllegalArgumentException("Lazy optional must not be empty")) : null;
	}
}
