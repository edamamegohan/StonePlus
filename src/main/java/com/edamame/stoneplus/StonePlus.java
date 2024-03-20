package com.edamame.stoneplus;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;

public final class StonePlus extends JavaPlugin implements Listener {
    @Override
    public void onEnable() {
        Bukkit.getPluginManager().registerEvents(this,this);
        Bukkit.getLogger().info("StonePlus is loaded.");
    }

    @Override
    public void onDisable() {

    }

    @EventHandler
    public void onBlockBreakEvent(BlockBreakEvent event){
        //アルミニウムの追加
        ItemStack aluminium = new ItemStack(Material.IRON_NUGGET);
        ItemMeta aluminiumMeta = aluminium.getItemMeta();
        aluminiumMeta.setDisplayName("アルミニウム");
        aluminiumMeta.setLore(Arrays.asList("アルミニウムの鉱物", "一応鉄塊と同じように使えるらしい"));
        aluminium.setItemMeta(aluminiumMeta);

        Block block = event.getBlock();
        if(block.getType() == Material.STONE){
            if(Math.random() < 0.05){
                event.setCancelled(true);
                block.setType(Material.AIR);
                block.getWorld().dropItem(block.getLocation(), aluminium);
            }
        }
    }
}
