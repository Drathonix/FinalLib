package com.vicious.finallib.client.gui.widget;

import com.vicious.finallib.client.gui.widget.enums.WidgetFlag;
import com.vicious.finallib.client.utils.impl.MultiBounds;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.function.Consumer;

public class CompoundWidget extends WidgetBase implements IHasChildren<IWidget>{
    protected Set<IWidget> children = new HashSet<>();

    public CompoundWidget(){
        this.bounds=new MultiBounds();
    }

    public IWidget getWidgetAt(int x, int y){
        for (IWidget child : children) {
            if(child.getBounds().isWithin(x,y)){
                return child;
            }
        }
        return this;
    }

    public IWidget getWidgetAt(int x, int y, WidgetFlag flag){
        for (IWidget child : children) {
            if(child.getBounds().isWithin(x,y) && child.isFlagEnabled(flag)){
                return child;
            }
        }
        return this;
    }

    public void descend(Consumer<IWidget> action){
        for (IWidget child : children) {
            if(child instanceof CompoundWidget){
                ((CompoundWidget) child).descend(action);
            }
            else{
                action.accept(child);
            }
        }
        action.accept(this);
    }

    public void descend(WidgetFlag flag, Consumer<IWidget> action){
        if(isFlagDisabled(flag)){
            return;
        }
        for (IWidget child : children) {

            if(child instanceof CompoundWidget){
                ((CompoundWidget) child).descend(flag, action);
            }
            else{
                if(child.isFlagDisabled(flag)){
                    continue;
                }
                action.accept(child);
            }
        }
        action.accept(this);
    }

    @Override
    public @NotNull Collection<IWidget> getChildren() {
        return children;
    }
}
