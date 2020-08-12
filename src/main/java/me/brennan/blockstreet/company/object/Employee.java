package me.brennan.blockstreet.company.object;

import java.util.UUID;

/**
 * made for BlockStreet
 *
 * @author Brennan
 * @since 8/9/2020
 **/
public class Employee {
    private final UUID uuid;
    private final String company, role;

    public Employee(UUID uuid, String company, String role) {
        this.uuid = uuid;
        this.company = company;
        this.role = role;
    }

    public UUID getUuid() {
        return uuid;
    }

    public String getCompany() {
        return company;
    }

    public String getRole() {
        return role;
    }
}
