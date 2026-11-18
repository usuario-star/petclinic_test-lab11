package com.tecsup.petclinic.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO de reporte para cantidad de mascotas por tipo activo.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TypePetCountDTO {

    private String typeName;
    private long petCount;
}
