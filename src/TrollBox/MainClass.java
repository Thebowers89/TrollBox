package TrollBox;

import TrollBox.EasyCommands.CommandEvent;
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

    public static CommandEvent commandEvent;

    public void onEnable() {
        saveResource("commands.yml", false);
        commandEvent = new CommandEvent();

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
        getCommand("ezcreload").setExecutor(commandEvent);
    }

    private void registerEvents() {
        PluginManager pm = Bukkit.getPluginManager();
        pm.registerEvents(commandEvent, this);
        pm.registerEvents(ce, this);
    }

}
