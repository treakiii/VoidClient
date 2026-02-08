package me.alpha432.oyvey.features.modules.player;

import me.alpha432.oyvey.OyVey;
import me.alpha432.oyvey.features.modules.Module;
import me.alpha432.oyvey.features.settings.Setting;
import me.alpha432.oyvey.util.InteractionUtil;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;

import java.util.*;

public class AutoMine extends Module {
    public Setting<Boolean> aiMine = bool("AiMine", true);
    public Setting<Boolean> autoTool = bool("AutoTool", true);
    public Setting<Boolean> safeDive = bool("SafeDive", true);
    public Setting<Float> rotSpeed = num("LookSmooth", 0.5f, 0.1f, 1.0f);
    public Setting<Boolean> diamond = bool("Diamond", true);
    public Setting<Boolean> gold = bool("Gold", true);
    public Setting<Boolean> iron = bool("Iron", true);
    public Setting<Boolean> ancient = bool("AncientDebris", true);
    public Setting<Float> range = num("Range", 5.0f, 1.0f, 6.0f);
    public Setting<Float> aiRange = num("AiScanRange", 30.0f, 5.0f, 100.0f);

    private BlockPos mining = null;
    private final List<BlockPos> path = new ArrayList<>();
    private final List<BlockPos> targets = new ArrayList<>();

    public AutoMine() {
        super("AutoMine", "Automatically mines minerals with Genius AI", Category.PLAYER, true, false, false);
    }

    @Override
    public void onTick() {
        if (mc.world == null || mc.player == null)
            return;

        if (isInventoryFull()) {
            stopMovement();
            mining = null;
            return;
        }

        if (safeDive.getValue() && mc.player.getAir() < 100) {
            mc.options.jumpKey.setPressed(true);
        }

        if (mc.player.age % 40 == 0 || targets.isEmpty()) {
            updateTargets();
        }

        if (mining != null) {
            if (mc.world.getBlockState(mining).isAir()) {
                mining = null;
                path.clear();
                stopMovement();
                return;
            }

            double distSq = mc.player.squaredDistanceTo(mining.toCenterPos());
            if (distSq <= range.getValue() * range.getValue()) {
                stopMovement();
                if (autoTool.getValue())
                    equipBestTool(mc.world.getBlockState(mining).getBlock());
                InteractionUtil.breakBlock(mining);
                OyVey.rotationManager.lookAtPosSmooth(mining, rotSpeed.getValue());
            } else {
                if (aiMine.getValue()) {
                    navigate();
                } else {
                    mining = null;
                }
            }
        } else {
            if (!targets.isEmpty()) {
                mining = targets.get(0);
            }
        }
    }

    private void updateTargets() {
        targets.clear();
        float r = aiMine.getValue() ? aiRange.getValue() : range.getValue();
        BlockPos playerPos = mc.player.getBlockPos();
        int radius = (int) Math.ceil(r);

        for (int x = -radius; x <= radius; x++) {
            for (int y = -radius; y <= radius; y++) {
                for (int z = -radius; z <= radius; z++) {
                    BlockPos pos = playerPos.add(x, y, z);
                    if (mc.player.squaredDistanceTo(pos.toCenterPos()) > r * r)
                        continue;
                    if (isTarget(mc.world.getBlockState(pos).getBlock())) {
                        targets.add(pos);
                    }
                }
            }
        }
        targets.sort(Comparator.comparingDouble(p -> mc.player.squaredDistanceTo(p.toCenterPos())));
    }

    private void navigate() {
        if (path.isEmpty() || mc.player.age % 20 == 0) {
            findPath();
        }

        if (!path.isEmpty()) {
            BlockPos target = path.get(0);
            if (mc.player.getBlockPos().equals(target)) {
                path.remove(0);
                if (path.isEmpty())
                    return;
                target = path.get(0);
            }

            OyVey.rotationManager.lookAtVec3dSmooth(target.toCenterPos(), rotSpeed.getValue());
            mc.options.forwardKey.setPressed(true);

            if (target.getY() > mc.player.getY() || mc.player.horizontalCollision) {
                mc.options.jumpKey.setPressed(true);
            } else {
                mc.options.jumpKey.setPressed(false);
            }
        } else {
            handleFallbackMovement();
        }
    }

