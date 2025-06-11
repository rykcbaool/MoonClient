package dev.ryk.mod.commands.impl;

import dev.ryk.MoonClient;
import dev.ryk.core.impl.CommandManager;
import dev.ryk.core.impl.ModuleManager;
import dev.ryk.mod.commands.Command;
import dev.ryk.mod.modules.Module;

import java.util.ArrayList;
import java.util.List;

public class TCommand extends Command {

	public TCommand() {
		super("t", "[module]");
	}

	@Override
	public void runCommand(String[] parameters) {
		if (parameters.length == 0) {
			sendUsage();
			return;
		}
		String moduleName = parameters[0];
		Module module = MoonClient.MODULE.getModuleByName(moduleName);
		if (module == null) {
			CommandManager.sendChatMessage("§fUnknown module!");
			return;
		}
		module.toggle();
	}

	@Override
	public String[] getAutocorrect(int count, List<String> seperated) {
		if (count == 1) {
			String input = seperated.get(seperated.size() - 1).toLowerCase();
			ModuleManager cm = MoonClient.MODULE;
			List<String> correct = new ArrayList<>();
			for (Module x : cm.modules) {
				if (input.equalsIgnoreCase(MoonClient.PREFIX + "toggle") || x.getName().toLowerCase().startsWith(input)) {
					correct.add(x.getName());
				}
			}
			int numCmds = correct.size();
			String[] commands = new String[numCmds];

			int i = 0;
			for (String x : correct) {
				commands[i++] = x;
			}

			return commands;
		}
		return null;
	}
}
