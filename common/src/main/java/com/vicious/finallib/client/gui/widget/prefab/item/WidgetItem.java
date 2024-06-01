package com.vicious.finallib.client.gui.widget.prefab.item;

import com.vicious.finallib.client.gui.widget.RenderInfo;
import com.vicious.finallib.client.gui.widget.WidgetBase;
import com.vicious.finallib.client.utils.impl.RectBounds;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public class WidgetItem extends WidgetBase {
    private final Supplier<ItemStack> supplier;

    public WidgetItem(int x, int y, Supplier<ItemStack> supplier){
        this(x,y,16,16,supplier);
    }


    public WidgetItem(int x, int y, int width, int height, Supplier<ItemStack> supplier){
        this.supplier = supplier;
        bounds = new RectBounds(x,y,x+width,y+height);
    }

    @Override
    public void render(@NotNull RenderInfo info) {
        info.renderDecoratedItemStack(supplier.get(),bounds);
    }
}
