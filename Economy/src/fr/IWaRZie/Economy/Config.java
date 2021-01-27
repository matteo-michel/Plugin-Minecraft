package fr.IWaRZie.Economy;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import fr.IWaRZie.Economy.Commands.Commands;
import fr.IWaRZie.Economy.Events.clickEvents;

public class Config {

	private ItemStack item;
	private static Config instance;
	private String PREFIXE;
	
	public static Config getConfig()
	{
		return instance;
	}
	
	public static void reloadConfig()
	{
		Main.getInstance().reloadConfig();
		new Config();
	}
	
	public Config()
	{
		PREFIXE = Main.getInstance().getConfig().getString("prefixe").replace("&", "§");
		instance = this;
		Commands.setConfig(this);
		clickEvents.setConfig(this);
		
	}
	
	public String getTitle(String montant)
	{
		return Main.getInstance().getConfig().getString("title").replace("&", "§").replace("%MONTANT%", montant);
	}
	
	public ItemStack getItem(String montant)
	{
		List<String> lore = new ArrayList<>();
		for (String str : Main.getInstance().getConfig().getStringList("itemDescription"))
		{
			lore.add(str.replace("&", "§"));
		}
		
		if (Double.parseDouble(montant) < Main.getInstance().getConfig().getInt("montant2"))
		{
			item = new ItemStack(Material.getMaterial(Main.getInstance().getConfig().getString("item1")));
		} else if (Double.parseDouble(montant) < Main.getInstance().getConfig().getInt("montant3"))
		{
			item = new ItemStack(Material.getMaterial(Main.getInstance().getConfig().getString("item2")));
		} else
		{
			item = new ItemStack(Material.getMaterial(Main.getInstance().getConfig().getString("item3")));
		}
		
		
		ItemMeta itM = item.getItemMeta();
		itM.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 100, true);
		itM.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		itM.setLore(lore);
		itM.setDisplayName(getTitle(montant));
		item.setItemMeta(itM);
		return item;
	}
	
	public String getMessage(String message)
	{
		return PREFIXE + " " + Main.getInstance().getConfig().getString(message).replace("&", "§");
	}
	
	public String getMessage(String message, String montant)
	{
		return getMessage(message).replace("%MONTANT%", montant);
	}
	
	public String getMessageWithoutPrefixe(String message)
	{
		return Main.getInstance().getConfig().getString(message).replace("&", "§");
	}
	
}
