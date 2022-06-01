package com.alaharranhonor.swem.entities.progression.leveling;

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

import com.alaharranhonor.swem.entities.SWEMHorseEntityBase;
import com.alaharranhonor.swem.util.registry.SWEMItems;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.server.ServerWorld;

public class AffinityLeveling implements ILeveling {

  private final SWEMHorseEntityBase horse;
  private final EntityDataManager dataManager;
  public static final DataParameter<Integer> LEVEL =
      EntityDataManager.defineId(SWEMHorseEntityBase.class, DataSerializers.INT);
  public static final DataParameter<Float> XP =
      EntityDataManager.defineId(SWEMHorseEntityBase.class, DataSerializers.FLOAT);
  private final float[] requiredXpArray =
      new float[] {100, 500, 1000, 1500, 2000, 3000, 4000, 5000, 7000, 10000, 13000, 17000};
  private final String[] levelNames =
      new String[] {
        "Unwilling",
        "Reluctant",
        "Tolerant",
        "Indifferent",
        "Accepting",
        "Willing",
        "Committed",
        "Trusted",
        "Friends",
        "Best Friends",
        "Inseparable",
        "Bonded",
      };
  private final float[] obeyDebuff =
      new float[] {1.0f, 0.9f, 0.75f, 0.65f, 0.5f, 0.4f, 0.35f, 0.3f, 0.25f, 0.2f, 0.1f, 0};

  public static final DataParameter<ItemStack> CURRENT_DESENSITIZING_ITEM =
      EntityDataManager.defineId(SWEMHorseEntityBase.class, DataSerializers.ITEM_STACK);
  private int currentSwipes = 0;
  private int[] daysSwiped = new int[5];
  private int timesBrushed = 0;

  /**
   * Instantiates a new Affinity leveling.
   *
   * @param horse the horse
   */
  public AffinityLeveling(SWEMHorseEntityBase horse) {
    this.horse = horse;
    this.dataManager = this.horse.getEntityData();
  }

  /**
   * Brush boolean.
   *
   * @return the boolean
   */
  public boolean brush() {
    if (this.timesBrushed < 7) {
      if (this.timesBrushed >= 5) {
        horse.emitMehParticles((ServerWorld) horse.level, 4);
      } else if (this.timesBrushed >= 3) {
        horse.emitYayParticles((ServerWorld) horse.level, 4);
      } else {
        horse.emitWootParticles((ServerWorld) horse.level, 4);
      }
      this.timesBrushed++;
      this.addXP(10 - timesBrushed);
      return true;
    } else {
      horse.emitBadParticles((ServerWorld) horse.level, 4);
      return false;
    }
  }

  @Override
  public boolean addXP(float amount) {
    if (this.getLevel() == this.getMaxLevel()) return false;
    this.setXp(this.getXp() + amount);
    return this.checkLevelUp();
  }

  @Override
  public void removeXp(float amount) {
    this.setXp(this.getXp() - amount);
  }

  @Override
  public boolean checkLevelUp() {
    if (this.getXp() >= this.getRequiredXp() && this.getLevel() < this.getMaxLevel()) {
      this.levelUp();
      return true;
    } else {
      return false;
    }
  }

  @Override
  public void levelUp() {
    float excessXP = this.getXp() - this.getRequiredXp();
    this.setLevel(this.getLevel() + 1);
    this.setXp(excessXP);
  }

  /**
   * Level down.
   *
   * @param excessXP the excess xp
   */
  private void levelDown(float excessXP) {
    this.setLevel(this.getLevel() - 1);
    this.setXp(this.getRequiredXp() + excessXP);
  }

  @Override
  public int getLevel() {
    return this.dataManager.get(LEVEL);
  }

  /**
   * Sets level.
   *
   * @param level the level
   */
  public void setLevel(int level) {
    this.dataManager.set(LEVEL, MathHelper.clamp(level, 0, this.getMaxLevel()));
    if (level == 2) {
      if (this.horse.progressionManager.getJumpLeveling().getLevel() < 1) {
        this.horse.progressionManager.getJumpLeveling().levelUp();
      }
    }
  }

  @Override
  public int getMaxLevel() {
    return 11;
  }

  @Override
  public float getXp() {
    return this.dataManager.get(XP);
  }

  /**
   * Sets xp.
   *
   * @param xp the xp
   */
  public void setXp(float xp) {
    if (xp < 0 && this.getLevel() > 0) {
      this.levelDown(xp);
      return;
    }
    this.dataManager.set(XP, Math.max(xp, 0));
  }

  @Override
  public float getRequiredXp() {
    return this.requiredXpArray[this.dataManager.get(LEVEL)];
  }

