package TrollBox.TrackingArrows;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.AbstractArrow;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;

public class TArrowCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        if (sender instanceof Player) {
            if (sender.isOp() || sender.hasPermission("TrollBox.tarrow")) {
                double s = (args.length >= 2) ? Double.parseDouble(args[1]) : 1;
                Player player = (Player) sender;
                Arrow p = player.launchProjectile(Arrow.class);
                TArrow a = new TArrow(p, s);
                if (args.length == 3) p.setDamage(1000000);
                p.setPickupStatus(AbstractArrow.PickupStatus.CREATIVE_ONLY);
                a.setTarget(Bukkit.getPlayer(args[0]));
                ArrowRunnable.arrows.add(a);
                return true;
            }
        }
        return false;
    }

}
