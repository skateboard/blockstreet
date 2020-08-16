package me.brennan.blockstreet.stock;

import me.brennan.blockstreetapi.stock.StockData;


/**
 * made for BlockStreet
 *
 * @author Brennan
 * @since 8/15/2020
 **/
public class AbstractStockData implements StockData {

    @Override
    public String getSymbol() {
        return null;
    }

    @Override
    public String getCompanyName() {
        return null;
    }

    @Override
    public double getOpenPrice() {
        return 0;
    }

    @Override
    public double getClosePrice() {
        return 0;
    }

    @Override
    public double getLatestPrice() {
        return 0;
    }

    @Override
    public double getHighestPrice() {
        return 0;
    }

    @Override
    public double getLowestPrice() {
        return 0;
    }

    @Override
    public float getVolume() {
        return 0;
    }
}