  @Override
  public String getLevelName() {
    if (this.getLevel() == this.getMaxLevel()) {
      return this.levelNames[this.levelNames.length - 1];
    }
    return this.levelNames[this.dataManager.get(LEVEL)];
  }

  /**
   * Gets debuff.
   *
   * @return the debuff
   */
  public float getDebuff() {
    return this.obeyDebuff[this.dataManager.get(LEVEL)];
  }

  /**
   * Gets current desensitizing item.
   *
   * @return the current desensitizing item
   */
  public ItemStack getCurrentDesensitizingItem() {
    return this.dataManager.get(CURRENT_DESENSITIZING_ITEM);
  }

  /**
   * Sets current desensitizing item.
   *
   * @param stack the stack
   */
  private void setCurrentDesensitizingItem(ItemStack stack) {
    this.dataManager.set(CURRENT_DESENSITIZING_ITEM, stack);
  }

  /** Reset daily. */
  public void resetDaily() {
    this.currentSwipes = 0;
    this.timesBrushed = 0;
  }

  /**
   * Desensitize.
   *
   * @param stack the stack
   */
  public void desensitize(ItemStack stack) {
    if ((this.getCurrentDesensitizingItem().getItem() != stack.getItem()
            && !this.getCurrentDesensitizingItem().isEmpty())
        || this.currentSwipes >= 7) {
      // TODO: SEND STATUS MESSAGE TO CLIENT.
      return;
    }
    this.currentSwipes++;
    if (stack.getItem() == SWEMItems.BELLS.get() && this.daysSwiped[0] != -1) {
      this.setCurrentDesensitizingItem(stack);
      if (this.currentSwipes == 7) {
        this.daysSwiped[0]++;
        if (this.daysSwiped[0] == 3) {
          this.daysSwiped[0] = -1;
          return;
        }
      }
    } else if (stack.getItem() == SWEMItems.HOOLAHOOP.get() && this.daysSwiped[1] != -1) {
      this.setCurrentDesensitizingItem(stack);
      if (this.currentSwipes == 7) {
        this.daysSwiped[1]++;
        if (this.daysSwiped[1] == 3) {
          this.daysSwiped[1] = -1;
          return;
        }
      }
    } else if (stack.getItem() == SWEMItems.POMPOM.get() && this.daysSwiped[2] != -1) {
      this.setCurrentDesensitizingItem(stack);
      if (this.currentSwipes == 7) {
        this.daysSwiped[2]++;
        if (this.daysSwiped[2] == 3) {
          this.daysSwiped[2] = -1;
          return;
        }
      }
    } else if (stack.getItem() == SWEMItems.SHOPPING_BAG.get() && this.daysSwiped[3] != -1) {
      this.setCurrentDesensitizingItem(stack);
      if (this.currentSwipes == 7) {
        this.daysSwiped[3]++;
        if (this.daysSwiped[3] == 3) {
          this.daysSwiped[3] = -1;
          return;
        }
      }
    } else if (stack.getItem() == SWEMItems.TARP.get() && this.daysSwiped[4] != -1) {
      this.setCurrentDesensitizingItem(stack);
      if (this.currentSwipes == 7) {
        this.daysSwiped[4]++;
        if (this.daysSwiped[4] == 3) {
          this.daysSwiped[4] = -1;
          return;
        }
      }
    }

    this.addXP(250);
  }

  @Override
  public void write(CompoundNBT compound) {
    compound.putInt("AffinityLevel", this.dataManager.get(LEVEL));
    compound.putFloat("AffinityXP", this.dataManager.get(XP));
    CompoundNBT nbt = new CompoundNBT();
    compound.put("desensiztingItem", this.dataManager.get(CURRENT_DESENSITIZING_ITEM).save(nbt));
    compound.putInt("currentSwipes", this.currentSwipes);
    compound.putIntArray("daysSwiped", this.daysSwiped);
  }

  @Override
  public void read(CompoundNBT compound) {
    if (compound.contains("AffinityLevel")) {
      this.setLevel(compound.getInt("AffinityLevel"));
    }
    if (compound.contains("AffinityXP")) {
      this.setXp(compound.getFloat("AffinityXP"));
    }
    if (compound.contains("desensiztingItem")) {
      this.setCurrentDesensitizingItem(
          ItemStack.of((CompoundNBT) compound.get("desensiztingItem")));
    }
    if (compound.contains("currentSwipes")) {
      this.currentSwipes = compound.getInt("currentSwipes");
    }
    if (compound.contains("daysSwiped")) {
      this.daysSwiped = compound.getIntArray("daysSwiped");
    }
  }
}
