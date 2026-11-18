package com.tecsup.petclinic.services;

import com.tecsup.petclinic.dtos.OwnerDTO;
import com.tecsup.petclinic.exceptions.OwnerNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Slf4j
public class OwnerServiceTest {

	@Autowired
	private OwnerService ownerService;

	@Test
	public void testFindOwnerById() {

		final String FIRST_NAME_EXPECTED = "George";
		final String LAST_NAME_EXPECTED = "Franklin";
		Integer ID = 1;

		OwnerDTO owner = null;

		try {
			owner = this.ownerService.findById(ID);
		} catch (OwnerNotFoundException e) {
			fail(e.getMessage());
		}

		log.info("" + owner);
		assertEquals(FIRST_NAME_EXPECTED, owner.getFirstName());
		assertEquals(LAST_NAME_EXPECTED, owner.getLastName());
	}

	@Test
	public void testFindOwnerByFirstName() {

		String FIND_FIRST_NAME = "George";
		int SIZE_EXPECTED = 1;

		List<OwnerDTO> owners = this.ownerService.findByFirstName(FIND_FIRST_NAME);

		assertEquals(SIZE_EXPECTED, owners.size());
	}

	@Test
	public void testFindOwnerByLastName() {

		String FIND_LAST_NAME = "Davis";
		int SIZE_EXPECTED = 2;

		List<OwnerDTO> owners = this.ownerService.findByLastName(FIND_LAST_NAME);

		assertEquals(SIZE_EXPECTED, owners.size());
	}

	@Test
	public void testFindOwnerByCity() {

		String FIND_CITY = "Madison";
		int SIZE_EXPECTED = 4;

		List<OwnerDTO> owners = this.ownerService.findByCity(FIND_CITY);

		assertEquals(SIZE_EXPECTED, owners.size());
	}

	@Test
	public void testCreateOwner() {

		String FIRST_NAME = "Jerardo";
		String LAST_NAME = "Cutipa";
		String ADDRESS = "Av. Cascanueces 2221";
		String CITY = "Lima";
		String TELEPHONE = "987654321";

		OwnerDTO ownerDTO = OwnerDTO.builder()
				.firstName(FIRST_NAME)
				.lastName(LAST_NAME)
				.address(ADDRESS)
				.city(CITY)
				.telephone(TELEPHONE)
				.build();

		OwnerDTO newOwnerDTO = this.ownerService.create(ownerDTO);

		log.info("OWNER CREATED : " + newOwnerDTO);

		assertNotNull(newOwnerDTO.getId());
		assertEquals(FIRST_NAME, newOwnerDTO.getFirstName());
		assertEquals(LAST_NAME, newOwnerDTO.getLastName());
		assertEquals(ADDRESS, newOwnerDTO.getAddress());
		assertEquals(CITY, newOwnerDTO.getCity());
		assertEquals(TELEPHONE, newOwnerDTO.getTelephone());
	}

	@Test
	public void testUpdateOwner() {

		String FIRST_NAME = "Carlos";
		String LAST_NAME = "Mendoza";
		String ADDRESS = "Jr. Los Olivos 123";
		String CITY = "Arequipa";
		String TELEPHONE = "912345678";

		String UP_FIRST_NAME = "Carlos Alberto";
		String UP_LAST_NAME = "Mendoza Salas";
		String UP_ADDRESS = "Av. Ejercito 456";
		String UP_CITY = "Cusco";
		String UP_TELEPHONE = "999888777";

		OwnerDTO ownerDTO = OwnerDTO.builder()
				.firstName(FIRST_NAME)
				.lastName(LAST_NAME)
				.address(ADDRESS)
				.city(CITY)
				.telephone(TELEPHONE)
				.build();

		// ------------ Create ---------------
		log.info(">" + ownerDTO);
		OwnerDTO ownerDTOCreated = this.ownerService.create(ownerDTO);
		log.info(">>" + ownerDTOCreated);

		// ------------ Update ---------------
		ownerDTOCreated.setFirstName(UP_FIRST_NAME);
		ownerDTOCreated.setLastName(UP_LAST_NAME);
		ownerDTOCreated.setAddress(UP_ADDRESS);
		ownerDTOCreated.setCity(UP_CITY);
		ownerDTOCreated.setTelephone(UP_TELEPHONE);

		OwnerDTO upgradeOwnerDTO = this.ownerService.update(ownerDTOCreated);
		log.info(">>>>" + upgradeOwnerDTO);

		assertEquals(UP_FIRST_NAME, upgradeOwnerDTO.getFirstName());
		assertEquals(UP_LAST_NAME, upgradeOwnerDTO.getLastName());
		assertEquals(UP_ADDRESS, upgradeOwnerDTO.getAddress());
		assertEquals(UP_CITY, upgradeOwnerDTO.getCity());
		assertEquals(UP_TELEPHONE, upgradeOwnerDTO.getTelephone());
	}

	@Test
	public void testDeleteOwner() {

		String FIRST_NAME = "Pedro";
		String LAST_NAME = "Castillo";
		String ADDRESS = "Calle Real 555";
		String CITY = "Trujillo";
		String TELEPHONE = "944555666";

		// ------------ Create ---------------
		OwnerDTO ownerDTO = OwnerDTO.builder()
				.firstName(FIRST_NAME)
				.lastName(LAST_NAME)
				.address(ADDRESS)
				.city(CITY)
				.telephone(TELEPHONE)
				.build();

		OwnerDTO newOwnerDTO = this.ownerService.create(ownerDTO);
		log.info("OWNER TO DELETE: " + newOwnerDTO);

		// ------------ Delete ---------------
		try {
			this.ownerService.delete(newOwnerDTO.getId());
		} catch (OwnerNotFoundException e) {
			fail(e.getMessage());
		}

		// ------------ Validation ---------------
		assertThrows(OwnerNotFoundException.class, () -> this.ownerService.findById(newOwnerDTO.getId()));
	}

	@Test
	public void testFindOwnerById_NotFound() {
		assertThrows(OwnerNotFoundException.class, () -> this.ownerService.findById(9999));
	}

	@Test
	public void testDeleteOwner_NotFound() {
		assertThrows(OwnerNotFoundException.class, () -> this.ownerService.delete(9999));
	}

	@Test
	public void testFindByLastName_Empty() {
		List<OwnerDTO> owners = this.ownerService.findByLastName("ApellidoInexistente");
		assertTrue(owners.isEmpty());
	}
}
