package me.alpha432.oyvey.features.modules.donut;

import me.alpha432.oyvey.features.modules.Module;
import me.alpha432.oyvey.features.settings.Setting;

public class RemoteChest extends Module {
    public Setting<Float> range = num("Range", 10.0f, 1.0f, 100.0f);

    public RemoteChest() {
        super("RemoteChest", "Open chests from distance (Updating...)", Category.DONUT_SMP, true, false, false);
    }

    @Override
    public void onTick() {
        // Optimizing remote interaction for 1.21
    }
}
