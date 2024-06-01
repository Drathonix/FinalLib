package com.vicious.finallib.common.nbt.reflect;

import com.vicious.finallib.common.nbt.access.INBTAccessor;

import java.lang.reflect.Field;

public record ExposedNBTField(Field field, NBTField annotation, Typing typing) {

    public Class<?> getType(){
        return field.getType();
    }

    public String getName(){
        return field.getName();
    }

    public boolean canWrite(INBTAccessor accessor){
        return accessor.supercedes(annotation.writeLevel());
    }

    public boolean canRead(INBTAccessor accessor){
        return accessor.supercedes(annotation.readLevel());
    }

    public void set(Object object, Object value) {
        try {
            field.set(object,value);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public Object get(Object object) {
        try {
            return field.get(object);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public Class<?>[] getTyping() {
        if(typing != null){
            return typing.value();
        }
        else{
            return new Class<?>[0];
        }
    }
}
