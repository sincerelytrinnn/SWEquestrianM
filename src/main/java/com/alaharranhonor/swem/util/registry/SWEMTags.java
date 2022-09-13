
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

package com.alaharranhonor.swem.util.registry;

import com.alaharranhonor.swem.SWEM;
import net.minecraft.item.Item;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.Tags;

public class SWEMTags {
    public static final Tags.IOptionalNamedTag<Item> HALF_BARRELS = ItemTags.createOptional(new ResourceLocation(SWEM.MOD_ID, "half_barrels"));
    public static final Tags.IOptionalNamedTag<Item> ENGLISH_BLANKETS = ItemTags.createOptional(new ResourceLocation(SWEM.MOD_ID, "english_blankets"));
    public static final Tags.IOptionalNamedTag<Item> ENGLISH_LEG_WRAPS = ItemTags.createOptional(new ResourceLocation(SWEM.MOD_ID, "english_leg_wraps"));
    public static final Tags.IOptionalNamedTag<Item> WESTERN_BLANKETS = ItemTags.createOptional(new ResourceLocation(SWEM.MOD_ID, "western/blankets"));
    public static final Tags.IOptionalNamedTag<Item> WESTERN_BREAST_COLLARS = ItemTags.createOptional(new ResourceLocation(SWEM.MOD_ID, "western/breast_collars"));
    public static final Tags.IOptionalNamedTag<Item> WESTERN_BRIDLES = ItemTags.createOptional(new ResourceLocation(SWEM.MOD_ID, "western/bridles"));
    public static final Tags.IOptionalNamedTag<Item> WESTERN_GIRTH_STRAPS = ItemTags.createOptional(new ResourceLocation(SWEM.MOD_ID, "western/girth_straps"));
    public static final Tags.IOptionalNamedTag<Item> WESTERN_LEG_WRAPS = ItemTags.createOptional(new ResourceLocation(SWEM.MOD_ID, "western/leg_wraps"));
    public static final Tags.IOptionalNamedTag<Item> WESTERN_SADDLES = ItemTags.createOptional(new ResourceLocation(SWEM.MOD_ID, "western/saddles"));
    public static final Tags.IOptionalNamedTag<Item> SADDLE_BAGS = ItemTags.createOptional(new ResourceLocation(SWEM.MOD_ID, "saddle_bags"));

}
