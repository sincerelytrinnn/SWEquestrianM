package com.alaharranhonor.swem.loot;

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

import com.google.gson.JsonObject;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootContext;
import net.minecraft.loot.conditions.ILootCondition;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.loot.GlobalLootModifierSerializer;
import net.minecraftforge.common.loot.LootModifier;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class GrassDropsModifier extends LootModifier {
    private final Item itemToAdd;

    /**
     * Constructs a LootModifier.
     *
     * @param conditionsIn the ILootConditions that need to be matched before the loot is modified.
     */
    protected GrassDropsModifier(ILootCondition[] conditionsIn, Item itemToAdd) {
        super(conditionsIn);
        this.itemToAdd = itemToAdd;
    }

    @NotNull
    @Override
    protected List<ItemStack> doApply(List<ItemStack> generatedLoot, LootContext context) {
        generatedLoot.add(new ItemStack(this.itemToAdd));
        return generatedLoot;
    }

    public static class Serializer extends GlobalLootModifierSerializer<GrassDropsModifier> {

        @Override
        public GrassDropsModifier read(
                ResourceLocation location, JsonObject object, ILootCondition[] ailootcondition) {
            Item itemToAdd =
                    ForgeRegistries.ITEMS.getValue(
                            new ResourceLocation((JSONUtils.getAsString(object, "name"))));
            return new GrassDropsModifier(ailootcondition, itemToAdd);
        }

        @Override
        public JsonObject write(GrassDropsModifier instance) {
            JsonObject json = makeConditions(instance.conditions);
            json.addProperty("name", ForgeRegistries.ITEMS.getKey(instance.itemToAdd).toString());
            return json;
        }
    }
}
