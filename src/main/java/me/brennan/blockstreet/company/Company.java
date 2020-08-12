package me.brennan.blockstreet.company;

import me.brennan.blockstreet.company.object.CEO;
import me.brennan.blockstreet.company.object.Employee;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

/**
 * made for BlockStreet
 *
 * @author Brennan
 * @since 8/9/2020
 **/
public class Company {
    private final String name, symbol;
    private double balance;
    private boolean open;
    private final UUID foundersUUID;

    private final CEO ceo;
    private final List<Employee> employees = new LinkedList<>();

    public Company(String name, String symbol, double balance, String open, UUID foundersUUID, CEO ceo) {
        this.name = name;
        this.symbol = symbol;
        this.balance = balance;
        this.ceo = ceo;
        this.foundersUUID = foundersUUID;

        this.open = open.equalsIgnoreCase("Yes");
    }

    public UUID getFoundersUUID() {
        return foundersUUID;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }

    public boolean isOpen() {
        return open;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public void addEmployee(Employee employee) {
        employees.add(employee);
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setCEOUUID(UUID uuid) {
        this.ceo.setUuid(uuid);
    }

    public boolean isCEO(UUID uuid) {
        return getCEO().getUUID().equals(uuid);
    }

    public CEO getCEO() {
        return ceo;
    }

    public String getSymbol() {
        return symbol;
    }

    public String getName() {
        return name;
    }

    public double getBalance() {
        return balance;
    }
}
