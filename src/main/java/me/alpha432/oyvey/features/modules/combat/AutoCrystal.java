package me.alpha432.oyvey.features.modules.combat;

import me.alpha432.oyvey.features.modules.Module;
import me.alpha432.oyvey.features.settings.Setting;
import net.minecraft.entity.decoration.EndCrystalEntity;
import net.minecraft.item.Items;
import net.minecraft.util.Hand;

public class AutoCrystal extends Module {
    public Setting<Float> range = num("Range", 5.0f, 1.0f, 10.0f);
    public Setting<Boolean> place = bool("Place", true);
    public Setting<Boolean> explode = bool("Explode", true);

    public AutoCrystal() {
        super("AutoCrystal", "Automatically places and explodes crystals", Category.COMBAT, true, false, false);
    }

    @Override
    public void onTick() {
        if (mc.player == null || mc.world == null)
            return;

        if (explode.getValue()) {
            for (var entity : mc.world.getEntities()) {
                if (entity instanceof EndCrystalEntity crystal) {
                    if (mc.player.squaredDistanceTo(crystal.getPos()) <= range.getValue() * range.getValue()) {
                        mc.interactionManager.attackEntity(mc.player, crystal);
                        mc.player.swingHand(Hand.MAIN_HAND);
                        return;
                    }
                }
            }
        }

        if (place.getValue() && mc.player.getStackInHand(Hand.MAIN_HAND).getItem() == Items.END_CRYSTAL) {
            // Placement logic would involve finding a suitable block (obsidian/bedrock)
        }
    }
}
