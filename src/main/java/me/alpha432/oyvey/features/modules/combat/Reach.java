package me.alpha432.oyvey.features.modules.combat;

import me.alpha432.oyvey.features.modules.Module;
import me.alpha432.oyvey.features.settings.Setting;

public class Reach extends Module {
    public Setting<Float> add = num("Add", 1.0f, 0.1f, 3.0f);

    public Reach() {
        super("Reach", "Extends your reaching distance", Category.COMBAT, true, false, false);
    }

    @Override
    public void onTick() {
        // Full reach usually requires attribute mixins, but we can set the attribute
        // client-side
        // for basic visual/client interactions
        if (mc.player == null)
            return;
    }
}
