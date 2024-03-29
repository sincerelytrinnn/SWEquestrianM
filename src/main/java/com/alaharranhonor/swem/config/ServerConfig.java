package com.alaharranhonor.swem.config;

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

import net.minecraftforge.common.ForgeConfigSpec;

public final class ServerConfig {

    public final ForgeConfigSpec.BooleanValue serverTickFoodNeed;
    public final ForgeConfigSpec.BooleanValue serverTickWaterNeed;
    public final ForgeConfigSpec.BooleanValue serverTickPoopNeed;
    public final ForgeConfigSpec.IntValue serverPoopInterval;
    public final ForgeConfigSpec.BooleanValue serverTickPeeNeed;
    public final ForgeConfigSpec.IntValue serverPeeInterval;
    public final ForgeConfigSpec.BooleanValue halterDependency;
    public final ForgeConfigSpec.BooleanValue needBridleToSteer;
    public final ForgeConfigSpec.BooleanValue blanketBeforeSaddle;
    public final ForgeConfigSpec.BooleanValue saddleBeforeGirthStrap;
    public final ForgeConfigSpec.BooleanValue riderNeedsGirthStrap;
    public final ForgeConfigSpec.BooleanValue lapisCycleCoats;
    public final ForgeConfigSpec.BooleanValue multiplayerHungerThirst;
    public final ForgeConfigSpec.IntValue foalAgeInSeconds;
    public final ForgeConfigSpec.IntValue horseInLoveInSeconds;
    public final ForgeConfigSpec.IntValue maxSpeedXP;
    public final ForgeConfigSpec.IntValue maxJumpXP;
    public final ForgeConfigSpec.IntValue maxHealthXP;
    public final ForgeConfigSpec.IntValue maxAffinityXP;
    final ForgeConfigSpec.BooleanValue serverEnableAmethystOre;
    final ForgeConfigSpec.IntValue serverAmethystVeinSize;
    final ForgeConfigSpec.IntValue serverAmethystVeinCount;
    final ForgeConfigSpec.IntValue serverAmethystBottomHeight;
    final ForgeConfigSpec.IntValue serverAmethystMaxHeight;
    final ForgeConfigSpec.BooleanValue serverEnableCantazariteOre;
    final ForgeConfigSpec.IntValue serverCantazariteVeinSize;
    final ForgeConfigSpec.IntValue serverCantazariteVeinCount;
    final ForgeConfigSpec.IntValue serverCantazariteBottomHeight;
    final ForgeConfigSpec.IntValue serverCantazariteMaxHeight;
    final ForgeConfigSpec.BooleanValue serverEnableSWEMCobbleOre;
    final ForgeConfigSpec.IntValue serverSWEMCobbleVeinSize;
    final ForgeConfigSpec.IntValue serverSWEMCobbleVeinCount;
    final ForgeConfigSpec.IntValue serverSWEMCobbleBottomHeight;
    final ForgeConfigSpec.IntValue serverSWEMCobbleMaxHeight;

