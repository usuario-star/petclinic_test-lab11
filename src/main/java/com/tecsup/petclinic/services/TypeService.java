package com.tecsup.petclinic.services;

import com.tecsup.petclinic.dtos.TypeDTO;
import com.tecsup.petclinic.dtos.TypePetCountDTO;
import com.tecsup.petclinic.entities.Type;
import com.tecsup.petclinic.exceptions.TypeNotFoundException;

import java.util.List;

/**
 * Contrato de servicio para la gestion de tipos de mascota.
 */
public interface TypeService {

    TypeDTO create(TypeDTO typeDTO);

    TypeDTO update(TypeDTO typeDTO);

    void delete(Integer id) throws TypeNotFoundException;

    TypeDTO findById(Integer id) throws TypeNotFoundException;

    List<TypeDTO> findByName(String name);

    List<TypeDTO> findByActive(Boolean active);

    List<TypePetCountDTO> getPetCountByType();

    List<Type> findAll();
}
