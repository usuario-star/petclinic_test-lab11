package com.tecsup.petclinic.services;

import com.tecsup.petclinic.dtos.VetDTO;
import com.tecsup.petclinic.entities.Vet;
import com.tecsup.petclinic.exceptions.VetNotFoundException;
import com.tecsup.petclinic.mappers.VetMapper;
import com.tecsup.petclinic.repositories.VetRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementacion de VetService con persistencia JPA.
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class VetServiceImpl implements VetService {

    private final VetRepository vetRepository;
    private final VetMapper vetMapper;

    @Override
    public VetDTO create(VetDTO vetDTO) {
        if (vetDTO.getActive() == null) {
            vetDTO.setActive(Boolean.TRUE);
        }
        Vet saved = vetRepository.save(vetMapper.mapToEntity(vetDTO));
        return vetMapper.mapToDto(saved);
    }

    @Override
    public VetDTO update(VetDTO vetDTO) {
        Vet updated = vetRepository.save(vetMapper.mapToEntity(vetDTO));
        return vetMapper.mapToDto(updated);
    }

    @Override
    public void delete(Integer id) throws VetNotFoundException {
        VetDTO vet = findById(id);
        vetRepository.delete(vetMapper.mapToEntity(vet));
    }

    @Override
    public VetDTO findById(Integer id) throws VetNotFoundException {
        Vet vet = vetRepository.findById(id)
                .orElseThrow(() -> new VetNotFoundException("Vet not found with id: " + id));
        return vetMapper.mapToDto(vet);
    }

    @Override
    public List<VetDTO> findByFirstName(String firstName) {
        return mapList(vetRepository.findByFirstName(firstName));
    }

    @Override
    public List<VetDTO> findByLastName(String lastName) {
        return mapList(vetRepository.findByLastName(lastName));
    }

    @Override
    public List<VetDTO> findActiveVets() {
        return mapList(vetRepository.findByActive(Boolean.TRUE));
    }

    @Override
    public List<VetDTO> findInactiveVets() {
        return mapList(vetRepository.findByActive(Boolean.FALSE));
    }

    @Override
    public VetDTO deactivate(Integer id) throws VetNotFoundException {
        return setActiveState(id, Boolean.FALSE);
    }

    @Override
    public VetDTO reactivate(Integer id) throws VetNotFoundException {
        return setActiveState(id, Boolean.TRUE);
    }

    @Override
    public List<Vet> findAll() {
        return vetRepository.findAll();
    }

    private VetDTO setActiveState(Integer id, Boolean active) throws VetNotFoundException {
        VetDTO vet = findById(id);
        vet.setActive(active);
        return update(vet);
    }

    private List<VetDTO> mapList(List<Vet> vets) {
        vets.forEach(v -> log.info("{}", v));
        return vets.stream().map(vetMapper::mapToDto).collect(Collectors.toList());
    }
}
