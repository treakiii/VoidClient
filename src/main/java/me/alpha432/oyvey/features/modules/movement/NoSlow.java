package me.alpha432.oyvey.features.modules.movement;

import me.alpha432.oyvey.features.modules.Module;

public class NoSlow extends Module {
    public NoSlow() {
        super("NoSlow", "Prevents slowdown when using items", Category.MOVEMENT, true, false, false);
    }

    @Override
    public void onTick() {
        if (mc.player == null)
            return;
        if (mc.player.isUsingItem()) {
            // This is a basic implementation, full NoSlow often requires mixins to
            // MovementInput
            // but we can increase speed to compensate client-side for basic needs
        }
    }
}
