package com.alaharranhonor.swem.entities.misc;

import com.alaharranhonor.swem.entities.ai.WalkToWhistlerGoal;
import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.ai.goal.GoalSelector;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.pathfinding.PathNavigator;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.Nullable;

public class WhistleManager<E extends CreatureEntity> {
    private PlayerEntity whisteCaller;
    private BlockPos lastKnownWhistlingPosition;
    private final E whistlee;

    public WhistleManager(E whistlee) {
        this.whistlee = whistlee;
    }

    @Nullable
    public BlockPos getLastKnownWhistlingPosition() {
        return this.lastKnownWhistlingPosition;
    }

    @Nullable
    public PlayerEntity getWhisteCaller() {
        return this.whisteCaller;
    }

    public boolean hasWhistler() {
        return this.whisteCaller != null && this.lastKnownWhistlingPosition != null;
    }

    public E getWhistlee() {
        return this.whistlee;
    }

    public void setWhisteCaller(PlayerEntity whisteCaller) {
        this.whisteCaller = whisteCaller;
        this.lastKnownWhistlingPosition = whisteCaller.blockPosition();
    }

    public void clearWhisteCaller() {
        this.whisteCaller = null;
        this.lastKnownWhistlingPosition = null;
        this.whistlee.getNavigation().stop();
    }

    public void moveToLastKnownWhistlingPosition(double velocity) {
        PathNavigator navigation = this.getWhistlee().getNavigation();
        navigation.stop();
        navigation.moveTo(
                this.lastKnownWhistlingPosition.getX(),
                this.lastKnownWhistlingPosition.getY(),
                this.lastKnownWhistlingPosition.getZ(),
                velocity
        );
    }
}
