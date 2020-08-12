package me.brennan.blockstreet.listeners;

import me.brennan.blockstreet.BlockStreet;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;

/**
 * made for BlockStreet
 *
 * @author Brennan
 * @since 8/10/2020
 **/
public class PlayerListener implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onInventoryClick (final InventoryClickEvent e) {
        if (e.getInventory() != null) {
            if (e.getInventory().getName().contains("Stocks") && !ChatColor.stripColor(e.getInventory().getName()).equalsIgnoreCase("Stocks - List")) {
                e.setCancelled(true);
                e.setResult(Event.Result.DENY);
                return;
            }

            if(ChatColor.stripColor(e.getInventory().getName()).equalsIgnoreCase("Employment - Request")) {
                if(e.getCurrentItem() != null) {
                    if(e.getCurrentItem().hasItemMeta()) {
                        if(e.getCurrentItem().getType() == Material.PAPER) {
                            final String company = ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName());
                            final String uuid = ChatColor.stripColor(e.getCurrentItem().getItemMeta().getLore().get(1));
                            e.getWhoClicked().closeInventory();
                            BlockStreet.INSTANCE.getGuiManager().openEmploymentShow((Player) e.getWhoClicked(), uuid);
                        }
                    }
                }

                e.setCancelled(true);
            }

            if(ChatColor.stripColor(e.getInventory().getName()).contains("Employment Request for")) {
                if(e.getCurrentItem() != null) {
                    if(e.getCurrentItem().hasItemMeta()) {
                        if(e.getCurrentItem().getType() == Material.EMERALD) {

                        } else if(e.getCurrentItem().getType() == Material.REDSTONE) {

                        }
                    }
                }

                e.setCancelled(true);
            }

            if (ChatColor.stripColor(e.getInventory().getName()).equalsIgnoreCase("Stocks - List")) {
                if (e.getCurrentItem() != null) {
                    if (e.getCurrentItem().hasItemMeta()) {
                        if (e.getCurrentItem().getType() == Material.BOOK) {
                            final String toLookupSymbol = ChatColor.stripColor(e.getCurrentItem().getItemMeta().getLore().get(0));
                            final Player commandSender = (Player) e.getWhoClicked();
                            e.getWhoClicked().closeInventory();
                            BlockStreet.INSTANCE.getGuiManager().openLookupInventory(commandSender, toLookupSymbol);
                        }
                    }
                }
                e.setCancelled(true);
            }
        } else {
            e.setCancelled(true);
        }

    }

    @EventHandler (priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onInventoryDrag (final InventoryDragEvent e) {
        if (e.getInventory() != null) {
            if (e.getInventory().getName().contains("Stocks")) {
                e.setCancelled(true);
                e.setResult(Event.Result.DENY);
            }
        }
    }
}
