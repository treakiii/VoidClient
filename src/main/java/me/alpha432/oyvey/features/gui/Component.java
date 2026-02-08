package me.alpha432.oyvey.features.gui;

import me.alpha432.oyvey.features.Feature;
import me.alpha432.oyvey.features.gui.items.Item;
import me.alpha432.oyvey.features.gui.items.buttons.Button;
import me.alpha432.oyvey.features.modules.client.ClickGui;
import me.alpha432.oyvey.util.ColorUtil;
import me.alpha432.oyvey.util.render.RenderUtil;
import me.alpha432.oyvey.util.render.ScissorUtil;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.sound.PositionedSoundInstance;
import net.minecraft.sound.SoundEvents;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Component
        extends Feature {
    public static int[] counter1 = new int[] { 1 };
    protected DrawContext context;
    private final List<Item> items = new ArrayList<>();
    public boolean drag;
    private int x;
    private int y;
    private int x2;
    private int y2;
    private int width;
    private int height;
    private boolean open;
    private boolean hidden = false;
    private float fullHeight = Float.NaN;

    public Component(String name, int x, int y, boolean open) {
        super(name);
        this.x = x;
        this.y = y;
        this.width = 88;
        this.height = 18;
        this.open = open;
        this.setupItems();
    }

    public void setupItems() {
    }

    private void drag(int mouseX, int mouseY) {
        if (!this.drag) {
            return;
        }
        this.x = this.x2 + mouseX;
        this.y = this.y2 + mouseY;
    }

    public void drawScreen(DrawContext context, int mouseX, int mouseY, float partialTicks) {
        this.context = context;
        this.drag(mouseX, mouseY);
        counter1 = new int[] { 1 };
        float totalItemHeight = this.open ? this.getTotalItemHeight() - 2.0f : 0.0f;
        int color = ColorUtil.toARGB(ClickGui.getInstance().red.getValue(), ClickGui.getInstance().green.getValue(),
                ClickGui.getInstance().blue.getValue(), 255);
        int color2 = ColorUtil.toARGB(ClickGui.getInstance().topRed.getValue(),
                ClickGui.getInstance().topGreen.getValue(), ClickGui.getInstance().topBlue.getValue(), 255);

        // Title Bar Gradient & Glow
        if (ClickGui.getInstance().rainbow.getValue()) {
            int rainbow = ColorUtil.rainbow(ClickGui.getInstance().rainbowHue.getValue()).getRGB();
            // Glow layers for Title
            for (int i = 1; i <= 3; i++) {
                RenderUtil.rect(context.getMatrices(), this.x - (i * 0.5f), this.y - 1.5f - (i * 0.5f),
                        this.x + this.width + (i * 0.5f), this.y + 12.5f + (i * 0.5f),
                        ColorUtil.toRGBA(new Color(rainbow >> 16 & 0xFF, rainbow >> 8 & 0xFF, rainbow & 0xFF, 40 / i)));
            }
            if (ClickGui.getInstance().gradient.getValue()) {
                RenderUtil.drawGradientRect(context.getMatrices(), this.x, this.y - 1.5f, this.width, 14,
                        rainbow,
                        ColorUtil.rainbow(ClickGui.getInstance().rainbowHue.getValue() * 2).getRGB());
            } else {
                context.fill(this.x, (int) (this.y - 1.5f), this.x + this.width, (int) (this.y + 12.5f),
                        rainbow);
            }
        } else {
            if (ClickGui.getInstance().gradient.getValue()) {
                RenderUtil.drawGradientRect(context.getMatrices(), this.x, this.y - 1.5f, this.width, 14,
                        color, color2);
            } else {
                context.fill(this.x, (int) (this.y - 1.5f), this.x + this.width, (int) (this.y + 12.5f),
                        color);
            }
        }

        // Module List Background (Glassmorphism)
        if (this.open) {
            RenderUtil.rect(context.getMatrices(), this.x, (float) this.y + 12.5f, this.x + this.width,
                    (float) (this.y + 12.5f) + totalItemHeight + 2.0f, 0x66000000);
            // Side accents
            RenderUtil.rect(context.getMatrices(), this.x, (float) this.y + 12.5f, this.x + 0.5f,
                    (float) (this.y + 12.5f) + totalItemHeight + 2.0f, ColorUtil.rainbow(0).getRGB());
        }

        // Title Outline
        RenderUtil.rect(context.getMatrices(), this.x, this.y - 1.5f, this.x + this.width,
                this.y + 12.5f, 0xFF000000, 1.0f);

        drawString(this.getName(), (float) this.x + 3.0f,
                (float) this.y - 4.0f - (float) OyVeyGui.getClickGui().getTextOffset(), -1);
        ScissorUtil.enable(context, x, 0, x + width, mc.getWindow().getScaledHeight());

        if (this.open) {
            float y = (float) (this.getY() + this.getHeight()) - 3.0f;
            for (Item item : this.getItems()) {
                Component.counter1[0] = counter1[0] + 1;
                if (item.isHidden())
                    continue;
                item.setLocation((float) this.x + 2.0f, y);
                item.setWidth(this.getWidth() - 4);
                if (item.isHovering(mouseX, mouseY)) {
                    ScissorUtil.disable(context);
                }
                item.drawScreen(context, mouseX, mouseY, partialTicks);

                if (item.isHovering(mouseX, mouseY)) {
                    ScissorUtil.enable(context);
                }
                y += (float) item.getHeight() + 1.5f;
            }
        }

        ScissorUtil.disable(context);
    }

    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        if (mouseButton == 0 && this.isHovering(mouseX, mouseY)) {
            this.x2 = this.x - mouseX;
            this.y2 = this.y - mouseY;
            OyVeyGui.getClickGui().getComponents().forEach(component -> {
                if (component.drag) {
                    component.drag = false;
                }
            });
            this.drag = true;
            return;
        }
        if (mouseButton == 1 && this.isHovering(mouseX, mouseY)) {
            this.open = !this.open;
            mc.getSoundManager().play(PositionedSoundInstance.master(SoundEvents.UI_BUTTON_CLICK, 1f));
            return;
        }
        if (!this.open) {
            return;
        }
        this.getItems().forEach(item -> item.mouseClicked(mouseX, mouseY, mouseButton));
    }

    public void mouseReleased(int mouseX, int mouseY, int releaseButton) {
        if (releaseButton == 0) {
            this.drag = false;
        }
        if (!this.open) {
            return;
        }
        this.getItems().forEach(item -> item.mouseReleased(mouseX, mouseY, releaseButton));
    }

    public void onKeyTyped(char typedChar, int keyCode) {
        if (!this.open) {
            return;
        }
        this.getItems().forEach(item -> item.onKeyTyped(typedChar, keyCode));
    }

    public void onKeyPressed(int key) {
        if (!open)
            return;
        this.getItems().forEach(item -> item.onKeyPressed(key));
    }

    public void addButton(Button button) {
        this.items.add(button);
    }

    public int getX() {
        return this.x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return this.y;
    }

    public void setY(int y) {
        this.y = y;
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

    public void setHidden(boolean hidden) {
        this.hidden = hidden;
    }

    public boolean isOpen() {
        return this.open;
    }

    public final List<Item> getItems() {
        return this.items;
    }

    public boolean isHovering(int mouseX, int mouseY) {
        return mouseX >= this.getX() && mouseX <= this.getX() + this.getWidth() && mouseY >= this.getY()
                && mouseY <= this.getY() + this.getHeight() - (this.open ? 2 : 0);
    }

    private float getTotalItemHeight() {
        float height = 0.0f;
        for (Item item : this.getItems()) {
            height += (float) item.getHeight() + 1.5f;
        }
        return height;
    }

    protected void drawString(String text, double x, double y, Color color) {
        drawString(text, x, y, color.hashCode());
    }

    protected void drawString(String text, double x, double y, int color) {
        context.drawTextWithShadow(mc.textRenderer, text, (int) x, (int) y, color);
    }
}