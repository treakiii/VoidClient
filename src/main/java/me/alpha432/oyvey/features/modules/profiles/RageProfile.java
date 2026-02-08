package me.alpha432.oyvey.features.modules.profiles;

import me.alpha432.oyvey.OyVey;
import me.alpha432.oyvey.features.modules.Module;

public class RageProfile extends Module {
    public RageProfile() {
        super("Rage", "Enable rage configurations", Category.PROFILES, true, false, false);
    }

    @Override
    public void onEnable() {
        // High performance/rage settings
        enableModule("Aura");
        enableModule("Velocity");
        enableModule("Criticals");
        enableModule("Flight");

        disableOtherProfiles();
        this.disable();
    }

    private void enableModule(String name) {
        Module m = OyVey.moduleManager.getModuleByName(name);
        if (m != null)
            m.enable();
    }

    private void disableOtherProfiles() {
        OyVey.moduleManager.getModulesByCategory(Category.PROFILES).forEach(m -> {
            if (m != this && m.isEnabled())
                m.disable();
        });
    }
}
