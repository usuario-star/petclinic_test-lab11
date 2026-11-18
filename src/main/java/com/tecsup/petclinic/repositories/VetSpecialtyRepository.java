package com.tecsup.petclinic.repositories;

import com.tecsup.petclinic.entities.VetSpecialty;
import com.tecsup.petclinic.entities.VetSpecialtyId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repositorio JPA para la entidad VetSpecialty.
 */
@Repository
public interface VetSpecialtyRepository extends JpaRepository<VetSpecialty, VetSpecialtyId> {

    List<VetSpecialty> findByVetId(Integer vetId);

    List<VetSpecialty> findBySpecialtyId(Integer specialtyId);

    List<VetSpecialty> findBySpecialtyIdAndYearsExperienceGreaterThanEqual(Integer specialtyId, Integer minYears);

    @Override
    List<VetSpecialty> findAll();
}
