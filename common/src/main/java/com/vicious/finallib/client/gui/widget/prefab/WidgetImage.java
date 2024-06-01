package com.vicious.finallib.client.gui.widget.prefab;

import com.vicious.finallib.client.gui.widget.RenderInfo;
import com.vicious.finallib.client.gui.widget.WidgetBase;
import com.vicious.finallib.client.gui.widget.enums.RenderType;
import com.vicious.finallib.client.utils.impl.RectBounds;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

//TODO: render bounds only in included space.
public class WidgetImage extends WidgetBase {
    private final Supplier<RectBounds> imageBoxSupplier;
    private final Supplier<ResourceLocation> imageSupplier;
    private final RenderType type;

    /**
     * Creates a static image widget that renders only one image statically.
     * @param x the x position
     * @param y the y position
     * @param width the image width
     * @param height the image height
     * @param image the ResourceLocation of the texture.
     */
    public WidgetImage(int x, int y, int width, int height, @NotNull ResourceLocation image){
        bounds = new RectBounds(x,y,x+width,y+height);
        imageBoxSupplier = ()->(RectBounds)getBounds();
        imageSupplier = ()->image;
        type = RenderType.STATIC;
    }

    /**
     * Creates a non-static image widget that renders only one image, but supports rendering only parts of the image.
     * @param imageBoxSupplier the texture coordinates to render.
     * @param image the ResourceLocation of the texture.
     */
    public WidgetImage(Supplier<RectBounds> imageBoxSupplier, @NotNull ResourceLocation image){
        this(imageBoxSupplier,()->image);
    }

    /**
     * Creates a non-static image widget that renders only one image at a tim, but supports rendering only parts of the image and changing the image texture.
     * @param imageBoxSupplier the texture coordinates to render.
     * @param imageSupplier the ResourceLocation of the texture.
     */
    public WidgetImage(Supplier<RectBounds> imageBoxSupplier, @NotNull Supplier<ResourceLocation> imageSupplier){
        this.imageBoxSupplier = imageBoxSupplier;
        this.imageSupplier = imageSupplier;
        type = RenderType.SCISSOR;
    }

    @Override
    public void preRender(@NotNull RenderInfo info) {
        super.preRender(info);
    }

    @Override
    public void render(@NotNull RenderInfo info) {
        if(type == RenderType.STATIC) {
            info.renderTexture(imageSupplier.get(), bounds);
        }
        else{
            info.renderTextureWithScissor(imageSupplier.get(),bounds,imageBoxSupplier.get());
        }
    }
}
