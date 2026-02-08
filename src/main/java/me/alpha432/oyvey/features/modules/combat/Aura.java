package me.alpha432.oyvey.features.modules.combat;

import me.alpha432.oyvey.OyVey;
import me.alpha432.oyvey.features.modules.Module;
import me.alpha432.oyvey.features.settings.Setting;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Hand;

public class Aura extends Module {
    public Setting<Float> range = num("Range", 6.0f, 0.1f, 7.0f);
    public Setting<Boolean> rotate = bool("Rotate", true);

    public Aura() {
        super("Aura", "Attacks entities", Category.COMBAT, true, false, false);
    }

    @Override
    public void onTick() {
        if (mc.player == null || mc.world == null)
            return;

        Entity target = null;
        float dist = range.getValue();

        for (Entity entity : mc.world.getEntities()) {
            if (entity instanceof PlayerEntity && entity != mc.player && entity.isAlive()) {
                float d = mc.player.distanceTo(entity);
                if (d < dist) {
                    dist = d;
                    target = entity;
                }
            }
        }

        if (target != null) {
            if (rotate.getValue()) {
                OyVey.rotationManager.lookAtEntity(target);
            }
            mc.interactionManager.attackEntity(mc.player, target);
            mc.player.swingHand(Hand.MAIN_HAND);
        }
    }
}
