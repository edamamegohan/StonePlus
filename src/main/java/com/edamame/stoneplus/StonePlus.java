package com.edamame.stoneplus;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Item;
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

        //イッテルビウムの追加
        ItemStack ytterbium = new ItemStack(Material.DIAMOND);
        ItemMeta ytterbiumMeta = ytterbium.getItemMeta();
        ytterbiumMeta.setDisplayName("イッテルビウム");
        ytterbiumMeta.setLore(Arrays.asList("レアメタルの一種", "Yb株式会社の名前の由来にもなっている", "一応ダイヤモンドと同じように使えるらしい"));
        ytterbium.setItemMeta(ytterbiumMeta);

        //プラチナの追加
        ItemStack platinum = new ItemStack(Material.GOLD_INGOT);
        ItemMeta platinumMeta = platinum.getItemMeta();
        platinumMeta.setDisplayName("プラチナ");
        platinumMeta.setLore(Arrays.asList("めっちゃきれい", "一応金インゴットと同じように使えるらしい"));
        platinum.setItemMeta(platinumMeta);

        Block block = event.getBlock();
        if(block.getType() == Material.STONE){
            //3%で宝石をドロップ
            if(Math.random() < 0.03){
                double r = Math.random();
                //30%でアルミニウム
                if(r < 0.3){
                    event.setCancelled(true);
                    block.setType(Material.AIR);
                    block.getWorld().dropItem(block.getLocation(), aluminium);
                }
                //20%でプラチナ
                else if (r<0.5) {
                    event.setCancelled(true);
                    block.setType(Material.AIR);
                    block.getWorld().dropItem(block.getLocation(), platinum);
                }
                //1%でイッテルビウム
                else if (r < 0.51) {
                    event.setCancelled(true);
                    block.setType(Material.AIR);
                    block.getWorld().dropItem(block.getLocation(), ytterbium);
                }
            }
        }
    }
}
