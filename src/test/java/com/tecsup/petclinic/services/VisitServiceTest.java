package com.tecsup.petclinic.services;

import com.tecsup.petclinic.dtos.VisitDTO;
import com.tecsup.petclinic.exceptions.VisitNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Pruebas unitarias con base de datos para VisitService (Caso 4).
 */
@SpringBootTest
@Slf4j
public class VisitServiceTest {

    @Autowired
    private VisitService visitService;

    @Test
    public void testFindVisitById() {
        Integer ID = 1;
        VisitDTO visit = null;

        try {
            visit = this.visitService.findById(ID);
        } catch (VisitNotFoundException e) {
            fail(e.getMessage());
        }

        log.info("VISIT FOUND: {}", visit);
        assertEquals(7, visit.getPetId());
        assertEquals("rabies shot", visit.getDescription());
        assertEquals(0, new BigDecimal("45.00").compareTo(visit.getCost()));
    }

    @Test
    public void testFindByPetId() {
        List<VisitDTO> visits = this.visitService.findByPetId(7);
        assertEquals(2, visits.size());
    }

    @Test
    public void testFindByVetId() {
        List<VisitDTO> visits = this.visitService.findByVetId(3);
        assertEquals(2, visits.size());
    }

    @Test
    public void testCreateVisit() {
        VisitDTO dto = VisitDTO.builder()
                .petId(2)
                .vetId(5)
                .visitDate(LocalDate.of(2026, 11, 18))
                .description("annual vaccination")
                .cost(new BigDecimal("85.50"))
                .build();

        VisitDTO created = this.visitService.create(dto);
        log.info("VISIT CREATED: {}", created);

        assertNotNull(created.getId());
        assertEquals(2, created.getPetId());
        assertEquals(5, created.getVetId());
        assertEquals("annual vaccination", created.getDescription());
        assertEquals(0, new BigDecimal("85.50").compareTo(created.getCost()));
    }

    @Test
    public void testUpdateVisit() {
        VisitDTO created = this.visitService.create(VisitDTO.builder()
                .petId(4)
                .vetId(5)
                .visitDate(LocalDate.of(2026, 10, 10))
                .description("initial check")
                .cost(new BigDecimal("50.00"))
                .build());

        created.setDescription("blood test and consultation");
        created.setCost(new BigDecimal("120.00"));

        VisitDTO updated = this.visitService.update(created);
        log.info("VISIT UPDATED: {}", updated);

        assertEquals("blood test and consultation", updated.getDescription());
        assertEquals(0, new BigDecimal("120.00").compareTo(updated.getCost()));
    }

    @Test
    public void testDeleteVisit() {
        VisitDTO created = this.visitService.create(VisitDTO.builder()
                .petId(5)
                .vetId(5)
                .visitDate(LocalDate.of(2026, 9, 5))
                .description("temporary visit")
                .cost(new BigDecimal("30.00"))
                .build());

        try {
            this.visitService.delete(created.getId());
        } catch (VisitNotFoundException e) {
            fail(e.getMessage());
        }

        assertThrows(VisitNotFoundException.class, () -> this.visitService.findById(created.getId()));
    }

    @Test
    public void testCalculateTotalCostByPet() {
        BigDecimal total = this.visitService.calculateTotalCostByPet(7);
        assertEquals(0, new BigDecimal("320.00").compareTo(total));
    }

    @Test
    public void testCalculateTotalCostByPet_NoVisits() {
        BigDecimal total = this.visitService.calculateTotalCostByPet(13);
        assertEquals(0, BigDecimal.ZERO.compareTo(total));
    }

    @Test
    public void testFindByDateBetween() {
        List<VisitDTO> visits = this.visitService.findByDateBetween(
                LocalDate.of(2024, 1, 1),
                LocalDate.of(2024, 12, 31));
        assertEquals(2, visits.size());
    }

    @Test
    public void testFindByVetIdAndDateBetween() {
        List<VisitDTO> visits = this.visitService.findByVetIdAndDateBetween(
                2,
                LocalDate.of(2010, 1, 1),
                LocalDate.of(2012, 1, 1));
        assertEquals(2, visits.size());
    }

    @Test
    public void testCountVisitsByPet() {
        assertEquals(2L, this.visitService.countVisitsByPet(8));
    }

    @Test
    public void testFindVisitById_NotFound() {
        assertThrows(VisitNotFoundException.class, () -> this.visitService.findById(9999));
    }
}
