package com.tecsup.petclinic.services;

import com.tecsup.petclinic.dtos.SpecialtyDTO;
import com.tecsup.petclinic.entities.Specialty;
import com.tecsup.petclinic.exceptions.SpecialtyNotFoundException;

import java.util.List;

/**
 * Contrato de servicio para la gestion de especialidades.
 */
public interface SpecialtyService {

    SpecialtyDTO create(SpecialtyDTO specialtyDTO);

    SpecialtyDTO update(SpecialtyDTO specialtyDTO);

    void delete(Integer id) throws SpecialtyNotFoundException;

    SpecialtyDTO findById(Integer id) throws SpecialtyNotFoundException;

    List<SpecialtyDTO> findByName(String name);

    List<SpecialtyDTO> findByOffice(String office);

    List<Specialty> findAll();
}
