package me.brennan.blockstreet.stock.object;

/**
 * made for BlockStreet
 *
 * @author Brennan
 * @since 8/9/2020
 **/
public class Stock {
    private final String symbol, companyName;
    private final int id;
    private final double price;
    private double volume;

    public Stock(int id, String symbol, String companyName, double price, double volume) {
        this.symbol = symbol;
        this.companyName = companyName;
        this.id = id;
        this.price = price;
        this.volume = volume;
    }

    public int getId() {
        return id;
    }

    public double getPrice() {
        return price;
    }

    public String getCompanyName() {
        return companyName;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setVolume(double volume) {
        this.volume = volume;
    }

    public double getVolume() {
        return volume;
    }
}
