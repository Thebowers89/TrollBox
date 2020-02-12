package TrollBox.EasyCommands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class CommandEvent implements Listener, CommandExecutor {

    HashMap<String, String> commands = new HashMap<>();

    public CommandEvent() {
        reload();
    }

    /*
        PREFIXES:
            msg:
            cmd:
     */

    @EventHandler
    public void onCommand(PlayerCommandPreprocessEvent e) {
        String command = e.getMessage().substring(1);
        if (commands.containsKey(command)) {
            e.setCancelled(true);
            String message = commands.get(command);
            String prefix = message.substring(0,3);
            String output = message.substring(4);
            if (prefix.equals("msg")) {
                e.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', output));
            } else if (prefix.equals("cmd")) {
                e.getPlayer().performCommand(output);
            }

        }
    }

    public static final String reloadPermission = "TrollBox.EasyCommands.reload";

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (commandSender instanceof Player) {
            if (strings.length >= 1 && strings[0].equals("perms")) {
                commandSender.sendMessage(reloadPermission);
                return true;
            }
            if (strings.length >= 2) {
                String operator = strings[0];
                String selection = strings[1];
                if (operator.equals("list")) {
                    if (selection.equals("messages")) {
                        commandSender.sendMessage("List of message commands:");
                        for (String key : commands.keySet()) {
                            String cmd = commands.get(key);
                            String prefix = cmd.substring(0, 3);
                            if (prefix.equals("msg")) {
                                commandSender.sendMessage("     " + key + " : " + cmd);
                            }
                        }
                        return true;
                    } else if (selection.equals("commands")) {
                        commandSender.sendMessage("List of command commands:");
                        for (String key : commands.keySet()) {
                            String cmd = commands.get(key);
                            String prefix = cmd.substring(0, 3);
                            if (prefix.equals("cmd")) {
                                commandSender.sendMessage("     " + key + " : " + cmd);
                            }
                        }
                        return true;
                    } else {
                        commandSender.sendMessage("List of all commands:");
                        for (String key : commands.keySet()) {
                            commandSender.sendMessage("     " + key + " : " + commands.get(key));
                        }
                        return true;
                    }
                }
            }
            if (commandSender.isOp() || commandSender.hasPermission(reloadPermission)) {
                commandSender.sendMessage("Reloading commands file... Check the console for errors...");
                reload();
                return true;
            }
        }
        return false;
    }

    private void reload() {
        File file = new File(Bukkit.getPluginManager().getPlugin("TrollBox").getDataFolder() + "/commands.yml");
        YamlConfiguration myFile = YamlConfiguration.loadConfiguration(file);
        for (String command : myFile.getConfigurationSection("Commands").getKeys(false)) {
            commands.put(command, (String) myFile.get("Commands." + command));
        }
    }
}
