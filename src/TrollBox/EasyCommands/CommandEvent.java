package TrollBox.EasyCommands;

import org.bukkit.Bukkit;
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

public class CommandEvent implements Listener, CommandExecutor {

    HashMap<String, String> commands = new HashMap<>();

    public CommandEvent() {
        reload();
    }

    @EventHandler
    public void onCommand(PlayerCommandPreprocessEvent e) {
        String command = e.getMessage().substring(1);
        if (commands.containsKey(command)) {
            e.setCancelled(true);
            e.getPlayer().sendMessage(commands.get(command));
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
