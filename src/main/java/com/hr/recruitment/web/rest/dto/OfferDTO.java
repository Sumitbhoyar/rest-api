package com.hr.recruitment.web.rest.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * An Offer.
 */
public class OfferDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    @NotNull
    @Size(min = 3, max = 100)
    private String jobTitle;

    @NotNull
    private LocalDate startDate;

    private Integer numberOfApplications;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public Integer getNumberOfApplications() {
        return numberOfApplications;
    }

    public void setNumberOfApplications(Integer numberOfApplications) {
        this.numberOfApplications = numberOfApplications;
    }

    @Override
    public String toString() {
        return "Offer{" +
                "id=" + getId() +
                ", jobTitle='" + getJobTitle() + "'" +
                ", startDate='" + getStartDate() + "'" +
                ", numberOfApplications='" + getNumberOfApplications() + "'" +
                "}";
    }
}
