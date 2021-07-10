package com.alaharranhonor.swem.entities.misc;

import net.minecraft.entity.CreatureEntity;

public interface WhistleManagerProvider<E extends CreatureEntity> {
    WhistleManager<E> getWhistleManager();
}
