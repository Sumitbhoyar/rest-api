package com.hr.recruitment.service;

import com.hr.recruitment.web.rest.dto.ApplicationDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing Application.
 */
public interface ApplicationService {

    /**
     * Save a application.
     *
     * @param offerId
     * @param applicationDTO the entity to save
     * @return the persisted entity
     */
    ApplicationDTO save(Long offerId, ApplicationDTO applicationDTO);

    /**
     * Get all the applications.
     *
     * @param pageable the pagination information
     * @param offerId
     * @return the list of entities
     */
    Page<ApplicationDTO> findAll(Pageable pageable, Long offerId);

    /**
     * Get the "id" application.
     *
     * @param id the id of the entity
     * @return the entity
     */
    ApplicationDTO findOne(Long id);

    /**
     * Delete the "id" application.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
