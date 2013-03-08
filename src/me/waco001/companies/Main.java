package me.waco001.companies;


import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Logger;

import lib.Databases.Database;
import lib.Databases.SQLite;
import me.waco001.companies.includes.Metrics;
//import me.waco001.companies.listeners.CommandListener;
import me.waco001.companies.listeners.PlayerListener;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin{

	private PlayerListener PlayerListener = new PlayerListener(this);
	public static Permission permission = null;
	public static Economy economy = null;
	static FileConfiguration config;
	public static SQLite sqlite;
	public final Logger logger = Logger.getLogger("Minecraft");

	public void registerListeners(){
		PluginManager pm = getServer().getPluginManager();

		// Register The Listeners
		pm.registerEvents(this.PlayerListener, this);



	}
	public void onEnable(){
		registerListeners();
		pluginCheck();
		getLogger().info("---Companies Enabled---");
		//getCommand("basic").setExecutor(new CommandListener());
		sqlConnection();
		sqlDoesDatabaseExist();
	}
	public void onDisable(){
		try 
		{
			sqlite.close();
		} 
		catch (Exception e) 
		{
			logger.info(e.getMessage());
			getPluginLoader().disablePlugin(this);
		}

		getLogger().info("---Companies Disabled---");
	}
	private void pluginCheck() {
		try {
			Metrics metrics = new Metrics(this);
			metrics.start();
		} catch (IOException e) {
			// :{ Oh No!
		}
		if (!setupEconomy() ) {
			getLogger().severe(String.format("[%s] - Disabled due to no Vault dependency found!", getDescription().getName()));
			getServer().getPluginManager().disablePlugin(this);
			return;
		}
		if (setupEconomy()){
			getLogger().info("Vault Dependencies found!");
		}
		setupPermissions();	
		this.saveDefaultConfig();
	}
	private boolean setupPermissions()
	{
		RegisteredServiceProvider<Permission> permissionProvider = getServer().getServicesManager().getRegistration(net.milkbowl.vault.permission.Permission.class);
		if (permissionProvider != null) {
			permission = permissionProvider.getProvider();
		}
		return (permission != null);
	}
	private boolean setupEconomy()
	{
		RegisteredServiceProvider<Economy> economyProvider = getServer().getServicesManager().getRegistration(net.milkbowl.vault.economy.Economy.class);
		if (economyProvider != null) {
			economy = economyProvider.getProvider();
		}

		return (economy != null);
	}

	//SQLite
	public void sqlConnection() 
	{
		sqlite = new SQLite(logger, "Companies", getDataFolder().getAbsolutePath() + "/database", "database");
		try 
		{
			sqlite.open();
		} 
		catch (Exception e) 
		{
			logger.info(e.getMessage());
			getPluginLoader().disablePlugin(this);
		}
	}
	public void sqlDoesDatabaseExist()
	{

		try {
			getdb().query("CREATE TABLE IF NOT EXISTS companies (id INTEGER PRIMARY KEY, name STRING, short STRING, owner STRING);");

		} catch (SQLException e) {
			// TODO Auto-	generated catch block
			e.printStackTrace();
		}

		System.out.println("[Companies] Database Loaded.");
	}       
	public static Database getdb()
	{
		return sqlite;
	}
}
