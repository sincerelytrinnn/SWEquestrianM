package com.alaharranhonor.swem.tools;

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

import com.alaharranhonor.swem.blocks.jumps.JumpControllerBlock;
import com.alaharranhonor.swem.blocks.jumps.JumpLayer;
import com.alaharranhonor.swem.blocks.jumps.StandardLayer;
import com.alaharranhonor.swem.container.JumpContainer;
import com.alaharranhonor.swem.tileentity.JumpPasserTE;
import com.alaharranhonor.swem.tileentity.JumpTE;
import com.alaharranhonor.swem.util.registry.SWEMBlocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.fml.network.NetworkHooks;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MeasurementTool extends Item {

    /**
     * Instantiates a new Measurement tool.
     *
     * @param pProperties the p properties
     */
    public MeasurementTool(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public ActionResultType useOn(ItemUseContext context) {

        if (context.getLevel().isClientSide) return ActionResultType.PASS;

        TileEntity te = context.getLevel().getBlockEntity(context.getClickedPos());
        if (te != null) {
            if (te instanceof JumpPasserTE) {
                JumpPasserTE jumpPasser = (JumpPasserTE) te;
                if (jumpPasser.getControllerPos() != null) {
                    JumpTE controller = (JumpTE) context.getLevel().getBlockEntity(jumpPasser.getControllerPos());
                    INamedContainerProvider provider = new INamedContainerProvider() {
                        @Override
                        public ITextComponent getDisplayName() {
                            return new TranslationTextComponent("screen.swem.jump_builder");
                        }

                        @Nullable
                        @Override
                        public Container createMenu(int p_createMenu_1_, PlayerInventory p_createMenu_2_, PlayerEntity p_createMenu_3_) {
                            return new JumpContainer(p_createMenu_1_, p_createMenu_2_, controller);
                        }
                    };
                    NetworkHooks.openGui((ServerPlayerEntity) context.getPlayer(), provider, buffer -> buffer.writeBlockPos(controller.getBlockPos()));
                    // SWEMPacketHandler.INSTANCE.send(PacketDistributor.PLAYER.with(() ->
                    // (ServerPlayerEntity) context.getPlayer()), new OpenGuiPacket(controller.getPos(),
                    // controller.getLayerAmount(), controller.getCurrentStandard()));

                }
            }
            if (te instanceof JumpTE) {
                JumpTE jumpController = (JumpTE) te;
                if (jumpController.layerAmount == 0) return ActionResultType.PASS;
                INamedContainerProvider provider = new INamedContainerProvider() {
                    @Override
                    public ITextComponent getDisplayName() {
                        return new TranslationTextComponent("screen.swem.jump_builder");
                    }

                    @Nullable
                    @Override
                    public Container createMenu(int p_createMenu_1_, PlayerInventory p_createMenu_2_, PlayerEntity p_createMenu_3_) {
                        return new JumpContainer(p_createMenu_1_, p_createMenu_2_, jumpController);
                    }
                };
                NetworkHooks.openGui((ServerPlayerEntity) context.getPlayer(), provider, buffer -> buffer.writeBlockPos(jumpController.getBlockPos()));
                // SWEMPacketHandler.INSTANCE.send(PacketDistributor.PLAYER.with(() -> (ServerPlayerEntity)
                // context.getPlayer()), new OpenGuiPacket(jumpController.getPos(),
                // jumpController.getLayerAmount(), jumpController.getCurrentStandard()));
            }
            return ActionResultType.CONSUME;
        }

        ItemStack stack = context.getItemInHand();
        CompoundNBT nbt = stack.getOrCreateTag();

        if (nbt.contains("pos1")) {
            int[] posCords = nbt.getIntArray("pos1");
            BlockPos firstPos = new BlockPos(posCords[0], posCords[1], posCords[2]);
            BlockPos pos = context.getClickedPos();
            Direction initialDirection = Direction.byName(nbt.getString("direction"));
            if (initialDirection != context.getHorizontalDirection()) {
                nbt.remove("pos1");
                nbt.remove("direction");
                stack.setTag(nbt);
                context.getPlayer().displayClientMessage(new StringTextComponent("You are not facing the same direction as the first position"), true);
                return ActionResultType.FAIL;
            }
            int direction = isValidPos(firstPos, pos, initialDirection);
            if (direction == -1) {
                context.getPlayer().displayClientMessage(new StringTextComponent("The jump base is on the ground! Make sure you make your jump on the same Y level!"), true);
            } else if (direction == -2) {
                context.getPlayer().displayClientMessage(new StringTextComponent("The gap is too wide or too narrow! Must be block, 5 spaces, then another block."), true);
            } else if (direction == -3) {
                context.getPlayer().displayClientMessage(new StringTextComponent("The block gap must be left/right of the way you are facing, not forward/backward."), true);
            }

            if (direction <= 0) {
                nbt.remove("pos1");
                nbt.remove("direction");
                stack.setTag(nbt);
                return ActionResultType.CONSUME;
            }

            ArrayList<BlockPos> blockPositions = new ArrayList<>();
            BlockPos.betweenClosed(firstPos, pos).forEach((ps) -> {
                BlockPos pos1 = ps.immutable();
                blockPositions.add(pos1);
            });
            int layerAmount = blockPositions.size() / 7;
            nbt.remove("pos1");
            nbt.remove("direction");
            int lowestYValue = Math.min(firstPos.getY(), pos.getY());
            Map<Integer, ArrayList<BlockPos>> layers = this.rearrangeLayers(lowestYValue, blockPositions);

            // Should default to either the player facing north/east

            Direction facing = context.getHorizontalDirection().getAxis() == Direction.Axis.Z ? Direction.SOUTH : Direction.WEST;

            context.getLevel().setBlock(layers.get(1).get(0).relative(Direction.UP, 5), SWEMBlocks.JUMP_CONTROLLER.get().defaultBlockState().setValue(JumpControllerBlock.FACING, facing), 3);

            JumpTE jumpController = (JumpTE) context.getLevel().getBlockEntity(layers.get(1).get(0).relative(Direction.UP, 5));
            jumpController.setLayerAmount(layerAmount);
            jumpController.assignJumpBlocks(layers);
            jumpController.initStandards(StandardLayer.SCHOOLING);
            for (int i = 1; i <= layerAmount; i++) {
                jumpController.placeLayer(i, JumpLayer.NONE);
            }

            INamedContainerProvider provider = new INamedContainerProvider() {
                @Override
                public ITextComponent getDisplayName() {
                    return new TranslationTextComponent("screen.swem.jump_builder");
                }

                @Nullable
                @Override
                public Container createMenu(int p_createMenu_1_, PlayerInventory p_createMenu_2_, PlayerEntity p_createMenu_3_) {
                    return new JumpContainer(p_createMenu_1_, p_createMenu_2_, jumpController);
                }
            };
            NetworkHooks.openGui((ServerPlayerEntity) context.getPlayer(), provider, buffer -> buffer.writeBlockPos(jumpController.getBlockPos()));

            // SWEMPacketHandler.INSTANCE.send(PacketDistributor.PLAYER.with(() -> (ServerPlayerEntity)
            // context.getPlayer()), new OpenGuiPacket(jumpController.getPos(),
            // jumpController.getLayerAmount(), jumpController.getCurrentStandard()));

            stack.setTag(new CompoundNBT());

            return ActionResultType.CONSUME;
        } else {
            BlockPos pos = context.getClickedPos();
            nbt.putIntArray("pos1", new int[]{pos.getX(), pos.getY(), pos.getZ()});
            nbt.putString("direction", context.getHorizontalDirection().name());
            stack.setTag(nbt);
            return ActionResultType.CONSUME;
        }
    }

    /**
     * Rearrange layers map.
     *
     * @param lowestYValue the lowest y value
     * @param positions    the positions
     * @return the map
     */
    private Map<Integer, ArrayList<BlockPos>> rearrangeLayers(int lowestYValue, ArrayList<BlockPos> positions) {
        Map<Integer, ArrayList<BlockPos>> layers = new HashMap<>();
        for (int i = 1; i <= positions.size(); i++) {
            BlockPos pos = positions.get(i - 1);
            int layerNumber = (pos.getY() - lowestYValue) + 1;
            ArrayList<BlockPos> layer = layers.getOrDefault(layerNumber, new ArrayList<>());
            layer.add(pos);
            layers.put(layerNumber, layer);
        }

        return layers;
    }

    /**
     * Is valid pos int.
     *
     * @param first  the first
     * @param second the second
     * @return the int
     */
    private int isValidPos(BlockPos first, BlockPos second, Direction initialDirection) {
        if (first.getY() != second.getY()) {
            return -1;
        }

        if (initialDirection.getAxis() == Direction.Axis.Z) {
            if (first.getZ() != second.getZ()) {
                return -3;
            }
        } else {
            if (first.getX() != second.getX()) {
                return -3;
            }
        }

        if (initialDirection.getAxis() == Direction.Axis.X) {
            if (first.getZ() - second.getZ() == -6) {
                return 1; // Negative Z
            } else if (first.getZ() - second.getZ() == 6) {
                return 2; // Positive Z
            }
            return -2;
            // check < or > 5 on z
        } else if (initialDirection.getAxis() == Direction.Axis.Z) {
            if (first.getX() - second.getX() == -6) {
                return 3; // Negative X
            } else if (first.getX() - second.getX() == 6) {
                return 4; // Positive X
            }
            return -2;
        }
        return -3;
    }

    @Override
    public void readShareTag(ItemStack stack, @Nullable CompoundNBT nbt) {
        stack.setTag(new CompoundNBT());
    }
}
