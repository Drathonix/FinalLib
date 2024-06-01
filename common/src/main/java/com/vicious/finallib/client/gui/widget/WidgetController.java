package com.vicious.finallib.client.gui.widget;

import com.vicious.finallib.client.gui.widget.enums.ClickState;
import com.vicious.finallib.client.gui.widget.enums.MouseButton;
import com.vicious.finallib.client.gui.widget.enums.WidgetFlag;
import net.minecraft.client.Minecraft;
import net.minecraft.client.MouseHandler;

public class WidgetController {
    private static final MouseHandler mouseHandler = Minecraft.getInstance().mouseHandler;
    private double prevX = mouseHandler.xpos();
    private double prevY = mouseHandler.ypos();
    private IWidget focusedWidget;
    private boolean dragging=false;
    private final CompoundWidget rootWidget = new CompoundWidget();

    {
        for (WidgetFlag value : WidgetFlag.values()) {
            rootWidget.enableFlag(value);
        }
    }

    public void preRender(RenderInfo renderInfo) {
        MouseButton.updateAll();
        int mx = (int) mouseHandler.xpos();
        int my = (int) mouseHandler.ypos();
        //Get the widget to focus on this render.
        IWidget focusedThisRender = focusedWidget;
        if(focusedThisRender == null){
            focusedThisRender = rootWidget.getWidgetAt(mx,my,WidgetFlag.VISIBLE);
        }
        //Handle hovers.
        if (focusedThisRender.isFlagEnabled(WidgetFlag.HOVERABLE)) {
            focusedThisRender.hover(mx,my);
        }
        //Handle Click states.
        if(focusedThisRender.isFlagEnabled(WidgetFlag.CLICKABLE)){
            focusedThisRender.handleMouseClicks(mx,my);
        }
        //Handle drag, also set dragged widget as focused
        if(MouseButton.RIGHT.getState() == ClickState.DOWN && focusedThisRender.isFlagEnabled(WidgetFlag.DRAGGABLE)){
            dragging=true;
            focusedWidget=focusedThisRender;
            focusedThisRender.drag(mx,my, (int) prevX, (int) prevY);
        }
        //End of drag, unfocus the widget.
        if(dragging && MouseButton.RIGHT.getState() == ClickState.RELEASED || MouseButton.RIGHT.getState() == ClickState.UP){
            dragging=false;
            focusedWidget=null;
        }
        rootWidget.descend(WidgetFlag.VISIBLE,widget -> {
            widget.preRender(renderInfo);
        });
        prevX = mouseHandler.xpos();
        prevY = mouseHandler.ypos();
    }

    public void render(RenderInfo renderInfo){
        rootWidget.descend(WidgetFlag.VISIBLE,widget -> {
            widget.render(renderInfo);
        });
    }

    public CompoundWidget getRootWidget(){
        return rootWidget;
    }
}
