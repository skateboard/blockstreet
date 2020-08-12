package me.brennan.blockstreet.command;

import me.brennan.blockstreet.BlockStreet;
import me.brennan.blockstreet.util.Logger;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * made for BlockStreet
 *
 * @author Brennan
 * @since 8/11/2020
 **/
public class BlockStreetCommand implements CommandExecutor {
    private final BlockStreet blockStreet;

    public BlockStreetCommand(BlockStreet blockStreet) {
        this.blockStreet = blockStreet;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {
        if(commandSender instanceof Player) {
            Player player = (Player) commandSender;
            if(!commandSender.hasPermission("blockstreet.main")) {
                Logger.sendMessage(player, ChatColor.RED, "You do not have the permission level for that!");
                return false;
            }

            Logger.sendMessage(player, ChatColor.GREEN, String.format("%s%s %s- %s", ChatColor.GOLD, "BlockStreet", ChatColor.GRAY, blockStreet.VERSION));
            Logger.sendMessage(player, ChatColor.GREEN, String.format("%s%s %s- %s", ChatColor.GOLD, "Amount of Companies", ChatColor.GRAY, blockStreet.getCompanyManager().getCompanies().size()));
            Logger.sendMessage(player, ChatColor.GREEN, String.format("%s%s %s- %s", ChatColor.GOLD, "Amount of Stocks", ChatColor.GRAY, blockStreet.getStockManager().getStocks().size()));
            return true;
        }


        return false;
    }
}
