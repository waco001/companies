package me.waco001.companies.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import me.waco001.companies.Main;

public class PlayerListener implements Listener{
	public Main plugin;

	public PlayerListener(Main m)
	{
	    this.plugin = m;
	}
	@EventHandler(priority=EventPriority.MONITOR)
	public void onPlayerJoin(PlayerJoinEvent e) {
		Player p = e.getPlayer();
		p.sendMessage("Hello There");
		}
	}
