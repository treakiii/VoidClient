package me.alpha432.oyvey.features.gui.items.buttons;

import me.alpha432.oyvey.OyVey;
import me.alpha432.oyvey.features.gui.Component;
import me.alpha432.oyvey.features.gui.OyVeyGui;
import me.alpha432.oyvey.features.gui.items.Item;
import me.alpha432.oyvey.features.modules.client.ClickGui;
import me.alpha432.oyvey.util.ColorUtil;
import me.alpha432.oyvey.util.render.RenderUtil;
import java.awt.Color;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.sound.PositionedSoundInstance;
import net.minecraft.sound.SoundEvents;

public class Button
        extends Item {
    private boolean state;

    public Button(String name) {
        super(name);
        this.height = 15;
    }

    @Override
    public void drawScreen(DrawContext context, int mouseX, int mouseY, float partialTicks) {
        ClickGui gui = ClickGui.getInstance();
        int color = ColorUtil.toARGB(gui.red.getValue(), gui.green.getValue(), gui.blue.getValue(),
                gui.hoverAlpha.getValue());
        int colorHover = ColorUtil.toARGB(gui.red.getValue(), gui.green.getValue(), gui.blue.getValue(),
                gui.alpha.getValue());
        int rainbow = ColorUtil.rainbow(Component.counter1[0] * gui.rainbowHue.getValue()).getRGB();
        int rainbowHover = ColorUtil
                .toRGBA(new Color(rainbow >> 16 & 0xFF, rainbow >> 8 & 0xFF, rainbow & 0xFF, gui.alpha.getValue()));
        int rainbowPlain = ColorUtil.toRGBA(
                new Color(rainbow >> 16 & 0xFF, rainbow >> 8 & 0xFF, rainbow & 0xFF, gui.hoverAlpha.getValue()));

        if (this.getState()) {
            if (gui.moduleRGB.getValue()) {
                RenderUtil.rect(context.getMatrices(), this.x, this.y, this.x + (float) this.width,
                        this.y + (float) this.height - 0.5f,
                        this.isHovering(mouseX, mouseY) ? rainbowHover : rainbowPlain);
            } else {
                RenderUtil.rect(context.getMatrices(), this.x, this.y, this.x + (float) this.width,
                        this.y + (float) this.height - 0.5f, this.isHovering(mouseX, mouseY) ? colorHover : color);
            }
        } else {
            RenderUtil.rect(context.getMatrices(), this.x, this.y, this.x + (float) this.width,
                    this.y + (float) this.height - 0.5f, this.isHovering(mouseX, mouseY) ? 0x33555555 : 0x11555555);
        }

        drawString(this.getName(), this.x + 2.3f, this.y - 2.0f - (float) OyVeyGui.getClickGui().getTextOffset(),
                this.getState() ? -1 : -5592406);
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        if (mouseButton == 0 && this.isHovering(mouseX, mouseY)) {
            this.onMouseClick();
        }
    }

    public void onMouseClick() {
        this.state = !this.state;
        this.toggle();
        mc.getSoundManager().play(PositionedSoundInstance.master(SoundEvents.UI_BUTTON_CLICK, 1f));
    }

    public void toggle() {
    }

    public boolean getState() {
        return this.state;
    }

    @Override
    public int getHeight() {
        return 14;
    }

    public boolean isHovering(int mouseX, int mouseY) {
        for (Component component : OyVeyGui.getClickGui().getComponents()) {
            if (!component.drag)
                continue;
            return false;
        }
        return (float) mouseX >= this.getX() && (float) mouseX <= this.getX() + (float) this.getWidth()
                && (float) mouseY >= this.getY() && (float) mouseY <= this.getY() + (float) this.height;
    }
}