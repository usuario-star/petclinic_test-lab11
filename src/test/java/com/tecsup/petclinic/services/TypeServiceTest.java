package com.tecsup.petclinic.services;

import com.tecsup.petclinic.dtos.TypeDTO;
import com.tecsup.petclinic.dtos.TypePetCountDTO;
import com.tecsup.petclinic.exceptions.TypeNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Pruebas unitarias con base de datos para TypeService (Caso 5).
 */
@SpringBootTest
@Slf4j
public class TypeServiceTest {

    @Autowired
    private TypeService typeService;

    @Test
    public void testFindTypeById() {
        final String NAME_EXPECTED = "cat";
        final String DESC_EXPECTED = "Domestic feline";
        Integer ID = 1;

        TypeDTO type = null;
        try {
            type = this.typeService.findById(ID);
        } catch (TypeNotFoundException e) {
            fail(e.getMessage());
        }

        log.info("TYPE FOUND: {}", type);
        assertEquals(NAME_EXPECTED, type.getName());
        assertEquals(DESC_EXPECTED, type.getDescription());
        assertTrue(type.getActive());
    }

    @Test
    public void testFindTypeByName() {
        List<TypeDTO> types = this.typeService.findByName("dog");
        assertEquals(1, types.size());
        assertEquals("medium", types.get(0).getSizeCategory());
    }

    @Test
    public void testCreateType() {
        TypeDTO dto = TypeDTO.builder()
                .name("ferret")
                .description("Domestic ferret")
                .active(true)
                .sizeCategory("small")
                .averageLifespan(8)
                .careLevel("medium")
                .build();

        TypeDTO created = this.typeService.create(dto);
        log.info("TYPE CREATED: {}", created);

        assertNotNull(created.getId());
        assertEquals("ferret", created.getName());
        assertEquals("Domestic ferret", created.getDescription());
        assertTrue(created.getActive());
    }

    @Test
    public void testUpdateType() {
        TypeDTO created = this.typeService.create(TypeDTO.builder()
                .name("iguana")
                .description("Green reptile")
                .active(true)
                .sizeCategory("medium")
                .averageLifespan(12)
                .careLevel("high")
                .build());

        created.setName("green-iguana");
        created.setDescription("Tropical herbivore lizard");
        created.setCareLevel("medium");

        TypeDTO updated = this.typeService.update(created);
        log.info("TYPE UPDATED: {}", updated);

        assertEquals("green-iguana", updated.getName());
        assertEquals("Tropical herbivore lizard", updated.getDescription());
        assertEquals("medium", updated.getCareLevel());
    }

    @Test
    public void testDeleteType() {
        TypeDTO created = this.typeService.create(TypeDTO.builder()
                .name("chinchilla")
                .description("Rodent")
                .active(true)
                .sizeCategory("small")
                .averageLifespan(10)
                .careLevel("medium")
                .build());

        try {
            this.typeService.delete(created.getId());
        } catch (TypeNotFoundException e) {
            fail(e.getMessage());
        }

        assertThrows(TypeNotFoundException.class, () -> this.typeService.findById(created.getId()));
    }

    @Test
    public void testGetPetCountByType_ExcludeInactive() {
        List<TypePetCountDTO> report = this.typeService.getPetCountByType();
        log.info("PET COUNT BY ACTIVE TYPE: {}", report);

        assertFalse(report.isEmpty());
        // 'snake' (id=6) tiene active=false en data.sql, debe excluirse del reporte
        assertFalse(report.stream().anyMatch(r -> "snake".equals(r.getTypeName())));
        assertTrue(report.stream().anyMatch(r -> "cat".equals(r.getTypeName()) && r.getPetCount() == 4));
        assertTrue(report.stream().anyMatch(r -> "dog".equals(r.getTypeName()) && r.getPetCount() == 4));
    }

    @Test
    public void testFindTypeById_NotFound() {
        assertThrows(TypeNotFoundException.class, () -> this.typeService.findById(9999));
    }

    @Test
    public void testDeleteType_NotFound() {
        assertThrows(TypeNotFoundException.class, () -> this.typeService.delete(9999));
    }
}
