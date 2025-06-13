package dev.ryk.mod.modules.impl.combat;

import dev.ryk.api.events.eventbus.EventHandler;
import dev.ryk.api.events.impl.UpdateWalkingPlayerEvent;
import dev.ryk.api.utils.entity.EntityUtil;
import dev.ryk.api.utils.entity.InventoryUtil;
import dev.ryk.api.utils.math.Timer;
import dev.ryk.mod.gui.clickgui.ClickGuiScreen;
import dev.ryk.mod.modules.Module;
import dev.ryk.mod.modules.settings.impl.BooleanSetting;
import dev.ryk.mod.modules.settings.impl.SliderSetting;
import net.minecraft.client.gui.screen.*;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import net.minecraft.item.*;
import net.minecraft.screen.slot.SlotActionType;
import org.jetbrains.annotations.Nullable;

public class Offhand extends Module {
	private final BooleanSetting mainHand = add(new BooleanSetting("MainHand", false));
	private final BooleanSetting crystal = add(new BooleanSetting("Crystal", false, () -> !mainHand.getValue()));
	private final BooleanSetting gapple = add(new BooleanSetting("Gapple", false, () -> !mainHand.getValue()));
	private final SliderSetting healthThreshold = add(new SliderSetting("Health", 16.0f, 0.0f, 36.0f, .1f));

	private final Timer timer = new Timer();
	private int totemsUsed = 0;

	public Offhand() {
		super("Offhand", Category.Combat);
		setChinese("副手管理");
	}

	@Override
	public String getInfo() {
		return String.valueOf(totemsUsed);
	}

	@EventHandler
	public void onUpdate(UpdateWalkingPlayerEvent event) {
		handleSwapLogic();
	}

	@Override
	public void onUpdate() {
		handleSwapLogic();
	}

	private void handleSwapLogic() {
		if (nullCheck() || !timer.passedMs(200)) return;

		// only do swaps when inventory or chat or GUI-friendly open, like current check
		if (mc.currentScreen != null &&
				!(mc.currentScreen instanceof InventoryScreen) &&
				!(mc.currentScreen instanceof ChatScreen) &&
				!(mc.currentScreen instanceof ClickGuiScreen) &&
				!(mc.currentScreen instanceof GameMenuScreen)) {
			return;
		}

		float currentHealth = mc.player.getHealth() + mc.player.getAbsorptionAmount();

		// Priority 1: if health <= threshold → ensure Totem in off/main hand
		if (currentHealth <= healthThreshold.getValue()) {
			ensureTotem();
			return;
		}

		// Priority 2: if health > threshold → restore chosen item in offhand (only if mainHand=false)
		if (!mainHand.getValue() && currentHealth > healthThreshold.getValue()) {
			if (gapple.getValue()) swapOffhandItem(Items.ENCHANTED_GOLDEN_APPLE, Items.GOLDEN_APPLE);
			else if (crystal.getValue()) swapOffhandItem(Items.END_CRYSTAL, null);
		}
	}

	private void ensureTotem() {
		ItemStack offStack = mc.player.getOffHandStack();
		ItemStack mainStack = mc.player.getMainHandStack();
		boolean totemInHand = offStack.getItem() == Items.TOTEM_OF_UNDYING
				|| (mainHand.getValue() && mainStack.getItem() == Items.TOTEM_OF_UNDYING);

		if (totemInHand) return;

		int totemSlot = findItemSlot(Items.TOTEM_OF_UNDYING);
		if (totemSlot == -1) return;

		if (mainHand.getValue()) {
			InventoryUtil.switchToSlot(0);
			swapSlotToSlot(totemSlot, 36);
		} else {
			swapSlotToSlot(totemSlot, 45);
		}
		timer.reset();
		totemsUsed++;
	}

	private void swapOffhandItem(Item primary, @Nullable Item fallback) {
		ItemStack off = mc.player.getOffHandStack();

		// if already has primary or fallback (e.g. enchanted vs regular gapple), noop
		if (off.getItem() == primary || (fallback != null && off.getItem() == fallback)) return;

		// find primary first, else fallback
		int slot = findItemSlot(primary);
		if (slot == -1 && fallback != null) {
			slot = findItemSlot(fallback);
		}
		if (slot == -1) return;

		swapSlotToSlot(slot, 45);
		timer.reset();
	}

	private void swapSlotToSlot(int from, int to) {
		mc.interactionManager.clickSlot(mc.player.currentScreenHandler.syncId, from, 0, SlotActionType.PICKUP, mc.player);
		mc.interactionManager.clickSlot(mc.player.currentScreenHandler.syncId, to, 0, SlotActionType.PICKUP, mc.player);
		mc.interactionManager.clickSlot(mc.player.currentScreenHandler.syncId, from, 0, SlotActionType.PICKUP, mc.player);
		EntityUtil.syncInventory();
	}

	public static int findItemSlot(Item item) {
		for (int i = 44; i >= 0; i--) {
			ItemStack s = mc.player.getInventory().getStack(i);
			if (s.getItem() == item) {
				return i < 9 ? i + 36 : i;
			}
		}
		return -1;
	}
}
