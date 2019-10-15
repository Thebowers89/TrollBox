package TrollBox.TntScare;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.entity.TNTPrimed;

public class TNTScareCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        if (sender instanceof Player) {
            if (sender.isOp() || sender.hasPermission("TrollBox.tntscare")) {
                Player target = Bukkit.getPlayer(args[0]);
                TNTPrimed tnt = target.getWorld().spawn(target.getLocation(), TNTPrimed.class);
                tnt.setYield(0f);
            }
        }
        return false;
    }
}
