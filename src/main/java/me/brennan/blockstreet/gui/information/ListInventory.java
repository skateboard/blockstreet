package me.brennan.blockstreet.gui.information;

import me.brennan.blockstreet.BlockStreet;
import me.brennan.blockstreet.stock.object.Stock;
import me.brennan.blockstreet.util.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

/**
 * made for BlockStreet
 *
 * @author Brennan
 * @since 8/10/2020
 **/
public class ListInventory {
    private Inventory inventory;

    public ListInventory(Player player) {
        createList();

        player.openInventory(inventory);
    }

    private void createList() {
        inventory = Bukkit.createInventory(null, 36,
                ChatColor.GOLD + "" + ChatColor.BOLD + "Stocks" + ChatColor.GRAY + " - " + ChatColor.GOLD + "List");

        for(int i = 0; i < BlockStreet.INSTANCE.getStockManager().getStocks().size(); i++) {
            final Stock stock = BlockStreet.INSTANCE.getStockManager().getStocks().get(i);
            inventory.setItem(i, new ItemBuilder(Material.BOOK, 1)
                    .setDisplayName(ChatColor.GRAY + "" + ChatColor.BOLD + stock.getCompanyName())
                    .addLoreLine(ChatColor.GOLD + stock.getSymbol())
                    .buildItemStack());
        }

    }

    public Inventory getInventory() {
        return inventory;
    }
}
