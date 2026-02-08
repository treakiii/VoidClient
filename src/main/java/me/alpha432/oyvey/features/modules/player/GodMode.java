package me.alpha432.oyvey.features.modules.player;

import me.alpha432.oyvey.features.modules.Module;
import me.alpha432.oyvey.features.modules.Module;

public class GodMode extends Module {
    public GodMode() {
        super("GodMode", "Attempts to make you invincible", Category.PLAYER, true, false, false);
    }

    @Override
    public void onTick() {
        if (mc.player == null)
            return;
        // Basic exploit: stay in a riding state or cancel damage via specific packets
        // This is a common "Portal Godmode" style placeholder for 1.21
        mc.player.getAbilities().invulnerable = true;
    }

    @Override
    public void onDisable() {
        if (mc.player != null)
            mc.player.getAbilities().invulnerable = false;
    }
}
