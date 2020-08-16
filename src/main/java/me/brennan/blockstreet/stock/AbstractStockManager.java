package me.brennan.blockstreet.stock;

import me.brennan.blockstreetapi.stock.Stock;
import me.brennan.blockstreetapi.stock.StockData;
import me.brennan.blockstreetapi.stock.StockManager;

import java.util.LinkedList;
import java.util.List;

/**
 * made for BlockStreet
 *
 * @author Brennan
 * @since 8/15/2020
 **/
public class AbstractStockManager implements StockManager {
    private final List<StockData> stockData = new LinkedList<>();

    @Override
    public List<StockData> getData() {
        return stockData;
    }

    @Override
    public List<StockData> getStocksData(String... symbols) {
        return null;
    }
    @Override
    public StockData getStockData(String s) {
        return null;
    }

    @Override
    public List<StockData> getStocksData(List<String> list) {
        return null;
    }

    @Override
    public boolean saveStockData(StockData stockData) {
        return false;
    }

    @Override
    public boolean isTradable(String s) {
        return false;
    }

    @Override
    public void add(StockData stockData) {
        getData().add(stockData);
    }

    @Override
    public void remove(StockData stockData) {
        getData().remove(stockData);
    }
}