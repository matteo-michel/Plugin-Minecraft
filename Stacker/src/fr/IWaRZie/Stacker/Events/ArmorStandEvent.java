package fr.IWaRZie.Stacker.Events;

import org.bukkit.entity.ArmorStand;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerArmorStandManipulateEvent;

import fr.IWaRZie.Stacker.Barrel;

public class ArmorStandEvent implements Listener{

	
	@EventHandler
	public void onArmorStandEvent(PlayerArmorStandManipulateEvent event)
	{
		ArmorStand as = event.getRightClicked();
		if (Barrel.isBarrelAs(as)) event.setCancelled(true);
	}
	
}
