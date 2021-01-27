package fr.IWaRZie.Economy;

import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import fr.IWaRZie.Economy.Commands.Commands;
import fr.IWaRZie.Economy.Events.clickEvents;
import net.milkbowl.vault.economy.Economy;




public class Main extends JavaPlugin {

	public static Economy economy = null;
	private static Main main;
	
	public boolean setupEconomy(){
        RegisteredServiceProvider<Economy> economyProvider = getServer().getServicesManager().getRegistration(net.milkbowl.vault.economy.Economy.class);
        if (economyProvider != null) {
            economy = economyProvider.getProvider();
        }
        return (economy != null);
    }
	
	public void onEnable()
	{
		Bukkit.getPluginManager().registerEvents(new clickEvents(), this);
		this.getCommand("coin").setExecutor(new Commands());
		main = this;
		saveDefaultConfig();
		new Config();
		clickEvents.registerMat();
	}
	
	public static Main getInstance()
	{
		return main;
	}
	
}
