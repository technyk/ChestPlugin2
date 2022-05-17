package me.technyk.chestplugin2.commands;

import me.technyk.chestplugin2.ChestPlugin2;
import org.bukkit.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.block.TileState;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class checkOwner implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender instanceof Player){

            Player p = (Player) sender;

            Block block = p.getTargetBlockExact(5);

            if(block != null) {
                if (block.getState() instanceof TileState) {

                    TileState tileState = (TileState) block.getState();

                    PersistentDataContainer container = tileState.getPersistentDataContainer();

                    if (container.has(new NamespacedKey(ChestPlugin2.getPlugin(), "owner"), PersistentDataType.STRING)) {

                        String currentOwner = container.get(new NamespacedKey(ChestPlugin2.getPlugin(), "owner"), PersistentDataType.STRING);
                        p.sendMessage(ChatColor.RED + "The owner of this chest is: " + ChatColor.WHITE + currentOwner);

                        if(container.has(new NamespacedKey(ChestPlugin2.getPlugin(), "friends"), PersistentDataType.STRING)) {
                            String currentFriends = container.get(new NamespacedKey(ChestPlugin2.getPlugin(), "friends"), PersistentDataType.STRING);

                            if (currentFriends != null) {
                                p.sendMessage(ChatColor.RED + "List of added buddies:");
                                List<String> list = new ArrayList<>(Arrays.asList(currentFriends.split("\\n")));
                                p.sendMessage(String.join("\n", list));
                            }
                        }else{
                            p.sendMessage(ChatColor.RED + "This chest doesn't seem to have any friends");
                        }
                    } else {
                        p.sendMessage(ChatColor.RED + "No one owns this chest");
                    }
                } else {
                    p.sendMessage("nah bro");
                }
            }else{
                p.sendMessage(ChatColor.DARK_RED + "No chest found");
            }
        }

        return true;
    }
}
