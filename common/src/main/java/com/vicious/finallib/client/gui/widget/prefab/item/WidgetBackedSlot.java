package com.vicious.finallib.client.gui.widget.prefab.item;

import com.vicious.finallib.client.gui.widget.RenderInfo;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public class WidgetBackedSlot extends WidgetContainerSlot{
    private final Supplier<ResourceLocation> texture;

    public WidgetBackedSlot(int x, int y, int width, int height, Container container, int slot, ResourceLocation texture) {
        this(x, y, width, height, container, slot,()->texture);
    }
    public WidgetBackedSlot(int x, int y, int width, int height, Container container, int slot, Supplier<ResourceLocation> texture) {
        super(x, y, width, height, container, slot);
        this.texture = texture;
    }

    @Override
    public void render(@NotNull RenderInfo info) {
        info.renderTexture(texture.get(),bounds);
        super.render(info);
    }
}
