package com.vicious.finallib.client.gui.widget;

import org.jetbrains.annotations.NotNull;

import java.util.Collection;

public interface IHasChildren<T> {
    @NotNull Collection<T> getChildren();
    default void addChild(@NotNull T child){
        getChildren().add(child);
    }
    default boolean removeChild(@NotNull T child){
        return getChildren().remove(child);
    }
}
