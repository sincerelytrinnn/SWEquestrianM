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

import com.alaharranhonor.swem.util.SWEMUtil;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.CauldronBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.stats.Stats;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(CauldronBlock.class)
public class CauldronBlockMixin {


	@Final
	@Shadow public static final IntegerProperty LEVEL = BlockStateProperties.LEVEL_CAULDRON;

	@Shadow public void setWaterLevel(World p_176590_1_, BlockPos p_176590_2_, BlockState p_176590_3_, int p_176590_4_) {
		throw new IllegalStateException("Mixin failed to shadow setWaterLevel");
	}

	@Inject(at = @At("HEAD"), method = "Lnet/minecraft/block/CauldronBlock;use(Lnet/minecraft/block/BlockState;Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/entity/player/PlayerEntity;Lnet/minecraft/util/Hand;Lnet/minecraft/util/math/BlockRayTraceResult;)Lnet/minecraft/util/ActionResultType;", cancellable = true)
	private void use(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit, CallbackInfoReturnable<ActionResultType> callback) {
		ItemStack stack = player.getItemInHand(handIn);

		int i = state.getValue(LEVEL);

		Block foundBlock = SWEMUtil.mappings.get(stack.getItem());

		if (i > 0 && foundBlock != null) {
			player.setItemInHand(handIn, new ItemStack(foundBlock));
			this.setWaterLevel(worldIn, pos, state, i -1);
			player.awardStat(Stats.USE_CAULDRON);
			callback.setReturnValue(ActionResultType.CONSUME);
		}

	}

}
