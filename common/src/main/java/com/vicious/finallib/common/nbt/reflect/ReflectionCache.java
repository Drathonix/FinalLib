package com.vicious.finallib.common.nbt.reflect;

import com.vicious.finallib.common.util.ClassMap;

import java.util.IdentityHashMap;
import java.util.Map;

public class ReflectionCache {
    private static final ClassMap<Manifest> manifests = new ClassMap<>();

    public static Manifest getManifest(Object o){
        Class<?> type;
        if(o instanceof Class<?>){
            type = (Class<?>) o;
        }
        else{
            type = o.getClass();
        }
        return manifests.computeIfAbsent(type,Manifest::new);
    }
}
