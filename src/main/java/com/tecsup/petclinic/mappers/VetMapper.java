package com.tecsup.petclinic.mappers;

import com.tecsup.petclinic.dtos.VetDTO;
import com.tecsup.petclinic.entities.Vet;
import org.mapstruct.Mapper;

/**
 * Mapper MapStruct entre Vet y VetDTO.
 */
@Mapper(componentModel = "spring")
public interface VetMapper {

    Vet mapToEntity(VetDTO dto);

    VetDTO mapToDto(Vet entity);
}
