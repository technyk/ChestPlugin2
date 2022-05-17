package me.technyk.chestplugin2;

import me.technyk.chestplugin2.commands.checkOwner;
import me.technyk.chestplugin2.commands.manageChest;
import me.technyk.chestplugin2.listeners.chestOpen;
import me.technyk.chestplugin2.listeners.chestPlacement;
import org.bukkit.plugin.java.JavaPlugin;

public final class ChestPlugin2 extends JavaPlugin {

    private static ChestPlugin2 plugin;

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(new chestPlacement(), this);
        getServer().getPluginManager().registerEvents(new chestOpen(), this);

        getCommand("checkOwner").setExecutor(new checkOwner());
        getCommand("manageChest").setExecutor(new manageChest());

        plugin = this;
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }


    public static ChestPlugin2 getPlugin() {
        return plugin;
    }
}
