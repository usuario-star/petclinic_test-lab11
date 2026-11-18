package com.tecsup.petclinic.services;

import com.tecsup.petclinic.dtos.SpecialtyDTO;
import com.tecsup.petclinic.exceptions.InvalidScheduleException;
import com.tecsup.petclinic.exceptions.SpecialtyNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Pruebas unitarias con base de datos para SpecialtyService (Caso 3).
 */
@SpringBootTest
@Slf4j
public class SpecialtyServiceTest {

    @Autowired
    private SpecialtyService specialtyService;

    @Test
    public void testFindSpecialtyById() {
        final String NAME_EXPECTED = "radiology";
        final String OFFICE_EXPECTED = "Farewell";
        Integer ID = 1;

        SpecialtyDTO specialty = null;
        try {
            specialty = this.specialtyService.findById(ID);
        } catch (SpecialtyNotFoundException e) {
            fail(e.getMessage());
        }

        log.info("SPECIALTY FOUND: {}", specialty);
        assertEquals(NAME_EXPECTED, specialty.getName());
        assertEquals(OFFICE_EXPECTED, specialty.getOffice());
    }

    @Test
    public void testFindByName() {
        List<SpecialtyDTO> list = this.specialtyService.findByName("surgery");
        assertEquals(1, list.size());
        assertEquals("Maryland", list.get(0).getOffice());
    }

    @Test
    public void testFindByOffice() {
        List<SpecialtyDTO> list = this.specialtyService.findByOffice("Terranova");
        assertEquals(1, list.size());
        assertEquals("dentistry", list.get(0).getName());
    }

    @Test
    public void testCreateSpecialty() {
        SpecialtyDTO dto = SpecialtyDTO.builder()
                .name("cardiology")
                .office("Emerald")
                .openHour(9)
                .closeHour(17)
                .build();

        SpecialtyDTO created = this.specialtyService.create(dto);
        log.info("SPECIALTY CREATED: {}", created);

        assertNotNull(created.getId());
        assertEquals("cardiology", created.getName());
        assertEquals("Emerald", created.getOffice());
        assertEquals(9, created.getOpenHour());
        assertEquals(17, created.getCloseHour());
    }

    @Test
    public void testUpdateSpecialty() {
        SpecialtyDTO created = this.specialtyService.create(SpecialtyDTO.builder()
                .name("dermatology")
                .office("NorthWing")
                .openHour(8)
                .closeHour(14)
                .build());

        created.setName("dermatology-advanced");
        created.setOffice("SouthWing");
        created.setOpenHour(10);
        created.setCloseHour(18);

        SpecialtyDTO updated = this.specialtyService.update(created);
        log.info("SPECIALTY UPDATED: {}", updated);

        assertEquals("dermatology-advanced", updated.getName());
        assertEquals("SouthWing", updated.getOffice());
        assertEquals(10, updated.getOpenHour());
        assertEquals(18, updated.getCloseHour());
    }

    @Test
    public void testDeleteSpecialty() {
        SpecialtyDTO created = this.specialtyService.create(SpecialtyDTO.builder()
                .name("neurology")
                .office("TempOffice")
                .openHour(8)
                .closeHour(12)
                .build());

        try {
            this.specialtyService.delete(created.getId());
        } catch (SpecialtyNotFoundException e) {
            fail(e.getMessage());
        }

        assertThrows(SpecialtyNotFoundException.class, () -> this.specialtyService.findById(created.getId()));
    }

    @Test
    public void testFindSpecialtyById_NotFound() {
        assertThrows(SpecialtyNotFoundException.class, () -> this.specialtyService.findById(9999));
    }

    @Test
    public void testCreateSpecialty_InvalidSchedule() {
        SpecialtyDTO invalid = SpecialtyDTO.builder()
                .name("oncology")
                .office("WestWing")
                .openHour(18)
                .closeHour(10)
                .build();

        assertThrows(InvalidScheduleException.class, () -> this.specialtyService.create(invalid));
    }

    @Test
    public void testCreateSpecialty_InvalidHourRange() {
        SpecialtyDTO invalid = SpecialtyDTO.builder()
                .name("ophthalmology")
                .office("EastWing")
                .openHour(-1)
                .closeHour(25)
                .build();

        assertThrows(InvalidScheduleException.class, () -> this.specialtyService.create(invalid));
    }
}
