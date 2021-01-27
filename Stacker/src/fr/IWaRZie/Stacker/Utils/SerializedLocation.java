package fr.IWaRZie.Stacker.Utils;

import org.bukkit.Bukkit;
import org.bukkit.Location;

public class SerializedLocation {

    private String world;
    private int x, y, z;

    public SerializedLocation(Location loc) {
        this.world = loc.getWorld().getName();
        this.x = loc.getBlockX();
        this.y = loc.getBlockY();
        this.z = loc.getBlockZ();
    }

    public SerializedLocation(String elevator) {
        String[] str = elevator.split(";");

        this.world = str[0];
        this.x = Integer.parseInt(str[1]);
        this.y = Integer.parseInt(str[2]);
        this.z = Integer.parseInt(str[3]);
    }

    @Override
    public String toString() {
        return world + ";" + x + ";" + y + ";" + z;
    }

    public Location getLocation() {
        return new Location(Bukkit.getWorld(world), x, y, z);
    }
}
