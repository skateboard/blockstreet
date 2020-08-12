package me.brennan.blockstreet.company.object;

import java.util.UUID;

/**
 * made for BlockStreet
 *
 * @author Brennan
 * @since 8/9/2020
 **/
public class CEO {
    private UUID uuid;
    private String company;

    public CEO(UUID uuid, String company) {
        this.uuid = uuid;
        this.company = company;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public UUID getUUID() {
        return uuid;
    }

    public String getCompany() {
        return company;
    }
}
