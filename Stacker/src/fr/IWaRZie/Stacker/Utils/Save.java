package fr.IWaRZie.Stacker.Utils;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import fr.IWaRZie.Stacker.Barrel;
import fr.IWaRZie.Stacker.Main;

public class Save {
	
	private static Save instance;
	public static Save getSave() {
		return instance;
	}
	
	private File SAVEFILE = new File(Main.getMain().getDataFolder(), "barrels.yml");
	private String CONFIGPATH = "barrels";
	
	private FileConfiguration config;
	
	public Save() {
		this.config = getConfig();
		
		ConfigurationSection barrels = config.getConfigurationSection(CONFIGPATH);
		
		if(barrels != null) {
			
			for(String str : barrels.getKeys(false)) {
				SerializedBarrel srzBarrel = new SerializedBarrel(config.getString(CONFIGPATH + "." + str));
				Barrel.registerBarrel(new SerializedLocation(str).getLocation(), srzBarrel.getType(), srzBarrel.getNumber());
			}
		}
		
		instance = this;
	}
	
	public void addBarrel(Barrel barrel) {
		SerializedLocation serializedLocation = barrel.getSerializedLocation();
		
		config.set(CONFIGPATH + "." + serializedLocation.toString(), barrel.getSerializedBarrel().toString());
		
		saveConfig(config);
	}
	
	public void removeBarrel(Barrel barrel) {
		SerializedLocation serializedLocation = barrel.getSerializedLocation();
		
		config.set(CONFIGPATH + "." + serializedLocation.toString(), null);
		
		saveConfig(config);
	}
	
	public void modifyBarrel(Barrel barrel)
	{
		SerializedLocation serializedLocation = barrel.getSerializedLocation();

		config.set(CONFIGPATH + "." + serializedLocation.toString(), barrel.getSerializedBarrel().toString());
		
		saveConfig(config);
	}
	
	private FileConfiguration getConfig() {
		
		if(!SAVEFILE.exists()){
			try{
				SAVEFILE.createNewFile();
			}catch(IOException e){
				e.printStackTrace();
			}
		}
		
		FileConfiguration config = YamlConfiguration.loadConfiguration(SAVEFILE);
		
		return config;
	}
	
	private void saveConfig(FileConfiguration config) {
		try {
			config.save(SAVEFILE);
			
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	
}
