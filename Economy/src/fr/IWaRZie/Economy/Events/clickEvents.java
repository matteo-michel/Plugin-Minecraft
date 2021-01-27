package fr.IWaRZie.Economy.Events;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

import fr.IWaRZie.Economy.Config;
import fr.IWaRZie.Economy.Main;

public class clickEvents implements Listener {

	private static Config config = Config.getConfig();
	private static List<Material> mat = new ArrayList<>();
	
	public static void registerMat()
	{
		mat.add(config.getItem(Main.getInstance().getConfig().getString("montant1")).getType());
		mat.add(config.getItem(Main.getInstance().getConfig().getString("montant2")).getType());
		mat.add(config.getItem(Main.getInstance().getConfig().getString("montant3")).getType());
	}
	
	public static void setConfig(Config config)
	{
		clickEvents.config = config;
	}
	
	public static int getAmount(Player arg0, ItemStack arg1) {
        if (arg1 == null)
            return 0;
        int amount = 0;
        for (int i = 0; i < 36; i++) {
            ItemStack slot = arg0.getInventory().getItem(i);
            if (slot == null || !slot.isSimilar(arg1))
                continue;
            amount += slot.getAmount();
        }
        return amount;
    }
	
	@EventHandler
	public void onClick(PlayerInteractEvent event)
	{
		
		Player player = event.getPlayer();
		Inventory pInv = player.getInventory();
		
		
		if (event.getItem() == null)
		{
			return;
		}
		
		if ((event.getAction() == Action.RIGHT_CLICK_AIR) || (event.getAction() == Action.RIGHT_CLICK_BLOCK))
		{
			if (event.getHand().toString() == "HAND")
			{
				if (mat.contains(event.getItem().getType()) && (event.getItem().hasItemMeta()))
				{
					if (event.getItem().getItemMeta().hasItemFlag(ItemFlag.HIDE_ENCHANTS))
					{
						String name = event.getItem().getItemMeta().getDisplayName();
						String result = Config.getConfig().getMessageWithoutPrefixe("title");
						String[] str = result.split("%MONTANT%");
						String mont = name.replace(str[0], "");
						double montant = Double.parseDouble(mont.replace(str[1], "")) * getAmount(player, event.getItem());

						Main.economy.depositPlayer(player, montant);
						player.sendMessage(config.getMessage("creditMessage", "" + montant));
						event.setCancelled(true);
						pInv.remove(player.getInventory().getItemInMainHand());						
					}
				}
			}
		}
		
	}
	
	
}
