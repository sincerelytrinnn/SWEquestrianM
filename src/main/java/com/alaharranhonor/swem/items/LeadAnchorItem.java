package com.alaharranhonor.swem.items;
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


import com.alaharranhonor.swem.blocks.LeadAnchorBlock;
import com.alaharranhonor.swem.util.registry.SWEMBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.item.LeashKnotEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemUseContext;
import net.minecraft.state.properties.AttachFace;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class LeadAnchorItem extends ItemBase {

	/**
	 * Called when this item is used when targetting a Block
	 *
	 * @param pContext
	 */
	@Override
	public ActionResultType useOn(ItemUseContext pContext) {
		World world = pContext.getLevel();
		BlockPos blockpos = pContext.getClickedPos();
		BlockPos anchorPos = blockpos.relative(pContext.getClickedFace());


		if (world.isEmptyBlock(anchorPos)) {
			BlockState anchorState = SWEMBlocks.LEAD_ANCHOR.get().defaultBlockState();
			if (pContext.getClickedFace() == Direction.DOWN) {
				anchorState = anchorState.setValue(LeadAnchorBlock.FACE, AttachFace.CEILING);
			} else if (pContext.getClickedFace() == Direction.UP) {
				anchorState = anchorState.setValue(LeadAnchorBlock.FACE, AttachFace.FLOOR);
			} else {
				anchorState = anchorState.setValue(LeadAnchorBlock.FACING, pContext.getClickedFace());
			}
			world.setBlockAndUpdate(anchorPos, anchorState);
			pContext.getItemInHand().shrink(1);
			PlayerEntity playerentity = pContext.getPlayer();
			if (!world.isClientSide && playerentity != null) {
				bindPlayerMobs(playerentity, world, anchorPos);
			}

			return ActionResultType.sidedSuccess(world.isClientSide);
		} else {
			return ActionResultType.PASS;
		}
	}

	public static ActionResultType bindPlayerMobs(PlayerEntity pPlayer, World pLevel, BlockPos pPos) {
		LeashKnotEntity leashknotentity = null;
		boolean flag = false;
		double d0 = 7.0D;
		int i = pPos.getX();
		int j = pPos.getY();
		int k = pPos.getZ();

		for(MobEntity mobentity : pLevel.getEntitiesOfClass(MobEntity.class, new AxisAlignedBB((double)i - 7.0D, (double)j - 7.0D, (double)k - 7.0D, (double)i + 7.0D, (double)j + 7.0D, (double)k + 7.0D))) {
			if (mobentity.getLeashHolder() == pPlayer) {
				if (leashknotentity == null) {
					leashknotentity = LeashKnotEntity.getOrCreateKnot(pLevel, pPos);
					leashknotentity.setInvisible(true);
				}

				mobentity.setLeashedTo(leashknotentity, true);
				flag = true;
			}
		}

		return flag ? ActionResultType.SUCCESS : ActionResultType.PASS;
	}
}
