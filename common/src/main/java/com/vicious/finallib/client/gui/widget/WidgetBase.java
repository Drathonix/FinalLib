package com.vicious.finallib.client.gui.widget;


import com.vicious.finallib.client.gui.widget.enums.WidgetFlag;
import com.vicious.finallib.client.utils.IBounds;
import com.vicious.finallib.client.utils.impl.RectBounds;
import org.jetbrains.annotations.NotNull;

public class WidgetBase implements IWidget{
    protected IBounds bounds = new RectBounds(0,0,0,0);
    protected boolean[] flags = new boolean[WidgetFlag.values().length];
    protected IWidget parent = null;

    @Override
    public int getX() {
        return bounds.getLeastX();
    }

    @Override
    public int getY() {
        return bounds.getLeastY();
    }

    @Override
    public @NotNull IBounds getBounds() {
        return bounds;
    }

    @Override
    public boolean isFlagEnabled(@NotNull WidgetFlag flag) {
        return flags[flag.ordinal()];
    }

    @Override
    public void enableFlag(@NotNull WidgetFlag flag) {
        flags[flag.ordinal()]=true;
    }

    @Override
    public void disableFlag(@NotNull WidgetFlag flag) {
        flags[flag.ordinal()]=false;
    }

    @Override
    public void setParent(@NotNull IWidget widget) {
        this.parent=widget;
    }

    @Override
    public IWidget getParent() {
        return parent;
    }

    @Override
    public void shift(int x, int y) {
        bounds.shift(x,y);
    }
}
