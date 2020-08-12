package me.brennan.blockstreet.employment;

import me.brennan.blockstreet.employment.object.Employment;

import java.util.LinkedList;
import java.util.List;

/**
 * made for BlockStreet
 *
 * @author Brennan
 * @since 8/12/2020
 **/
public class EmploymentManager {
    private final List<Employment> employments = new LinkedList<>();

    public Employment getEmployment(String uuid) {
        for(Employment employment : getEmployments()) {
            if(employment.getUuid().toString().equalsIgnoreCase(uuid))
                return employment;
        }

        return null;
    }

    public void add(Employment employment) {
        this.employments.add(employment);
    }

    public List<Employment> getEmployments() {
        return employments;
    }
}
