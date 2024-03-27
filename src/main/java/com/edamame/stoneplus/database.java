package com.edamame.stoneplus;

import org.bukkit.Bukkit;
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
            this.statement.executeUpdate("create table minedata(uuid text, name text, amount integer)");
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

    }
}
