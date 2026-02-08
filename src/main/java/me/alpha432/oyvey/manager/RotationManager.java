package me.alpha432.oyvey.manager;

import me.alpha432.oyvey.util.MathUtil;
import me.alpha432.oyvey.util.traits.Util;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.entity.Entity;

public class RotationManager implements Util {
    private float yaw;
    private float pitch;

    public void updateRotations() {
        this.yaw = mc.player.getYaw();
        this.pitch = mc.player.getPitch();
    }

    public void restoreRotations() {
        mc.player.setYaw(yaw);
        mc.player.headYaw = yaw;
        mc.player.setPitch(pitch);
    }

    public void setPlayerRotations(float yaw, float pitch) {
        mc.player.setYaw(yaw);
        mc.player.headYaw = yaw;
        mc.player.setPitch(pitch);
    }

    public void setPlayerYaw(float yaw) {
        mc.player.setYaw(yaw);
        mc.player.headYaw = yaw;
    }

    public void lookAtPos(BlockPos pos) {
        float[] angle = MathUtil.calcAngle(mc.player.getEyePos(),
                new Vec3d((float) pos.getX() + 0.5f, (float) pos.getY() + 0.5f, (float) pos.getZ() + 0.5f));
        this.setPlayerRotations(angle[0], angle[1]);
    }

    public void lookAtVec3d(Vec3d vec3d) {
        float[] angle = MathUtil.calcAngle(mc.player.getEyePos(), new Vec3d(vec3d.x, vec3d.y, vec3d.z));
        this.setPlayerRotations(angle[0], angle[1]);
    }

    public void lookAtVec3d(double x, double y, double z) {
        Vec3d vec3d = new Vec3d(x, y, z);
        this.lookAtVec3d(vec3d);
    }

    public void lookAtEntity(Entity entity) {
        float[] angle = MathUtil.calcAngle(mc.player.getEyePos(), entity.getEyePos());
        this.setPlayerRotations(angle[0], angle[1]);
    }

    public void setPlayerRotations(float yaw, float pitch, float speed) {
        float f1 = mc.player.getYaw() + MathHelper.wrapDegrees(yaw - mc.player.getYaw());
        float f2 = mc.player.getPitch() + MathHelper.wrapDegrees(pitch - mc.player.getPitch());

        mc.player.setYaw(mc.player.getYaw() + (f1 - mc.player.getYaw()) * speed);
        mc.player.setPitch(mc.player.getPitch() + (f2 - mc.player.getPitch()) * speed);
        mc.player.headYaw = mc.player.getYaw();
    }

    public void setPlayerPitch(float pitch) {
        mc.player.setPitch(pitch);
    }

    public float getYaw() {
        return this.yaw;
    }

    public void setYaw(float yaw) {
        this.yaw = yaw;
    }

    public float getPitch() {
        return this.pitch;
    }

    public void setPitch(float pitch) {
        this.pitch = pitch;
    }

    public void lookAtPosSmooth(BlockPos pos, float speed) {
        float[] angle = MathUtil.calcAngle(mc.player.getEyePos(),
                new Vec3d((float) pos.getX() + 0.5f, (float) pos.getY() + 0.5f, (float) pos.getZ() + 0.5f));
        this.setPlayerRotations(angle[0], angle[1], speed);
    }

    public void lookAtVec3dSmooth(Vec3d vec3d, float speed) {
        float[] angle = MathUtil.calcAngle(mc.player.getEyePos(), vec3d);
        this.setPlayerRotations(angle[0], angle[1], speed);
    }

}
