package com.vicious.finallib.common.nbt.access;

public interface INBTAccessor {
    default Access getAccess(){
        return Access.NONE;
    }
    default String getName(){
        return "unnamed";
    }

    default boolean supercedes(Access access){
        return getAccess().supercedes(access);
    }
}
