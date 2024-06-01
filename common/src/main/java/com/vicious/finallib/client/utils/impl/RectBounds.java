package com.vicious.finallib.client.utils.impl;

import com.vicious.finallib.client.utils.IBounds;

import java.util.Objects;

public class RectBounds implements IBounds {
    public int lX;
    public int lY;
    public int gX;
    public int gY;

    public RectBounds(int lX, int lY, int gX, int gY) {
        this.lX = lX;
        this.lY = lY;
        this.gX = gX;
        this.gY = gY;
    }

    @Override
    public IBounds add(IBounds bounds) {
        int lX = Math.min(bounds.getLeastX(),getLeastX());
        int lY = Math.min(bounds.getLeastY(),getLeastY());
        int gX = Math.min(bounds.getGreatestX(),getGreatestX());
        int gY = Math.min(bounds.getGreatestY(),getGreatestY());
        return new RectBounds(lX,lY,gX,gY);
    }

    @Override
    public void shift(int x, int y) {
        this.lX+=x;
        this.lY+=y;
        this.gX+=x;
        this.gY+=y;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RectBounds that = (RectBounds) o;
        return lX == that.lX && lY == that.lY && gX == that.gX && gY == that.gY;
    }

    @Override
    public int hashCode() {
        return Objects.hash(lX, lY, gX, gY);
    }
}
