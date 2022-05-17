package me.technyk.chestplugin2.listeners;

import me.technyk.chestplugin2.ChestPlugin2;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.TileState;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class chestOpen implements Listener {

    @EventHandler
    public void onChestOpen(PlayerInteractEvent e){
        if(e.hasBlock()){
            if(e.getClickedBlock() != null){
                if(e.getClickedBlock().getType() == Material.CHEST) {

                    Block block = e.getClickedBlock();
                    Player p = e.getPlayer();
                    BlockState blockState = block.getState();

                    TileState tileState = (TileState) blockState;

                    PersistentDataContainer container = tileState.getPersistentDataContainer();

                    boolean cancel = false;
                    boolean found = false;
                    if(container.has(new NamespacedKey(ChestPlugin2.getPlugin(), "owner"), PersistentDataType.STRING)) {
                        String currentOwner = container.get(new NamespacedKey(ChestPlugin2.getPlugin(), "owner"), PersistentDataType.STRING);
                        if(container.has(new NamespacedKey(ChestPlugin2.getPlugin(), "friends"), PersistentDataType.STRING)) {
                            String currentFriends = container.get(new NamespacedKey(ChestPlugin2.getPlugin(), "friends"), PersistentDataType.STRING);
                            List<String> list = null;
                            if (currentFriends != null) {
                                list = new ArrayList<>(Arrays.asList(currentFriends.split("\\\\r?\\\\n")));
                            }

                            if (list != null) {
                                for (String s : list) {

                                    if (s.equals(p.getName())) {
                                        found = true;
                                    }

                                }
                            }
                        }
                        if (!p.getName().equals(currentOwner)) {
                            if(!found) {
                                p.sendMessage(ChatColor.DARK_RED + "You are not allowed to open this chest!");
                                cancel = true;
                            }
                        }
                        if (cancel) {
                            e.setCancelled(true);
                        }
                    }
                }
            }
        }
    }
}
