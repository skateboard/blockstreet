package me.brennan.blockstreet.command;

import me.brennan.blockstreet.BlockStreet;
import me.brennan.blockstreet.company.Company;
import me.brennan.blockstreet.company.object.Employee;
import me.brennan.blockstreet.employment.object.Employment;
import me.brennan.blockstreet.util.Logger;
import me.brennan.blockstreet.util.NumberUtils;
import net.milkbowl.vault.economy.EconomyResponse;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import sun.rmi.runtime.Log;

/**
 * made for BlockStreet
 *
 * @author Brennan
 * @since 8/9/2020
 **/
public class CompanyCommand implements CommandExecutor {
    private final BlockStreet blockStreet;

    public CompanyCommand(BlockStreet blockStreet) {
        this.blockStreet = blockStreet;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if(commandSender instanceof Player) {
            Player player = (Player) commandSender;

            if(!commandSender.hasPermission("blockstreet.company.use")) {
                Logger.sendMessage(player, ChatColor.RED, "You do not have the permission level for that!");
                return false;
            }

//            if (args.length <= 1) {
//                Logger.sendMessage(player, ChatColor.RED, "Not Enough Args!");
//                return false;
//            }

            switch (args[0]) {
                case "create":
                case "make":
                    if(args.length == 3) {
                        if(commandSender.hasPermission("blockstreet.company.create")) {
                            final String name = args[1];
                            final String symbol = args[2];

                            if(blockStreet.getEconomy().getBalance(player) < 200) {
                                Logger.sendMessage(player, ChatColor.RED, "You must have at least $250 to create a company!");
                                return false;
                            }

                            if(symbol.length() > 4) {
                                Logger.sendMessage(player, ChatColor.RED, "The symbol cannot be greater than 4 characters!");
                                return false;
                            }

                            final EconomyResponse withdrawResponse = blockStreet.getEconomy().withdrawPlayer(player, 200);

                            if(withdrawResponse.transactionSuccess()) {
                                Logger.sendMessage(player, ChatColor.GREEN, "$200 has been taken from your account to create " + name);
                                final Company company = blockStreet.getCompanySystem().createCompany(player, name, symbol, 0);
                                blockStreet.getCompanyManager().add(company);

                                if(company != null) {
                                    Logger.sendMessage(player, ChatColor.GREEN, String.format("Congratulations! You are now the Founder and CEO of %s!", name));
                                } else {
                                    Logger.sendMessage(player, ChatColor.RED, "Something went wrong!");
                                    return false;
                                }
                            } else {
                                Logger.sendMessage(player, ChatColor.RED, String.format("An error has occurred %s", withdrawResponse.errorMessage));
                                return false;
                            }

                        } else {
                            Logger.sendMessage(player, ChatColor.RED, "You do not have the permission level for that!");
                            return false;
                        }
                    } else {
                        Logger.sendMessage(player, ChatColor.RED, "Invalid Usage! /company create <name> <symbol>");
                        return false;
                    }
                    break;
                case "ceo":
                    if(args.length == 3) {
                        if(commandSender.hasPermission("blockstreet.company.ceo")) {
                            final String name = args[1];
                            final Player appointedPlayer = Bukkit.getPlayer(args[2]);

                            if(!blockStreet.getCompanySystem().doesCompanyExist(name)) {
                                Logger.sendMessage(player, ChatColor.RED, "That company does not exist!");
                                return false;
                            }

                            final Company company = blockStreet.getCompanyManager().getCompany(name);

                            if(company != null) {
                                if(!company.isCEO(player.getUniqueId())) {
                                    Logger.sendMessage(player, ChatColor.RED, "You are not the current CEO of that company! You may not appoint one!");
                                    return false;
                                }

                                if(appointedPlayer != null) {
                                    blockStreet.getCompanySystem().setCEO(name, appointedPlayer.getUniqueId());
                                    company.setCEOUUID(appointedPlayer.getUniqueId());
                                    Logger.sendMessage(player, ChatColor.GREEN, String.format("You have appointed %s CEO of %s", appointedPlayer.getName(), name));

                                    Logger.sendMessage(appointedPlayer, ChatColor.GREEN, String.format("Congratulations! You are now the CEO of %s!", name));
                                } else {
                                    Logger.sendMessage(player, ChatColor.RED, "That isn't a player? Player must be online to be made CEO!");
                                    return false;
                                }
                            } else {
                                Logger.sendMessage(player, ChatColor.RED, "This company does not exist!");
                                return false;
                            }
                        } else {
                            Logger.sendMessage(player, ChatColor.RED, "You do not have the permission level for that!");
                            return false;
                        }
                    } else {
                        Logger.sendMessage(player, ChatColor.RED, "Invalid Usage! /company ceo <player>");
                        return false;
                    }
                    break;
                case "disband":
                case "close":
                    if(args.length == 2) {
                        if(commandSender.hasPermission("blockstreet.company.disband")) {
                            final String name = args[1];
                            final Company company = blockStreet.getCompanyManager().getCompany(name);

                            if(company != null) {
                                if(!company.isCEO(player.getUniqueId())) {
                                    Logger.sendMessage(player, ChatColor.RED, "You are not the current CEO of that company! You may not appoint one!");
                                    return false;
                                }

                                blockStreet.getCompanySystem().deleteCompany(name);
                                blockStreet.getStockSystem().deleteStock(company.getSymbol());

                                blockStreet.getStockManager().getStocks().remove(BlockStreet.INSTANCE.getStockManager().getStock(company.getSymbol()));
                                blockStreet.getCompanyManager().remove(company);

                                Logger.sendMessage(player, ChatColor.GREEN, String.format("Successfully disbanded %s", name));
                            } else {
                                Logger.sendMessage(player, ChatColor.RED, "Company does not exist!");
                                return false;
                            }
                        } else {
                            Logger.sendMessage(player, ChatColor.RED, "You do not have the permission level for that!");
                            return false;
                        }
                    } else {
                        Logger.sendMessage(player, ChatColor.RED, "Invalid Usage! /company close <company>");
                        return false;
                    }
                    break;
                case "hire":
                    if(args.length == 4) {
                        if(commandSender.hasPermission("blockstreet.company.hire")) {
                            final String companyName = args[1];
                            final String employeeName = args[2];
                            final String role = args[3];

                            final Company company = blockStreet.getCompanyManager().getCompany(companyName);

                            if(company != null) {
                                final OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(employeeName);
                                final Player employeePlayer = Bukkit.getPlayerExact(employeeName);

                                if(offlinePlayer != null) {
                                    final Employment employment = new Employment(offlinePlayer.getUniqueId(), companyName, role);
                                    if(offlinePlayer.isOnline()) {
                                        blockStreet.getEmploymentSystem().addEmploymentRequest(employment);
                                        blockStreet.getEmploymentManager().add(employment);
                                        Logger.sendMessage(employeePlayer, ChatColor.GREEN, String.format("You have been requested to join %s as %s! To accept just do /employment requests.", companyName, role));
                                    } else {
                                        blockStreet.getEmploymentSystem().addEmploymentRequest(employment);
                                    }
                                }

                                Logger.sendMessage(player, ChatColor.GREEN, String.format("Successfully sent a request to hire %s as %s", employeeName, role));
                            } else {
                                Logger.sendMessage(player, ChatColor.RED, "Company does not exist!");
                                return false;
                            }
                        } else {
                            Logger.sendMessage(player, ChatColor.RED, "You do not have the permission level for that!");
                            return false;
                        }
                    } else {
                        Logger.sendMessage(player, ChatColor.RED, "Invalid Usage! /company hire <company> <employee name> <role>");
                        return false;
                    }
                    break;
                case "balance":
                case "bal":
                case "account":
                    if(args.length == 2) {
                        if(commandSender.hasPermission("blockstreet.company.account")) {
                            final String name = args[1];
                            final Company company = blockStreet.getCompanyManager().getCompany(name);

                            if(company != null) {
                                Logger.sendMessage(player, ChatColor.GREEN, String.format("Account Balance: %s", blockStreet.getEconomy().format(company.getBalance())));
                            } else {
                                Logger.sendMessage(player, ChatColor.RED, "That company does not exist!");
                                return false;
                            }
                        }
                    } else {
                        Logger.sendMessage(player, ChatColor.RED, "Invalid Usage! /company balance <company>");
                        return false;
                    }
                    break;
                case "deposit":
                    if(args.length == 3) {
                        if(commandSender.hasPermission("blockstreet.company.deposit")) {

                            if(!NumberUtils.isNumber(args[2])) {
                                Logger.sendMessage(player, ChatColor.RED, "The amount is not a number!");
                                return false;
                            }
                            final String name = args[1];
                            final double amount = Double.parseDouble(args[2]);

                            if(!(blockStreet.getEconomy().getBalance(player) >= amount)) {
                                Logger.sendMessage(player, ChatColor.RED, "You do not have that much money to put in!");
                                return false;
                            }

                            final Company company = blockStreet.getCompanyManager().getCompany(name);

                            if(company != null) {
                                final double finalBalance = company.getBalance() + amount;
                                final EconomyResponse withdrawResponse = blockStreet.getEconomy().withdrawPlayer(player, amount);

                                if(withdrawResponse.transactionSuccess()) {
                                    blockStreet.getCompanySystem().setBalance(name, finalBalance);
                                    company.setBalance(finalBalance);

                                    Logger.sendMessage(player, ChatColor.GREEN, String.format("Successfully deposited %s into %s account!", blockStreet.getEconomy().format(finalBalance), name));
                                } else {
                                    Logger.sendMessage(player, ChatColor.RED, String.format("An error has occurred %s", withdrawResponse.errorMessage));
                                    return false;
                                }

                            } else {
                                Logger.sendMessage(player, ChatColor.RED, "That company does not exist!");
                                return false;
                            }
                        } else {
                            Logger.sendMessage(player, ChatColor.RED, "You do not have the permission level for that!");
                            return false;
                        }
                    } else {
                        Logger.sendMessage(player, ChatColor.RED, "Invalid Usage! /company deposit <company> <amount>");
                        return false;
                    }
                    break;
                case "withdraw":
                    if(args.length == 3) {
                        if(commandSender.hasPermission("blockstreet.company.withdraw")) {
                            if(!NumberUtils.isNumber(args[2])) {
                                Logger.sendMessage(player, ChatColor.RED, "The amount is not a number!");
                                return false;
                            }
                            final String name = args[1];
                            final double amount = Double.parseDouble(args[2]);
                            final Company company = blockStreet.getCompanyManager().getCompany(name);

                            if(company != null) {
                                if(company.getBalance() <= 0) {
                                    Logger.sendMessage(player, ChatColor.RED, "You cannot withdraw under $0!");
                                    return false;
                                }

                                final double finalBalance = company.getBalance() - amount;
                                final EconomyResponse depositResponse = blockStreet.getEconomy().depositPlayer(player, amount);

                                if(depositResponse.transactionSuccess()) {
                                    blockStreet.getCompanySystem().setBalance(name, finalBalance);
                                    company.setBalance(finalBalance);

                                    Logger.sendMessage(player, ChatColor.GREEN, String.format("You have withdrawn %s from %s account!", blockStreet.getEconomy().format(finalBalance), name));
                                } else {
                                    Logger.sendMessage(player, ChatColor.RED, String.format("An error has occurred %s", depositResponse.errorMessage));
                                    return false;
                                }
                            } else {
                                Logger.sendMessage(player, ChatColor.RED, "That company does not exist!");
                                return false;
                            }

                        }
                    } else {
                        Logger.sendMessage(player, ChatColor.RED, "Invalid Usage! /company withdraw <company> <amount>");
                        return false;
                    }
                    break;
                case "open":
                case "ipo":
                    if(args.length == 2) {
                        if (commandSender.hasPermission("blockstreet.company.open")) {
                            final String name = args[1];
                            final Company company = blockStreet.getCompanyManager().getCompany(name);

                            if(company != null) {

                                if(!company.isCEO(player.getUniqueId())) {
                                    Logger.sendMessage(player, ChatColor.RED, "You are not the current CEO! You cannot open the company!");
                                    return false;
                                }

                                if(company.isOpen()) {
                                    Logger.sendMessage(player, ChatColor.RED, "This company is already opened!");
                                    return false;
                                }

                                if(company.getBalance() == 0) {
                                    Logger.sendMessage(player, ChatColor.RED, "You cannot open a company with $0 in the account! deposit some money before opening again!");
                                    return false;
                                }

                                final double value = Double.parseDouble(blockStreet.getEconomy().format(company.getBalance() / 3).replace("$", ""));

                                company.setOpen(true);
                                blockStreet.getCompanySystem().openCompany(company.getName());
                                blockStreet.getStockSystem().createStock(company.getSymbol(), company.getName());
                                blockStreet.getStockSystem().setValue(company.getSymbol(), value);

                                Logger.sendMessage(player, ChatColor.GREEN, String.format("Congratulations! Your company is now open for anyone to buy! it is currently valued at $%s", value));
                                Logger.broadCast(ChatColor.GREEN, String.format("%s has been opened to the public! You can now buy shares at %s", name, value));
                            } else {
                                Logger.sendMessage(player, ChatColor.RED, "That company does not exist!");
                                return false;
                            }
                        }
                    } else {
                        Logger.sendMessage(player, ChatColor.RED, "Invalid Usage! /company open <company>");
                        return false;
                    }
                    break;
            }

            return true;
        } else {
            commandSender.sendMessage("This is a player command only!");
        }

        return false;
    }
}
