package com.vicious.finallib.client.utils.impl;

import com.vicious.finallib.client.utils.IBounds;

import java.util.*;

public class MultiBounds implements IBounds {
    private int shiftX;
    private int shiftY;
    public int lX = Integer.MAX_VALUE;
    public int lY = Integer.MAX_VALUE;
    public int gX = Integer.MIN_VALUE;
    public int gY = Integer.MIN_VALUE;

    private final List<IBounds> bounds = new ArrayList<>();

    public MultiBounds(){}

    public MultiBounds(Collection<IBounds> vals){
        vals.forEach(this::add);
    }

    @Override
    public boolean isWithin(int x, int y) {
        for (IBounds bound : bounds) {
            if(bound.isWithin(x,y)){
                return true;
            }
        }
        return false;
    }

    public MultiBounds add(IBounds bounds){
        bounds.shift(shiftX,shiftY);
        this.bounds.add(bounds);
        recalc();
        return this;
    }

    @Override
    public void shift(int x, int y) {
        this.shiftX+=x;
        this.shiftY+=y;
        for (IBounds bound : bounds) {
            bound.shift(x,y);
        }
        recalc();
    }

    public MultiBounds remove(IBounds bounds){
        this.bounds.remove(bounds);
        recalc();
        return this;
    }

    public MultiBounds clear(){
        bounds.clear();
        return this;
    }

    private void recalc(){
        lX = Integer.MAX_VALUE;
        lY = Integer.MAX_VALUE;
        gX = Integer.MIN_VALUE;
        gY = Integer.MIN_VALUE;
        bounds.forEach(this::calc);
    }

    private void calc(IBounds bounds){
        lX = Math.min(bounds.getLeastX(),getLeastX());
        lY = Math.min(bounds.getLeastY(),getLeastY());
        gX = Math.min(bounds.getGreatestX(),getGreatestX());
        gY = Math.min(bounds.getGreatestY(),getGreatestY());
    }

    @Override
    public int getLeastX() {
        return lX;
    }

    @Override
    public int getLeastY() {
        return lY;
    }

    @Override
    public int getGreatestX() {
        return gX;
    }

    @Override
    public int getGreatestY() {
        return gY;
    }
}
