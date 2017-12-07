package com.hr.recruitment.web.rest.mapper;

import com.hr.recruitment.domain.Offer;
import com.hr.recruitment.web.rest.dto.OfferDTO;
import org.springframework.stereotype.Component;

/**
 * Mapper for the entity Offer and its DTO OfferDTO.
 */
@Component
public class OfferMapper {

    public OfferDTO toDto(Offer offer) {
        OfferDTO offerDTO = new OfferDTO();
        offerDTO.setId(offer.getId());
        offerDTO.setJobTitle(offer.getJobTitle());
        offerDTO.setStartDate(offer.getStartDate());
        offerDTO.setNumberOfApplications(offer.getApplications().size());
        return offerDTO;
    }

    public Offer toEntity(OfferDTO offerDTO) {
        Offer offer = new Offer();
        offer.setJobTitle(offerDTO.getJobTitle());
        offer.setStartDate(offerDTO.getStartDate());
        return offer;
    }
}
