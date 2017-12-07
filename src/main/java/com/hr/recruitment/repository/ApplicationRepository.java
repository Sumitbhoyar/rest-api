package com.hr.recruitment.repository;

import com.hr.recruitment.domain.Application;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


/**
 * Spring Data JPA repository for the Application entity.
 */
@Repository
public interface ApplicationRepository extends JpaRepository<Application, Long> {
    @Query("select appl from Application appl where appl.offer.id = ?1")
    Page<Application> findByOffer(Pageable pageable, Long offerId);
}
