package me.brennan.blockstreet.company;

import me.brennan.blockstreetapi.company.CompanyData;
import me.brennan.blockstreetapi.company.CompanyManager;

import java.util.LinkedList;
import java.util.List;

/**
 * made for BlockStreet
 *
 * @author Brennan
 * @since 8/15/2020
 **/
public class AbstractCompanyManager implements CompanyManager {
    private final List<CompanyData> companyData = new LinkedList<>();

    @Override
    public List<CompanyData> getData() {
        return companyData;
    }

    @Override
    public CompanyData getCompanyData(String s) {
        return null;
    }

    @Override
    public CompanyData getCompanyDataByName(String s) {
        return null;
    }

    @Override
    public boolean saveCompanyData(CompanyData companyData) {
        return false;
    }

    @Override
    public void add(CompanyData companyData) {
        getData().add(companyData);
    }

    @Override
    public void remove(CompanyData companyData) {
        getData().remove(companyData);
    }
}
