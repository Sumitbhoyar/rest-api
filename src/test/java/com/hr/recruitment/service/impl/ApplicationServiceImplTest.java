package com.hr.recruitment.service.impl;

import com.hr.recruitment.domain.Application;
import com.hr.recruitment.repository.ApplicationRepository;
import com.hr.recruitment.service.ApplicationService;
import com.hr.recruitment.web.rest.dto.ApplicationDTO;
import com.hr.recruitment.web.rest.mapper.ApplicationMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.internal.verification.VerificationModeFactory;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;
import java.util.Random;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ApplicationServiceImplTest {
    public static final long Application_ID = new Random().nextLong();
    public static final long Offer_ID = new Random().nextLong();
    @Mock
    private ApplicationRepository applicationRepository;
    @Mock
    private ApplicationMapper applicationMapper;

    private ApplicationService applicationService;

    @Before
    public void setUp() throws Exception {
        applicationService = new ApplicationServiceImpl(applicationRepository, applicationMapper);
    }

    @Test
    public void savesApplication() throws Exception {
        ApplicationDTO applicationDTO = new ApplicationDTO();
        Application Application = new Application();
        when(applicationMapper.toEntity(applicationDTO, Offer_ID)).thenReturn(Application);
        when(applicationRepository.save(Application)).thenReturn(Application);
        when(applicationMapper.toDto(Application)).thenReturn(applicationDTO);

        ApplicationDTO actualDTO = applicationService.save(Offer_ID, applicationDTO);

        assertThat(actualDTO, is(equalTo(applicationDTO)));
    }

    @Test
    public void findsAllApplications() throws Exception {
        Application Application = new Application();
        Page<Application> Applications = new PageImpl<>(Arrays.asList(Application));
        Pageable pageable = new PageRequest(1, 1);
        when(applicationRepository.findByOffer(pageable, Offer_ID)).thenReturn(Applications);
        ApplicationDTO ApplicationDTO = new ApplicationDTO();
        when(applicationMapper.toDto(Application)).thenReturn(ApplicationDTO);

        Page<ApplicationDTO> applicationDTOS = applicationService.findAll(pageable, Offer_ID);

        assertThat(applicationDTOS.getContent(), hasItem(ApplicationDTO));
        assertThat(applicationDTOS.getContent(), hasSize(1));
    }

    @Test
    public void findsAllApplicationsWhenNoApplicationsAvailable() throws Exception {
        Pageable pageable = new PageRequest(1, 1);
        when(applicationRepository.findAll(pageable)).thenReturn(null);

        Page<ApplicationDTO> ApplicationDTOS = applicationService.findAll(pageable, Offer_ID);

        assertThat(ApplicationDTOS, is(nullValue()));
    }

    @Test
    public void findsOneApplication() throws Exception {
        Application Application = new Application();
        when(applicationRepository.findOne(Application_ID)).thenReturn(Application);
        ApplicationDTO ApplicationDTO = new ApplicationDTO();
        when(applicationMapper.toDto(Application)).thenReturn(ApplicationDTO);

        ApplicationDTO actual = applicationService.findOne(Application_ID);

        assertThat(actual, is(equalTo(ApplicationDTO)));
    }

    @Test
    public void findsOneApplicationWhenNoApplicationAvailable() throws Exception {
        when(applicationRepository.findOne(Application_ID)).thenReturn(null);

        ApplicationDTO actual = applicationService.findOne(Application_ID);

        assertThat(actual, is(nullValue()));
    }

    @Test
    public void deletesApplication() throws Exception {
        applicationService.delete(Application_ID);

        verify(applicationRepository, VerificationModeFactory.times(1)).delete(Application_ID);
    }

}