package me.alpha432.oyvey.features.modules.donut;

import me.alpha432.oyvey.features.modules.Module;
import me.alpha432.oyvey.features.settings.Setting;

public class BaseFinder extends Module {
    public Setting<Integer> threshold = num("Threshold", 5, 1, 20);

    public BaseFinder() {
        super("BaseFinder", "Detects base clusters (Updating...)", Category.DONUT_SMP, true, false, false);
    }

    @Override
    public void onTick() {
        // Scanning logic being updated for 1.21
    }
}
