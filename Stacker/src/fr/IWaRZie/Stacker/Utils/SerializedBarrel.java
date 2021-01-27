package fr.IWaRZie.Stacker.Utils;

import org.bukkit.Material;

import fr.IWaRZie.Stacker.Barrel;

public class SerializedBarrel {

	
	private Material type;
    private int number;

    public SerializedBarrel(Barrel b) {
    	this.type = b.getType();
    	this.number = b.getNbBlock();
    }

    public SerializedBarrel(String barrel) {
        String[] str = barrel.split("-");
        
        this.type = Material.getMaterial(str[0]);
        this.number = Integer.parseInt(str[1]);
    }

    @Override
    public String toString() {
        return type.toString() + "-" + number;
    }

    public Material getType() {
        return type;
    }

	public int getNumber() {
		return number;
	}
}
