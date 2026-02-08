package me.alpha432.oyvey.features.modules.render;

import me.alpha432.oyvey.features.modules.Module;

public class FullBright extends Module {
    public FullBright() {
        super("FullBright", "Makes everything bright", Category.RENDER, true, false, false);
    }

    @Override
    public void onUpdate() {
        if (mc.options != null) {
            mc.options.getGamma().setValue(100.0);
        }
    }

    @Override
    public void onDisable() {
        if (mc.options != null) {
            mc.options.getGamma().setValue(1.0);
        }
    }
}
