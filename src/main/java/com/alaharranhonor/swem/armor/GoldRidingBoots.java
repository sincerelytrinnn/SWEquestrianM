package com.alaharranhonor.swem.armor;

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

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.FlowingFluidBlock;
import net.minecraft.block.material.Material;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.text.Color;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class GoldRidingBoots extends IronRidingBoots {
    /**
     * Instantiates a new Gold riding boots.
     *
     * @param path       the path
     * @param materialIn the material in
     * @param slot       the slot
     * @param builderIn  the builder in
     */
    public GoldRidingBoots(String path, IArmorMaterial materialIn, EquipmentSlotType slot, Properties builderIn) {
        super(path, materialIn, slot, builderIn);
    }

    @Override
    public void onCraftedBy(ItemStack stack, World worldIn, PlayerEntity playerIn) {
        super.onCraftedBy(stack, worldIn, playerIn);
    }

    @Override
    public void appendHoverText(ItemStack p_77624_1_, @Nullable World p_77624_2_, List<ITextComponent> p_77624_3_, ITooltipFlag p_77624_4_) {
        p_77624_3_.add(new StringTextComponent("Ice, Ice baby.").setStyle(Style.EMPTY.withColor(Color.parseColor("#585858"))));
    }

    @Override
    public boolean makesPiglinsNeutral(ItemStack stack, LivingEntity wearer) {
        return true;
    }

    /**
     * Called to tick armor in the armor slot. Override to do something
     *
     * @param stack
     * @param world
     * @param player
     */
    @Override
    public void onArmorTick(ItemStack stack, World world, PlayerEntity player) {
        if (player.isOnGround()) {
            BlockState blockstate = Blocks.FROSTED_ICE.defaultBlockState();
            float f = (float) Math.min(16, 3);
            BlockPos.Mutable blockpos$mutable = new BlockPos.Mutable();

            BlockPos pos = player.blockPosition();

            for (BlockPos blockpos : BlockPos.betweenClosed(pos.offset(-f, -1.0D, -f), pos.offset(f, -1.0D, f))) {
                if (blockpos.closerThan(player.position(), (double) f)) {
                    blockpos$mutable.set(blockpos.getX(), blockpos.getY() + 1, blockpos.getZ());
                    BlockState blockstate1 = world.getBlockState(blockpos$mutable);
                    if (blockstate1.getBlock().isAir(blockstate1, world, blockpos$mutable)) {
                        BlockState blockstate2 = world.getBlockState(blockpos);
                        boolean isFull = blockstate2.getBlock() == Blocks.WATER && blockstate2.getValue(FlowingFluidBlock.LEVEL) == 0; // TODO: Forge, modded waters?
                        if (((blockstate2.getMaterial() == Material.WATER && isFull) || blockstate2.getMaterial() == Material.WATER_PLANT) && blockstate.canSurvive(world, blockpos) && world.isUnobstructed(blockstate, blockpos, ISelectionContext.empty()) && !net.minecraftforge.event.ForgeEventFactory.onBlockPlace(player, net.minecraftforge.common.util.BlockSnapshot.create(world.dimension(), world, blockpos), net.minecraft.util.Direction.UP)) {
                            world.setBlock(blockpos, blockstate, 3);
                            world.getBlockTicks().scheduleTick(blockpos, Blocks.FROSTED_ICE, 20);
                        }
                    }
                }
            }
        }
    }
}
