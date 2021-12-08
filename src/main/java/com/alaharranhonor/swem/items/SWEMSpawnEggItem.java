package com.alaharranhonor.swem.items;


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

import net.minecraft.block.DispenserBlock;
import net.minecraft.dispenser.DefaultDispenseItemBehavior;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.util.Lazy;
import net.minecraftforge.fml.RegistryObject;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

import net.minecraft.item.Item.Properties;

public class SWEMSpawnEggItem extends SpawnEggItem {
	protected static final List<SWEMSpawnEggItem> UNADDED_EGGS = new ArrayList<>();

	private final Lazy<? extends EntityType<?>> entityTypeSupplier;

	public SWEMSpawnEggItem(final RegistryObject<? extends EntityType<?>> entityTypeSupplier, int primaryColorIn, int secondaryColorIn, Properties builder) {
		super(null, primaryColorIn, secondaryColorIn, builder);
		this.entityTypeSupplier = Lazy.of(entityTypeSupplier::get);
		UNADDED_EGGS.add(this);
	}

	public static void initSpawnEggs() {
		DefaultDispenseItemBehavior dispenseBehavior = new DefaultDispenseItemBehavior() {
			/**
			 * Dispense the specified stack, play the dispense sound and spawn particles.
			 *
			 * @param source
			 * @param stack
			 */
			@Override
			protected ItemStack execute(IBlockSource source, ItemStack stack) {
				Direction direction = source.getBlockState().getValue(DispenserBlock.FACING);
				EntityType<?> type = ((SpawnEggItem) stack.getItem()).getType(stack.getTag());
				type.spawn(source.getLevel(), stack, null, source.getPos(), SpawnReason.DISPENSER, direction != Direction.UP, false);
				stack.shrink(1);
				return stack;
			}
		};

		for (final SpawnEggItem spawnEgg : UNADDED_EGGS) {
			BY_ID.put(spawnEgg.getType(null), spawnEgg);
			DispenserBlock.registerBehavior(spawnEgg, dispenseBehavior);
		}

		UNADDED_EGGS.clear();
	}

	@Override
	public EntityType<?> getType(@Nullable CompoundNBT nbt) {
		return this.entityTypeSupplier.get();
	}
}
