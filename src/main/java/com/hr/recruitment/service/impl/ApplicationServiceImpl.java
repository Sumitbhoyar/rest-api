package com.hr.recruitment.service.impl;

import com.hr.recruitment.domain.Application;
import com.hr.recruitment.repository.ApplicationRepository;
import com.hr.recruitment.service.ApplicationService;
import com.hr.recruitment.web.rest.dto.ApplicationDTO;
import com.hr.recruitment.web.rest.mapper.ApplicationMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing Application.
 */
@Service
@Transactional
public class ApplicationServiceImpl implements ApplicationService {

    private final Logger log = LoggerFactory.getLogger(ApplicationServiceImpl.class);

    private final ApplicationRepository applicationRepository;
    private final ApplicationMapper applicationMapper;

    public ApplicationServiceImpl(ApplicationRepository applicationRepository, ApplicationMapper applicationMapper) {
        this.applicationRepository = applicationRepository;
        this.applicationMapper = applicationMapper;
    }

    /**
     * Save an application.
     *
     * @param offerId
     * @param applicationDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public ApplicationDTO save(Long offerId, ApplicationDTO applicationDTO) {
        log.debug("Request to save Application : {}", applicationDTO);
        Application entity = applicationMapper.toEntity(applicationDTO, offerId);
        Application application = applicationRepository.save(entity);
        return applicationMapper.toDto(application);
    }

    /**
     * Get all the applications.
     *
     * @param pageable the pagination information
     * @param offerId
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ApplicationDTO> findAll(Pageable pageable, Long offerId) {
        log.debug("Request to get all Applications");
        Page<Application> applications = applicationRepository.findByOffer(pageable, offerId);
        if (applications == null) return null;
        return applications.map(applicationMapper::toDto);
    }

    /**
     * Get one application by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public ApplicationDTO findOne(Long id) {
        log.debug("Request to get Application : {}", id);
        Application application = applicationRepository.findOne(id);
        if (application == null) return null;
        return applicationMapper.toDto(application);
    }

    /**
     * Delete the  application by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Application : {}", id);
        applicationRepository.delete(id);
    }
}
