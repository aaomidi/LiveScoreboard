package dev.nationcraft.org.LiveScoreboard;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;

public class LiveScoreboard extends JavaPlugin {

    public final static Logger logger = Logger.getLogger("minecraft");
    public String prefix;
    HashMap<String, Integer> hmd = new HashMap<String, Integer>();
    HashMap<String, Integer> hmk = new HashMap<String, Integer>();
    SortedMap<String, Integer> kdr = new TreeMap<>();
    ArrayList<Integer> nums;
    public ScoreboardManager sm = Bukkit.getScoreboardManager();
    public Scoreboard b = sm.getNewScoreboard();

    @Override
    public void onDisable() {
        PluginDescriptionFile pdf = this.getDescription();

        logger.log(Level.INFO, "{0}, version {1} coded by {2} has been Disabled!", new Object[]{pdf.getName(), pdf.getVersion(), pdf.getAuthors()});
    }

    @Override
    public void onEnable() {
        PluginDescriptionFile pdf = this.getDescription();
        logger.log(Level.INFO, "{0}, version {1} coded by {2} has been Enabled!", new Object[]{pdf.getName(), pdf.getVersion(), pdf.getAuthors()});
    }

    public void ScoreBoard() {

        Objective obj = b.registerNewObjective("test", "dummy");
        obj.setDisplaySlot(DisplaySlot.SIDEBAR);
        obj.setDisplayName("Live KDR");
        Set<String> players = kdr.keySet();
        Iterator i = kdr.keySet().iterator();
        String[] finallist = players.toString().split(",");
        while (i.hasNext()) {
            String key = (String) i.next();
            int num = kdr.get(key);
            nums.add(num);
        }
        Score s = obj.getScore(Bukkit.getServer().getOfflinePlayer(finallist[0]));
        s.setScore(nums.get(0));
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        int kills = 1;
        int deaths = 1;
        Player p = e.getPlayer();
        hmk.put(p.getName(), kills);
        hmd.put(p.getName(), deaths);
        this.ScoreBoard();

    }

    @EventHandler
    public void onPlayerKill(PlayerDeathEvent e) {
        Player killed = e.getEntity();
        Player killer = killed.getKiller();
        hmd.put(killed.getName(), (hmd.get(killed.getName()) + 1));
        hmk.put(killer.getName(), (hmk.get(killer.getName()) + 1));
        int death = hmd.get(killer.getName());
        int kill = hmk.get(killer.getName());
        int ratio = Math.round(kill / death);
        kdr.put(killer.getName(), ratio);
        this.ScoreBoard();
        killed.setScoreboard(b);
        killer.setScoreboard(b);

    }

    public void onPlayerQuit(PlayerQuitEvent e) {
        Player p = e.getPlayer();
        hmd.remove(p.getName());
        hmk.remove(p.getName());
    }
}
