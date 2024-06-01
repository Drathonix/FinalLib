package com.vicious.finallib.client.gui.widget;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.vicious.finallib.client.utils.IBounds;
import com.vicious.finallib.client.utils.impl.RectBounds;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

public class RenderInfo {
    private final PoseStack poseStack;

    public RenderInfo(PoseStack stack){
        this.poseStack = stack;
    }

    public void useTexture(ResourceLocation texture, float r, float g, float b, float alpha){
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(r,g,b,alpha);
        RenderSystem.setShaderTexture(0,texture);
    }

    public void renderTexture(ResourceLocation texture, IBounds bounds){
        renderTexture(texture,bounds,ColorSettings.FULL);
    }
    public void renderTexture(ResourceLocation texture, IBounds bounds, ColorSettings color){
        useTexture(texture,color.r(),color.g(), color.b(),color.alpha());
        GuiComponent.blit(poseStack, bounds.getLeastX(), bounds.getLeastY(), 0, 0, bounds.getWidth(), bounds.getHeight(),256,256);
    }
    public void renderDecoratedItemStack(ItemStack itemStack, IBounds bounds){
        Minecraft.getInstance().getItemRenderer().renderAndDecorateItem(itemStack,bounds.getLeastX(),bounds.getLeastY());
    }

    public void renderTextureWithScissor(ResourceLocation texture, IBounds bounds, RectBounds include) {
        ColorSettings color = ColorSettings.FULL;
        useTexture(texture,color.r(),color.g(), color.b(),color.alpha());
        GuiComponent.blit(poseStack, bounds.getLeastX(), bounds.getLeastY(), include.lX, include.lY, include.gX, include.gY,256,256);
    }
}
