package com.alaharranhonor.swem.entities.progression.leveling;

import com.alaharranhonor.swem.entities.SWEMHorseEntityBase;
import com.alaharranhonor.swem.util.initialization.SWEMItems;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;

public class AffinityLeveling implements ILeveling{

	private SWEMHorseEntityBase horse;
	private EntityDataManager dataManager;
	public static final DataParameter<Integer> LEVEL = EntityDataManager.createKey(SWEMHorseEntityBase.class, DataSerializers.VARINT);
	public static final DataParameter<Float> XP = EntityDataManager.createKey(SWEMHorseEntityBase.class, DataSerializers.FLOAT);
	private float[] requiredXpArray = new float[]{100, 500, 1000, 1500, 2000, 3000, 4000, 5000, 7000, 10000, 13000, 17000};
	private String[] levelNames = new String[] {"Unwilling", "Reluctant", "Tolerant", "Indifferent", "Accepting",  "Willing",  "Committed", "Trusted",  "Friends",  "Best Friends", "Inseparable", "Bonded", };
	private float[] obeyDebuff = new float[] {1.0f, 0.9f, 0.75f, 0.65f, 0.5f, 0.4f, 0.35f, 0.3f, 0.25f, 0.2f, 0.1f, 0};

	public static final DataParameter<ItemStack> CURRENT_DESENSITIZING_ITEM = EntityDataManager.createKey(SWEMHorseEntityBase.class, DataSerializers.ITEMSTACK);
	private int currentSwipes = 0;
	private int[] daysSwiped = new int[5];



	public AffinityLeveling(SWEMHorseEntityBase horse) {
		this.horse = horse;
		this.dataManager = this.horse.getDataManager();
	}
	@Override
	public boolean addXP(float amount) {
		if (this.getLevel() == this.getMaxLevel()) return false;
		this.setXp(this.getXp() + amount);
		return this.checkLevelUp();
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

	@Override
	public int getLevel() {
		return this.dataManager.get(LEVEL);
	}

	public void setLevel(int level) {
		this.dataManager.set(LEVEL, level);
	}


	@Override
	public int getMaxLevel() {
		return 11;
	}

	@Override
	public float getXp() {
		return this.dataManager.get(XP);
	}

	public void setXp(float xp) {
		this.dataManager.set(XP, xp);
	}

	@Override
	public float getRequiredXp() {
		if (this.getLevel() == this.getMaxLevel()) {
			return -1.0f;
		}
		return this.requiredXpArray[this.dataManager.get(LEVEL)];
	}

	@Override
	public String getLevelName() {
		if (this.getLevel() == this.getMaxLevel()) {
			return this.levelNames[this.getMaxLevel() - 1];
		}
		return this.levelNames[this.dataManager.get(LEVEL)];
	}

	public float getDebuff() {
		return this.obeyDebuff[this.dataManager.get(LEVEL)];
	}

	public ItemStack getCurrentDesensitizingItem() {
		return this.dataManager.get(CURRENT_DESENSITIZING_ITEM);
	}

	private void setCurrentDesensitizingItem(ItemStack stack) {
		this.dataManager.set(CURRENT_DESENSITIZING_ITEM, stack);
	}

	public void resetCurrentSwipes() {
		this.currentSwipes = 0;
	}

	public void desensitize(ItemStack stack) {
		if ((this.getCurrentDesensitizingItem().getItem() != stack.getItem() && !this.getCurrentDesensitizingItem().isEmpty()) || this.currentSwipes >= 7) {
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
		compound.put("desensiztingItem", this.dataManager.get(CURRENT_DESENSITIZING_ITEM).write(nbt));
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
			this.setCurrentDesensitizingItem(ItemStack.read((CompoundNBT) compound.get("desensiztingItem")));
		}
		if (compound.contains("currentSwipes")) {
			this.currentSwipes = compound.getInt("currentSwipes");
		}
		if (compound.contains("daysSwiped")) {
			this.daysSwiped = compound.getIntArray("daysSwiped");
		}
	}
}
