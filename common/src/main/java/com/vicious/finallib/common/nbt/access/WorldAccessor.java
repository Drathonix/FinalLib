package com.vicious.finallib.common.nbt.access;

public class WorldAccessor implements INBTAccessor{
    public static final WorldAccessor INSTANCE = new WorldAccessor();
    private WorldAccessor(){}

    @Override
    public Access getAccess() {
        return Access.WRITE;
    }

    @Override
    public String getName() {
        return "SERVER";
    }
}
