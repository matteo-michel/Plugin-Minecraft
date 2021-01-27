package fr.iwarzie.veinMiner.event;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import java.util.ArrayList;

public class BreakMineraiEvent implements Listener {

    @EventHandler
    public void onPlayerBreakBlockEvent(BlockBreakEvent event){
        Block blockBreak = event.getBlock();
        nbOre(blockBreak.getType() ,blockBreak.getLocation());
    }

    public boolean isOre(Block block){
        ArrayList<Material> oreList = new ArrayList<>();
        oreList.add(Material.COAL_ORE);
        oreList.add(Material.DIAMOND_ORE);
        oreList.add(Material.EMERALD_ORE);
        oreList.add(Material.GOLD_ORE);
        oreList.add(Material.IRON_ORE);
        oreList.add(Material.LAPIS_ORE);
        oreList.add(Material.REDSTONE_ORE);
        oreList.add(Material.NETHER_QUARTZ_ORE);

        return oreList.contains(block.getType());
    }

    public void nbOre(Material initialBlock, Location locationBlock){
        if (!isOre(locationBlock.getBlock()) || (locationBlock.getBlock().getType() != initialBlock)) return;
        locationBlock.getWorld().getBlockAt(locationBlock).breakNaturally();

        //Décalage en X
        int locX = locationBlock.getBlockX() + 1;
        nbOre(initialBlock, new Location(locationBlock.getWorld(), locX, locationBlock.getBlockY(), locationBlock.getBlockZ()));
        locX = locationBlock.getBlockX() - 1;
        nbOre(initialBlock, new Location(locationBlock.getWorld(), locX, locationBlock.getBlockY(), locationBlock.getBlockZ()));

        //Décalage en Y
        int locY = locationBlock.getBlockY() + 1;
        nbOre(initialBlock, new Location(locationBlock.getWorld(), locationBlock.getBlockX(), locY, locationBlock.getBlockZ()));
        locY = locationBlock.getBlockY() - 1;
        nbOre(initialBlock, new Location(locationBlock.getWorld(), locationBlock.getBlockX(), locY, locationBlock.getBlockZ()));

        //Décalage en Z
        int locZ = locationBlock.getBlockZ() + 1;
        nbOre(initialBlock, new Location(locationBlock.getWorld(), locationBlock.getBlockX(), locationBlock.getBlockY(), locZ));
        locZ = locationBlock.getBlockZ() - 1;
        nbOre(initialBlock, new Location(locationBlock.getWorld(), locationBlock.getBlockX(), locationBlock.getBlockY(), locZ));
    }


}
