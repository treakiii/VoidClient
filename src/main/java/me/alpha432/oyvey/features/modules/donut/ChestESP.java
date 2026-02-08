package me.alpha432.oyvey.features.modules.donut;

import me.alpha432.oyvey.event.impl.Render3DEvent;
import me.alpha432.oyvey.features.modules.Module;
import me.alpha432.oyvey.features.settings.Setting;

import java.awt.*;

public class ChestESP extends Module {
    public Setting<Boolean> chest = bool("Chest", true);
    public Setting<Boolean> enderChest = bool("EnderChest", true);
    public Setting<Boolean> shulker = bool("Shulker", true);

    public ChestESP() {
        super("ChestESP", "Highlights containers (Updating for 1.21...)", Category.DONUT_SMP, true, false, false);
    }

    @Override
    public void onRender3D(Render3DEvent event) {
        // Logic being optimized for 1.21 performance
    }
}
