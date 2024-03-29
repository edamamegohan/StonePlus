package com.edamame.stoneplus;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;

public final class StonePlus extends JavaPlugin implements Listener {
    database database = new database();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(command.getName().equalsIgnoreCase("create")){
            Player player = (Player) sender;
            database.AddPlayerData(player);
            return true;
        }
        return false;
    }

    @Override
    public void onEnable() {
        Bukkit.getPluginManager().registerEvents(this,this);
        Bukkit.getLogger().info("StonePlus is loaded.");
        database.CreateTable();
    }

    @Override
    public void onDisable() {
        database.CloseConnection();
    }

    @EventHandler
    public void onBlockBreakEvent(BlockBreakEvent event){
        //アルミニウムの追加
        ItemStack aluminium = new ItemStack(Material.IRON_NUGGET);
        ItemMeta aluminiumMeta = aluminium.getItemMeta();
        aluminiumMeta.setDisplayName("アルミニウム");
        aluminiumMeta.setLore(Arrays.asList("アルミニウムの鉱物"));
        aluminiumMeta.setCustomModelData(1);
        aluminium.setItemMeta(aluminiumMeta);

        //イッテルビウムの追加
        ItemStack ytterbium = new ItemStack(Material.IRON_NUGGET);
        ItemMeta ytterbiumMeta = ytterbium.getItemMeta();
        ytterbiumMeta.setDisplayName("イッテルビウム");
        ytterbiumMeta.setLore(Arrays.asList("レアメタルの一種", "Yb株式会社の名前の由来にもなっている"));
        ytterbiumMeta.setCustomModelData(2);
        ytterbium.setItemMeta(ytterbiumMeta);

        //プラチナの追加
        ItemStack platinum = new ItemStack(Material.IRON_NUGGET);
        ItemMeta platinumMeta = platinum.getItemMeta();
        platinumMeta.setDisplayName("プラチナ");
        platinumMeta.setLore(Arrays.asList("めっちゃきれい"));
        platinumMeta.setCustomModelData(3);
        platinum.setItemMeta(platinumMeta);

        Block block = event.getBlock();
        if(block.getType() == Material.STONE){
            //採掘数を1増加
            database.IncreaseCount(event.getPlayer());

            //3%で宝石をドロップ
            if(Math.random() < 0.03){
                event.setCancelled(true);
                block.setType(Material.AIR);
                double r = Math.random();
                //30%でアルミニウム
                if(r < 0.3){
                    block.getWorld().dropItem(block.getLocation(), aluminium);
                }
                //20%でプラチナ
                else if (r<0.5) {
                    block.getWorld().dropItem(block.getLocation(), platinum);
                }
                //1%でイッテルビウム
                else if (r < 0.51) {
                    block.getWorld().dropItem(block.getLocation(), ytterbium);
                }
            }
        }
    }

    @EventHandler
    public void onPlayerJoinEvent(PlayerJoinEvent event){
        Player player = event.getPlayer();
        database.AddPlayerData(player);
    }
}
