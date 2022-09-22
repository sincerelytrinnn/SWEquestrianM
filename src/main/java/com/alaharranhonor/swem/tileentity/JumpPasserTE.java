package com.alaharranhonor.swem.tileentity;

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

import com.alaharranhonor.swem.util.registry.SWEMTileEntities;
import net.minecraft.block.BlockState;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;

public class JumpPasserTE extends TileEntity {

    public BlockPos controllerPos;

    /**
     * Instantiates a new Jump passer te.
     */
    public JumpPasserTE() {
        super(SWEMTileEntities.JUMP_PASSER_TILE_ENTITY.get());
    }

    /**
     * Gets controller pos.
     *
     * @return the controller pos
     */
    public BlockPos getControllerPos() {
        return this.controllerPos;
    }

    /**
     * Sets controller pos.
     *
     * @param controllerPos the controller pos
     */
    public void setControllerPos(BlockPos controllerPos) {
        this.controllerPos = controllerPos;
    }

    @Override
    public CompoundNBT save(CompoundNBT compound) {
        if (this.controllerPos != null) {
            compound.putIntArray(
                    "controller",
                    new int[]{controllerPos.getX(), controllerPos.getY(), controllerPos.getZ()});
        }
        return super.save(compound);
    }

    @Override
    public void load(BlockState state, CompoundNBT nbt) {
        if (nbt.contains("controller")) {
            int[] pos = nbt.getIntArray("controller");
            this.setControllerPos(new BlockPos(pos[0], pos[1], pos[2]));
        }
        super.load(state, nbt);
    }
}
