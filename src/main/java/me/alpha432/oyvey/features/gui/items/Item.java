package me.alpha432.oyvey.features.gui.items;

import me.alpha432.oyvey.features.Feature;
import me.alpha432.oyvey.features.gui.Component;
import me.alpha432.oyvey.features.gui.OyVeyGui;
import net.minecraft.client.gui.DrawContext;

import java.awt.*;

public class Item
        extends Feature {
    public static DrawContext context;
    protected float x;
    protected float y;
    protected int width;
    protected int height;
    private boolean hidden;

    public Item(String name) {
        super(name);
    }

    protected long hoverTimer = 0;
    protected boolean hovering = false;

    public void onUpdateHover(int mouseX, int mouseY) {
        if (this.isHovering(mouseX, mouseY)) {
            if (!hovering) {
                hoverTimer = System.currentTimeMillis();
                hovering = true;
            }
        } else {
            hovering = false;
        }
    }

    public boolean shouldShowTooltip() {
        return hovering && System.currentTimeMillis()
                - hoverTimer > me.alpha432.oyvey.features.modules.client.ClickGui.getInstance().tooltipDelay.getValue();
    }

    public void drawTooltip(DrawContext context, int mouseX, int mouseY) {
    }

    public void setLocation(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public void drawScreen(DrawContext context, int mouseX, int mouseY, float partialTicks) {
    }

    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
    }

    public void mouseReleased(int mouseX, int mouseY, int releaseButton) {
    }

    public void update() {
    }

    public void onKeyTyped(char typedChar, int keyCode) {
    }

    public void onKeyPressed(int key) {
    }

    public float getX() {
        return this.x;
    }

    public float getY() {
        return this.y;
    }

    public int getWidth() {
        return this.width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return this.height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public boolean isHidden() {
        return this.hidden;
    }

    public boolean setHidden(boolean hidden) {
        this.hidden = hidden;
        return this.hidden;
    }

    protected void drawString(String text, double x, double y, Color color) {
        drawString(text, x, y, color.hashCode());
    }

    protected void drawString(String text, double x, double y, int color) {
        context.drawTextWithShadow(mc.textRenderer, text, (int) x, (int) y, color);
    }

    public boolean isHovering(int mouseX, int mouseY) {
        return false;
    }
}