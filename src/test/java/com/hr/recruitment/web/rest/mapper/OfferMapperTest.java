package com.hr.recruitment.web.rest.mapper;

import com.hr.recruitment.domain.Application;
import com.hr.recruitment.domain.Offer;
import com.hr.recruitment.web.rest.dto.OfferDTO;
import org.junit.Test;
import org.mockito.internal.util.collections.Sets;

import java.time.LocalDate;
import java.util.Random;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class OfferMapperTest {
    public static final String JOB_TITLE = "Dummy Job Title";
    public static final LocalDate START_DATE = LocalDate.now();
    public static final long OFFER_ID = new Random().nextLong();

    @Test
    public void populatesDTOFromEntity() throws Exception {
        Offer offer = new Offer();
        offer.setStartDate(START_DATE);
        offer.setJobTitle(JOB_TITLE);
        offer.setId(OFFER_ID);

        OfferDTO offerDTO = new OfferMapper().toDto(offer);

        assertThat(offerDTO.getId(), is(equalTo(offer.getId())));
        assertThat(offerDTO.getJobTitle(), is(equalTo(offer.getJobTitle())));
        assertThat(offerDTO.getStartDate(), is(equalTo(offer.getStartDate())));
    }

    @Test
    public void populatesNoOfApplicationsFromEntity() throws Exception {
        Offer offer = new Offer();
        offer.setApplications(Sets.newSet(new Application(), new Application(), new Application()));

        OfferDTO offerDTO = new OfferMapper().toDto(offer);

        assertThat(offerDTO.getNumberOfApplications(), is(equalTo(3)));
    }


    @Test
    public void populatesEntityFromDTO() throws Exception {
        OfferDTO offerDTO = new OfferDTO();
        offerDTO.setStartDate(START_DATE);
        offerDTO.setJobTitle(JOB_TITLE);

        Offer offer = new OfferMapper().toEntity(offerDTO);

        assertThat(offer.getJobTitle(), is(equalTo(offerDTO.getJobTitle())));
        assertThat(offer.getStartDate(), is(equalTo(offerDTO.getStartDate())));
    }

}