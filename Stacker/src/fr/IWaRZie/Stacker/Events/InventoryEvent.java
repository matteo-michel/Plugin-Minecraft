package fr.IWaRZie.Stacker.Events;

import java.util.Arrays;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryMoveItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import fr.IWaRZie.Stacker.Barrel;

public class InventoryEvent implements Listener{

	@EventHandler
	public void onInventoryEvent(InventoryMoveItemEvent event)
	{
			if (Barrel.getBarrel(event.getDestination().getLocation()) != null)
			{
				Barrel b = Barrel.getBarrel(event.getDestination().getLocation());
				if (b.getType().equals(event.getItem().getType()))
				{

					ItemStack[] invSourceStart = event.getSource().getContents();

					if (isContaining(event.getInitiator().getContents(), b.getType()) != (-1))
					{
						b.addBlock(b.getType(), getAmount(invSourceStart, isContaining(invSourceStart, b.getType())) + 1);
						setAmount(event.getSource().getContents(), isContaining(invSourceStart, b.getType()));
						
					}
					else if (event.getItem().getType().equals(b.getType()))
					{
						b.addBlock(b.getType(), 1);
					}

					event.getDestination().clear();
					b.setHasMoveItemHopper();
				} else
				{
					event.setCancelled(true);
				}
				
				return;
			}
			
			if (Barrel.getBarrel(event.getSource().getLocation()) != null)
				event.setCancelled(true);
			
			if (event.getDestination().getHolder() instanceof org.bukkit.block.Barrel)
				event.setCancelled(true);
			
			if (event.getSource().getHolder() instanceof org.bukkit.block.Barrel)
				event.setCancelled(true);
	}
	
	public ItemStack configItm()
	{
		ItemStack itm = new ItemStack(Material.ACACIA_BUTTON, 1);
		ItemMeta itmMeta = itm.getItemMeta();
		itmMeta.setLore(Arrays.asList("[Drawer]"));
		itm.setItemMeta(itmMeta);
		return itm;
	}
	
	public ItemStack[] configInventory(ItemStack itm)
	{
		ItemStack[] inv = new ItemStack[27];
		
		for (int i = 0; i<27; i++)
		{
			inv[i] = itm;
		}
		return inv;
	}
	
	public int isContaining(ItemStack[] list, Material type)
	{
		for (int i = 0; i<list.length; i++)
		{
			if (list[i] != null)
			{
				if (list[i].getType().equals(type))
					return i;
			}
		}
		return -1;
	}
	
	public int getAmount(ItemStack[] list, int i)
	{
		if (i == (-1)) return 1;
		return list[i].getAmount();
	}
	
	public void setAmount(ItemStack[] list, int i)
	{
		list[i].setAmount(0);
	}
	
	public ItemStack[] correctInv(ItemStack[] list, Material type)
	{
		for (int i = 0; i<list.length; i++)
		{
			if (list[i] != null)
			{
				if (list[i].getType().equals(type))
					list[i].setAmount(0);
			}
		}
		return list;
	}
}
