package me.brennan.blockstreet.employment.object;

import java.util.UUID;

/**
 * made for BlockStreet
 *
 * @author Brennan
 * @since 8/12/2020
 **/
public class Employment {
    private final UUID uuid;
    private final String company, role;

    public Employment(UUID uuid, String company, String role) {
        this.uuid = uuid;
        this.company = company;
        this.role = role;
    }

    public String getCompany() {
        return company;
    }

    public UUID getUuid() {
        return uuid;
    }

    public String getRole() {
        return role;
    }
}
