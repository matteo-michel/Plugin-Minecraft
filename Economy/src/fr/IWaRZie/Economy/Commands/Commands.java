package fr.IWaRZie.Economy.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import fr.IWaRZie.Economy.Config;
import fr.IWaRZie.Economy.Main;

public class Commands implements CommandExecutor {

	private static Config config = Config.getConfig();
	
	public static void setConfig(Config config)
	{
		Commands.config = config;
	}
	
	
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String str, String[] args) {
		
		if (!(sender instanceof Player))
		{
			return false;
		}
		
		Player player = (Player) sender;
		
		if (args.length != 1)
		{
			player.sendMessage(config.getMessage("argumentErrorMessage"));
			return false;
		}
		
		if (!(isDouble(args[0])))
		{
			player.sendMessage(config.getMessage("argumentErrorMessage"));
			return false;
		}
		
		if (Double.parseDouble(args[0]) <= 0)
		{
			player.sendMessage(config.getMessage("intErrorMessage"));
			return false;
		}
		
		if (testBalance(player, Double.parseDouble(args[0])))
		{
			Inventory pINV = player.getInventory();
			pINV.addItem(config.getItem(args[0]));
			Main.economy.withdrawPlayer(player, Double.parseDouble(args[0]));
			player.sendMessage(config.getMessage("debitMessage", args[0]));
		} else {
			player.sendMessage(config.getMessage("moneyErrorMessage"));
		}
			
		return true;
	}

	public boolean testBalance(Player player, double montant)
	{
        Boolean b = false;
        if(Main.getInstance().setupEconomy()) 
        {
            double balance = Main.economy.getBalance(player);
            if(balance >= montant) {
                b = true;
            } else {
                b = false;
            }
        }
        return b;
    }
	
	
	public boolean isDouble(String chaine) {
		try {
			Double.parseDouble(chaine);
		} catch (NumberFormatException e){
			return false;
		}
 
		return true;
	}
	


		

}
