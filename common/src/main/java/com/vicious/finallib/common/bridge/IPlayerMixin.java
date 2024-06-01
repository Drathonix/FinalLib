package com.vicious.finallib.common.bridge;

import com.vicious.finallib.common.guisync.GUISynchronizer;
import org.jetbrains.annotations.Nullable;

public interface IPlayerMixin {
    @Nullable GUISynchronizer finalLib$getGUISynchronizer();
    default boolean hasGUISynchronizer(){
        return finalLib$getGUISynchronizer() != null;
    }
    void finalLib$setGUISynchronizer(@Nullable GUISynchronizer synchronizer);

}
