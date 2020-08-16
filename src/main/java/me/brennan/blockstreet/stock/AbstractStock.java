package me.brennan.blockstreet.stock;

import me.brennan.blockstreetapi.stock.Stock;

import java.util.UUID;

/**
 * made for BlockStreet
 *
 * @author Brennan
 * @since 8/15/2020
 **/
public class AbstractStock implements Stock {
    private final String symbol;
    private final UUID uuid;
    private int shares;

    public AbstractStock(String symbol, UUID uuid, int shares) {
        this.symbol = symbol;
        this.uuid = uuid;
        this.shares = shares;
    }

    @Override
    public String getSymbol() {
        return symbol;
    }

    @Override
    public UUID getOwner() {
        return uuid;
    }

    @Override
    public int getShares() {
        return shares;
    }

    @Override
    public void setShares(int shares) {
        this.shares = shares;
    }
}
