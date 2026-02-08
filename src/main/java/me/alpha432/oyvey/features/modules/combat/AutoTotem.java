package me.alpha432.oyvey.features.modules.combat;

import me.alpha432.oyvey.features.modules.Module;
import net.minecraft.item.Items;
import net.minecraft.screen.slot.SlotActionType;

public class AutoTotem extends Module {
    public AutoTotem() {
        super("AutoTotem", "Automatically puts a totem in your offhand", Category.COMBAT, true, false, false);
    }

    @Override
    public void onTick() {
        if (mc.player == null || mc.player.getOffHandStack().getItem() == Items.TOTEM_OF_UNDYING)
            return;

        for (int i = 0; i < 36; i++) {
            if (mc.player.getInventory().getStack(i).getItem() == Items.TOTEM_OF_UNDYING) {
                mc.interactionManager.clickSlot(mc.player.currentScreenHandler.syncId, i < 9 ? i + 36 : i, 0,
                        SlotActionType.PICKUP, mc.player);
                mc.interactionManager.clickSlot(mc.player.currentScreenHandler.syncId, 45, 0, SlotActionType.PICKUP,
                        mc.player);
                mc.interactionManager.clickSlot(mc.player.currentScreenHandler.syncId, i < 9 ? i + 36 : i, 0,
                        SlotActionType.PICKUP, mc.player);
                return;
            }
        }
    }
}
