package dev.ryk.mod.commands.impl;

import dev.ryk.MoonClient;
import dev.ryk.core.impl.CommandManager;
import dev.ryk.core.impl.ConfigManager;
import dev.ryk.mod.commands.Command;

import java.util.List;

public class ReloadCommand extends Command {

	public ReloadCommand() {
		super("reload", "");
	}

	@Override
	public void runCommand(String[] parameters) {
		CommandManager.sendChatMessage("§fReloading..");
		MoonClient.CONFIG = new ConfigManager();
		MoonClient.PREFIX = MoonClient.CONFIG.getString("prefix", MoonClient.PREFIX);
		MoonClient.CONFIG.loadSettings();
		MoonClient.XRAY.read();
		MoonClient.TRADE.read();
		MoonClient.FRIEND.read();
	}

	@Override
	public String[] getAutocorrect(int count, List<String> seperated) {
		return null;
	}
}
