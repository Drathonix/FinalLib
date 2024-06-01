package com.vicious.finallib.client.gui.widget.enums;

import net.minecraft.client.Minecraft;

public enum MouseButton {
    LEFT{
        @Override
        void update() {
            setDown(Minecraft.getInstance().mouseHandler.isLeftPressed());
        }
    },
    MIDDLE{
        @Override
        void update() {
            setDown(Minecraft.getInstance().mouseHandler.isMiddlePressed());
        }
    },
    RIGHT{
        @Override
        void update() {
            setDown(Minecraft.getInstance().mouseHandler.isRightPressed());
        }
    };

    private ClickState state;

    public static void updateAll() {
        for (MouseButton value : values()) {
            value.update();
        }
    }

    public ClickState getState(){
        return state;
    }

    abstract void update();

    public void setDown(boolean down){
        if(down) {
            if (state == ClickState.UP) {
                state = ClickState.PRESSED;
            }
            else if(state == ClickState.PRESSED){
                state = ClickState.DOWN;
            }
        }
        else{
            if(state == ClickState.PRESSED){
                state = ClickState.RELEASED;
            }
            else{
                state = ClickState.UP;
            }
        }
    }
}

