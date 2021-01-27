package fr.IWaRZie.Stacker;

public class Config {

	
	private String PREFIXE;
	private static Config instance;
	
	public static Config getConfig()
	{
		return instance;
	}
	
	public Config()
	{
		PREFIXE = Main.getMain().getConfig().getString("prefixe").replace("&", "§");
		instance = this;
		
	}
	
	public String getMessage(String message)
	{
		return PREFIXE + " " + Main.getMain().getConfig().getString(message).replace("&", "§");
	}
	
	public String getMessageWithoutPrefixe(String message)
	{
		return Main.getMain().getConfig().getString(message).replace("&", "§");
	}
}
