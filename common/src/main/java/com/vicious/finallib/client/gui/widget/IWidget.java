package com.vicious.finallib.client.gui.widget;
import com.vicious.finallib.client.gui.widget.enums.ClickState;
import com.vicious.finallib.client.gui.widget.enums.MouseButton;
import com.vicious.finallib.client.gui.widget.enums.WidgetFlag;
import com.vicious.finallib.client.utils.IBounds;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface IWidget {
    int getX();
    int getY();
    @NotNull IBounds getBounds();

    boolean isFlagEnabled(@NotNull WidgetFlag flag);
    default boolean isFlagDisabled(@NotNull WidgetFlag flag){
        return !isFlagEnabled(flag);
    }

    void enableFlag(@NotNull WidgetFlag flag);
    void disableFlag(@NotNull WidgetFlag flag);

    void setParent(@NotNull IWidget widget);
    @Nullable IWidget getParent();

    void shift(int x, int y);

    default boolean hasParent(){
        return getParent() != null;
    }

    default boolean inBounds(int x, int y){
        return getBounds().isWithin(x,y);
    }

    default boolean intersects(@NotNull IBounds bounds){
        return getBounds().intersects(bounds);
    }

    default void preRender(@NotNull RenderInfo info){}
    default void hover(int x, int y){}
    default void handleMouseClicks(int x, int y){
        for (MouseButton value : MouseButton.values()) {
            if(value.getState() == ClickState.PRESSED){
                mousePressed(x,y,value);
            }
            if(value.getState() == ClickState.RELEASED){
                mouseReleased(x,y,value);
            }
            if(value.getState() == ClickState.UP){
                mouseUp(x,y,value);
            }
            if(value.getState() == ClickState.DOWN){
                mouseDown(x,y,value);
            }
        }
    }

    default void mousePressed(int x, int y, MouseButton button){}
    default void mouseReleased(int x, int y, MouseButton button){}
    default void mouseUp(int x, int y, MouseButton button){}
    default void mouseDown(int x, int y, MouseButton button){}

    default void drag(int mouseX, int mouseY, int changeX, int changeY){
        shift(changeX,changeY);
    }
    default void render(@NotNull RenderInfo info){}

}
