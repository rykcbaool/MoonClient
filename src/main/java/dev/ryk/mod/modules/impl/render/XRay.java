package dev.ryk.mod.modules.impl.render;

import dev.ryk.MoonClient;
import dev.ryk.mod.modules.Module;
import net.minecraft.block.Block;

public class XRay extends Module {
    public static XRay INSTANCE;
    public XRay() {
        super("XRay", Category.Render);
        setChinese("矿物透视");
        INSTANCE = this;
    }

    @Override
    public void onEnable() {
        mc.chunkCullingEnabled = false;
        mc.worldRenderer.reload();
    }

    @Override
    public void onDisable() {
        mc.chunkCullingEnabled = true;
        mc.worldRenderer.reload();
    }

    public boolean isCheckableOre(Block block) {
        return MoonClient.XRAY.inWhitelist(block.getTranslationKey());
    }
}
