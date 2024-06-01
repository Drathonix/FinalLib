package com.vicious.finallib.client.gui.widget.prefab.item;

import net.minecraft.world.Container;

public class WidgetContainerSlot extends WidgetItem {
    public WidgetContainerSlot(int x, int y, Container container, int slot){
        this(x,y,16,16,container,slot);
    }
    public WidgetContainerSlot(int x, int y, int width, int height, Container container, int slot){
        super(x,y,width,height,()->container.getItem(slot));
    }
}
