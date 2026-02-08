package me.alpha432.oyvey.features.modules.movement;

import me.alpha432.oyvey.features.modules.Module;

public class Sprint extends Module {
    public Sprint() {
        super("Sprint", "Automatically sprints", Category.MOVEMENT, true, false, false);
    }

    @Override
    public void onUpdate() {
        if (mc.player != null && (mc.player.forwardSpeed != 0 || mc.player.sidewaysSpeed != 0)) {
            mc.player.setSprinting(true);
        }
    }
}
