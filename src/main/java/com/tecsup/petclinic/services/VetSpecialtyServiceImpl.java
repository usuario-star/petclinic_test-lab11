package com.tecsup.petclinic.services;

import com.tecsup.petclinic.dtos.VetSpecialtyDTO;
import com.tecsup.petclinic.entities.VetSpecialty;
import com.tecsup.petclinic.entities.VetSpecialtyId;
import com.tecsup.petclinic.exceptions.VetSpecialtyAlreadyExistsException;
import com.tecsup.petclinic.exceptions.VetSpecialtyNotFoundException;
import com.tecsup.petclinic.mappers.VetSpecialtyMapper;
import com.tecsup.petclinic.repositories.VetSpecialtyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementacion de VetSpecialtyService para la tabla vet_specialties.
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class VetSpecialtyServiceImpl implements VetSpecialtyService {

    private final VetSpecialtyRepository vetSpecialtyRepository;
    private final VetSpecialtyMapper vetSpecialtyMapper;

    @Override
    public VetSpecialtyDTO assignSpecialtyToVet(VetSpecialtyDTO dto) {
        VetSpecialtyId id = new VetSpecialtyId(dto.getVetId(), dto.getSpecialtyId());
        if (vetSpecialtyRepository.existsById(id)) {
            throw new VetSpecialtyAlreadyExistsException(
                    "Specialty " + dto.getSpecialtyId() + " is already assigned to vet " + dto.getVetId());
        }
        if (dto.getYearsExperience() == null) {
            dto.setYearsExperience(0);
        }
        if (dto.getIsPrimary() == null) {
            dto.setIsPrimary(Boolean.FALSE);
        }
        VetSpecialty saved = vetSpecialtyRepository.save(vetSpecialtyMapper.mapToEntity(dto));
        return vetSpecialtyMapper.mapToDto(saved);
    }

    @Override
    public void removeSpecialtyFromVet(Integer vetId, Integer specialtyId) throws VetSpecialtyNotFoundException {
        VetSpecialty entity = findEntityOrThrow(vetId, specialtyId);
        vetSpecialtyRepository.delete(entity);
    }

    @Override
    public VetSpecialtyDTO findById(Integer vetId, Integer specialtyId) throws VetSpecialtyNotFoundException {
        return vetSpecialtyMapper.mapToDto(findEntityOrThrow(vetId, specialtyId));
    }

    @Override
    public List<VetSpecialtyDTO> findSpecialtiesByVet(Integer vetId) {
        return mapList(vetSpecialtyRepository.findByVetId(vetId));
    }

    @Override
    public List<VetSpecialtyDTO> findVetsBySpecialty(Integer specialtyId) {
        return mapList(vetSpecialtyRepository.findBySpecialtyId(specialtyId));
    }

    @Override
    @Transactional
    public VetSpecialtyDTO setPrimarySpecialty(Integer vetId, Integer specialtyId) throws VetSpecialtyNotFoundException {
        findEntityOrThrow(vetId, specialtyId);
        List<VetSpecialty> vetAssignments = vetSpecialtyRepository.findByVetId(vetId);
        for (VetSpecialty vs : vetAssignments) {
            vs.setIsPrimary(specialtyId.equals(vs.getSpecialtyId()));
            vetSpecialtyRepository.save(vs);
        }
        return findById(vetId, specialtyId);
    }

    @Override
    public List<VetSpecialtyDTO> findVetsByMinExperience(Integer specialtyId, Integer minYears) {
        return mapList(vetSpecialtyRepository.findBySpecialtyIdAndYearsExperienceGreaterThanEqual(specialtyId, minYears));
    }

    @Override
    public VetSpecialtyDTO updateYearsExperience(Integer vetId, Integer specialtyId, Integer years) throws VetSpecialtyNotFoundException {
        VetSpecialty entity = findEntityOrThrow(vetId, specialtyId);
        entity.setYearsExperience(years);
        return vetSpecialtyMapper.mapToDto(vetSpecialtyRepository.save(entity));
    }

    @Override
    public VetSpecialtyDTO updateCertificationDate(Integer vetId, Integer specialtyId, LocalDate certificationDate) throws VetSpecialtyNotFoundException {
        VetSpecialty entity = findEntityOrThrow(vetId, specialtyId);
        entity.setCertificationDate(certificationDate);
        return vetSpecialtyMapper.mapToDto(vetSpecialtyRepository.save(entity));
    }

    private VetSpecialty findEntityOrThrow(Integer vetId, Integer specialtyId) throws VetSpecialtyNotFoundException {
        return vetSpecialtyRepository.findById(new VetSpecialtyId(vetId, specialtyId))
                .orElseThrow(() -> new VetSpecialtyNotFoundException(
                        "VetSpecialty not found for vetId=" + vetId + ", specialtyId=" + specialtyId));
    }

    private List<VetSpecialtyDTO> mapList(List<VetSpecialty> items) {
        items.forEach(item -> log.info("{}", item));
        return items.stream().map(vetSpecialtyMapper::mapToDto).collect(Collectors.toList());
    }
}
