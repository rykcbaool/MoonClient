package dev.ryk.mod.commands.impl;

import dev.ryk.MoonClient;
import dev.ryk.core.impl.CommandManager;
import dev.ryk.mod.commands.Command;
import dev.ryk.mod.modules.Module;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class BindsCommand extends Command {

	public BindsCommand() {
		super("binds", "");
	}

	@Override
	public void runCommand(String[] parameters) {
		List<String> list = new ArrayList<>();
		for (Module x : MoonClient.MODULE.modules) {
			if (x.getBind().getKey() != -1) {
				list.add("§f" + x.getDisplayName() + " §7- §e" + x.getBind().getBind());
			}
		}
		Iterator<String> temp = list.iterator();
		int i = 0;
		StringBuilder string = new StringBuilder();
		while (temp.hasNext()) {
			if (i == 0) {
				string = new StringBuilder(temp.next());
			} else {
				string.append("§7, ").append(temp.next());
			}
			i++;
			if (i >= 3 || !temp.hasNext()) {
				CommandManager.sendChatMessage(string.toString());
				i = 0;
			}
		}
	}


	@Override
	public String[] getAutocorrect(int count, List<String> seperated) {
		return null;
	}
}
