package me.alpha432.oyvey.features.modules.movement;

import me.alpha432.oyvey.features.modules.Module;
import net.minecraft.util.math.Vec3d;

public class Jesus extends Module {
    public Jesus() {
        super("Jesus", "Allows you to walk on water", Category.MOVEMENT, true, false, false);
    }

    @Override
    public void onTick() {
        if (mc.player == null || mc.world == null)
            return;
        if (mc.player.isSubmergedInWater() || mc.player.isInLava()) {
            mc.player.setVelocity(mc.player.getVelocity().x, 0.1, mc.player.getVelocity().z);
        } else if (mc.world.getBlockState(mc.player.getBlockPos().down()).getFluidState().isStill()) {
            mc.player.setVelocity(mc.player.getVelocity().x, 0.0, mc.player.getVelocity().z);
            mc.player.setOnGround(true);
        }
    }
}
