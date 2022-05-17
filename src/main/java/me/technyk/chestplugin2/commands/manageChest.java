package me.technyk.chestplugin2.commands;

import me.technyk.chestplugin2.ChestPlugin2;
import org.bukkit.ChatColor;
import org.bukkit.Material;
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

public class manageChest implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player) {
            Player p = (Player) sender;
            Block block = p.getTargetBlockExact(5);

            if(block != null) {
                if(block.getType() == Material.CHEST) {
                    if (block.getState() instanceof TileState) {

                        TileState tileState = (TileState) block.getState();

                        PersistentDataContainer container = tileState.getPersistentDataContainer();

                        if (container.has(new NamespacedKey(ChestPlugin2.getPlugin(), "owner"), PersistentDataType.STRING)) {

                            String currentOwner = container.get(new NamespacedKey(ChestPlugin2.getPlugin(), "owner"), PersistentDataType.STRING);

                            if (p.getName().equals(currentOwner)) {

                                if (args.length != 0) {
                                    if (args[0].equalsIgnoreCase("add")) {
                                        if(args[1].length() != 0) {
                                            String friend = args[1];
                                            if (container.has(new NamespacedKey(ChestPlugin2.getPlugin(), "friends"), PersistentDataType.STRING)) {

                                                String currentFriends = container.get(new NamespacedKey(ChestPlugin2.getPlugin(), "friends"), PersistentDataType.STRING);

                                                List<String> list;
                                                if (currentFriends != null) {
                                                    list = new ArrayList<>(Arrays.asList(currentFriends.split("\\n")));
                                                    list.add(friend);
                                                    container.set(new NamespacedKey(ChestPlugin2.getPlugin(), "friends"), PersistentDataType.STRING, String.join("\n", list));

                                                    tileState.update();
                                                }

                                            } else {
                                                List<String> list = new ArrayList<>();

                                                list.add(friend);
                                                container.set(new NamespacedKey(ChestPlugin2.getPlugin(), "friends"), PersistentDataType.STRING, String.join("\n", list));

                                                tileState.update();
                                            }
                                        }else{
                                            p.sendMessage(ChatColor.RED + "Wrong syntax!");
                                            p.sendMessage(ChatColor.RED + "Use: " + ChatColor.GRAY + "/manageChest <add/remove> <player>");
                                        }

                                    } else if (args[0].equalsIgnoreCase("remove")) {

                                        if(args[1].length() != 0) {
                                            String friend = args[1];
                                            if (container.has(new NamespacedKey(ChestPlugin2.getPlugin(), "friends"), PersistentDataType.STRING)) {

                                                String currentFriends = container.get(new NamespacedKey(ChestPlugin2.getPlugin(), "friends"), PersistentDataType.STRING);

                                                List<String> list;
                                                if (currentFriends != null) {
                                                    list = new ArrayList<>(Arrays.asList(currentFriends.split("\\n")));
                                                    for (int i = 0; i < list.size(); i++) {
                                                        if(list.get(i).equals(friend)){
                                                            list.remove(i);
                                                            i = i-1;
                                                        }
                                                    }
                                                    container.set(new NamespacedKey(ChestPlugin2.getPlugin(), "friends"), PersistentDataType.STRING, String.join("\n", list));

                                                    tileState.update();
                                                }

                                            } else {
                                                p.sendMessage(ChatColor.RED + "This chest doesn't have any buddies added!");
                                            }
                                        }else{
                                            p.sendMessage(ChatColor.RED + "Wrong syntax!");
                                            p.sendMessage(ChatColor.RED + "Use: " + ChatColor.GRAY + "/manageChest <add/remove> <player>");
                                        }

                                    } else {
                                        p.sendMessage(ChatColor.RED + "Wrong syntax!");
                                        p.sendMessage(ChatColor.RED + "Use: " + ChatColor.GRAY + "/manageChest <add/remove> <player>");
                                    }
                                } else {
                                    p.sendMessage(ChatColor.RED + "Wrong syntax!");
                                    p.sendMessage(ChatColor.RED + "Use: " + ChatColor.GRAY + "/manageChest <add/remove> <player>");
                                }
                            } else {
                                p.sendMessage(ChatColor.DARK_RED + "You are not allowed to manage this chest!");
                            }
                        } else {
                            p.sendMessage(ChatColor.DARK_RED + "You are not allowed to manage this chest!");
                        }
                    }
                }
            }else{
                p.sendMessage(ChatColor.DARK_RED + "No chest found");
            }
        }

        return true;
    }
}
