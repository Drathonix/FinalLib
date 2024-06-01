package com.vicious.finallib.client.gui.widget;

public record ColorSettings(float r, float g, float b, float alpha) {
    public static final ColorSettings FULL = new ColorSettings(1,1,1,1);
}
