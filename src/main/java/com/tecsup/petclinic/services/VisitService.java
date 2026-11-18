package com.tecsup.petclinic.services;

import com.tecsup.petclinic.dtos.VisitDTO;
import com.tecsup.petclinic.entities.Visit;
import com.tecsup.petclinic.exceptions.VisitNotFoundException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/**
 * Contrato de servicio para la gestion de visitas veterinarias.
 */
public interface VisitService {

    VisitDTO create(VisitDTO visitDTO);

    VisitDTO update(VisitDTO visitDTO);

    void delete(Integer id) throws VisitNotFoundException;

    VisitDTO findById(Integer id) throws VisitNotFoundException;

    List<VisitDTO> findByPetId(Integer petId);

    List<VisitDTO> findByVetId(Integer vetId);

    List<VisitDTO> findByDateBetween(LocalDate startDate, LocalDate endDate);

    List<VisitDTO> findByVetIdAndDateBetween(Integer vetId, LocalDate startDate, LocalDate endDate);

    BigDecimal calculateTotalCostByPet(Integer petId);

    long countVisitsByPet(Integer petId);

    List<Visit> findAll();
}
