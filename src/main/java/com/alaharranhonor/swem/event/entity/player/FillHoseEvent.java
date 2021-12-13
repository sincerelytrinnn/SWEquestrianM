package com.alaharranhonor.swem.event.entity.player;


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

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.PlayerEvent;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class FillHoseEvent extends PlayerEvent
{

	private final ItemStack current;
	private final World world;
	@Nullable
	private final RayTraceResult target;

	private ItemStack result;

	public FillHoseEvent(PlayerEntity player, @Nonnull ItemStack current, World world, @Nullable RayTraceResult target)
	{
		super(player);
		this.current = current;
		this.world = world;
		this.target = target;
	}

	@Nonnull
	public ItemStack getEmptyHose() { return this.current; }
	public World getWorld(){ return this.world; }
	@Nullable
	public RayTraceResult getTarget() { return this.target; }
	@Nonnull
	public ItemStack getFilledHose() { return this.result; }
	public void setFilledHose(@Nonnull ItemStack hose) { this.result = hose; }
}

