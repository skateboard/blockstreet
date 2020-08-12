package me.brennan.blockstreet.command;

import me.brennan.blockstreet.BlockStreet;
import me.brennan.blockstreet.company.Company;
import me.brennan.blockstreet.stock.object.Stock;
import me.brennan.blockstreet.util.Logger;
import me.brennan.blockstreet.util.NumberUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * made for BlockStreet
 *
 * @author Brennan
 * @since 8/9/2020
 **/
public class StockCommand implements CommandExecutor {
    private final BlockStreet blockStreet;

    public StockCommand(BlockStreet blockStreet) {
        this.blockStreet = blockStreet;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if(commandSender instanceof Player) {
            Player player = (Player) commandSender;

            if(!commandSender.hasPermission("blockstreet.stock.use")) {
                Logger.sendMessage(player, ChatColor.RED, "You do not have the permission level for that!");
                return false;
            }

//            if (args.length > 1) {
//                commandSender.sendMessage("Not Enough Args!");
//                return false;
//            }

            switch (args[0]) {
                case "tutorial":
                case "tut":

                    break;
                case "portfolio":
                case "port":
                    if(commandSender.hasPermission("blockstreet.portfolio")) {

                    } else if(args.length == 2) {
                        if(commandSender.hasPermission("blockstreet.portfolio.other")) {
                            final Player appendedPlayer = Bukkit.getPlayer(args[1]);
                        } else {
                            Logger.sendMessage(player, ChatColor.RED, "You dont have the permission level for that!");
                            return false;
                        }
                    } else {
                        Logger.sendMessage(player, ChatColor.RED, "You do not have the permission level for that!");
                        return false;
                    }
                    break;
                case "leaderboard":
                case "boards":
                    if(commandSender.hasPermission("blockstreet.leaderboards")) {

                    } else if(args.length == 2) {

                        if(args[1].equalsIgnoreCase("profit")) {
                            if(commandSender.hasPermission("blockstreet.leaderboards.profit")) {

                            } else {
                                Logger.sendMessage(player, ChatColor.RED, "You dont have the permission level for that!");
                                return false;
                            }
                        }
                    } else {
                        Logger.sendMessage(player, ChatColor.RED, "You do not have the permission level for that!");
                        return false;
                    }
                    break;
                case "transactions":
                case "orders":
                    if(commandSender.hasPermission("blockstreet.transactions")) {

                    } else if(args.length == 2) {
                        if(commandSender.hasPermission("blockstreet.transactions.other")) {

                        } else {
                            Logger.sendMessage(player, ChatColor.RED, "You dont have the permission level for that!");
                            return false;
                        }
                    } else {
                        Logger.sendMessage(player, ChatColor.RED, "You do not have the permission level for that!");
                        return false;
                    }
                    break;
                case "history":
                case "shistory":
                    if(commandSender.hasPermission("blockstreet.history")) {

                    } else {
                        Logger.sendMessage(player, ChatColor.RED, "You do not have the permission level for that!");
                        return false;
                    }
                    break;
                case "list":
                    blockStreet.getGuiManager().openListInventory(player);
                    break;
                case "help":

                    break;
                case "lookup":
                case "search":
                    if(args.length == 2) {
                        if(commandSender.hasPermission("blockstreet.lookup")) {
                            final String symbol = args[1];
                            blockStreet.getGuiManager().openLookupInventory(player, symbol);
                        }
                    }
                    break;
                case "information":
                case "info":
                    if(args.length == 2) {
                        if(commandSender.hasPermission("blockstreet.information")) {
                            final String symbol = args[1];
                            final Stock stock = blockStreet.getStockManager().getStock(symbol);

                            if(stock != null) {
                                final double currentMarketValue = stock.getPrice() * stock.getVolume();
                                Logger.sendMessage(player, ChatColor.GREEN, "Stock: " + symbol);
                                Logger.sendMessage(player, ChatColor.GREEN, String.format("%s%s %s- %s%s", ChatColor.GOLD, "Company Name", ChatColor.WHITE, ChatColor.GRAY, stock.getCompanyName()));
                                Logger.sendMessage(player, ChatColor.GREEN, String.format("%s%s %s- %s%s", ChatColor.GOLD, "Current Price", ChatColor.WHITE, ChatColor.GRAY, stock.getPrice()));
                                Logger.sendMessage(player, ChatColor.GREEN, String.format("%s%s %s- %s%s", ChatColor.GOLD, "Current Volume", ChatColor.WHITE, ChatColor.GRAY, stock.getVolume()));
                                Logger.sendMessage(player, ChatColor.GREEN, String.format("%s%s %s- %s%s", ChatColor.GOLD, "Current Market Value", ChatColor.WHITE, ChatColor.GRAY, blockStreet.getEconomy().format(currentMarketValue)));
                            } else {
                                Logger.sendMessage(player, ChatColor.RED, "This stock doesnt exist?");
                                return false;
                            }
                        }
                    }
                    break;
                case "compare":
                    if(args.length == 2) {
                        if(commandSender.hasPermission("blockstreet.compare")) {

                        }
                    }
                    break;
                case "buy":
                case "purchase":
                case "order":
                    if(args.length == 3) {
                        if(commandSender.hasPermission("blockstreet.buy")) {

                            if(!NumberUtils.isNumber(args[2])) {
                                commandSender.sendMessage("Invalid Quantity amount!");
                                return false;
                            }

                            final String symbol = args[1];
                            final int quantity = Integer.parseInt(args[2]);

                            if(blockStreet.getCompanySystem().isPrivateCompany(symbol)) {
                                Logger.sendMessage(player, ChatColor.RED, "That Company is PRIVATE!");
                                return false;
                            }

                            if(quantity <= 0) {
                                Logger.sendMessage(player, ChatColor.RED, "Invalid Quantity!");
                                return false;
                            }

                            final Company company = blockStreet.getCompanyManager().getCompanyBySymbol(symbol);

                            if(company != null) {
                                final Stock stock = blockStreet.getStockManager().getStock(symbol);

                                if(stock != null) {
                                    blockStreet.getStockManager().buyStock(player, symbol, quantity);
                                    final double newVolume = stock.getVolume() + 1;
                                    stock.setVolume(newVolume);
                                    blockStreet.getStockSystem().setVolume(symbol, newVolume);
                                } else {
                                    Logger.sendMessage(player, ChatColor.RED, "This stock doesnt exist?");
                                    return false;
                                }
                            } else {
                                Logger.sendMessage(player, ChatColor.RED, "That company does not exist!");
                                return false;
                            }
                        }
                    }
                    break;
                case "sell":
                case "trade":
                    if (args.length == 3) {
                        if(commandSender.hasPermission("blockstreet.sell")) {

                            if(!NumberUtils.isNumber(args[2])) {
                                Logger.sendMessage(player, ChatColor.RED, "Invalid Quantity amount!");
                                return false;
                            }

                            final String symbol = args[1];
                            final int quantity = Integer.parseInt(args[2]);

                            if(quantity <= 0) {
                                Logger.sendMessage(player, ChatColor.RED, "Invalid Quantity!");
                                return false;
                            }

                            blockStreet.getStockManager().sellStock(player, symbol, quantity);
                        }
                    }
                    break;
            }
            return true;
        }

        return false;
    }
}
