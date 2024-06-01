package com.vicious.finallib.client.gui.widget.prefab;

import com.vicious.finallib.client.gui.widget.RenderInfo;
import com.vicious.finallib.client.gui.widget.WidgetBase;
import com.vicious.finallib.client.utils.impl.RectBounds;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class WidgetImageWithCutoff extends WidgetBase {
    private final ResourceLocation image;

    public WidgetImageWithCutoff(int x, int y, int width, int height, @NotNull ResourceLocation image){
        this.image = image;
        bounds = new RectBounds(x,y,x+width,y+height);
    }

    //blit(texture,x,y,textureStartX,textureStartY,textureXEnd,textureYEnd,idfk,idfk)

    @Override
    public void render(@NotNull RenderInfo info) {
        info.renderTexture(image,bounds);
    }

    public ResourceLocation getImage() {
        return image;
    }
}
