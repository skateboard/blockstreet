package me.brennan.blockstreet.company;

import me.brennan.blockstreetapi.company.CompanyData;
import me.brennan.blockstreetapi.company.WageType;

import java.util.UUID;

/**
 * made for BlockStreet
 *
 * @author Brennan
 * @since 8/15/2020
 **/
public class AbstractCompanyData implements CompanyData {
    private final String name, symbol;
    private final UUID foundersUUID;
    private final double bankBalance;
    private final boolean open;
    private final WageType wageType;

    public AbstractCompanyData(String name, String symbol, WageType wageType, UUID foundersUUID, double bankBalance, boolean open) {
        this.name = name;
        this.symbol = symbol;
        this.wageType = wageType;
        this.foundersUUID = foundersUUID;
        this.bankBalance = bankBalance;
        this.open = open;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getSymbol() {
        return symbol;
    }

    @Override
    public WageType getWageType() {
        return wageType;
    }

    @Override
    public double getBankBalance() {
        return bankBalance;
    }

    @Override
    public boolean isOpen() {
        return open;
    }

    @Override
    public UUID getFoundersUUID() {
        return foundersUUID;
    }
}
