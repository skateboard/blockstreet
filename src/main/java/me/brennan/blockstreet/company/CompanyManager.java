package me.brennan.blockstreet.company;

import java.util.LinkedList;
import java.util.List;

/**
 * made for BlockStreet
 *
 * @author Brennan
 * @since 8/9/2020
 **/
public class CompanyManager {
    private final List<Company> companies = new LinkedList<>();

    public Company getCompany(String name) {
        for(Company company : companies) {
            if(company.getName().equalsIgnoreCase(name))
                return company;
        }

        return null;
    }

    public Company getCompanyBySymbol(String symbol) {
        for(Company company : companies) {
            if(company.getSymbol().equalsIgnoreCase(symbol))
                return company;
        }

        return null;
    }

    public void add(Company company) {
        this.companies.add(company);
    }

    public void remove(Company company) {
        this.companies.remove(company);
    }

    public List<Company> getCompanies() {
        return companies;
    }
}
