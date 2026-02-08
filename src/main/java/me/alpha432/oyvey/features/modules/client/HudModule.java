package me.alpha432.oyvey.features.modules.client;

import me.alpha432.oyvey.OyVey;
import me.alpha432.oyvey.event.impl.Render2DEvent;
import me.alpha432.oyvey.features.modules.Module;
import java.awt.Color;
import me.alpha432.oyvey.util.ColorUtil;
import net.minecraft.util.Formatting;

public class HudModule extends Module {
    public HudModule() {
        super("Hud", "hud", Category.CLIENT, true, false, false);
    }

    @Override
    public void onRender2D(Render2DEvent event) {
        int width = mc.getWindow().getScaledWidth();
        int height = mc.getWindow().getScaledHeight();

        // Watermark with RGB Glow
        String watermark = OyVey.NAME + " v" + OyVey.VERSION;
        int color = ColorUtil.rainbow(0).getRGB();

        // Glow layers
        for (int i = 1; i <= 3; i++) {
            event.getContext().drawTextWithShadow(
                    mc.textRenderer,
                    watermark.substring(0, 1),
                    2, 2,
                    ColorUtil.toRGBA(new Color(color >> 16 & 0xFF, color >> 8 & 0xFF, color & 0xFF, 50 / i)));
        }

        event.getContext().drawTextWithShadow(
                mc.textRenderer,
                watermark.substring(0, 1),
                2, 2,
                color);
        event.getContext().drawTextWithShadow(
                mc.textRenderer,
                watermark.substring(1),
                2 + mc.textRenderer.getWidth(watermark.substring(0, 1)), 2,
                -1);

        // Spotify Overlay
        OyVey.spotifyManager.update();
        String track = "Spotify: " + OyVey.spotifyManager.getCurrentTrack();
        int spotifyColor = ColorUtil.rainbow(100).getRGB();
        event.getContext().drawTextWithShadow(
                mc.textRenderer,
                track,
                2, 12,
                spotifyColor);

        // Module List
        int y = 2;
        OyVey.moduleManager.sortModules(true);
        for (Module module : OyVey.moduleManager.sortedModules) {
            String displayInfo = module.getDisplayInfo();
            String str = module.getDisplayName()
                    + (displayInfo != null ? Formatting.GRAY + " [" + displayInfo + "]" : "");
            int moduleColor = ColorUtil.rainbow(y * 20).getRGB();
            event.getContext().drawTextWithShadow(
                    mc.textRenderer,
                    str,
                    width - 2 - mc.textRenderer.getWidth(str),
                    y,
                    moduleColor);
            y += mc.textRenderer.fontHeight + 1;
        }

        // Stats
        String fps = "FPS " + Formatting.WHITE + mc.getCurrentFps();
        event.getContext().drawTextWithShadow(mc.textRenderer, fps, 2, height - 10, color);
    }
}
