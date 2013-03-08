package me.waco001.companies.listeners;

import me.waco001.companies.Main;
import me.waco001.companies.cmds.*;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandListener implements CommandExecutor{
	@SuppressWarnings("unused")
	private Main plugin; // pointer to your main class, unrequired if you don't need methods from the main class

	public void MyPluginCommandExecutor(Main plugin) {
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		Player player = (Player) sender;
		if(cmd.getName().equalsIgnoreCase("c") || cmd.getName().equalsIgnoreCase("company") || cmd.getName().equalsIgnoreCase("companies")){
			if (sender instanceof Player) {
				// do something
			} else {
				sender.sendMessage("You must be a player!");
				return false;
			}
			// do something
			return false;
		}
		return false;
	}

}
