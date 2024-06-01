package com.vicious.finallib.common.nbt.reflect;

import com.vicious.finallib.common.nbt.access.Access;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface NBTField {
    Access writeLevel() default Access.WRITE;
    Access readLevel() default Access.READ;
}
