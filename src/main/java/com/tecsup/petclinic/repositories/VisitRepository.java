package com.tecsup.petclinic.repositories;

import com.tecsup.petclinic.entities.Visit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

/**
 * Repositorio JPA para la entidad Visit.
 */
@Repository
public interface VisitRepository extends JpaRepository<Visit, Integer> {

    List<Visit> findByPetId(Integer petId);

    List<Visit> findByVetId(Integer vetId);

    List<Visit> findByVisitDateBetween(LocalDate startDate, LocalDate endDate);

    List<Visit> findByVetIdAndVisitDateBetween(Integer vetId, LocalDate startDate, LocalDate endDate);

    long countByPetId(Integer petId);

    @Override
    List<Visit> findAll();
}
