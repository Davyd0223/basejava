package com.unise.webapp.model;

import java.time.YearMonth;
import java.util.Objects;

public class Period {

    private final String website;
    private final String title;
    private final YearMonth startDate;
    private final YearMonth endDate;

    public Period(String title, String nameCompany, String website, String description, YearMonth startDate, YearMonth endDate) {
        this.title = title;
        this.website = website;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public String getTitle() {
        return title;
    }

    public String getWebsite() {
        return website;
    }

    public YearMonth getStartDate() {
        return startDate;
    }

    public YearMonth getEndDate() {
        return endDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Period period = (Period) o;
        return Objects.equals(title, period.title) && Objects.equals(website, period.website) && Objects.equals(startDate, period.startDate) && Objects.equals(endDate, period.endDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), title, website, startDate, endDate);
    }

    @Override
    public String toString() {
        return "Period{" +
                "website='" + website + '\'' +
                ", title='" + title + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                '}';
    }
}
