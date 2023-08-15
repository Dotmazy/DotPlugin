package fr.dotmazy.dotplugin.util;

import fr.dotmazy.dotplugin.DotPlugin;

import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Database {
    public static final String dbName = "database";
    private static Connection connection;

    public static Connection getConnection() {
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
        }catch(Exception ignored){
            ignored.printStackTrace();
        }
        return null;
    }

    public static boolean initDatabase(){
        try {
            StringBuilder sql = new StringBuilder();
            BufferedReader br = new BufferedReader(new InputStreamReader(DotPlugin.getInstance().getResource("database.sql")));
            String line;
            while ((line = br.readLine()) != null){
                sql.append(line);
            }
            PreparedStatement preparedStatement = connection.prepareStatement(sql.toString());
            preparedStatement.executeUpdate();
        }catch(Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static boolean executeUpdate(String sql){
        try {
            connection.prepareStatement(sql).executeUpdate();
        }catch (Exception e){
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

}
