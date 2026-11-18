package com.tecsup.petclinic.mappers;

import com.tecsup.petclinic.dtos.TypeDTO;
import com.tecsup.petclinic.entities.Type;
import org.mapstruct.Mapper;

/**
 * Mapper MapStruct entre Type y TypeDTO.
 */
@Mapper(componentModel = "spring")
public interface TypeMapper {

    Type mapToEntity(TypeDTO dto);

    TypeDTO mapToDto(Type entity);
}
