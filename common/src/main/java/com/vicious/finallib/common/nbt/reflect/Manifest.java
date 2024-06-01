package com.vicious.finallib.common.nbt.reflect;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class Manifest {
    private final Map<String, ExposedNBTField> exposed = new HashMap<>();

    public Manifest(Class<?> cls){
        for (Field declaredField : cls.getDeclaredFields()) {
            declaredField.setAccessible(true);
            if(declaredField.isAnnotationPresent(NBTField.class)){
                exposed.put(declaredField.getName(),new ExposedNBTField(declaredField,declaredField.getAnnotation(NBTField.class),declaredField.getAnnotation(Typing.class)));
            }
        }
    }

    public ExposedNBTField getField(String name){
        return exposed.get(name);
    }

    public Map<String,ExposedNBTField> getFields() {
        return exposed;
    }
}
