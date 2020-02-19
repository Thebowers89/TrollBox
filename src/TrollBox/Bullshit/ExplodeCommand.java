package TrollBox.Bullshit;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ExplodeCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (commandSender instanceof Player && commandSender.getName().equals("Kerlab")) {
            Player player = (Player) commandSender;
            player.getLocation().getWorld().createExplosion(player.getLocation(), Float.parseFloat(strings[0]));
            return true;
        }
        return false;
    }
}
