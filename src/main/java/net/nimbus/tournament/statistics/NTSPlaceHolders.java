package net.nimbus.tournament.statistics;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class NTSPlaceHolders extends PlaceholderExpansion {
    @Override
    public @NotNull String getIdentifier() {
        return "nts";
    }

    @Override
    public @NotNull String getAuthor() {
        return "MrNimbus777";
    }

    @Override
    public @NotNull String getVersion() {
        return "1.0";
    }

    @Override
    public @Nullable String onPlaceholderRequest(Player player, @NotNull String params) {
        String[] split = params.split("_");
        try {
            if (split[0].equals("player")) {
                if (split[1].equals("top")) {
                    String p = NTStatistics.a.playersTop.get(Integer.parseInt(split[2]));
                    return p + " - " + MySQLUtils.getPlayerInt(p, "points") + " p.";
                } else {
                    return MySQLUtils.getPlayerInt(MySQLUtils.getUuid(split[1]), split[2]) + "";
                }
            } else if (split[0].equals("team")) {
                if (split[1].equals("top")) {
                    String team = NTStatistics.a.teamsTop.get(Integer.parseInt(split[2]));
                    return team + " - " + MySQLUtils.getTeamInt(team, "points") + " p.";
                } else {
                    return MySQLUtils.getTeamInt(split[1], split[2]) + "";
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "-";
        }
        return "";
    }
}