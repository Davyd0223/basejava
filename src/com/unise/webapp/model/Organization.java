package com.unise.webapp.model;

import java.util.Objects;

public class Organization {
    private final String name;
    private final String website;

    public Organization(String name, String website) {
        this.name = name;
        this.website = website;
    }

    public String getName() {
        return name;
    }

    public String getWebsite() {
        return website;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Organization)) return false;
        Organization that = (Organization) o;
        return Objects.equals(name, that.name) && Objects.equals(website, that.website);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, website);
    }

    @Override
    public String toString() {
        return "Organization{" +
                "name='" + name + '\'' +
                ", website='" + website + '\'' +
                '}';
    }
}
