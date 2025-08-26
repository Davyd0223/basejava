package com.unise.webapp.model;

import java.io.Serial;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class CompanySection extends Section {
    @Serial
    private static final long serialVersionUID = 1L;

    private final List<Company> company;

    public CompanySection(Company... company){
        this(Arrays.asList(company));
    }

    public CompanySection(List<Company> company) {
        Objects.requireNonNull(company,"Company must not be null");
        this.company = company;
    }

    public List<Company> getCompany() {
        return company;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CompanySection that = (CompanySection) o;
        return Objects.equals(company, that.company);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(company);
    }

    @Override
    public String toString() {
        return "CompanySection{" +
                "company=" + company +
                '}';
    }
}
