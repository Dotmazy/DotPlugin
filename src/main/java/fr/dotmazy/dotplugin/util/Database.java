package fr.dotmazy.dotplugin.util;

import fr.dotmazy.dotplugin.DotPlugin;
import org.bukkit.configuration.ConfigurationSection;

import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

public class Database {
    private static final String dbName = "database";
    private static Connection connection;
    private static boolean isMysql = false;

    public static Connection getConnection() {
        if(DotPlugin.getInstance().getConfig().getBoolean("database.isMysql")){
            isMysql = true;
            ConfigurationSection dbInfo = DotPlugin.getInstance().getConfig().getConfigurationSection("database.mysql");
            if(connection==null) try {
                connection = DriverManager.getConnection("jdbc:mysql://"+
                                dbInfo.getString("host")+":"+
                                dbInfo.getString("port")+"/"+
                                dbInfo.getString("name"),
                        dbInfo.getString("user"),
                        dbInfo.getString("password"));
            } catch (SQLException e) {e.printStackTrace();}
            return connection;
        }else {
            File folder = new File(DotPlugin.getInstance().getDataFolder(), dbName+".db");
            if (!folder.exists()){
                try {
                    folder.createNewFile();
                }catch (Exception ignored){
                    ignored.printStackTrace();
                }
            }
            try {
                if(connection!=null && !connection.isClosed()){
                    return connection;
                }
                Class.forName("org.sqlite.JDBC");
                connection = DriverManager.getConnection("jdbc:sqlite:"+folder);
                return connection;
            }catch(Exception e){
                e.printStackTrace();
            }
            return null;
        }
    }

    public static void init(){
        if(getConnection()!=null){
            try {
                StringBuilder sql = new StringBuilder();
                BufferedReader br = new BufferedReader(new InputStreamReader(DotPlugin.getInstance().getResource((isMysql?"mysql":"sqlite")+".sql")));
                String line;
                while ((line = br.readLine()) != null){
                    sql.append(line).append("\n");
                }
                if(isMysql){
                    for(String part : sql.toString().split(";"))
                        connection.prepareStatement(part).executeUpdate();
                }else{
                    //for(String part : sql.toString().split(";"))
                        //connection.prepareStatement(part).executeUpdate();
                    //connection.prepareStatement(sql.toString()).executeUpdate();
                }
                ConsoleColors.info("[Database] "+(isMysql?"Mysql":"Sqlite")+" Database connected and initialized successfully !");
            }catch(Exception e) {
                //if(!Objects.equals(e.getMessage(), "The prepared statement has been finalized"))
                e.printStackTrace();
            }
        }
    }

    public static boolean executeUpdate(String sql){
        try {
            connection.prepareStatement(sql).executeUpdate();
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static ResultSet executeQuery(String sql){
        try {
            return connection.prepareStatement(sql).executeQuery();
        }catch (Exception e){
            return null;
        }
    }

    public static String executeStringQuery(String sql){
        try {
            ResultSet result = Database.executeQuery(sql);
            if(result.next()){
                return result.getString(1);
            }
        }catch(Exception ignore){}
        return null;
    }

    public static boolean executeBooleanQuery(String sql){
        try {
            ResultSet result = Database.executeQuery(sql);
            if(result.next()){
                return result.getBoolean(1);
            }
        }catch(Exception ignore){}
        return false;
    }

    public static int executeIntegerQuery(String sql){
        try {
            ResultSet result = Database.executeQuery(sql);
            if(result.next()){
                return result.getInt(1);
            }
        }catch(Exception ignore){}
        return 0;
    }

    public static boolean disconnect(){
        try {
            if(connection.isClosed())
                return false;
            connection.close();
            connection = null;
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

}
