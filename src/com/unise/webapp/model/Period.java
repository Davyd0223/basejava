package com.unise.webapp.model;

import com.unise.webapp.util.LocalDateAdapter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.io.Serial;
import java.io.Serializable;
import java.time.YearMonth;
import java.util.Objects;

@XmlAccessorType(XmlAccessType.FIELD)
public class Period implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private String description;
    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    private YearMonth startDate;
    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    private YearMonth endDate;

    public Period() {
    }

    public Period(String description, YearMonth startDate, YearMonth endDate) {
        Objects.requireNonNull(startDate, "startDate must nor be null");
        Objects.requireNonNull(endDate, "endDate must nor be null");
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public String getDescription() {
        return description;
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
        return Objects.equals(description, period.description) && Objects.equals(startDate, period.startDate) && Objects.equals(endDate, period.endDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), description, startDate, endDate);
    }

    @Override
    public String toString() {
        return "Period{" +
                "description='" + description + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                '}';
    }
}
