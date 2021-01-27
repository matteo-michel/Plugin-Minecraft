package fr.IWaRZie.Stacker;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import fr.IWaRZie.Stacker.Events.ArmorStandEvent;
import fr.IWaRZie.Stacker.Events.ExplodeEvent;
import fr.IWaRZie.Stacker.Events.InventoryEvent;
import fr.IWaRZie.Stacker.Events.breakEvent;
import fr.IWaRZie.Stacker.Events.clickEvents;
import fr.IWaRZie.Stacker.Utils.Save;


public class Main extends JavaPlugin{

	private static Main main;
	
	public static Main getMain()
	{
		return Main.main;
	}
	
	public void onEnable()
	{
		main = this;
		
		saveDefaultConfig();
		new Config();
		new Save();
		Bukkit.getPluginManager().registerEvents(new clickEvents(), this);
		Bukkit.getPluginManager().registerEvents(new breakEvent(), this);
		Bukkit.getPluginManager().registerEvents(new ExplodeEvent(), this);
		Bukkit.getPluginManager().registerEvents(new ArmorStandEvent(), this);
		Bukkit.getPluginManager().registerEvents(new InventoryEvent(), this);
	}
	
	public void onDisable()
	{
		Barrel.removeArmorStand();
	}
}
