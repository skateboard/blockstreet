package me.brennan.blockstreet.stock;

import me.brennan.blockstreet.BlockStreet;
import me.brennan.blockstreet.stock.object.Stock;
import me.brennan.blockstreet.transaction.object.Transaction;
import me.brennan.blockstreet.util.Logger;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.economy.EconomyResponse;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

/**
 * made for BlockStreet
 *
 * @author Brennan
 * @since 8/7/2020
 **/
public class StockManager {
    private final List<Stock> stocks = new LinkedList<>();

    private final BlockStreet blockStreet;
    private Economy economy;

    public StockManager(BlockStreet blockStreet) {
        this.blockStreet = blockStreet;
        if(blockStreet.getEconomy() == null) {
            System.out.println("[BlockStreet] ERROR Vault Economy not setup! Cannot work without.");
            return;
        }

        this.economy = blockStreet.getEconomy();
    }

    public Stock getStock(String symbol) {
        for(Stock stock : getStocks()) {
            if(stock.getSymbol().equalsIgnoreCase(symbol))
                return stock;
        }

        return null;
    }

    public boolean buyStock(Player player, String symbol, int amount) {
        final Stock stock = getStock(symbol);

        if(stock != null) {
            final int priceEach = (int) stock.getPrice();
            Logger.sendMessage(player, ChatColor.GREEN, String.format("You wish to buy %s for %s at %s a piece.", symbol, amount, priceEach));

            final int finalPrice = amount * priceEach;

            if(economy.getBalance(player) >= finalPrice) {
                final EconomyResponse withdrawResponse = economy.withdrawPlayer(player, finalPrice);

                if(withdrawResponse.transactionSuccess()) {
                    blockStreet.getStockSystem().addOwner(player, symbol, amount, priceEach, finalPrice);

                    blockStreet.getTransactionManager().createTransaction(new Transaction(player.getUniqueId(), UUID.randomUUID(), symbol, new Date().toString(), "BUYING", amount, finalPrice));

                    Logger.sendMessage(player, ChatColor.GREEN, String.format("You successfully purchased %s for %s and now have a balance of %s",
                            symbol,
                            economy.format(withdrawResponse.amount),
                            economy.format(withdrawResponse.balance)));

                } else {
                    Logger.sendMessage(player, ChatColor.RED, String.format("An error has occurred %s", withdrawResponse.errorMessage));
                    return false;
                }
            } else {
                Logger.sendMessage(player, ChatColor.RED, "You don't have enough money for this order!");
                return false;
            }
        } else {
            Logger.sendMessage(player, ChatColor.RED, "That is not a stock!");
        }

        return true;
    }

    public boolean sellStock(Player player, String symbol, int amount) {
        final Stock stock = getStock(symbol);

        if(stock != null) {
            final int amountOfStock = blockStreet.getStockSystem().getPlayersStockAmount(player, symbol);
            final double priceEach = stock.getPrice();

            player.sendMessage(String.format("You want to sell %s for %s and sell %s this much", symbol, amount, priceEach));

            final double finalPrice = amountOfStock * priceEach;

            if(amount >= amountOfStock) {
                final EconomyResponse depositResponse = economy.depositPlayer(player, finalPrice);

                if(depositResponse.transactionSuccess()) {
                    blockStreet.getStockSystem().removeCertainAmountOfStocks(player, symbol, amount);

                    blockStreet.getTransactionManager().createTransaction(new Transaction(player.getUniqueId(), UUID.randomUUID(), symbol, new Date().toString(), "SELLING", amount, finalPrice));

                    Logger.sendMessage(player, ChatColor.GREEN, String.format("You successfully sold %s for %s", symbol, finalPrice));
                } else {
                    Logger.sendMessage(player, ChatColor.RED, String.format("An error has occurred %s", depositResponse.errorMessage));
                    return false;
                }

                Logger.sendMessage(player, ChatColor.GREEN, String.format("You know have %s amount of %s", amountOfStock, symbol));
                return true;
            } else {
                Logger.sendMessage(player, ChatColor.RED, "You dont have this much stock!");
                return false;
            }
        } else {
            Logger.sendMessage(player, ChatColor.RED, "Invalid Stock!.");
        }

        return false;
    }

    public List<Stock> getStocks() {
        return stocks;
    }
}
