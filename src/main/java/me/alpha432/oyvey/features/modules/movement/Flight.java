package me.alpha432.oyvey.features.modules.movement;

import me.alpha432.oyvey.features.modules.Module;
import me.alpha432.oyvey.features.settings.Setting;

public class Flight extends Module {
    public Setting<Float> speed = num("Speed", 1.0f, 0.1f, 10.0f);

    public Flight() {
        super("Flight", "Allows you to fly", Category.MOVEMENT, true, false, false);
    }

    @Override
    public void onUpdate() {
        if (mc.player == null)
            return;
        mc.player.getAbilities().flying = true;
        mc.player.getAbilities().setFlySpeed(speed.getValue() / 10f);
    }

    @Override
    public void onDisable() {
        if (mc.player == null)
            return;
        mc.player.getAbilities().flying = false;
    }
}
