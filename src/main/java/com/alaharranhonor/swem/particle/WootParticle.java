package com.alaharranhonor.swem.particle;

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

import net.minecraft.client.particle.*;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particles.BasicParticleType;
import net.minecraft.util.math.MathHelper;

public class WootParticle extends SpriteTexturedParticle {

    /**
     * Instantiates a new Woot particle.
     *
     * @param world the world
     * @param x     the x
     * @param y     the y
     * @param z     the z
     */
    private WootParticle(ClientWorld world, double x, double y, double z) {
        super(world, x, y, z, 0.0D, 0.0D, 0.0D);
        this.xd *= (double) 0.01F;
        this.yd *= (double) 0.01F;
        this.zd *= (double) 0.01F;
        this.yd += 0.1D;
        this.quadSize *= 1.5F;
        this.lifetime = 16;
        this.hasPhysics = false;
    }

    public IParticleRenderType getRenderType() {
        return IParticleRenderType.PARTICLE_SHEET_OPAQUE;
    }

    /**
     * Gets scale.
     *
     * @param scaleFactor the scale factor
     * @return the scale
     */
    public float getScale(float scaleFactor) {
        return this.quadSize
                * MathHelper.clamp(
                ((float) this.age + scaleFactor) / (float) this.lifetime * 32.0F, 0.0F, 1.0F);
    }

    public void tick() {
        this.xo = this.x;
        this.yo = this.y;
        this.zo = this.z;
        if (this.age++ >= this.lifetime) {
            this.remove();
        } else {
            this.move(this.xd, this.yd, this.zd);
            if (this.y == this.yo) {
                this.xd *= 1.1D;
                this.zd *= 1.1D;
            }

            this.xd *= (double) 0.86F;
            this.yd *= (double) 0.86F;
            this.zd *= (double) 0.86F;
            if (this.onGround) {
                this.xd *= (double) 0.7F;
                this.zd *= (double) 0.7F;
            }
        }
    }

    public static class Factory implements IParticleFactory<BasicParticleType> {
        private final IAnimatedSprite spriteSet;

        /**
         * Instantiates a new Factory.
         *
         * @param spriteSet the sprite set
         */
        public Factory(IAnimatedSprite spriteSet) {
            this.spriteSet = spriteSet;
        }

        public Particle createParticle(
                BasicParticleType typeIn,
                ClientWorld worldIn,
                double x,
                double y,
                double z,
                double xSpeed,
                double ySpeed,
                double zSpeed) {
            WootParticle badParticle = new WootParticle(worldIn, x, y, z);
            badParticle.pickSprite(this.spriteSet);
            return badParticle;
        }
    }
}
