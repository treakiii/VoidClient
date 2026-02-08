package me.alpha432.oyvey.features.modules.profiles;

import me.alpha432.oyvey.OyVey;
import me.alpha432.oyvey.features.modules.Module;
import me.alpha432.oyvey.features.modules.combat.Aura;
import me.alpha432.oyvey.features.modules.movement.Sprint;

public class LegitProfile extends Module {
    public LegitProfile() {
        super("Legit", "Enable legit configurations", Category.PROFILES, true, false, false);
    }

    @Override
    public void onEnable() {
        // Configure for legit play
        Module aura = OyVey.moduleManager.getModuleByClass(Aura.class);
        if (aura != null) {
            aura.disable();
        }

        Module sprint = OyVey.moduleManager.getModuleByClass(Sprint.class);
        if (sprint != null) {
            sprint.enable();
        }

        // Disable other profile modules
        disableOtherProfiles();

        this.disable(); // Disable self after applying
    }

    private void disableOtherProfiles() {
        OyVey.moduleManager.getModulesByCategory(Category.PROFILES).forEach(m -> {
            if (m != this && m.isEnabled())
                m.disable();
        });
    }
}
