package com.tecsup.petclinic.services;

import com.tecsup.petclinic.dtos.VetSpecialtyDTO;
import com.tecsup.petclinic.exceptions.VetSpecialtyNotFoundException;

import java.time.LocalDate;
import java.util.List;

/**
 * Contrato de servicio para la gestion de relaciones veterinario-especialidad.
 */
public interface VetSpecialtyService {

    VetSpecialtyDTO assignSpecialtyToVet(VetSpecialtyDTO dto);

    void removeSpecialtyFromVet(Integer vetId, Integer specialtyId) throws VetSpecialtyNotFoundException;

    VetSpecialtyDTO findById(Integer vetId, Integer specialtyId) throws VetSpecialtyNotFoundException;

    List<VetSpecialtyDTO> findSpecialtiesByVet(Integer vetId);

    List<VetSpecialtyDTO> findVetsBySpecialty(Integer specialtyId);

    VetSpecialtyDTO setPrimarySpecialty(Integer vetId, Integer specialtyId) throws VetSpecialtyNotFoundException;

    List<VetSpecialtyDTO> findVetsByMinExperience(Integer specialtyId, Integer minYears);

    VetSpecialtyDTO updateYearsExperience(Integer vetId, Integer specialtyId, Integer years) throws VetSpecialtyNotFoundException;

    VetSpecialtyDTO updateCertificationDate(Integer vetId, Integer specialtyId, LocalDate certificationDate) throws VetSpecialtyNotFoundException;
}
