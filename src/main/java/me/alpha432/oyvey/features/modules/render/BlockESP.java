package me.alpha432.oyvey.features.modules.render;

import me.alpha432.oyvey.event.impl.Render3DEvent;
import me.alpha432.oyvey.features.modules.Module;
import me.alpha432.oyvey.features.modules.client.ClickGui;
import me.alpha432.oyvey.features.settings.Setting;
import me.alpha432.oyvey.util.ColorUtil;
import me.alpha432.oyvey.util.render.RenderUtil;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class BlockESP extends Module {
    public Setting<Boolean> diamond = bool("Diamond", true);
    public Setting<Boolean> gold = bool("Gold", true);
    public Setting<Boolean> iron = bool("Iron", true);
    public Setting<Boolean> ancient = bool("Ancient", true);
    public Setting<Boolean> spawner = bool("Spawner", true);
    public Setting<Integer> range = num("Range", 64, 10, 128);
    public Setting<Boolean> outline = bool("Outline", true);
    public Setting<Boolean> filled = bool("Filled", false);
    public Setting<Integer> alpha = num("Alpha", 50, 0, 255);

    private final List<BlockPos> posList = new ArrayList<>();

    public BlockESP() {
        super("BlockESP", "Highlights valuable blocks through walls", Category.RENDER, true, false, false);
    }

    @Override
    public void onTick() {
        if (mc.world == null || mc.player == null)
            return;

        posList.clear();
        BlockPos playerPos = mc.player.getBlockPos();
        int r = range.getValue();

        for (int x = -r; x <= r; x++) {
            for (int y = -r; y <= r; y++) {
                for (int z = -r; z <= r; z++) {
                    BlockPos pos = playerPos.add(x, y, z);
                    Block block = mc.world.getBlockState(pos).getBlock();
                    if (isTarget(block)) {
                        posList.add(pos);
                    }
                }
            }
        }
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
        if (spawner.getValue() && block == Blocks.SPAWNER)
            return true;
        return false;
    }

    @Override
    public void onRender3D(Render3DEvent event) {
        if (posList.isEmpty())
            return;

        int count = 0;
        for (BlockPos pos : posList) {
            Color color = ColorUtil.rainbow(count * ClickGui.getInstance().rainbowHue.getValue());
            if (outline.getValue()) {
                RenderUtil.drawBox(event.getMatrix(), pos, color, 1.0);
            }
            if (filled.getValue()) {
                RenderUtil.drawBoxFilled(event.getMatrix(), pos,
                        new Color(color.getRed(), color.getGreen(), color.getBlue(), alpha.getValue()));
            }
            count++;
            if (count > 500)
                break;
        }
    }
}