    private void findPath() {
        path.clear();
        BlockPos start = mc.player.getBlockPos();
        BlockPos end = mining;

        Queue<BlockPos> queue = new LinkedList<>();
        Map<BlockPos, BlockPos> parents = new HashMap<>();

        queue.add(start);
        parents.put(start, null);

        while (!queue.isEmpty()) {
            BlockPos current = queue.poll();
            if (current.getSquaredDistance(end) < 9) {
                BlockPos node = current;
                while (node != null) {
                    path.add(0, node);
                    node = parents.get(node);
                }
                if (!path.isEmpty())
                    path.remove(0);
                return;
            }

            for (Direction dir : Direction.values()) {
                BlockPos next = current.offset(dir);
                if (parents.containsKey(next))
                    continue;

                if (mc.world.getBlockState(next).isAir() && mc.world.getBlockState(next.up()).isAir()) {
                    if (next.getSquaredDistance(start) < aiRange.getValue() * aiRange.getValue()) {
                        parents.put(next, current);
                        queue.add(next);
                    }
                }
            }
            if (parents.size() > 1000)
                break;
        }
    }

    private void handleFallbackMovement() {
        OyVey.rotationManager.lookAtVec3dSmooth(mining.toCenterPos(), rotSpeed.getValue());
        mc.options.forwardKey.setPressed(true);

        if (mc.player.horizontalCollision) {
            Direction dir = mc.player.getHorizontalFacing();
            BlockPos footPos = mc.player.getBlockPos().offset(dir);
            BlockPos eyePos = footPos.up();

            if (!mc.world.getBlockState(eyePos).isAir()) {
                if (autoTool.getValue())
                    equipBestTool(mc.world.getBlockState(eyePos).getBlock());
                InteractionUtil.breakBlock(eyePos);
            } else if (!mc.world.getBlockState(footPos).isAir()) {
                if (autoTool.getValue())
                    equipBestTool(mc.world.getBlockState(footPos).getBlock());
                InteractionUtil.breakBlock(footPos);
            } else {
                mc.options.jumpKey.setPressed(true);
            }
        }
    }

    private void stopMovement() {
        mc.options.forwardKey.setPressed(false);
        mc.options.jumpKey.setPressed(false);
    }

    private void equipBestTool(Block block) {
        float bestSpeed = 1.0f;
        int bestSlot = -1;
        for (int i = 0; i < 9; i++) {
            ItemStack stack = mc.player.getInventory().getStack(i);
            float speed = stack.getMiningSpeedMultiplier(block.getDefaultState());
            if (speed > bestSpeed) {
                bestSpeed = speed;
                bestSlot = i;
            }
        }
        if (bestSlot != -1) {
            mc.player.getInventory().selectedSlot = bestSlot;
        }
    }

    private boolean isInventoryFull() {
        for (int i = 0; i < 36; i++) {
            if (mc.player.getInventory().getStack(i).isEmpty())
                return false;
        }
        return true;
    }

    private boolean isTarget(Block block) {
        if (diamond.getValue() && (block == Blocks.DIAMOND_ORE || block == Blocks.DEEPSLATE_DIAMOND_ORE))
            return true;
        if (gold.getValue()
                && (block == Blocks.GOLD_ORE || block == Blocks.DEEPSLATE_GOLD_ORE || block == Blocks.NETHER_GOLD_ORE))
            return true;
        if (iron.getValue() && (block == Blocks.IRON_ORE || block == Blocks.DEEPSLATE_IRON_ORE))
            return true;
        if (ancient.getValue() && block == Blocks.ANCIENT_DEBRIS)
            return true;
        return false;
    }

    @Override
    public void onDisable() {
        stopMovement();
        mining = null;
        path.clear();
        targets.clear();
    }

    @Override
    public String getDisplayInfo() {
        if (isInventoryFull())
            return "Full";
        return mining != null ? "Mining" : "Scanning";
    }
}
