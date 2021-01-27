package fr.IWaRZie.Stacker.Events;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import fr.IWaRZie.Stacker.Barrel;
import fr.IWaRZie.Stacker.Config;

public class clickEvents implements Listener {

	private static Config config = Config.getConfig();
	
	public static void setConfig(Config config)
	{
		clickEvents.config = config;
	}
	
	@EventHandler
	public void onBlockClick(PlayerInteractEvent event)
	{
		Player player = event.getPlayer();
		Block block = event.getClickedBlock();
		if (block == null) return;
		if (block.getType().equals(Material.BARREL))
		{
			if (!player.isSneaking())
			{
				if (event.getItem() != null)
				{
					if (event.getAction().equals(Action.RIGHT_CLICK_BLOCK) && event.getHand().toString() == "HAND" && player.getInventory().getItemInMainHand().getType().getMaxDurability()==0)
					{
						if (Barrel.getBarrel(block.getLocation()) == null)
						{
							new Barrel(block.getLocation(), player.getInventory().getItemInMainHand().getType());
						} 
						
						Barrel b = Barrel.getBarrel(block.getLocation());
						if (!b.addBlock(player.getInventory().getItemInMainHand().getType()))
							player.sendMessage(config.getMessage("NoStackableElementMessage"));
						else
						{
							player.getInventory().getItemInMainHand().setAmount(player.getInventory().getItemInMainHand().getAmount() - 1);
						}
						event.setCancelled(true);
					}
					if ((event.getAction().equals(Action.LEFT_CLICK_BLOCK) && event.getHand().toString() == "HAND"))
					{
						if (Barrel.getBarrel(block.getLocation()) == null)
						{
							new Barrel(block.getLocation(), player.getInventory().getItemInMainHand().getType());
						} 
						Barrel b = Barrel.getBarrel(block.getLocation());
						if (!b.addBlock(player.getInventory().getItemInMainHand().getType(), player.getInventory().getItemInMainHand().getAmount()))
							player.sendMessage(config.getMessage("NoStackableElementMessage"));
						else
						{
							player.getInventory().getItemInMainHand().setAmount(0);
						}		
						event.setCancelled(true);
					}
					
					if (player.getInventory().getItemInMainHand().getType().getMaxDurability()!=0)
					{
						event.setCancelled(true);
						player.sendMessage(config.getMessage("ElementDurabiltyNoStackable"));
					}
				}		
			} else
			{
				if (Barrel.getBarrel(block.getLocation()) != null)
				{
					Barrel b = Barrel.getBarrel(block.getLocation());
					
					
					if (event.getAction().equals(Action.RIGHT_CLICK_BLOCK) && event.getHand().toString() == "HAND")
					{
						if (!(player.getInventory().getItemInMainHand().getType() == Material.HOPPER && b.getType() != Material.HOPPER))
						{
							if (!player.getInventory().addItem(new ItemStack(b.getType(), 1)).isEmpty())
							{
								b.dropBlock(1);
							} else
							{
								b.removeBlock(player);
							}
							event.setCancelled(true);
						}
					}
					if ((event.getAction().equals(Action.LEFT_CLICK_BLOCK) && event.getHand().toString() == "HAND"))
					{
							if (!player.getInventory().addItem(new ItemStack(b.getType(), b.getNbBlock()> b.getType().getMaxStackSize()? b.getType().getMaxStackSize() : b.getNbBlock())).isEmpty())
							{
								b.dropBlock(b.getType().getMaxStackSize());
							} else
							{
								b.removeBlock(player, b.getType().getMaxStackSize());
							}
							event.setCancelled(true);
					}
				}
			}
			
			if (block.getType().equals(Material.BARREL))
			{
					if (event.getAction().equals(Action.RIGHT_CLICK_BLOCK) && event.getHand().toString() == "HAND" && (event.getItem() == null))
					{
						event.setCancelled(true);
					}
			}
		}
	}
}
