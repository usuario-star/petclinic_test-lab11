package com.tecsup.petclinic.services;

import com.tecsup.petclinic.dtos.SpecialtyDTO;
import com.tecsup.petclinic.entities.Specialty;
import com.tecsup.petclinic.exceptions.InvalidScheduleException;
import com.tecsup.petclinic.exceptions.SpecialtyNotFoundException;
import com.tecsup.petclinic.mappers.SpecialtyMapper;
import com.tecsup.petclinic.repositories.SpecialtyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementacion de SpecialtyService con validacion de horarios.
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class SpecialtyServiceImpl implements SpecialtyService {

    private final SpecialtyRepository specialtyRepository;
    private final SpecialtyMapper specialtyMapper;

    @Override
    public SpecialtyDTO create(SpecialtyDTO specialtyDTO) {
        validateSchedule(specialtyDTO);
        Specialty saved = specialtyRepository.save(specialtyMapper.mapToEntity(specialtyDTO));
        return specialtyMapper.mapToDto(saved);
    }

    @Override
    public SpecialtyDTO update(SpecialtyDTO specialtyDTO) {
        validateSchedule(specialtyDTO);
        Specialty updated = specialtyRepository.save(specialtyMapper.mapToEntity(specialtyDTO));
        return specialtyMapper.mapToDto(updated);
    }

    @Override
    public void delete(Integer id) throws SpecialtyNotFoundException {
        SpecialtyDTO specialty = findById(id);
        specialtyRepository.delete(specialtyMapper.mapToEntity(specialty));
    }

    @Override
    public SpecialtyDTO findById(Integer id) throws SpecialtyNotFoundException {
        Specialty specialty = specialtyRepository.findById(id)
                .orElseThrow(() -> new SpecialtyNotFoundException("Specialty not found with id: " + id));
        return specialtyMapper.mapToDto(specialty);
    }

    @Override
    public List<SpecialtyDTO> findByName(String name) {
        return mapList(specialtyRepository.findByName(name));
    }

    @Override
    public List<SpecialtyDTO> findByOffice(String office) {
        return mapList(specialtyRepository.findByOffice(office));
    }

    @Override
    public List<Specialty> findAll() {
        return specialtyRepository.findAll();
    }

    private void validateSchedule(SpecialtyDTO dto) {
        Integer open = dto.getOpenHour();
        Integer close = dto.getCloseHour();
        if (open != null && (open < 0 || open > 23)) {
            throw new InvalidScheduleException("Opening hour must be between 0 and 23: " + open);
        }
        if (close != null && (close < 0 || close > 23)) {
            throw new InvalidScheduleException("Closing hour must be between 0 and 23: " + close);
        }
        if (open != null && close != null && open >= close) {
            throw new InvalidScheduleException("Opening hour must be before closing hour");
        }
    }

    private List<SpecialtyDTO> mapList(List<Specialty> specialties) {
        specialties.forEach(s -> log.info("{}", s));
        return specialties.stream().map(specialtyMapper::mapToDto).collect(Collectors.toList());
    }
}
