package com.tecsup.petclinic.services;

import com.tecsup.petclinic.dtos.VetSpecialtyDTO;
import com.tecsup.petclinic.exceptions.VetSpecialtyAlreadyExistsException;
import com.tecsup.petclinic.exceptions.VetSpecialtyNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Pruebas unitarias con base de datos para VetSpecialtyService (Caso 6).
 */
@SpringBootTest
@Slf4j
public class VetSpecialtyServiceTest {

    @Autowired
    private VetSpecialtyService vetSpecialtyService;

    @Test
    public void testAssignSpecialtyToVet() {
        VetSpecialtyDTO dto = VetSpecialtyDTO.builder()
                .vetId(1)
                .specialtyId(1)
                .certificationDate(LocalDate.of(2025, 5, 10))
                .yearsExperience(4)
                .isPrimary(false)
                .notes("Radiology training completed")
                .build();

        VetSpecialtyDTO assigned = this.vetSpecialtyService.assignSpecialtyToVet(dto);
        log.info("VET SPECIALTY ASSIGNED: {}", assigned);

        assertEquals(1, assigned.getVetId());
        assertEquals(1, assigned.getSpecialtyId());
        assertEquals(4, assigned.getYearsExperience());
        assertFalse(assigned.getIsPrimary());
    }

    @Test
    public void testAssignDuplicate_ShouldFail() {
        // (vet_id=2, specialty_id=1) ya existe en data.sql
        VetSpecialtyDTO duplicate = VetSpecialtyDTO.builder()
                .vetId(2)
                .specialtyId(1)
                .certificationDate(LocalDate.of(2020, 1, 1))
                .yearsExperience(3)
                .isPrimary(false)
                .build();

        assertThrows(VetSpecialtyAlreadyExistsException.class,
                () -> this.vetSpecialtyService.assignSpecialtyToVet(duplicate));
    }

    @Test
    public void testRemoveSpecialtyFromVet() {
        this.vetSpecialtyService.assignSpecialtyToVet(VetSpecialtyDTO.builder()
                .vetId(1)
                .specialtyId(3)
                .certificationDate(LocalDate.of(2024, 3, 15))
                .yearsExperience(2)
                .isPrimary(false)
                .notes("Temporary dental assignment")
                .build());

        try {
            this.vetSpecialtyService.removeSpecialtyFromVet(1, 3);
        } catch (VetSpecialtyNotFoundException e) {
            fail(e.getMessage());
        }

        assertThrows(VetSpecialtyNotFoundException.class,
                () -> this.vetSpecialtyService.findById(1, 3));
    }

    @Test
    public void testFindSpecialtiesByVet() {
        List<VetSpecialtyDTO> specialties = this.vetSpecialtyService.findSpecialtiesByVet(3);
        assertEquals(2, specialties.size());
    }

    @Test
    public void testFindSpecialtiesByVet_Empty() {
        List<VetSpecialtyDTO> specialties = this.vetSpecialtyService.findSpecialtiesByVet(6);
        assertTrue(specialties.isEmpty());
    }

    @Test
    public void testFindVetsBySpecialty() {
        List<VetSpecialtyDTO> vets = this.vetSpecialtyService.findVetsBySpecialty(2);
        assertEquals(2, vets.size());
    }

    @Test
    public void testSetPrimarySpecialty_OnlyOnePrimary() throws VetSpecialtyNotFoundException {
        VetSpecialtyDTO updated = this.vetSpecialtyService.setPrimarySpecialty(3, 3);
        assertTrue(updated.getIsPrimary());

        List<VetSpecialtyDTO> vet3Specialties = this.vetSpecialtyService.findSpecialtiesByVet(3);
        long primaryCount = vet3Specialties.stream().filter(s -> Boolean.TRUE.equals(s.getIsPrimary())).count();
        assertEquals(1L, primaryCount);
    }

    @Test
    public void testSetPrimarySpecialty_NotAssigned() {
        assertThrows(VetSpecialtyNotFoundException.class,
                () -> this.vetSpecialtyService.setPrimarySpecialty(6, 3));
    }

    @Test
    public void testFindVetsByMinExperience() {
        List<VetSpecialtyDTO> experienced = this.vetSpecialtyService.findVetsByMinExperience(2, 9);
        assertEquals(2, experienced.size());
    }

    @Test
    public void testUpdateYearsExperience() throws VetSpecialtyNotFoundException {
        VetSpecialtyDTO updated = this.vetSpecialtyService.updateYearsExperience(5, 1, 10);
        assertEquals(10, updated.getYearsExperience());
    }

    @Test
    public void testUpdateCertificationDate() throws VetSpecialtyNotFoundException {
        LocalDate newDate = LocalDate.of(2025, 8, 20);
        VetSpecialtyDTO updated = this.vetSpecialtyService.updateCertificationDate(4, 2, newDate);
        assertEquals(newDate, updated.getCertificationDate());
    }
}
