package me.alpha432.oyvey.features.modules.render;

import me.alpha432.oyvey.event.impl.Render3DEvent;
import me.alpha432.oyvey.features.modules.Module;
import me.alpha432.oyvey.features.settings.Setting;
import me.alpha432.oyvey.util.render.RenderUtil;
import net.minecraft.entity.player.PlayerEntity;

import java.awt.*;

public class PlayerESP extends Module {
    public Setting<Boolean> boxes = bool("Boxes", true);
    public Setting<Boolean> tracer = bool("Tracers", false);
    public Setting<Integer> red = num("Red", 255, 0, 255);
    public Setting<Integer> green = num("Green", 255, 0, 255);
    public Setting<Integer> blue = num("Blue", 255, 0, 255);

    public PlayerESP() {
        super("PlayerESP", "Highlights other players", Category.RENDER, true, false, false);
    }

    @Override
    public void onRender3D(Render3DEvent event) {
        if (mc.world == null || mc.player == null)
            return;

        Color color = new Color(red.getValue(), green.getValue(), blue.getValue(), 150);

        for (PlayerEntity player : mc.world.getPlayers()) {
            if (player == mc.player)
                continue;

            if (boxes.getValue()) {
                RenderUtil.drawBox(event.getMatrix(), player.getBoundingBox(), color, 1.0);
            }
        }
    }
}
