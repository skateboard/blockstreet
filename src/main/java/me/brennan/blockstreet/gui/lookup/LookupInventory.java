package me.brennan.blockstreet.gui.lookup;

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
public class LookupInventory {
    private BlockStreet blockStreet;
    private String stockSymbol;
    private Player target;
    private Inventory inventory;

    public LookupInventory(BlockStreet blockStreet, Player target, String stockSymbol) {
        this.blockStreet = blockStreet;
        this.stockSymbol = stockSymbol;
        this.target = target;

        createInventory();
    }

    private void createInventory() {
        inventory = Bukkit.createInventory(null, 36,
                ChatColor.GOLD + "" + ChatColor.BOLD + "Stocks" + ChatColor.GRAY + " - " + ChatColor.GOLD
                        + stockSymbol.toUpperCase());

        Bukkit.getScheduler().runTaskAsynchronously(blockStreet, () -> {
            final Stock stock = blockStreet.getStockManager().getStock(stockSymbol);

            if (!(stock == null) && !stock.getCompanyName().equalsIgnoreCase("N/A")) {
                final String price = blockStreet.getEconomy().format(stock.getPrice());

                Bukkit.getScheduler().runTaskAsynchronously(blockStreet, () -> {
                    inventory.setItem(0, new ItemBuilder(Material.BOOK, 1)
                            .setDisplayName(ChatColor.GRAY + "" + ChatColor.BOLD + "Name")
                            .addLoreLine(ChatColor.GOLD + stock.getCompanyName())
                            .buildItemStack());

                    inventory.setItem(8, new ItemBuilder(Material.BOOK, 1)
                            .setDisplayName(ChatColor.GRAY + "" + ChatColor.BOLD + "Symbol")
                            .addLoreLine(ChatColor.GOLD + stock.getSymbol())
                            .buildItemStack());

                    inventory.setItem(13, new ItemBuilder(Material.BOOK, 1)
                            .setDisplayName(ChatColor.GRAY + "" + ChatColor.BOLD + "Volume")
                            .addLoreLine(ChatColor.GOLD + String.valueOf(stock.getVolume()))
                            .buildItemStack());


                    target.openInventory(inventory);
                });
            } else {
                target.sendMessage("Invalid Stock");
            }
        });

    }
}
