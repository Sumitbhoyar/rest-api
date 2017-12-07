package com.hr.recruitment.web.rest.dto;


import com.hr.recruitment.domain.enumeration.ApplicationStatus;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Objects;

/**
 * An Application.
 */
public class ApplicationDTO implements Serializable {

    private static final long serialVersionUID = 1L;
    private Long id;

    @NotNull
    @Size(min = 1, max = 100)
    private String candidateEmail;

    @Size(max = 5000)
    private String resumeText;

    @NotNull
    private ApplicationStatus applicationStatus;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCandidateEmail() {
        return candidateEmail;
    }

    public void setCandidateEmail(String candidateEmail) {
        this.candidateEmail = candidateEmail;
    }

    public String getResumeText() {
        return resumeText;
    }

    public void setResumeText(String resumeText) {
        this.resumeText = resumeText;
    }

    public ApplicationStatus getApplicationStatus() {
        return applicationStatus;
    }

    public void setApplicationStatus(ApplicationStatus applicationStatus) {
        this.applicationStatus = applicationStatus;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Application{" +
                "id=" + getId() +
                ", candidateEmail='" + getCandidateEmail() + "'" +
                ", resumeText='" + getResumeText() + "'" +
                ", applicationStatus='" + getApplicationStatus() + "'" +
                "}";
    }
}
