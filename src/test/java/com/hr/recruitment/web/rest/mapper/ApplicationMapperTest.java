package com.hr.recruitment.web.rest.mapper;

import com.hr.recruitment.domain.Application;
import com.hr.recruitment.domain.enumeration.ApplicationStatus;
import com.hr.recruitment.web.rest.dto.ApplicationDTO;
import org.junit.Before;
import org.junit.Test;

import java.util.Random;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class ApplicationMapperTest {
    public static final String CANDIDATE_EMAIL = "Random@email.com";
    public static final String RESUME_TEXT = "This is details about candidate";
    public static final long OFFER_ID = new Random().nextLong();
    private Long APPLICATION_ID = new Random().nextLong();
    private ApplicationStatus STATUS = ApplicationStatus.values()[new Random().nextInt(3)];

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void populatesDtoFromEntity() throws Exception {
        Application application = new Application();
        application.setId(APPLICATION_ID);
        application.setApplicationStatus(STATUS);
        application.setCandidateEmail(CANDIDATE_EMAIL);
        application.setResumeText(RESUME_TEXT);

        ApplicationDTO applicationDTO = new ApplicationMapper().toDto(application);

        assertThat(applicationDTO.getId(), is(equalTo(application.getId())));
        assertThat(applicationDTO.getApplicationStatus(), is(equalTo(application.getApplicationStatus())));
        assertThat(applicationDTO.getCandidateEmail(), is(equalTo(application.getCandidateEmail())));
        assertThat(applicationDTO.getResumeText(), is(equalTo(application.getResumeText())));
    }

    @Test
    public void populatesEntityFromDto() throws Exception {
        ApplicationDTO applicationDTO = new ApplicationDTO();
        applicationDTO.setId(APPLICATION_ID);
        applicationDTO.setApplicationStatus(STATUS);
        applicationDTO.setCandidateEmail(CANDIDATE_EMAIL);
        applicationDTO.setResumeText(RESUME_TEXT);

        Application application = new ApplicationMapper().toEntity(applicationDTO, OFFER_ID);

        assertThat(application.getId(), is(equalTo(applicationDTO.getId())));
        assertThat(application.getApplicationStatus(), is(equalTo(applicationDTO.getApplicationStatus())));
        assertThat(application.getCandidateEmail(), is(equalTo(applicationDTO.getCandidateEmail())));
        assertThat(application.getResumeText(), is(equalTo(applicationDTO.getResumeText())));
        assertThat(application.getOffer().getId(), is(equalTo(OFFER_ID)));
    }

}