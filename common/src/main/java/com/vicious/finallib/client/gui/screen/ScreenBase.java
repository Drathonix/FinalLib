package com.vicious.finallib.client.gui.screen;

import com.mojang.blaze3d.vertex.PoseStack;
import com.vicious.finallib.client.ClientNetwork;
import com.vicious.finallib.client.gui.widget.RenderInfo;
import com.vicious.finallib.client.gui.widget.WidgetController;
import com.vicious.finallib.common.bridge.IPlayerMixin;
import com.vicious.finallib.common.guisync.GUISynchronizer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public class ScreenBase extends Screen {
    public WidgetController controller = new WidgetController();
    public GUISynchronizer synchronizer = new GUISynchronizer();
    protected ScreenBase(Component component) {
        super(component);
        if(Minecraft.getInstance().player instanceof IPlayerMixin mixin){
            mixin.finalLib$setGUISynchronizer(synchronizer);
        }
    }

    @Override
    public void removed() {
        super.removed();
        if(Minecraft.getInstance().player instanceof IPlayerMixin mixin){
            mixin.finalLib$setGUISynchronizer(null);
            ClientNetwork.unsyncGUI();
        }
    }

    @Override
    public void render(PoseStack poseStack, int i, int j, float f) {
        super.render(poseStack, i, j, f);
        RenderInfo inf = new RenderInfo(poseStack);
        controller.preRender(inf);
        controller.render(inf);
    }
}
