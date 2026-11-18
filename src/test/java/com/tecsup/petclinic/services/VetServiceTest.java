package com.tecsup.petclinic.services;

import com.tecsup.petclinic.dtos.VetDTO;
import com.tecsup.petclinic.exceptions.VetNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Pruebas unitarias con base de datos para VetService (Caso 1).
 */
@SpringBootTest
@Slf4j
public class VetServiceTest {

    @Autowired
    private VetService vetService;

    @Test
    public void testFindVetById() {
        final String FIRST_NAME_EXPECTED = "James";
        final String LAST_NAME_EXPECTED = "Carter";
        Integer ID = 1;

        VetDTO vet = null;
        try {
            vet = this.vetService.findById(ID);
        } catch (VetNotFoundException e) {
            fail(e.getMessage());
        }

        log.info("VET FOUND: {}", vet);
        assertEquals(FIRST_NAME_EXPECTED, vet.getFirstName());
        assertEquals(LAST_NAME_EXPECTED, vet.getLastName());
    }

    @Test
    public void testFindVetByFirstName() {
        List<VetDTO> vets = this.vetService.findByFirstName("Helen");
        assertEquals(1, vets.size());
        assertEquals("Leary", vets.get(0).getLastName());
    }

    @Test
    public void testFindVetByLastName() {
        List<VetDTO> vets = this.vetService.findByLastName("Douglas");
        assertEquals(1, vets.size());
        assertEquals("Linda", vets.get(0).getFirstName());
    }

    @Test
    public void testFindActiveVets() {
        List<VetDTO> activeVets = this.vetService.findActiveVets();
        assertTrue(activeVets.size() >= 5);
        assertTrue(activeVets.stream().allMatch(v -> Boolean.TRUE.equals(v.getActive())));
        assertTrue(activeVets.stream().anyMatch(v -> Integer.valueOf(1).equals(v.getId())));
    }

    @Test
    public void testFindInactiveVets() {
        List<VetDTO> inactiveVets = this.vetService.findInactiveVets();
        assertFalse(inactiveVets.isEmpty());
        assertTrue(inactiveVets.stream().allMatch(v -> Boolean.FALSE.equals(v.getActive())));
        assertTrue(inactiveVets.stream().anyMatch(v -> "Sharon".equals(v.getFirstName())));
    }

    @Test
    public void testCreateVet() {
        String FIRST_NAME = "Andrea";
        String LAST_NAME = "Salazar";
        String EMAIL = "andrea.salazar@petclinic.com";
        String PHONE = "987111222";

        VetDTO vetDTO = VetDTO.builder()
                .firstName(FIRST_NAME)
                .lastName(LAST_NAME)
                .email(EMAIL)
                .phone(PHONE)
                .active(true)
                .build();

        VetDTO created = this.vetService.create(vetDTO);
        log.info("VET CREATED: {}", created);

        assertNotNull(created.getId());
        assertEquals(FIRST_NAME, created.getFirstName());
        assertEquals(LAST_NAME, created.getLastName());
        assertEquals(EMAIL, created.getEmail());
        assertEquals(PHONE, created.getPhone());
        assertTrue(created.getActive());
    }

    @Test
    public void testUpdateVet() {
        VetDTO vetDTO = VetDTO.builder()
                .firstName("Mario")
                .lastName("Vargas")
                .email("mario.vargas@petclinic.com")
                .phone("911222333")
                .active(true)
                .build();

        VetDTO created = this.vetService.create(vetDTO);

        String UP_FIRST = "Mario Luis";
        String UP_LAST = "Vargas Llosa";
        String UP_EMAIL = "mario.llosa@petclinic.com";
        String UP_PHONE = "999000111";

        created.setFirstName(UP_FIRST);
        created.setLastName(UP_LAST);
        created.setEmail(UP_EMAIL);
        created.setPhone(UP_PHONE);

        VetDTO updated = this.vetService.update(created);
        log.info("VET UPDATED: {}", updated);

        assertEquals(UP_FIRST, updated.getFirstName());
        assertEquals(UP_LAST, updated.getLastName());
        assertEquals(UP_EMAIL, updated.getEmail());
        assertEquals(UP_PHONE, updated.getPhone());
    }

    @Test
    public void testDeactivateAndReactivateVet() throws VetNotFoundException {
        VetDTO created = this.vetService.create(VetDTO.builder()
                .firstName("Diego")
                .lastName("Paredes")
                .email("diego.paredes@petclinic.com")
                .phone("944333222")
                .active(true)
                .build());

        VetDTO deactivated = this.vetService.deactivate(created.getId());
        assertFalse(deactivated.getActive());

        VetDTO reactivated = this.vetService.reactivate(created.getId());
        assertTrue(reactivated.getActive());
    }

    @Test
    public void testDeleteVet() {
        VetDTO created = this.vetService.create(VetDTO.builder()
                .firstName("Temporal")
                .lastName("Vet")
                .email("temp.vet@petclinic.com")
                .phone("900000000")
                .active(true)
                .build());

        try {
            this.vetService.delete(created.getId());
        } catch (VetNotFoundException e) {
            fail(e.getMessage());
        }

        assertThrows(VetNotFoundException.class, () -> this.vetService.findById(created.getId()));
    }

    @Test
    public void testFindVetById_NotFound() {
        assertThrows(VetNotFoundException.class, () -> this.vetService.findById(9999));
    }
}
