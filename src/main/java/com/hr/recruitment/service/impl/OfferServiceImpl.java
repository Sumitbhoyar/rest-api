package com.hr.recruitment.service.impl;

import com.hr.recruitment.domain.Offer;
import com.hr.recruitment.repository.OfferRepository;
import com.hr.recruitment.service.OfferService;
import com.hr.recruitment.web.rest.dto.OfferDTO;
import com.hr.recruitment.web.rest.mapper.OfferMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing Offer.
 */
@Service
@Transactional
public class OfferServiceImpl implements OfferService {

    private final Logger log = LoggerFactory.getLogger(OfferServiceImpl.class);

    private final OfferRepository offerRepository;
    private final OfferMapper offerMapper;

    public OfferServiceImpl(OfferRepository offerRepository, OfferMapper offerMapper) {
        this.offerRepository = offerRepository;
        this.offerMapper = offerMapper;
    }

    /**
     * Save a offer.
     *
     * @param offerDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public OfferDTO save(OfferDTO offerDTO) {
        log.debug("Request to save Offer : {}", offerDTO);
        return offerMapper.toDto(offerRepository.save(offerMapper.toEntity(offerDTO)));
    }

    /**
     * Get all the offers.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<OfferDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Offers");
        Page<Offer> offers = offerRepository.findAll(pageable);
        if (offers == null) return null;
        return offers.map(offerMapper::toDto);
    }

    /**
     * Get one offer by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public OfferDTO findOne(Long id) {
        log.debug("Request to get Offer : {}", id);
        Offer offer = offerRepository.findOne(id);
        if (offer == null) return null;
        return offerMapper.toDto(offer);
    }

    /**
     * Delete the  offer by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Offer : {}", id);
        offerRepository.delete(id);
    }
}
