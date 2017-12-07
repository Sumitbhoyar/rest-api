package com.hr.recruitment.web.rest;

import com.hr.recruitment.service.ApplicationService;
import com.hr.recruitment.web.rest.dto.ApplicationDTO;
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

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing ApplicationDTO.
 */
@RestController
@RequestMapping("/api")
public class ApplicationResource {

    private static final String ENTITY_NAME = "Application";
    private final Logger log = LoggerFactory.getLogger(ApplicationResource.class);
    private final ApplicationService applicationService;

    public ApplicationResource(ApplicationService applicationService) {
        this.applicationService = applicationService;
    }

    /**
     * POST  /offers/{offerId}/applications : Create a new Application.
     *
     * @param offerId     the id of the Application to retrieve
     * @param application the Application to create
     * @return the ResponseEntity with status 201 (Created) and with body the new Application, or with status 400 (Bad Request) if the Application has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @ApiOperation(value = "Create a new Application")
    @PostMapping("/offers/{offerId}/applications")
    public ResponseEntity<ApplicationDTO> createApplication(@PathVariable Long offerId, @Valid @RequestBody ApplicationDTO application) throws URISyntaxException {
        log.debug("REST request to save ApplicationDTO : {}", application);
        if (application.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new ApplicationDTO cannot already have an ID")).body(null);
        }
        ApplicationDTO result = applicationService.save(offerId, application);
        System.out.println("Application Created:" + result);
        return ResponseEntity.created(new URI("/api/" + offerId + "/applications/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
                .body(result);
    }

    /**
     * PUT  /Applications : Updates an existing ApplicationDTO.
     *
     * @param offerId     the id of the Application to retrieve
     * @param application the Application to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated Application,
     * or with status 400 (Bad Request) if the Application is not valid,
     * or with status 500 (Internal Server Error) if the Application couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/offers/{offerId}/applications")
    public ResponseEntity<ApplicationDTO> updateApplication(@PathVariable Long offerId, @Valid @RequestBody ApplicationDTO application) throws URISyntaxException {
        log.debug("REST request to update ApplicationDTO : {}", application);
        if (application.getId() == null) {
            return createApplication(offerId, application);
        }
        ApplicationDTO result = applicationService.save(offerId, application);
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, application.getId().toString()))
                .body(result);
    }

    /**
     * GET  /offers/{offerId}/applications : get all the Applications for offer.
     *
     * @param offerId  the id of the Application to retrieve
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of ApplicationDTOs in body
     */
    @GetMapping("/offers/{offerId}/applications")
    public ResponseEntity<List<ApplicationDTO>> getAllApplications(@PathVariable Long offerId, @ApiParam Pageable pageable) {
        log.debug("REST request to get a page of ApplicationDTOs");
        Page<ApplicationDTO> page = applicationService.findAll(pageable, offerId);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/offers/" + offerId + "/applications");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /offers/{offerId}/applications/{applicationId} : get the "applicationId" Application and offer id .
     *
     * @param offerId       the id of the Application to retrieve
     * @param applicationId the id of the Application to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the Application, or with status 404 (Not Found)
     */
    @GetMapping("/offers/{offerId}/Applications/{applicationId}")
    public ResponseEntity<ApplicationDTO> getApplication(@PathVariable Long offerId, @PathVariable Long applicationId) {
        log.debug("REST request to get Application : {}", applicationId);
        ApplicationDTO applicationDTO = applicationService.findOne(applicationId);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(applicationDTO));
    }

    /**
     * DELETE  /Applications/:id : delete the "id" ApplicationDTO.
     *
     * @param offerId       the id of the Application to retrieve
     * @param applicationId the id of the Application to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/offers/{offerId}/applications/{applicationId}")
    public ResponseEntity<Void> deleteApplication(@PathVariable Long offerId, @PathVariable Long applicationId) {
        log.debug("REST request to delete ApplicationDTO : {}", applicationId);
        applicationService.delete(applicationId);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, applicationId.toString())).build();
    }

}
