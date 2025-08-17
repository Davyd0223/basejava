package com.unise.webapp.model;

import java.util.List;
import java.util.Objects;

public class Company {

    private final String nameCompany;
    private final String description;
    private final List<Period> periods;

    public Company(String nameCompany, String description, List<Period> periods) {
        this.nameCompany = nameCompany;
        this.description = description;
        this.periods = periods;
    }

    public String getNameCompany() {
        return nameCompany;
    }

    public String getDescription() {
        return description;
    }

    public List<Period> getPeriods() {
        return periods;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Company company = (Company) o;
        return Objects.equals(nameCompany, company.nameCompany) && Objects.equals(description, company.description) && Objects.equals(periods, company.periods);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nameCompany, description, periods);
    }

    @Override
    public String toString() {
        return "Company{" +
                "nameCompany='" + nameCompany + '\'' +
                ", description='" + description + '\'' +
                ", periods=" + periods +
                '}';
    }
}
