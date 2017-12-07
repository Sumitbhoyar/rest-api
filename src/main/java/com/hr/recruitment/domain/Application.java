package com.hr.recruitment.domain;


import com.hr.recruitment.domain.enumeration.ApplicationStatus;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Objects;

/**
 * An Application.
 */
@Entity
@Table(name = "application", uniqueConstraints = @UniqueConstraint(columnNames = {"offer_id", "candidate_email"}))
public class Application implements Serializable {
    private static final String EMAIL_PATTERN =
            "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                    + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 1, max = 100)
    @Pattern(regexp = EMAIL_PATTERN)
    @Column(name = "candidate_email", length = 100, nullable = false)
    private String candidateEmail;

    @Size(max = 5000)
    @Column(name = "resume_text", length = 5000)
    private String resumeText;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "application_status", nullable = false)
    private ApplicationStatus applicationStatus;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @NotNull
    private Offer offer;

    @PostUpdate
    private void afterStatusChange() {
        System.out.println("Status is changed");
    }

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

    public Offer getOffer() {
        return offer;
    }

    public void setOffer(Offer offer) {
        this.offer = offer;
    }

    public Application offer(Offer offer) {
        this.offer = offer;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Application application = (Application) o;
        if (application.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), application.getId());
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
