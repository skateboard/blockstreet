package me.brennan.blockstreet.util;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

/**
 * made for BlockStreet
 *
 * @author Brennan
 * @since 8/10/2020
 **/
public class ItemBuilder {
    private ItemStack is;

    public ItemBuilder(Material m, int amount) {
        is = new ItemStack(m, amount);
    }

    public ItemBuilder(Material m, int amount, byte durability) {
        is = new ItemStack(m, amount, durability);
    }

    public ItemBuilder setDisplayName(String displayName) {
        ItemMeta im = is.getItemMeta();
        im.setDisplayName(ChatColor.translateAlternateColorCodes('&', displayName));
        is.setItemMeta(im);
        return this;
    }

    public ItemBuilder addLoreLine(String line) {
        ItemMeta im = is.getItemMeta();

        List<String> lore;
        if (im.hasLore()) {
            lore = new ArrayList<>(im.getLore());
        } else {
            lore = new ArrayList<>();
        }

        lore.add(ChatColor.translateAlternateColorCodes('&', line));

        im.setLore(lore);
        is.setItemMeta(im);
        return this;
    }

    public ItemStack buildItemStack() {
        return is;
    }
}
