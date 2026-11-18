package com.tecsup.petclinic.services;

import com.tecsup.petclinic.dtos.VetDTO;
import com.tecsup.petclinic.entities.Vet;
import com.tecsup.petclinic.exceptions.VetNotFoundException;

import java.util.List;

/**
 * Contrato de servicio para la gestion de veterinarios.
 */
public interface VetService {

    VetDTO create(VetDTO vetDTO);

    VetDTO update(VetDTO vetDTO);

    void delete(Integer id) throws VetNotFoundException;

    VetDTO findById(Integer id) throws VetNotFoundException;

    List<VetDTO> findByFirstName(String firstName);

    List<VetDTO> findByLastName(String lastName);

    List<VetDTO> findActiveVets();

    List<VetDTO> findInactiveVets();

    VetDTO deactivate(Integer id) throws VetNotFoundException;

    VetDTO reactivate(Integer id) throws VetNotFoundException;

    List<Vet> findAll();
}
