package me.brennan.blockstreet.player;

import me.brennan.blockstreet.stock.AbstractStock;
import me.brennan.blockstreetapi.player.PlayerData;
import me.brennan.blockstreetapi.stock.Stock;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

/**
 * made for BlockStreet
 *
 * @author Brennan
 * @since 8/15/2020
 **/
public class AbstractPlayerData implements PlayerData {
    private final UUID uuid;
    private final String name;
    private Date lastLogin;

    private final List<Stock> stocks = new LinkedList<>();

    public AbstractPlayerData(UUID uuid, String name, Date lastLogin) {
        this.uuid = uuid;
        this.name = name;
        this.lastLogin = lastLogin;
    }

    @Override
    public UUID getUUID() {
        return uuid;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Date getLastLogin() {
        return lastLogin;
    }

    @Override
    public List<Stock> getStocks() {
        return stocks;
    }

    @Override
    public Stock addStock(String symbol, int shares) {
        final Stock stock = new AbstractStock(symbol, getUUID(), shares);
        stocks.add(stock);

        return stock;
    }

    @Override
    public Stock removeStock(String symbol) {
        return null;
    }

    @Override
    public Stock removeStock(String symbol, int shares) {
        return null;
    }
}
