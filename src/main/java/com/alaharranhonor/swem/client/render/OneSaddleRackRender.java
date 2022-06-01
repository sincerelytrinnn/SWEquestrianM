package com.alaharranhonor.swem.client.render;

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

import com.alaharranhonor.swem.blocks.OneSaddleRack;
import com.alaharranhonor.swem.client.model.AdventureSaddleModel;
import com.alaharranhonor.swem.client.model.EnglishSaddleModel;
import com.alaharranhonor.swem.client.model.OneSaddleRackModel;
import com.alaharranhonor.swem.client.model.WesternSaddleModel;
import com.alaharranhonor.swem.items.tack.AdventureSaddleItem;
import com.alaharranhonor.swem.items.tack.EnglishSaddleItem;
import com.alaharranhonor.swem.items.tack.HorseSaddleItem;
import com.alaharranhonor.swem.items.tack.WesternSaddleItem;
import com.alaharranhonor.swem.tileentity.OneSaddleRackTE;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import java.util.Iterator;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Direction;
import net.minecraft.util.math.vector.Quaternion;
import software.bernie.geckolib3.core.util.Color;
import software.bernie.geckolib3.geo.render.built.GeoBone;
import software.bernie.geckolib3.renderers.geo.GeoBlockRenderer;

public class OneSaddleRackRender extends GeoBlockRenderer<OneSaddleRackTE> {

  /**
   * Instantiates a new One saddle rack render.
   *
   * @param rendererDispatcherIn the renderer dispatcher in
   */
  public OneSaddleRackRender(TileEntityRendererDispatcher rendererDispatcherIn) {
    super(rendererDispatcherIn, new OneSaddleRackModel());
  }

  @Override
  public void render(
      OneSaddleRackTE tile,
      float partialTicks,
      MatrixStack stack,
      IRenderTypeBuffer bufferIn,
      int packedLightIn) {
    super.render(tile, partialTicks, stack, bufferIn, packedLightIn);
    ItemStack itemStack = tile.itemHandler.getStackInSlot(0);
    if (itemStack.getItem() == Items.AIR || itemStack == ItemStack.EMPTY) {
      return;
    }

    stack.pushPose();

    Direction direction = tile.getBlockState().getValue(OneSaddleRack.FACING);
    switch (direction) {
      case WEST:
        stack.translate(0.4375, 0.125, 0.5);
        break;
      case EAST:
        stack.translate(0.5625, 0.125, 0.5);
        break;
      case SOUTH:
        stack.translate(0.5, 0.125, 0.5625);
        break;
      case NORTH:
        stack.translate(0.5, 0.125, 0.4375);
        break;
    }

    stack.mulPose(new Quaternion(0, 0 - direction.toYRot(), 0, true));

    IVertexBuilder builder =
        bufferIn.getBuffer(
            RenderType.entityCutoutNoCull(
                ((HorseSaddleItem) itemStack.getItem()).getSaddleRackTexture()));
    if (itemStack.getItem() instanceof WesternSaddleItem) {

      WesternSaddleItem item = (WesternSaddleItem) itemStack.getItem();
      WesternSaddleModel model = new WesternSaddleModel();

      Color renderColor =
          this.getRenderColor(
              tile, partialTicks, stack, bufferIn, (IVertexBuilder) null, packedLightIn);
      Iterator group = model.getModel(model.getModelLocation(item)).topLevelBones.iterator();
      while (group.hasNext()) {
        GeoBone bone = (GeoBone) group.next();

        this.renderRecursively(
            bone,
            stack,
            builder,
            packedLightIn,
            OverlayTexture.NO_OVERLAY,
            renderColor.getRed() / 255.0F,
            renderColor.getGreen() / 255.0F,
            renderColor.getBlue() / 255.0F,
            renderColor.getAlpha() / 255.0F);
      }

    } else if (itemStack.getItem() instanceof EnglishSaddleItem) {

      EnglishSaddleItem item = (EnglishSaddleItem) itemStack.getItem();
      EnglishSaddleModel model = new EnglishSaddleModel();

      Color renderColor =
          this.getRenderColor(
              tile, partialTicks, stack, bufferIn, (IVertexBuilder) null, packedLightIn);
      Iterator group = model.getModel(model.getModelLocation(item)).topLevelBones.iterator();
      while (group.hasNext()) {
        GeoBone bone = (GeoBone) group.next();

        this.renderRecursively(
            bone,
            stack,
            builder,
            packedLightIn,
            OverlayTexture.NO_OVERLAY,
            renderColor.getRed() / 255.0F,
            renderColor.getGreen() / 255.0F,
            renderColor.getBlue() / 255.0F,
            renderColor.getAlpha() / 255.0F);
      }

    } else if (itemStack.getItem() instanceof AdventureSaddleItem) {
      AdventureSaddleItem item = (AdventureSaddleItem) itemStack.getItem();
      AdventureSaddleModel model = new AdventureSaddleModel();

      Color renderColor =
          this.getRenderColor(
              tile, partialTicks, stack, bufferIn, (IVertexBuilder) null, packedLightIn);
      Iterator group = model.getModel(model.getModelLocation(item)).topLevelBones.iterator();
      while (group.hasNext()) {
        GeoBone bone = (GeoBone) group.next();

        this.renderRecursively(
            bone,
            stack,
            builder,
            packedLightIn,
            OverlayTexture.NO_OVERLAY,
            renderColor.getRed() / 255.0F,
            renderColor.getGreen() / 255.0F,
            renderColor.getBlue() / 255.0F,
            renderColor.getAlpha() / 255.0F);
      }
    }

    stack.popPose();
  }
}
