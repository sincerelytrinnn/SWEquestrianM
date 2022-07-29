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

import com.alaharranhonor.swem.SWEM;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ShieldItem;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingEquipmentChangeEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

public class AmethystShield extends ShieldItem {
    /**
     * Instantiates a new Amethyst shield.
     *
     * @param builder the builder
     */
    public AmethystShield(Properties builder) {
        super(builder);
    }

    /**
     * Called each tick as long the item is on a player inventory. Uses by maps to check if is on a
     * player hand and update it's contents.
     */
    @Override
    public void inventoryTick(
            ItemStack pStack, World pLevel, Entity pEntity, int pItemSlot, boolean pIsSelected) {
        if (pEntity instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity) pEntity;
            // If the player hasn't taken any damage in the last 5 seconds, regain half a heart every 10
            // seconds.
            if (player.lastDamageStamp < pLevel.getGameTime() - (5 * 20)
                    && pLevel.getGameTime() % 200 == 0) {
                player.heal(1f);
            }
        }
        super.inventoryTick(pStack, pLevel, pEntity, pItemSlot, pIsSelected);
    }

    @Mod.EventBusSubscriber(modid = SWEM.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
    public static class OnInventoryChangeListener {

        /**
         * On inventory change.
         *
         * @param event the event
         */
        @SubscribeEvent
        public static void onInventoryChange(LivingEquipmentChangeEvent event) {
            if (!(event.getEntityLiving() instanceof PlayerEntity)) return;
            PlayerEntity player = (PlayerEntity) event.getEntityLiving();

            if (event.getTo().getItem() instanceof AmethystShield) {
                ItemStack stack = event.getTo();
                CompoundNBT nbt = stack.getOrCreateTag();
                AttributeModifier modifier;
                if (nbt.contains("modifier")) {
                    CompoundNBT mod = nbt.getCompound("modifier");
                    modifier =
                            new AttributeModifier(
                                    mod.getUUID("uuid"),
                                    mod.getString("name"),
                                    mod.getDouble("value"),
                                    AttributeModifier.Operation.fromValue(mod.getInt("operation")));
                } else {
                    modifier =
                            new AttributeModifier("amethyst_shield", 20, AttributeModifier.Operation.ADDITION);
                    CompoundNBT mod = new CompoundNBT();
                    mod.putUUID("uuid", modifier.getId());
                    mod.putString("name", modifier.getName());
                    mod.putDouble("value", modifier.getAmount());
                    mod.putInt("operation", modifier.getOperation().toValue());
                    nbt.put("modifier", mod);
                    stack.setTag(nbt);
                }
                // apply 10 extra hearts
                if (!player.getAttribute(Attributes.MAX_HEALTH).hasModifier(modifier)) {
                    player.getAttribute(Attributes.MAX_HEALTH).addTransientModifier(modifier);
                }
            }

            if ((event.getFrom().getItem() instanceof AmethystShield)) {
                // Remove 10 max health hearts
                CompoundNBT nbt = event.getFrom().getTag();
                player
                        .getAttribute(Attributes.MAX_HEALTH)
                        .removeModifier(nbt.getCompound("modifier").getUUID("uuid"));
                if (player.getHealth() > player.getMaxHealth()) {
                    player.setHealth(player.getMaxHealth());
                }
            }
        }
    }
}
