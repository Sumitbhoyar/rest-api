package com.hr.recruitment.web.rest.mapper;

import com.hr.recruitment.domain.Application;
import com.hr.recruitment.domain.Offer;
import com.hr.recruitment.web.rest.dto.ApplicationDTO;
import org.springframework.stereotype.Component;

/**
 * Mapper for the entity Application and its DTO ApplicationDTO.
 */
@Component
public class ApplicationMapper {

    public ApplicationDTO toDto(Application application) {
        ApplicationDTO applicationDTO = new ApplicationDTO();
        applicationDTO.setId(application.getId());
        applicationDTO.setApplicationStatus(application.getApplicationStatus());
        applicationDTO.setCandidateEmail(application.getCandidateEmail());
        applicationDTO.setResumeText(application.getResumeText());
        return applicationDTO;
    }

    public Application toEntity(ApplicationDTO applicationDTO, Long offerId) {
        Application application = new Application();
        application.setId(applicationDTO.getId());
        application.setApplicationStatus(applicationDTO.getApplicationStatus());
        application.setCandidateEmail(applicationDTO.getCandidateEmail());
        application.setResumeText(applicationDTO.getResumeText());
        Offer offer = new Offer();
        offer.setId(offerId);
        application.setOffer(offer);
        return application;
    }
}
