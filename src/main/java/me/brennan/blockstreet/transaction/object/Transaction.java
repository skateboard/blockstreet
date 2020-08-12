package me.brennan.blockstreet.transaction.object;

import java.util.UUID;

/**
 * made for BlockStreet
 *
 * @author Brennan
 * @since 8/9/2020
 **/
public class Transaction {
    private int id;
    private final UUID ownerUUID, uuid;
    private final String symbol, date, type;
    private final int quantity;
    private final double total;

    public Transaction(int id, UUID ownerUUID, UUID uuid, String symbol, String date, String type, int quantity, double total) {
        this.id = id;
        this.ownerUUID = ownerUUID;
        this.uuid = uuid;
        this.symbol = symbol;
        this.date = date;
        this.type = type;
        this.quantity = quantity;
        this.total = total;
    }

    public Transaction(UUID ownerUUID, UUID uuid, String symbol, String date, String type, int quantity, double total) {
        this.ownerUUID = ownerUUID;
        this.uuid = uuid;
        this.symbol = symbol;
        this.date = date;
        this.type = type;
        this.quantity = quantity;
        this.total = total;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public UUID getUuid() {
        return uuid;
    }

    public UUID getOwnerUUID() {
        return ownerUUID;
    }

    public String getSymbol() {
        return symbol;
    }

    public String getDate() {
        return date;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getTotal() {
        return total;
    }
}
