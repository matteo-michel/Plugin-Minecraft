package fr.IWaRZie.Stacker;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.EulerAngle;

import fr.IWaRZie.Stacker.Utils.Save;
import fr.IWaRZie.Stacker.Utils.SerializedBarrel;
import fr.IWaRZie.Stacker.Utils.SerializedLocation;

public class Barrel {

	private static HashMap<Location, Barrel> barrels = new HashMap<>();
	
	
	private static Config config = Config.getConfig();
	
	public static void setConfig(Config config)
	{
		Barrel.config = config;
	}
	
	public static Barrel getBarrel(Location location)
	{
		return barrels.get(location);
	}
	
	public static void removeArmorStand()
	{
		for (Barrel b : barrels.values())
		{
			b.as.remove();
		}
	}
	
	public static void registerBarrel(Location location, Material type, int number)
	{
		new Barrel(location, type, number);
	}
	
	public static boolean isBarrelAs(ArmorStand a)
    {
		ArrayList<ArmorStand> listAs = new ArrayList<>();
    	for (Barrel b : barrels.values())
    	{
    		listAs.add(b.getAs());
    	}
    	return listAs.contains(a);
    }
	
	private Location location;
	private Material type;
	private int nbBlock;
	private ArmorStand as;
	private boolean HasMoveItemHopper = false;

	public Barrel(Location location, Material type) 
	{
		this.location = location;
		this.type = type;
		this.nbBlock = 0;
		if (!(barrels.containsKey(location)))
			barrels.put(location, this);
		Save.getSave().addBarrel(this);
		setUpArmorStand();
		loopBase(as);
		
	}
	
	public Barrel(Location location, Material type, int number) 
	{
		this.location = location;
		this.type = type;
		this.nbBlock = number;
		if (!(barrels.containsKey(location)))
			barrels.put(location, this);
		setUpAS();
		loopBase(as);
		
	}
	
	
	public void setHasMoveItemHopper()
	{
		HasMoveItemHopper = true;
	}
	
	public void setUpArmorStand()
	{
		Location centre = location.clone().add(0.5D, 0.1D, 0.5D);
		as = (ArmorStand) location.getWorld().spawnEntity(centre, EntityType.ARMOR_STAND);
		as.setGravity(false);
		as.setCanPickupItems(false);
		setCustomName();
		as.setSmall(true);
		as.setHelmet(new ItemStack(type));
		as.setCustomNameVisible(true); 
		as.setVisible(false);
		
	}
	
	public void setUpAS()
	{
		Location centre = location.clone().add(0.5D, 0.1D, 0.5D);
		as = (ArmorStand) location.getWorld().spawnEntity(centre, EntityType.ARMOR_STAND);
		as.setGravity(false);
		as.setCanPickupItems(false);
		setCName();
		as.setSmall(true);
		as.setHelmet(new ItemStack(type));
		as.setCustomNameVisible(true); 
		as.setVisible(false);
		
		
	}
	
	public void setCustomName()
	{
		String str = config.getMessageWithoutPrefixe("customNameDrawer");
		str = str.replace("%NUMBER%", "" + nbBlock);
		String string = type.toString().replace("_", " ");
		string = string.substring(0,  1).toUpperCase() + string.substring(1).toLowerCase();
		str = str.replace("%TYPE%", string);
		as.setCustomName(str); 
		Save.getSave().modifyBarrel(this);
	}
	
	public void setCName()
	{
		String str = config.getMessageWithoutPrefixe("customNameDrawer");
		str = str.replace("%NUMBER%", "" + nbBlock);
		String string = type.toString().replace("_", " ");
		string = string.substring(0,  1).toUpperCase() + string.substring(1).toLowerCase();
		str = str.replace("%TYPE%", string);
		as.setCustomName(str); 
	}
	
	public void removeAs()
	{
		as.remove();
		
	}
	
	public ArmorStand getAs()
	{
		return as;
	}
	
	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public Material getType() {
		return type;
	}

	public void setType(Material type) {
		this.type = type;
	}

	public int getNbBlock() {
		return nbBlock;
	}

	public void setNbBlock(int nbBlock) {
		this.nbBlock = nbBlock;
	}
	
	public boolean addBlock(Material material, int nombre)
	{
		if (material.equals(this.type))
		{
			this.nbBlock += nombre;
			setCustomName();
			return true;
		}
		return false;
	}
	
	public boolean addBlock(Material material)
	{
		if (material.equals(this.type))
		{
			this.nbBlock += 1;
			setCustomName();
			return true;
		}
		return false;
	}
	
	public boolean removeBlock(Player player)
	{
		if (nbBlock > 1)
		{
			nbBlock -= 1;
			setCustomName();
			return true;
		} else if (nbBlock <= 1)
		{
			deleteBarrel();
			return true;
		} 
		return false;
	}
	
	public boolean removeBlock(Player player, int number)
	{
		if (nbBlock > number)
		{
			nbBlock -= number;
			setCustomName();
			return true;
		} else if (nbBlock <= number)
		{
			deleteBarrel();
			return true;
		}
		return false;
	}
	
	public void destroyBarrel()
	{
		deleteBarrel();
		while (nbBlock > 0)
		{
			location.getWorld().dropItem(location, new ItemStack(type, nbBlock>type.getMaxStackSize()?type.getMaxStackSize():nbBlock));
			nbBlock -= type.getMaxStackSize();
		}
		
	}
	
	public void destroyBarrelExplode()
	{
		deleteBarrel();
		if (HasMoveItemHopper) nbBlock -= 1;
		
		while (nbBlock > 0)
		{
			location.getWorld().dropItem(location, new ItemStack(type, nbBlock>type.getMaxStackSize()?type.getMaxStackSize():nbBlock));
			nbBlock -= type.getMaxStackSize();
		}
		
	}
	
	public void dropBlock(int number)
	{
		if (nbBlock > number)
		{
			location.getWorld().dropItem(location, new ItemStack(type, number));
			nbBlock -= number;
			setCustomName();
		} else if (nbBlock <= number)
		{
			location.getWorld().dropItem(location, new ItemStack(type, nbBlock));
			deleteBarrel();
		}
	}
	
    public void loopBase(ArmorStand am){
        
    	if (config.getMessageWithoutPrefixe("animation") == "true")
    	{
	        new BukkitRunnable() {
	            int x = -360;
	            @Override
	            public void run() {
	                Location l = am.getLocation();
	                l.setPitch(x);
	                am.teleport(l);
	                am.setHeadPose(new EulerAngle( 0, Math.toRadians(x) , 0 ));    
	                x++;
	            }
	        }.runTaskTimer(Main.getMain(), 0, 1);
    	}
    }
    
    public SerializedBarrel getSerializedBarrel()
    {
    	return new SerializedBarrel(this);
    }
    
    public SerializedLocation getSerializedLocation()
    {
    	return new SerializedLocation(location);
    }
    
    public void deleteBarrel()
    {
    	barrels.remove(location);
		removeAs();
		Save.getSave().removeBarrel(this);	
    }
}
