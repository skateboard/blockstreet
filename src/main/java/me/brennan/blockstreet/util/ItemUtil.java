package me.brennan.blockstreet.util;

import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

/**
 * made for BlockStreet
 *
 * @author Brennan
 * @since 8/9/2020
 **/
public class ItemUtil {

    public static void createItem(Material material, Inventory inventoryName, int itemSlot, String displayName, String lore) {
        final ItemStack itemCreated = new ItemStack(material);
        final ArrayList<String> loreArray = new ArrayList<>();
        loreArray.add(lore);

        final ItemMeta itemMeta = itemCreated.getItemMeta();
        itemMeta.setDisplayName(displayName);
        itemMeta.setLore(loreArray);
        itemCreated.setItemMeta(itemMeta);

        inventoryName.setItem(itemSlot, itemCreated);
    }

    public static void createItem(Material material, Inventory inventoryName, int itemSlot, String displayName, List<String> lore) {
        final ItemStack itemCreated = new ItemStack(material);
        final ItemMeta itemMeta = itemCreated.getItemMeta();
        itemMeta.setDisplayName(displayName);
        itemMeta.setLore(lore);
        itemCreated.setItemMeta(itemMeta);

        inventoryName.setItem(itemSlot, itemCreated);
    }

}
