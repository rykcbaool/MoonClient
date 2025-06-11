package dev.ryk.mod.commands.impl;

import dev.ryk.MoonClient;
import dev.ryk.core.Manager;
import dev.ryk.core.impl.CommandManager;
import dev.ryk.core.impl.ConfigManager;
import dev.ryk.mod.commands.Command;

import java.util.List;

public class LoadCommand extends Command {

	public LoadCommand() {
		super("load", "[config]");
	}

	@Override
	public void runCommand(String[] parameters) {
		if (parameters.length == 0) {
			sendUsage();
			return;
		}
		CommandManager.sendChatMessage("§fLoading..");
		ConfigManager.options = Manager.getFile(parameters[0] + ".cfg");
		MoonClient.CONFIG = new ConfigManager();
		MoonClient.PREFIX = MoonClient.CONFIG.getString("prefix", MoonClient.PREFIX);
		MoonClient.CONFIG.loadSettings();
        ConfigManager.options = Manager.getFile("options.txt");
		MoonClient.save();
	}

	@Override
	public String[] getAutocorrect(int count, List<String> seperated) {
		return null;
	}
}
