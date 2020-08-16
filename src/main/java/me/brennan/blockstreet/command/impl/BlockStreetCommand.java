package me.brennan.blockstreet.command.impl;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import me.brennan.blockstreet.BlockStreet;
import me.brennan.blockstreet.util.Logger;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * made for BlockStreet
 *
 * @author Brennan
 * @since 8/15/2020
 **/
public class BlockStreetCommand extends BaseCommand {

    @CommandPermission("blockstreet.information")
    @CommandAlias("bs|blockstreet")
    public void execute(CommandSender sender) {
        if(sender instanceof Player) {
            final Player player = (Player) sender;
            Logger.sendMessage(player, ChatColor.GREEN, String.format("%s%s %s- %s", ChatColor.GOLD, "BlockStreet", ChatColor.GRAY, BlockStreet.VERSION));
            Logger.sendMessage(player, ChatColor.GREEN, String.format("%s%s %s- %s", ChatColor.GOLD, "Amount of Companies", ChatColor.GRAY, BlockStreet.getINSTANCE().getCompanyManager().getData().size()));
            Logger.sendMessage(player, ChatColor.GREEN, String.format("%s%s %s- %s", ChatColor.GOLD, "Amount of Stocks", ChatColor.GRAY, BlockStreet.getINSTANCE().getStockManager().getData().size()));
            return;
        }

        //just send console basic info lol.
        sender.sendMessage("BlockStreet " + BlockStreet.VERSION);
    }

}
