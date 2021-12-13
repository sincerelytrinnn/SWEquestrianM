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

import com.alaharranhonor.swem.util.registry.SWEMItems;
import net.minecraft.item.IItemTier;
import net.minecraft.item.crafting.Ingredient;

import java.util.function.Supplier;

public enum SWEMItemTier implements IItemTier {

    AMETHYST(4, 10000, 8, 15.0F, 0, () ->{
        return Ingredient.of(SWEMItems.AMETHYST_LONGSWORD.get());
    });

    private final int harvestLevel;
    private final int maxUses;
    private final float efficiency;
    private final float attackDamage;
    private final int enchantibility;
    private final Supplier<Ingredient> repairMaterial;

    SWEMItemTier(int harvestLevel, int maxUses, float efficiency, float attackDamage, int enchantibility, Supplier<Ingredient> repairMaterial){
        this.harvestLevel = harvestLevel;
        this.maxUses = maxUses;
        this.efficiency = efficiency;
        this.attackDamage = attackDamage;
        this.enchantibility = enchantibility;
        this.repairMaterial = repairMaterial;
    }

    @Override
    public int getUses() {
        return maxUses;
    }

    @Override
    public float getSpeed() {
        return efficiency;
    }

    @Override
    public float getAttackDamageBonus() {
        return attackDamage;
    }

    @Override
    public int getLevel() {
        return harvestLevel;
    }

    @Override
    public int getEnchantmentValue() {
        return enchantibility;
    }

    @Override
    public Ingredient getRepairIngredient() {
        return repairMaterial.get();
    }
}
