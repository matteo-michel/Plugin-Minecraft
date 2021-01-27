package fr.IWaRZie.Stacker.Events;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityExplodeEvent;

import fr.IWaRZie.Stacker.Barrel;

public class ExplodeEvent implements Listener{

	private List<Block> blockExplode = new ArrayList<>();
	
	@EventHandler
	public void onExplodeEvent(EntityExplodeEvent event)
	{	
		blockExplode = event.blockList();
		for (Block b : blockExplode)
		{
			if (Barrel.getBarrel(b.getLocation())  != null)
			{
				Barrel barrel = Barrel.getBarrel(b.getLocation());
				barrel.destroyBarrelExplode();
			}
		}
	}
}