    /**
     * Instantiates a new Server config.
     *
     * @param builder the builder
     */
    ServerConfig(ForgeConfigSpec.Builder builder) {

        builder.push("SWEMOreGen");
        this.serverEnableAmethystOre =
                builder
                        .comment("Enable amethyst ore generation?")
                        .translation("swem.config.enableAmethystOre")
                        .define("EnableAmethystOre", true);
        this.serverEnableCantazariteOre =
                builder
                        .comment("Enable cantazarite ore generation?")
                        .translation("swem.config.enableCantazariteOre")
                        .define("EnableCantazariteOre", true);
        this.serverEnableSWEMCobbleOre =
                builder
                        .comment("Enable SWEMCobble ore generation?")
                        .translation("swem.config.enableSWEMCobbleOre")
                        .define("EnableSWEMCobbleOre", true);

        this.serverAmethystVeinSize =
                builder
                        .comment("Amethyst ore vein size")
                        .translation("swem.config.serverAmethystVeinSize")
                        .defineInRange("AmethystVeinSize", 8, 1, Integer.MAX_VALUE);
        this.serverAmethystVeinCount =
                builder
                        .comment("Amethyst ore vein count per chunk")
                        .translation("swem.config.serverAmethystVeinCount")
                        .defineInRange("AmethystVeinCount", 2, 1, Integer.MAX_VALUE);
        this.serverAmethystBottomHeight =
                builder
                        .comment("Amethyst ore minimum height")
                        .translation("swem.config.serverAmethystBottomHeight")
                        .defineInRange("AmethystBottomHeight", 0, 0, Integer.MAX_VALUE);
        this.serverAmethystMaxHeight =
                builder
                        .comment("Amethyst ore maximum height")
                        .translation("swem.config.serverAmethystMaxHeight")
                        .defineInRange("AmethystMaxHeight", 15, 1, Integer.MAX_VALUE);

        this.serverCantazariteVeinSize =
                builder
                        .comment("Cantazarite ore vein size")
                        .translation("swem.config.serverCantazariteVeinSize")
                        .defineInRange("CantazariteVeinSize", 4, 1, Integer.MAX_VALUE);
        this.serverCantazariteVeinCount =
                builder
                        .comment("Cantazarite ore vein count per chunk")
                        .translation("swem.config.serverCantazariteVeinCount")
                        .defineInRange("CantazariteVeinCount", 6, 1, Integer.MAX_VALUE);
        this.serverCantazariteBottomHeight =
                builder
                        .comment("Cantazarite ore minimum height")
                        .translation("swem.config.serverCantazariteBottomHeight")
                        .defineInRange("CantazariteBottomHeight", 0, 0, Integer.MAX_VALUE);
        this.serverCantazariteMaxHeight =
                builder
                        .comment("Cantazarite ore maximum height")
                        .translation("swem.config.serverCantazariteMaxHeight")
                        .defineInRange("CantazariteMaxHeight", 30, 1, Integer.MAX_VALUE);

        this.serverSWEMCobbleVeinSize =
                builder
                        .comment("SWEMCobble ore vein size")
                        .translation("swem.config.serverSWEMCobbleVeinSize")
                        .defineInRange("SWEMCobbleVeinSize", 7, 1, Integer.MAX_VALUE);
        this.serverSWEMCobbleVeinCount =
                builder
                        .comment("SWEMCobble ore vein count per chunk")
                        .translation("swem.config.serverSWEMCobbleVeinCount")
                        .defineInRange("SWEMCobbleVeinCount", 12, 1, Integer.MAX_VALUE);
        this.serverSWEMCobbleBottomHeight =
                builder
                        .comment("SWEMCobble ore minimum height")
                        .translation("swem.config.serverSWEMCobbleBottomHeight")
                        .defineInRange("SWEMCobbleBottomHeight", 50, 1, Integer.MAX_VALUE);
        this.serverSWEMCobbleMaxHeight =
                builder
                        .comment("SWEMCobble ore maximum height")
                        .translation("swem.config.serverSWEMCobbleMaxHeight")
                        .defineInRange("SWEMCobbleMaxHeight", 128, 1, Integer.MAX_VALUE);

        builder.pop();

        builder.push("Config");
        this.serverTickFoodNeed =
                builder
                        .comment("Enable Food need ticking on swem horses?")
                        .translation("swem.config.enableFoodTick")
                        .define("foodTick", true);
        this.serverTickWaterNeed =
                builder
                        .comment("Enable Water need ticking on swem horses?")
                        .translation("swem.config.enableWaterTick")
                        .define("waterTick", true);
        this.serverTickPoopNeed =
                builder
                        .comment("Enable Poop ticking on swem horses?")
                        .translation("swem.config.enablePoopTick")
                        .define("poopTick", true);
        this.serverPoopInterval =
                builder
                        .comment("Specify in seconds the interval between each poop cycle.")
                        .translation("swem.config.poopInterval")
                        .defineInRange("poopInterval", 960, 1, Integer.MAX_VALUE);
        this.serverTickPeeNeed =
                builder
                        .comment("Enable Pee ticking on swem horses?")
                        .translation("swem.config.enablePeeTick")
                        .define("peeTick", true);
        this.serverPeeInterval =
                builder
                        .comment("Specify in seconds the interval between each pee cycle.")
                        .translation("swem.config.peeInterval")
                        .defineInRange("peeInterval", 930, 1, Integer.MAX_VALUE);
        builder.comment(
                "Set the option below to true, if you just want a piece of lapis lazuli to cycle the coats.");
        this.lapisCycleCoats =
                builder
                        .comment("Enable Lapis Lazuli coat cycling?")
                        .translation("swem.config.enableLapisCycle")
                        .define("lapisCycle", true);
        this.multiplayerHungerThirst =
                builder
                        .comment("Make hunger thirst system base on IRL days? (Preferred option for servers.)")
                        .translation("swem.config.multiplayerHungerThirst")
                        .define("multiPlayerHungerThirst", false);
        this.foalAgeInSeconds =
                builder
                        .comment(
                                "Specify how many seconds it takes for the foal to growp up? (Default is 1800 seconds = 30 minutes)")
                        .translation("swem.config.foalAgeInSeconds")
                        .defineInRange("foalAgeInSeconds", 1_800, 1, Integer.MAX_VALUE);
        this.horseInLoveInSeconds =
                builder
                        .comment(
                                "Specify how many seconds it takes for the parent to be able to breed again? (Default is 1800 seconds = 30 minutes)")
                        .translation("swem.config.horseInLoveInSeconds")
                        .defineInRange("horseInLoveInSeconds", 1_800, 1, Integer.MAX_VALUE);
        this.maxSpeedXP =
                builder
                        .comment(
                                "Specify how much xp is needed for reaching max speed level. The level ranges will be decided by an internal formula.")
                        .translation("swem.config.maxSpeedXP")
                        .defineInRange("maxSpeedXP", 13_500, 1, Integer.MAX_VALUE);
        this.maxJumpXP =
                builder
                        .comment(
                                "Specify how much xp is needed for reaching max jump level. The level ranges will be decided by an internal formula.")
                        .translation("swem.config.maxJumpXP")
                        .defineInRange("maxJumpXP", 13_500, 1, Integer.MAX_VALUE);
        this.maxHealthXP =
                builder
                        .comment(
                                "Specify how much xp is needed for reaching max health level. The level ranges will be decided by an internal formula.")
                        .translation("swem.config.maxHealthXP")
                        .defineInRange("maxHealthXP", 13_500, 1, Integer.MAX_VALUE);
        this.maxAffinityXP =
                builder
                        .comment(
                                "Specify how much xp that last level is. The level ranges and total xp required will be decided by an internal formula.")
                        .translation("swem.config.maxAffinityXP")
                        .defineInRange("maxAffinityXP", 17_000, 1, Integer.MAX_VALUE);
        builder.push("Tack Dependencies");
        this.halterDependency =
                builder
                        .comment("Enable/Disable the halter, being needed for any other tack.")
                        .translation("swem.config.halterDep")
                        .define("HalterDependency", true);
        this.needBridleToSteer =
                builder
                        .comment(
                                "Enable/Disable the need of a bridle in order to steer, the horse. (If disabled, you would still need a saddle.)")
                        .translation("swem.config.needBridleToSteer")
                        .define("NeedBridleToSteer", true);
        this.blanketBeforeSaddle =
                builder
                        .comment("Enable/Disable the need of a blanket, before saddling up.")
                        .translation("swem.config.needBlanket")
                        .define("NeedBlanket", true);
        this.saddleBeforeGirthStrap =
                builder
                        .comment(
                                "Enable/Disable the need of putting the saddle on, before you can put a girth strap on.")
                        .translation("swem.config.needSaddle")
                        .define("NeedSaddleForGirthStrap", true);
        this.riderNeedsGirthStrap =
                builder
                        .comment(
                                "Enable/Disable the rider falling off the horse, in case there is no girth strap equipped.")
                        .translation("swem.config.riderFallsOff")
                        .define("RiderFallingOff", true);
        builder.pop();

        builder.pop();
    }
}
