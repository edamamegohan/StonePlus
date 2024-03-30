package com.edamame.stoneplus;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.sql.*;

public class database {
    private Connection connection = null;
    private Statement statement = null;
    database(){
        try {
            Class.forName("org.sqlite.JDBC");
            this.connection = DriverManager.getConnection("jdbc:sqlite:database.db");
            Bukkit.getLogger().info("ーーーーStonePlusーーーー");
            Bukkit.getLogger().info("Database connected.");
            Bukkit.getLogger().info("ーーーーーーーーーーーーー");
        } catch (Exception e) {
            Bukkit.getLogger().warning(e.toString());
        }
    }

    public void CreateTable(){
        try {
            this.statement = connection.createStatement();
            this.statement.executeUpdate("create table minedata(uuid text, name text, minecount integer)");
            Bukkit.getLogger().info("ーーーーStonePlusーーーー");
            Bukkit.getLogger().info("Table created.");
            Bukkit.getLogger().info("ーーーーーーーーーーーーー");
            this.statement.close();
        } catch (SQLException e) {
            Bukkit.getLogger().warning(e.toString());
        }

    }

    public void CloseConnection(){
        try{this.connection.close();}
        catch(SQLException e){Bukkit.getLogger().info(e.toString());}
    }

    public void AddPlayerData(Player player){
        try{
            this.statement = connection.createStatement();
            String uuid = player.getUniqueId().toString();
            String name = player.getDisplayName();
            ResultSet resultSet = this.statement.executeQuery("select * from minedata where uuid = '" + uuid + "'");

            if(!resultSet.next()) {
                this.statement = connection.createStatement();
                this.statement.executeUpdate("insert into minedata values('" + uuid + "', '" + name + "', 0)");
                player.sendMessage(ChatColor.GREEN + "[StonePlus] " +
                        ChatColor.WHITE + name + "の採掘データを作成しました");
            }

            resultSet.close();
            statement.close();

        }catch (SQLException e){
            Bukkit.getLogger().warning(e.toString());
        }
    }

    public void IncreaseCount(Player player){
        try{
            this.statement = connection.createStatement();
            String uuid = player.getUniqueId().toString();

            ResultSet resultSet = this.statement.executeQuery("select minecount from minedata where uuid = '" + uuid + "'");
            int count = resultSet.getInt("minecount");

            count++;

            this.statement.executeUpdate("update minedata set minecount = " + count + " where uuid = '" + uuid + "'");
            resultSet.close();
            statement.close();
        }catch (SQLException e){
            Bukkit.getLogger().warning(e.toString());
        }
    }

    public int CheckCount(Player player){
        try{
            this.statement = connection.createStatement();
            String uuid = player.getUniqueId().toString();

            ResultSet resultSet = this.statement.executeQuery("select minecount from minedata where uuid = '" + uuid + "'");
            int count = resultSet.getInt("minecount");

            resultSet.close();
            statement.close();
            return count;
        }catch (SQLException e){
            Bukkit.getLogger().warning(e.toString());
            return -1;
        }
    }
}
