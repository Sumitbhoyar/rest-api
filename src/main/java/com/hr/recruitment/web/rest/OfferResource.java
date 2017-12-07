package com.hr.recruitment.web.rest;

import com.hr.recruitment.service.OfferService;
import com.hr.recruitment.web.rest.dto.OfferDTO;
import com.hr.recruitment.web.rest.util.HeaderUtil;
import com.hr.recruitment.web.rest.util.PaginationUtil;
import com.hr.recruitment.web.rest.util.ResponseUtil;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Offer.
 */
@RestController
@RequestMapping("/api")
public class OfferResource {

    private static final String ENTITY_NAME = "Offer";
    private final Logger log = LoggerFactory.getLogger(OfferResource.class);
    private final OfferService offerService;

    public OfferResource(OfferService offerService) {
        this.offerService = offerService;
    }

    /**
     * POST  /offers : Create a new offer.
     *
     * @param offer the offer to create
     * @return the ResponseEntity with status 201 (Created) and with body the new offer, or with status 400 (Bad Request) if the offer has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @ApiOperation(value = "Create a new Offer",
            notes = "numberOfApplications is Calculated field. Input has no effect on created record.")
    @PostMapping("/offers")
    public ResponseEntity<OfferDTO> createOffer(@Valid @RequestBody OfferDTO offer) throws URISyntaxException {
        log.debug("REST request to save Offer : {}", offer);
        if (offer.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new offer cannot already have an ID")).body(null);
        }
        OfferDTO result = offerService.save(offer);
        return ResponseEntity.created(new URI("/api/offers/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
                .body(result);
    }

    /**
     * PUT  /offers : Updates an existing offer.
     *
     * @param offer the offer to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated offer,
     * or with status 400 (Bad Request) if the offer is not valid,
     * or with status 500 (Internal Server Error) if the offer couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/offers")
    public ResponseEntity<OfferDTO> updateOffer(@Valid @RequestBody OfferDTO offer) throws URISyntaxException {
        log.debug("REST request to update Offer : {}", offer);
        if (offer.getId() == null) {
            return createOffer(offer);
        }
        OfferDTO result = offerService.save(offer);
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, offer.getId().toString()))
                .body(result);
    }

    /**
     * GET  /offers : get all the offers.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of offers in body
     */
    @GetMapping("/offers")
    @Transactional
    public ResponseEntity<List<OfferDTO>> getAllOffers(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of Offers");
        Page<OfferDTO> page = offerService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/offers");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /offers/:id : get the "id" offer.
     *
     * @param id the id of the offer to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the offer, or with status 404 (Not Found)
     */
    @GetMapping("/offers/{id}")
    @Transactional
    public ResponseEntity<OfferDTO> getOffer(@PathVariable Long id) {
        log.debug("REST request to get Offer : {}", id);
        OfferDTO offer = offerService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(offer));
    }

    /**
     * DELETE  /offers/:id : delete the "id" offer.
     *
     * @param id the id of the offer to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/offers/{id}")
    public ResponseEntity<Void> deleteOffer(@PathVariable Long id) {
        log.debug("REST request to delete Offer : {}", id);
        offerService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
