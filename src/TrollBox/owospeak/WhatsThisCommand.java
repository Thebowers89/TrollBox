package TrollBox.owospeak;

import TrollBox.MainClass;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

public class WhatsThisCommand implements CommandExecutor {

    public static final long defaultTime = 10;
    public static final String permission = "TrollBox.whatsthis";

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;

            // whatsthis [duration] --affects self
            // whatsthis list --lists all affected players
            // whatsthis target <player> [duration] --affects targeted player

            if (player.hasPermission(permission) || player.isOp()) {
                if (args.length == 0) {
                    MainClass.ce.curse(player, defaultTime);
                    return true;
                } else if (args.length == 1) {
                    if (args[0].equalsIgnoreCase("list")) {
                        if (player.isOp()) {
                            player.sendMessage("List of players:");
                            for (UUID p : ChatEvent.players.keySet()) {
                                long duration = ChatEvent.players.get(p).get(0);
                                player.sendMessage(Bukkit.getPlayer(p).getName() + ": " + (duration / 60 / 1000) + " minutes");
                            }
                            return true;
                        }
                    } else {
                        long time;
                        try {
                            time = Long.parseLong(args[0]);
                        } catch (Exception e) {
                            time = defaultTime;
                        }
                        MainClass.ce.curse(player, time);
                        return true;
                    }
                } else if (args.length == 2) {
                    if (args[0].equalsIgnoreCase("target")) {
                        Player target;
                        try {
                            target = Bukkit.getPlayer(args[1]);
                        } catch (Exception e) {
                            player.sendMessage(ChatColor.RED + "That username is not found");
                            return true;
                        }
                        MainClass.ce.curse(target, defaultTime);
                        return true;
                    } else {
                        player.sendMessage(ChatColor.RED + "Invalid parameters: " + args[0]);
                    }
                } else if (args.length == 3) {
                    if (args[0].equalsIgnoreCase("target")) {
                        Player target;
                        try {
                            target = Bukkit.getPlayer(args[1]);
                        } catch (Exception e) {
                            player.sendMessage(ChatColor.RED + "That username is not found");
                            return true;
                        }
                        long time;
                        try {
                            time = Long.parseLong(args[2]);
                        } catch (Exception e) {
                            time = defaultTime;
                        }
                        MainClass.ce.curse(target, time);
                        return true;
                    } else {
                        player.sendMessage(ChatColor.RED + "Invalid parameters: " + args[0]);
                    }
                }
            } else {
                // Making this silent so players think its broken
                return true;
            }
        } else {
            if (args.length == 1) {
                if (args[0].equalsIgnoreCase("list")) {
                    sender.sendMessage("List of players:");
                    for (UUID p : ChatEvent.players.keySet()) {
                        long duration = ChatEvent.players.get(p).get(0);
                        sender.sendMessage(Bukkit.getPlayer(p).getName() + ": " + (duration / 60 / 1000) + " minutes");
                    }
                    return true;
                }
            } else if (args.length == 2) {
                if (args[0].equalsIgnoreCase("target")) {
                    Player target;
                    try {
                        target = Bukkit.getPlayer(args[1]);
                    } catch (Exception e) {
                        sender.sendMessage(ChatColor.RED + "That username is not found");
                        return true;
                    }
                    MainClass.ce.curse(target, defaultTime);
                    return true;
                } else {
                    sender.sendMessage(ChatColor.RED + "Invalid parameters: " + args[0]);
                    return true;
                }
            } else if (args.length == 3) {
                if (args[0].equalsIgnoreCase("target")) {
                    Player target;
                    try {
                        target = Bukkit.getPlayer(args[1]);
                    } catch (Exception e) {
                        sender.sendMessage(ChatColor.RED + "That username is not found");
                        return true;
                    }
                    long time;
                    try {
                        time = Long.parseLong(args[2]);
                    } catch (Exception e) {
                        time = defaultTime;
                    }
                    MainClass.ce.curse(target, time);
                    return true;
                } else {
                    sender.sendMessage(ChatColor.RED + "Invalid parameters: " + args[0]);
                }
            } else {
                sender.sendMessage("[TrollBox] Hey man youre using this command wrong!");
                return true;
            }
        }
        return false;
    }

}
