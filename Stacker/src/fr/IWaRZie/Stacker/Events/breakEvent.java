package fr.IWaRZie.Stacker.Events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import fr.IWaRZie.Stacker.Barrel;

public class breakEvent implements Listener{

	@EventHandler
	public void onBreakBlock(BlockBreakEvent event)
	{
		Barrel b = Barrel.getBarrel(event.getBlock().getLocation());
		if (b != null) b.destroyBarrelExplode();
	}
}
