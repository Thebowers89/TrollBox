package TrollBox;

import TrollBox.TntScare.TNTScareCommand;
import TrollBox.TrackingArrows.ArrowRunnable;
import TrollBox.TrackingArrows.TArrowCommand;
import TrollBox.owospeak.ChatEvent;
import TrollBox.owospeak.WhatsThisCommand;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class MainClass extends JavaPlugin {

    public static final ArrowRunnable ar = new ArrowRunnable();
    public static final ChatEvent ce = new ChatEvent();

    public void onEnable() {
        registerCommands();
        registerEvents();
        Bukkit.getScheduler().runTaskTimer(this, ar, 0, 1);
    }

    public void onDisable() {

    }

    private void registerCommands() {
        getCommand("tarrow").setExecutor(new TArrowCommand());
        getCommand("whatsthis").setExecutor(new WhatsThisCommand());
        getCommand("tntscare").setExecutor(new TNTScareCommand());
    }

    private void registerEvents() {
        PluginManager pm = Bukkit.getPluginManager();
        pm.registerEvents(ce, this);
    }

}
