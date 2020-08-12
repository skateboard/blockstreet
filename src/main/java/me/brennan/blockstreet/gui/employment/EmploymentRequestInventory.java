package me.brennan.blockstreet.gui.employment;

import me.brennan.blockstreet.BlockStreet;
import me.brennan.blockstreet.employment.object.Employment;
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
 * @since 8/12/2020
 **/
public class EmploymentRequestInventory {
    private Inventory inventory;

    public EmploymentRequestInventory(Player player) {
        createInventory();

        player.openInventory(inventory);
    }

    private void createInventory() {
        inventory = Bukkit.createInventory(null, 36,
                ChatColor.GOLD + "" + ChatColor.BOLD + "Employment" + ChatColor.GRAY + " - " + ChatColor.GOLD + "Request");

        for(int i = 0; i < BlockStreet.INSTANCE.getEmploymentManager().getEmployments().size(); i++) {
            final Employment employment = BlockStreet.INSTANCE.getEmploymentManager().getEmployments().get(i);
            inventory.setItem(i, new ItemBuilder(Material.PAPER, 1)
                    .setDisplayName(ChatColor.GRAY + "" + ChatColor.BOLD + employment.getCompany())
                    .addLoreLine(ChatColor.GRAY + "Role: " + employment.getRole())
                    .addLoreLine(ChatColor.GRAY + "UUID: " + employment.getUuid().toString())
                    .buildItemStack());
        }
    }
}
