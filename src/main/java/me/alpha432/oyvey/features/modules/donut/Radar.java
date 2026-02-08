package me.alpha432.oyvey.features.modules.donut;

import me.alpha432.oyvey.features.modules.Module;
import java.util.Comparator;

public class Radar extends Module {
    private String nearest = "None";

    public Radar() {
        super("Radar", "Shows nearest player", Category.DONUT_SMP, true, false, false);
    }

    @Override
    public void onTick() {
        if (mc.world == null || mc.player == null)
            return;

        mc.world.getPlayers().stream()
                .filter(p -> p != mc.player)
                .min(Comparator.comparingDouble(p -> mc.player.squaredDistanceTo(p.getPos())))
                .ifPresentOrElse(
                        p -> nearest = p.getName().getString() + " ("
                                + (int) Math.sqrt(mc.player.squaredDistanceTo(p.getPos())) + "m)",
                        () -> nearest = "None");
    }

    @Override
    public String getDisplayInfo() {
        return nearest;
    }
}
