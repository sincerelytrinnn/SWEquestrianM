package com.alaharranhonor.swem.util.registry;

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
import net.minecraft.entity.item.PaintingType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class SWEMPaintings {

    public static final DeferredRegister<PaintingType> PAINTINGS =
            DeferredRegister.create(ForgeRegistries.PAINTING_TYPES, SWEM.MOD_ID);
    public static final RegistryObject<PaintingType> LUCY_REAR =
            PAINTINGS.register("lucy_rear_oak", () -> new PaintingType(64, 48));
    public static final RegistryObject<PaintingType> LUCY_SMILE =
            PAINTINGS.register("lucy_smile_oak", () -> new PaintingType(16, 16));
    public static final RegistryObject<PaintingType> LUCY_WITH_BARN =
            PAINTINGS.register("lucy_with_barn_oak", () -> new PaintingType(16, 32));
    public static final RegistryObject<PaintingType> LUCY_LUNGE =
            PAINTINGS.register("lucy_lunge_oak", () -> new PaintingType(32, 32));
    public static final RegistryObject<PaintingType> LUCY_IN_LAKE =
            PAINTINGS.register("lucy_in_lake_oak", () -> new PaintingType(32, 16));
    public static final RegistryObject<PaintingType> LUCY_CHARGING =
            PAINTINGS.register("lucy_charging_oak", () -> new PaintingType(16, 32));
    public static final RegistryObject<PaintingType> LUCY_AND_JESSIE =
            PAINTINGS.register("lucy_and_jessie_oak", () -> new PaintingType(64, 64));
    public static final RegistryObject<PaintingType> CHARLIE_DARK_OAK =
            PAINTINGS.register("charlie_dark_oak", () -> new PaintingType(32, 32)); // here
    public static final RegistryObject<PaintingType> GINGER_SPRUCE =
            PAINTINGS.register("ginger_spruce", () -> new PaintingType(32, 32));
    public static final RegistryObject<PaintingType> JIGGS_ARMORED_SPRUCE =
            PAINTINGS.register("jiggs_armored_spruce", () -> new PaintingType(32, 32));
    public static final RegistryObject<PaintingType> LADY_IN_BLANKET_BIRCH =
            PAINTINGS.register("lady_in_blanket_birch", () -> new PaintingType(16, 32));
    public static final RegistryObject<PaintingType> PHOENIX_WESTERN_DARK_OAK =
            PAINTINGS.register("phoenix_western_dark_oak", () -> new PaintingType(16, 32));
    public static final RegistryObject<PaintingType> SMOKEY_ACACIA =
            PAINTINGS.register("smokey_acacia", () -> new PaintingType(64, 48)); // Wrong format
    public static final RegistryObject<PaintingType> SPIRIT_SPRUCE =
            PAINTINGS.register("spirit_spruce", () -> new PaintingType(64, 48)); // Wrong format
    public static final RegistryObject<PaintingType> TROUBADOUR_AND_BELLA_DARK_OAK =
            PAINTINGS.register("troubadour_and_bella_dark_oak", () -> new PaintingType(64, 48));
    public static final RegistryObject<PaintingType> ZIPPY_CANTER_DARK_OAK =
            PAINTINGS.register("zippy_canter_dark_oak", () -> new PaintingType(32, 32));
    public static final RegistryObject<PaintingType> ZIPPY_JUMP_DARK_OAK =
            PAINTINGS.register("zippy_jump_dark_oak", () -> new PaintingType(64, 48));
    public static final RegistryObject<PaintingType> ZIPPY_WINNER_DARK_OAK =
            PAINTINGS.register("zippy_winner_dark_oak", () -> new PaintingType(16, 32));
    public static final RegistryObject<PaintingType> LADY_IN_MEMORY =
            PAINTINGS.register("lady_in_memory", () -> new PaintingType(16, 32));

    /**
     * Init.
     *
     * @param modBus the mod bus
     */
    public static void init(IEventBus modBus) {
        PAINTINGS.register(modBus);
    }
}
