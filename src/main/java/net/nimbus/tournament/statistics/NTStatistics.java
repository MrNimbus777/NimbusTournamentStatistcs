package net.nimbus.tournament.statistics;

import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;
import java.util.ArrayList;

public class NTStatistics extends JavaPlugin {

    public static NTStatistics a;

    public ArrayList<String> teamsTop = new ArrayList<>();
    public ArrayList<String> playersTop = new ArrayList<>();

    void loadEvents(){

    }
    void loadCommands(){
    }

    void loadVariables(){

    }

    public void loadConfig(boolean reload){
        File config = new File(getDataFolder(), "config.yml");
        if (!config.exists()) {
            getConfig().options().copyDefaults(true);
            saveDefaultConfig();
            getLogger().info("Created new config.yml file at " + config.getPath());
        } else if (reload) {
            try {
                getConfig().load(config);
                getLogger().info("Config reloaded.");
            } catch (Exception exception) {
                getLogger().info("Failed to reload the config");
            }
        }
    }

    public void onEnable() {
        a = this;

        loadConfig(false);
        String adress = getConfig().getString("Database.address");
        String name = getConfig().getString("Database.name");
        String user = getConfig().getString("Database.user");
        String password = getConfig().getString("Database.password");
        MySQLUtils.newConnection(adress, name, user, password);

        if(MySQLUtils.con != null && getServer().getPluginManager().getPlugin("PlaceholderAPI") != null) {
            loadCommands();
            loadEvents();
            loadEvents();
            new NTSPlaceHolders().register();

            new BukkitRunnable(){
                @Override
                public void run() {
                    playersTop = getPlayerTop();
                    teamsTop = getTeamTop();
                }
            }.runTaskTimer(a, 100, 600);
        }
    }

    public ArrayList<String> getTeamTop() {
        ArrayList<String> list = MySQLUtils.getValuesFromColumn("TeamStatistics", "name");
        ArrayList<String> largest = new ArrayList<>();
        for(int y = 0; y < 10 && y < list.size(); y++){
            int largestID = 0;
            for(int x = 1; x < list.size(); x++){
                if(MySQLUtils.getTeamInt(list.get(largestID), "points") < MySQLUtils.getTeamInt(list.get(x), "points")) largestID = x;
            }
            largest.add(list.get(largestID));
            list.remove(largestID);
        }
        return largest;
    }
    public ArrayList<String> getPlayerTop() {
        ArrayList<String> list = MySQLUtils.getValuesFromColumn("PlayerStatistics", "name");
        ArrayList<String> largest = new ArrayList<>();
        for(int y = 0; y < 10 && y < list.size(); y++){
            int largestID = 0;
            for(int x = 1; x < list.size(); x++){
                if(MySQLUtils.getTeamInt(list.get(largestID), "points") < MySQLUtils.getTeamInt(list.get(x), "points")) largestID = x;
            }
            largest.add(list.get(largestID));
            list.remove(largestID);
        }
        return largest;
    }
}
