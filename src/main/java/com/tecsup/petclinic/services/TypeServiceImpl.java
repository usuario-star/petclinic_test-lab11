package com.tecsup.petclinic.services;

import com.tecsup.petclinic.dtos.TypeDTO;
import com.tecsup.petclinic.dtos.TypePetCountDTO;
import com.tecsup.petclinic.entities.Type;
import com.tecsup.petclinic.exceptions.TypeNotFoundException;
import com.tecsup.petclinic.mappers.TypeMapper;
import com.tecsup.petclinic.repositories.PetRepository;
import com.tecsup.petclinic.repositories.TypeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementacion de TypeService con soporte de reporte de mascotas por tipo activo.
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class TypeServiceImpl implements TypeService {

    private final TypeRepository typeRepository;
    private final PetRepository petRepository;
    private final TypeMapper typeMapper;

    @Override
    public TypeDTO create(TypeDTO typeDTO) {
        if (typeDTO.getActive() == null) {
            typeDTO.setActive(Boolean.TRUE);
        }
        Type saved = typeRepository.save(typeMapper.mapToEntity(typeDTO));
        return typeMapper.mapToDto(saved);
    }

    @Override
    public TypeDTO update(TypeDTO typeDTO) {
        Type updated = typeRepository.save(typeMapper.mapToEntity(typeDTO));
        return typeMapper.mapToDto(updated);
    }

    @Override
    public void delete(Integer id) throws TypeNotFoundException {
        TypeDTO type = findById(id);
        typeRepository.delete(typeMapper.mapToEntity(type));
    }

    @Override
    public TypeDTO findById(Integer id) throws TypeNotFoundException {
        Type type = typeRepository.findById(id)
                .orElseThrow(() -> new TypeNotFoundException("Type not found with id: " + id));
        return typeMapper.mapToDto(type);
    }

    @Override
    public List<TypeDTO> findByName(String name) {
        return mapList(typeRepository.findByName(name));
    }

    @Override
    public List<TypeDTO> findByActive(Boolean active) {
        return mapList(typeRepository.findByActive(active));
    }

    @Override
    public List<TypePetCountDTO> getPetCountByType() {
        return typeRepository.findByActive(Boolean.TRUE).stream()
                .map(type -> new TypePetCountDTO(
                        type.getName(),
                        petRepository.findByTypeId(type.getId()).size()))
                .collect(Collectors.toList());
    }

    @Override
    public List<Type> findAll() {
        return typeRepository.findAll();
    }

    private List<TypeDTO> mapList(List<Type> types) {
        types.forEach(t -> log.info("{}", t));
        return types.stream().map(typeMapper::mapToDto).collect(Collectors.toList());
    }
}
