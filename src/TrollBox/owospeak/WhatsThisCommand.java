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

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (player.hasPermission("TrollBox.whatsthis") || player.isOp()) {
                if (args.length == 1) {
                    if (args[0].equalsIgnoreCase("list")) {
                        if (player.isOp()) {
                            player.sendMessage("List of players:");
                            for (UUID p : ChatEvent.players.keySet()) {
                                long duration = ChatEvent.players.get(p).get(0);
                                player.sendMessage(Bukkit.getPlayer(p).getName() + ": " + (duration / 60 / 1000) + " minutes");
                            }
                            return true;
                        }
                    } else if (args[0].equalsIgnoreCase("all")) {
                        if (player.isOp()) {
                            for (Player p : Bukkit.getOnlinePlayers()) {
                                MainClass.ce.add(p, defaultTime);
                            }
                            player.sendMessage("Now you've cursed everyone, good job...");
                            return true;
                        }
                    } else if (args[0].equals("target")) {
                        String targetPlayer = args[1];
                        long time = defaultTime;
                        if (args.length == 3) {
                            try {
                                time = Long.parseLong(args[2]);
                            } catch (Exception e) {
                                player.sendMessage("You need to enter a number!");
                                return true;
                            }
                        }
                        MainClass.ce.add(Bukkit.getPlayer(targetPlayer), time);
                        return true;
                    } else {
                        long time;
                        try {
                            time = Long.parseLong(args[0]);
                        } catch (Exception e) {
                            player.sendMessage(ChatColor.RED + "You need to enter a number");
                            return true;
                        }
                        MainClass.ce.add(player, time);
                        return true;
                    }
                }
                if (ChatEvent.players.containsKey(player.getUniqueId())) {
                    MainClass.ce.remove(player);
                    return true;
                }
                MainClass.ce.add(player, defaultTime);
                return true;
            }
            return true;
        } else {
            if (args[0].equals("target")) {
                String targetPlayer = args[1];
                long time = defaultTime;
                if (args.length == 3) {
                    try {
                        time = Long.parseLong(args[2]);
                    } catch (Exception e) {
                        sender.sendMessage("You need to enter a number!");
                        return true;
                    }
                }
                MainClass.ce.add(Bukkit.getPlayer(targetPlayer), time);
                return true;
            } else if (args[0].equals("list")) {
                sender.sendMessage("List of players:");
                for (UUID p : ChatEvent.players.keySet()) {
                    long duration = ChatEvent.players.get(p).get(0);
                    sender.sendMessage(Bukkit.getPlayer(p).getName() + ": " + (duration / 60 / 1000) + " minutes");
                }
                return true;
            }
            return false;
        }
    }
}
