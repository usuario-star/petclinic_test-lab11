package com.tecsup.petclinic.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO de transferencia para especialidades.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SpecialtyDTO {

    private Integer id;
    private String name;
    private String office;
    private Integer openHour;
    private Integer closeHour;
}
