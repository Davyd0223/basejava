package com.unise.webapp.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import java.io.Serial;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

@XmlAccessorType(XmlAccessType.FIELD)
public class Company implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private Organization organization;
    private List<Period> periods;

    public Company() {
    }

    public Company(Organization organization, List<Period> periods) {
        Objects.requireNonNull(organization, "organization must not be null");
        this.organization = organization;
        this.periods = periods;
    }

    public List<Period> getPeriods() {
        return periods;
    }

    public Organization getOrganization() {
        return organization;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Company company = (Company) o;
        return Objects.equals(organization, company.organization) &&
                Objects.equals(periods, company.periods);
    }

    @Override
    public int hashCode() {
        return Objects.hash(organization, periods);
    }

    @Override
    public String toString() {
        return "Company{" +
                "organization=" + organization +
                ", periods=" + periods +
                '}';
    }
}
