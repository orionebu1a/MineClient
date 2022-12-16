package net.hackedclient.gui;

import net.minecraft.client.util.Window;

public abstract class Component {
    private int x;
    private int y;

    private Window parent;

    public void handleMouseClick() {
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return 140;
    }

    public int getHeight() {
        return 20;
    }

    public Window getParent() {
        return parent;
    }

    public void setParent(Window parent) {
        this.parent = parent;
    }

}
