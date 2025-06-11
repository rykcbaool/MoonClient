package dev.ryk.mod.modules.impl.misc;

import dev.ryk.mod.modules.Module;

public class AntiBookBan extends Module {
    public static AntiBookBan INSTANCE;
    public AntiBookBan() {
        super("AntiBookBan", Category.Misc);
        setChinese("反书封禁");
        INSTANCE = this;
    }
}
