package me.brennan.blockstreet.command.impl;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Description;
import co.aikar.commands.annotation.Subcommand;
import me.brennan.blockstreet.BlockStreet;
import me.brennan.blockstreet.company.AbstractCompanyData;
import me.brennan.blockstreet.util.Logger;
import me.brennan.blockstreetapi.company.CompanyData;
import me.brennan.blockstreetapi.company.WageType;
import net.milkbowl.vault.economy.EconomyResponse;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * made for BlockStreet
 *
 * @author Brennan
 * @since 8/15/2020
 **/
@CommandAlias("company|comp")
@Description("The base company command")
public class CompanyCommand extends BaseCommand {

    @CommandPermission("blockstreet.company.create")
    @Subcommand("create|make")
    @Description("Creates a new company.")
    public void createCompany(CommandSender sender, String[] args) {
        if(sender instanceof Player) {
            Player player = (Player) sender;
            if(args.length == 4) {
                final String companyName = args[0].replaceAll("_", " ");
                final String symbol = args[1];
                final WageType wageType = WageType.fromName(args[2]);

                if(wageType == null) {
                    Logger.sendMessage(player, ChatColor.RED, "That is not a proper wage type!");
                    return;
                }

                if(BlockStreet.getINSTANCE().getEconomy().getBalance(player) < 200) {
                    Logger.sendMessage(player, ChatColor.RED, "You must have at least $150 to create a company!");
                    return;
                }

                if(symbol.length() > 4) {
                    Logger.sendMessage(player, ChatColor.RED, "The symbol must at least be 4 characters!");
                    return;
                }

                final EconomyResponse withdrawResponse = BlockStreet.getINSTANCE().getEconomy().withdrawPlayer(player, 150);

                if(withdrawResponse.transactionSuccess()) {
                    Logger.sendMessage(player, ChatColor.GREEN, String.format("$150 has been taken out from your account to create the trademarks and patients for %s", companyName));
                    final CompanyData company = new AbstractCompanyData(companyName, symbol, wageType, player.getUniqueId(), 0, false);
                    BlockStreet.getINSTANCE().getCompanyManager().add(company);

                    if(BlockStreet.getINSTANCE().getCompanyManager().saveCompanyData(company)) {
                        Logger.sendMessage(player, ChatColor.GREEN, String.format("Congratulations! You are now the Founder and CEO of %s!", player.getName()));
                    } else {
                        Logger.sendMessage(player, ChatColor.RED, "Something went wrong!");
                        return;
                    }
                } else {
                    Logger.sendMessage(player, ChatColor.RED, String.format("An error has occurred %s", withdrawResponse.errorMessage));
                    return;
                }
            } else {
                Logger.sendMessage(player, ChatColor.RED, "Invalid Usage! /company create <name> <symbol> <percentage / wage>");
            }
        }
    }

    @CommandPermission("blockstreet.company.disband")
    @Subcommand("disband|close|remove")
    @Description("Creates a new company.")
    public void removeCommand(CommandSender sender, String[] args) {
        if(sender instanceof Player) {

        }
    }

}
