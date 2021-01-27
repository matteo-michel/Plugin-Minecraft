package fr.iwarzie.veinMiner;

import fr.iwarzie.veinMiner.event.BreakMineraiEvent;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {

    @Override
    public void onEnable() {
        Bukkit.getPluginManager().registerEvents(new BreakMineraiEvent(), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
