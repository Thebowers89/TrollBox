package TrollBox.owospeak;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class ChatEvent implements Listener, Runnable {

    public static HashMap<UUID, ArrayList<Long>> players = new HashMap<>();

    @EventHandler
    public void onChat(AsyncPlayerChatEvent e) {
        if (players.containsKey(e.getPlayer().getUniqueId())) {
            String message = e.getMessage();
            message = message.replaceAll("[lr]", "w");
            message = message.replaceAll("[LR]", "W");
            message = message.replaceAll("ith", "if");
            message = message.replace("n[aeiou]", "ny");
            message = message.replace("N[AEIOU]", "NY");
            e.setMessage(message);
        }
    }

    public void curse(Player player, long minutes) {
        if (players.containsKey(player.getUniqueId())) {
            players.remove(player.getUniqueId());
            player.sendMessage(ChatColor.GOLD + "The curse has been lifted...");
        } else {
            ArrayList<Long> pack = new ArrayList<>();
            long duration = (minutes * 60 * 1000);
            pack.add(duration);
            pack.add(System.currentTimeMillis());
            players.put(player.getUniqueId(), pack);
            player.sendMessage(ChatColor.GOLD + "What a terrible night for a curse...");
        }
    }

    @Deprecated
    public void add(Player player, long minutes) {
        ArrayList<Long> pack = new ArrayList<>();
        long duration = (minutes * 60 * 1000);
        pack.add(duration);
        pack.add(System.currentTimeMillis());
        players.put(player.getUniqueId(), pack);
        player.sendMessage(ChatColor.GOLD + "What a terrible night for a curse...");
    }

    @Deprecated
    public void remove(Player player) {
        players.remove(player.getUniqueId());
        player.sendMessage(ChatColor.GOLD + "The curse has been lifted...");
    }

    @Override
    public void run() {
        ArrayList<UUID> temp = new ArrayList<>();
        for (UUID p : players.keySet()) {
            long start = players.get(p).get(1);
            long now = System.currentTimeMillis();
            if (now - start > players.get(p).get(0)) {
                temp.add(p);
            }
        }
        for (UUID p : temp) {
            players.remove(p);
        }
    }
}
