package net.nimbus.tournament.statistics;

import java.sql.*;
import java.util.ArrayList;

public class MySQLUtils {
    public static Connection con;
    static int tries = 10;


    static String adress;
    static String name;
    static String user;
    static String password;

    public static void newConnection(String adress, String name, String user, String password) {
        MySQLUtils.adress = adress;
        MySQLUtils.name = name;
        MySQLUtils.user = user;
        MySQLUtils.password = password;
        try {
            con = DriverManager.getConnection("jdbc:mysql://" + adress + "/" + name, user, password);
        } catch (Exception e) {
            con = null;
        }
    }

    public static void newConnection() {
        try {
            con = DriverManager.getConnection("jdbc:mysql://" + adress + "/" + name, user, password);
        } catch (Exception e) {
            con = null;
        }
    }
    public static double getTeamDouble(String team, String statistic) {
        try {
            PreparedStatement statement = con.prepareStatement("SELECT * FROM TeamStatistics WHERE name = '" + team + "'");
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                double d = rs.getDouble(statistic);
                tries = 10;
                return d;
            }
            tries = 10;
            return 0;
        } catch (SQLException e) {
            tries--;
            if (tries > 0) {
                newConnection();
                NTStatistics.a.getLogger().severe("Trying to recconect to MySQL Database...");
                if (con != null) return getTeamDouble(team, statistic);
            }
            tries = 10;
            return 0;
        }
    }

    public static int getTeamInt(String team, String statistic) {
        try {
            PreparedStatement statement = con.prepareStatement("SELECT * FROM TeamStatistics WHERE name = '" + team + "'");
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                int d = rs.getInt(statistic);
                tries = 10;
                return d;
            }
            tries = 10;
            return 0;
        } catch (SQLException e) {
            tries--;
            if (tries > 0) {
                newConnection();
                NTStatistics.a.getLogger().severe("Trying to recconect to MySQL Database...");
                if (con != null) return getTeamInt(team, statistic);
            }
            tries = 10;
            return 0;
        }
    }
    public static double getPlayerDouble(String uuid, String statistic) {
        try {
            PreparedStatement statement = con.prepareStatement("SELECT * FROM PlayerStatistics WHERE uuid = '" + uuid + "'");
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                double d = rs.getDouble(statistic);
                tries = 10;
                return d;
            }
            tries = 10;
            return 0;
        } catch (SQLException e) {
            tries--;
            if (tries > 0) {
                newConnection();
                NTStatistics.a.getLogger().severe("Trying to recconect to MySQL Database...");
                if (con != null) return getPlayerDouble(uuid, statistic);
            }
            tries = 10;
            return 0;
        }
    }
    public static int getPlayerInt(String uuid, String statistic) {
        try {
            PreparedStatement statement = con.prepareStatement("SELECT * FROM PlayerStatistics WHERE uuid = '" + uuid + "'");
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                int d = rs.getInt(statistic);
                tries = 10;
                return d;
            }
            tries = 10;
            return 0;
        } catch (SQLException e) {
            tries--;
            if (tries > 0) {
                newConnection();
                NTStatistics.a.getLogger().severe("Trying to recconect to MySQL Database...");
                if (con != null) return getPlayerInt(uuid, statistic);
            }
            tries = 10;
            return 0;
        }
    }
    public static ArrayList<String> getValuesFromColumn(String table, String column) {
        ArrayList<String> keys = new ArrayList<>();

        try {
            PreparedStatement statement = con.prepareStatement("SELECT * FROM " + table);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                keys.add(rs.getString(column));
            }
            tries = 10;
            return keys;
        } catch (SQLException e) {
            e.printStackTrace();
            tries--;
            if (tries > 0) {
                newConnection();
                NTStatistics.a.getLogger().severe("Trying to recconect to MySQL Database...");
                if (con != null) return getValuesFromColumn(table, column);
            }
            return new ArrayList<>();
        }
    }
    public static String getString(String table, String column_from, String key, String column_to) {
        try {
            PreparedStatement statement = con.prepareStatement("SELECT * FROM " + table + " WHERE " + column_from + " = '" + key + "'");
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                String s = rs.getString(column_to);
                tries = 10;
                return s;
            }
            tries = 10;
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
            tries--;
            if (tries > 0) {
                newConnection();
                NTStatistics.a.getLogger().severe("Trying to recconect to MySQL Database...");
                if (con != null) return getString(table, column_from, key, column_to);
            }
            return null;
        }
    }

    public static String getUuid(String name) {
        return MySQLUtils.getString("UUIDs", "name", name, "UUID");
    }
}