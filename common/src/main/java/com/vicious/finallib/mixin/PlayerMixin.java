package com.vicious.finallib.mixin;

import com.vicious.finallib.common.bridge.IPlayerMixin;
import com.vicious.finallib.common.guisync.GUISynchronizer;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(Player.class)
public class PlayerMixin implements IPlayerMixin {
    @Unique
    private GUISynchronizer finallib$synchronizer;

    @Override
    public @Nullable GUISynchronizer finalLib$getGUISynchronizer() {
        return finallib$synchronizer;
    }

    @Override
    public void finalLib$setGUISynchronizer(@Nullable GUISynchronizer synchronizer) {
        finallib$synchronizer=synchronizer;
    }
}
