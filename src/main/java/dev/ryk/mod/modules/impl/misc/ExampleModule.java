package dev.ryk.mod.modules.impl.misc;

import dev.ryk.mod.modules.Module;



public class ExampleModule extends Module {
    public static ExampleModule INSTANCE;

    public ExampleModule() {
        super("ExampleModule", Category.Misc);
        setChinese("加好友");
        INSTANCE = this;
    }

    @Override
    public void onEnable() {

    }
}