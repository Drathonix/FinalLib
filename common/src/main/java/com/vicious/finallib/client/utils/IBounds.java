package com.vicious.finallib.client.utils;

import com.vicious.finallib.client.utils.impl.RectBounds;

public interface IBounds {
    default boolean isWithin(int x, int y){
        return x >= this.getLeastX()
                && y >= this.getLeastY()
                && x <= this.getGreatestX()
                && y <= this.getGreatestY();
    }
    default boolean intersects(IBounds other){
        return isWithin(other.getLeastX(), other.getLeastY()) && isWithin(other.getGreatestX(),other.getGreatestY());
    }
    default IBounds add(IBounds other){
        throw new UnsupportedOperationException("Adding bounds not implemented for " + getClass());
    }

    void shift(int x, int y);

    default void setLeastPos(int x, int y){
        shift(x-getLeastX(),y-getGreatestY());
    }

    int getLeastX();
    int getLeastY();
    int getGreatestX();
    int getGreatestY();

    default int getHeight(){
        return getGreatestY()-getLeastY();
    }

    default int getWidth(){
        return getGreatestX()-getLeastX();
    }

    default RectBounds asRectBounds(){
        return new RectBounds(getLeastX(),getLeastY(),getGreatestX(),getGreatestY());
    }
}
