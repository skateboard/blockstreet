package me.brennan.blockstreet.gui;

import me.brennan.blockstreet.BlockStreet;
import me.brennan.blockstreet.gui.employment.EmploymentRequestInventory;
import me.brennan.blockstreet.gui.employment.EmploymentShowInventory;
import me.brennan.blockstreet.gui.information.ListInventory;
import me.brennan.blockstreet.gui.lookup.LookupInventory;
import org.bukkit.entity.Player;

/**
 * made for BlockStreet
 *
 * @author Brennan
 * @since 8/9/2020
 **/
public class GUIManager {
    private BlockStreet street;

    public GUIManager(BlockStreet street) {
        this.street = street;

    }

    public void openListInventory(Player player) {
        new ListInventory(player);
    }

    public void openEmploymentShow(Player player, String uuid) {
        new EmploymentShowInventory(player, uuid);
    }

    public void openEmploymentInventory(Player player) {
        new EmploymentRequestInventory(player);
    }

    public void openLookupInventory(Player player, String symbol) {
        new LookupInventory(street, player, symbol);
    }

}
