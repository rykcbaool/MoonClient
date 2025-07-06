package dev.ryk.mod.modules.impl.movement;

import dev.ryk.mod.modules.Module;
import dev.ryk.mod.modules.settings.impl.EnumSetting;

public class AutoWalk extends Module {
    public static AutoWalk INSTANCE;

    public enum Mode {
        Forward,
        Path
    }

    // EnumSetting to choose mode
    EnumSetting<Mode> mode = add(new EnumSetting<>("Mode", Mode.Forward));

    public AutoWalk() {
        super("AutoWalk", Category.Movement);
        setChinese("自动前进");
        INSTANCE = this;
    }

    @Override
    public void onUpdate() {
        if (mode.is(Mode.Forward)) {
            mc.options.forwardKey.setPressed(true);
        }
    }

    @Override
    public void onDisable() {
        mc.options.forwardKey.setPressed(false);
    }

    public boolean forward() {
        return isOn() && mode.is(Mode.Forward);
    }
}
