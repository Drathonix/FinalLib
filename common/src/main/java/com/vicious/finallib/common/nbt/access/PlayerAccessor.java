package com.vicious.finallib.common.nbt.access;

import net.minecraft.world.entity.player.Player;

public class PlayerAccessor implements INBTAccessor{
    private final Access level;
    private final Player player;

    public PlayerAccessor(Player player){
        level = player.hasPermissions(4) ? Access.WRITE : Access.NONE;
        this.player = player;
    }

    @Override
    public Access getAccess() {
        return level;
    }

    @Override
    public String getName() {
        return player.getName().getString();
    }
}
