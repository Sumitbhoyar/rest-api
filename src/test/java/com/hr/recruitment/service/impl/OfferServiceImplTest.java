package com.hr.recruitment.service.impl;

import com.hr.recruitment.domain.Offer;
import com.hr.recruitment.repository.OfferRepository;
import com.hr.recruitment.service.OfferService;
import com.hr.recruitment.web.rest.dto.OfferDTO;
import com.hr.recruitment.web.rest.mapper.OfferMapper;
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
public class OfferServiceImplTest {
    public static final long OFFER_ID = new Random().nextLong();
    @Mock
    private OfferRepository offerRepository;
    @Mock
    private OfferMapper offerMapper;

    private OfferService offerService;

    @Before
    public void setUp() throws Exception {
        offerService = new OfferServiceImpl(offerRepository, offerMapper);
    }

    @Test
    public void savesOffer() throws Exception {
        OfferDTO offerDTO = new OfferDTO();
        Offer offer = new Offer();
        when(offerMapper.toEntity(offerDTO)).thenReturn(offer);
        when(offerRepository.save(offer)).thenReturn(offer);
        when(offerMapper.toDto(offer)).thenReturn(offerDTO);

        OfferDTO actualDTO = offerService.save(offerDTO);

        assertThat(actualDTO, is(equalTo(offerDTO)));
    }

    @Test
    public void findsAllOffers() throws Exception {
        Offer offer = new Offer();
        Page<Offer> offers = new PageImpl<>(Arrays.asList(offer));
        Pageable pageable = new PageRequest(1, 1);
        when(offerRepository.findAll(pageable)).thenReturn(offers);
        OfferDTO offerDTO = new OfferDTO();
        when(offerMapper.toDto(offer)).thenReturn(offerDTO);

        Page<OfferDTO> offerDTOS = offerService.findAll(pageable);

        assertThat(offerDTOS.getContent(), hasItem(offerDTO));
        assertThat(offerDTOS.getContent(), hasSize(1));
    }

    @Test
    public void findsAllOffersWhenNoOffersAvailable() throws Exception {
        Pageable pageable = new PageRequest(1, 1);
        when(offerRepository.findAll(pageable)).thenReturn(null);

        Page<OfferDTO> offerDTOS = offerService.findAll(pageable);

        assertThat(offerDTOS, is(nullValue()));
    }

    @Test
    public void findsOneOffer() throws Exception {
        Offer offer = new Offer();
        when(offerRepository.findOne(OFFER_ID)).thenReturn(offer);
        OfferDTO offerDTO = new OfferDTO();
        when(offerMapper.toDto(offer)).thenReturn(offerDTO);

        OfferDTO actual = offerService.findOne(OFFER_ID);

        assertThat(actual, is(equalTo(offerDTO)));
    }

    @Test
    public void findsOneOfferWhenNoOfferAvailable() throws Exception {
        when(offerRepository.findOne(OFFER_ID)).thenReturn(null);

        OfferDTO actual = offerService.findOne(OFFER_ID);

        assertThat(actual, is(nullValue()));
    }

    @Test
    public void deletesOffer() throws Exception {
        offerService.delete(OFFER_ID);

        verify(offerRepository, VerificationModeFactory.times(1)).delete(OFFER_ID);
    }

}