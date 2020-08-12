package me.brennan.blockstreet.gui.employment;

import me.brennan.blockstreet.BlockStreet;
import me.brennan.blockstreet.employment.object.Employment;
import me.brennan.blockstreet.util.ItemBuilder;
import me.brennan.blockstreet.util.Logger;
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
public class EmploymentShowInventory {
    private Inventory inventory;
    private final Player player;
    private String uuid;

    public EmploymentShowInventory(Player player, String uuid) {
        this.uuid = uuid;
        this.player = player;
        createInventory();

        player.openInventory(inventory);

    }

    private void createInventory() {
        final Employment employment = BlockStreet.INSTANCE.getEmploymentManager().getEmployment(uuid);

        if(employment != null) {
            inventory = Bukkit.createInventory(null, 22,
                    ChatColor.GOLD + "" + ChatColor.BOLD + "Employment Request" + ChatColor.GRAY + " for " + ChatColor.GOLD + employment.getCompany());

            inventory.setItem(10, new ItemBuilder(Material.EMERALD, 1)
                    .setDisplayName(ChatColor.GREEN + "Accept").buildItemStack());

            inventory.setItem(12, new ItemBuilder(Material.REDSTONE, 1)
                    .setDisplayName(ChatColor.RED + "Deny").buildItemStack());
        } else {
            Logger.sendMessage(player, ChatColor.RED, "Employment doesnt exist?");
            player.closeInventory();
        }
    }
}
