package livescoreboard;

import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;
import org.bukkit.scoreboard.Team;

public class LiveScoreboard extends JavaPlugin {

    public final static Logger logger = Logger.getLogger("minecraft");
    public String prefix;
    public HashMap hm = new HashMap();

    @Override
    public void onDisable() {
        PluginDescriptionFile pdf = this.getDescription();

        logger.log(Level.INFO, "{0}, version {1} coded by {2} has been Disabled!", new Object[]{pdf.getName(), pdf.getVersion(), pdf.getAuthors()});
    }

    @Override
    public void onEnable() {
        saveDefaultConfig();
        PluginDescriptionFile pdf = this.getDescription();
        logger.log(Level.INFO, "{0}, version {1} coded by {2} has been Enabled!", new Object[]{pdf.getName(), pdf.getVersion(), pdf.getAuthors()});
        prefix = getConfig().getString("Prefix" + ":"); //Config Setup
    }

    public void ScoreBoard() {
        ScoreboardManager sm = Bukkit.getScoreboardManager();
        Scoreboard b = sm.getNewScoreboard();
        Objective obj = b.registerNewObjective("test", "dummy");
        obj.setDisplaySlot(DisplaySlot.SIDEBAR);
        obj.setDisplayName("Live KDR");


    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        int kills = 0;
        Player p = e.getPlayer();
        hm.put(p, kills);
    }

    @EventHandler
    public void onPlayerKill(PlayerDeathEvent e) {
        Player killed = e.getEntity();
        Player Killer = killed.getKiller();


    }
}
