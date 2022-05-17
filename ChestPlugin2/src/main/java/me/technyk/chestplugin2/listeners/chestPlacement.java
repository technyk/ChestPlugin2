package me.technyk.chestplugin2.listeners;

import me.technyk.chestplugin2.ChestPlugin2;
import org.bukkit.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.TileState;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

public class chestPlacement implements Listener {
    @EventHandler
    public void onBlockPlace(BlockPlaceEvent e){
        Player p = e.getPlayer();
        Block block = e.getBlock();
        BlockState blockState = block.getState();
        String pName = p.getName();

        if(blockState instanceof TileState){

            TileState tileState = (TileState) blockState;

            PersistentDataContainer container = tileState.getPersistentDataContainer();

            container.set(new NamespacedKey(ChestPlugin2.getPlugin(), "owner"), PersistentDataType.STRING, pName);

            p.sendMessage(ChatColor.GREEN + "Created a new locked chest!");

            tileState.update();

        }

    }


}
