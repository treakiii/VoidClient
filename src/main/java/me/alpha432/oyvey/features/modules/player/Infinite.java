package me.alpha432.oyvey.features.modules.player;

import me.alpha432.oyvey.features.modules.Module;
import me.alpha432.oyvey.features.settings.Setting;
import net.minecraft.item.ItemStack;

public class Infinite extends Module {
    public Setting<Boolean> durability = bool("Durability", true);
    public Setting<Boolean> items = bool("Items", true);

    public Infinite() {
        super("Infinite", "Makes items and durability infinite (Client-side/Visual)", Category.PLAYER, false, false,
                false);
    }

    @Override
    public void onTick() {
        if (mc.player == null)
            return;

        for (int i = 0; i < 36; i++) {
            ItemStack stack = mc.player.getInventory().getStack(i);
            if (!stack.isEmpty()) {
                if (durability.getValue() && stack.isDamageable()) {
                    stack.setDamage(0);
                }
                if (items.getValue() && stack.getCount() < 64) {
                    // This is visual only for most servers, but useful for creative/singlep or
                    // broken servers
                    stack.setCount(99);
                }
            }
        }
    }
}
