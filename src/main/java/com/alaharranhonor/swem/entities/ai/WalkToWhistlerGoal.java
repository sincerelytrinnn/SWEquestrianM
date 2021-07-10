package com.alaharranhonor.swem.entities.ai;

import com.alaharranhonor.swem.entities.misc.WhistleManager;
import com.alaharranhonor.swem.entities.misc.WhistleManagerProvider;
import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.pathfinding.PathNavigator;
import net.minecraft.util.math.BlockPos;

import java.util.EnumSet;

public class WalkToWhistlerGoal<E extends CreatureEntity> extends Goal {
    private final WhistleManagerProvider<E> whistleManagerProvider;

    public WalkToWhistlerGoal(WhistleManagerProvider<E> whistleManagerProvider) {
        this.whistleManagerProvider = whistleManagerProvider;
        this.setFlags(EnumSet.of(Goal.Flag.MOVE));
    }

    @Override
    public boolean canUse() {
        WhistleManager<E> whistleManager = this.whistleManagerProvider.getWhistleManager();
        BlockPos whistleeblockPos = whistleManager.getWhistlee().blockPosition();
        BlockPos lastKnownWhistlingPosition = whistleManager.getLastKnownWhistlingPosition();

        if (!whistleManager.hasWhistler()) {
            return false;
        }

        BlockPos.Mutable whistleeblockPosMutable = whistleeblockPos.mutable();
        whistleeblockPosMutable.setY(0);

        BlockPos.Mutable lastKnownWhistlingPositionMutable = lastKnownWhistlingPosition.mutable();
        lastKnownWhistlingPositionMutable.setY(0);

        return !whistleeblockPos.equals(lastKnownWhistlingPosition)
                && !whistleeblockPosMutable.closerThan(lastKnownWhistlingPositionMutable,3f);
    }

    @Override
    public boolean canContinueToUse() {
        PathNavigator navigation = this.whistleManagerProvider.getWhistleManager().getWhistlee().getNavigation();
        return super.canContinueToUse() && !navigation.isStuck() && navigation.isInProgress();
    }

    @Override
    public void start() {
        this.whistleManagerProvider.getWhistleManager().moveToLastKnownWhistlingPosition(1);
    }

    @Override
    public void tick() {
        super.tick();
    }

    @Override
    public void stop() {
        this.whistleManagerProvider.getWhistleManager().clearWhisteCaller();
    }
}
