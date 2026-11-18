package com.tecsup.petclinic.services;

import com.tecsup.petclinic.dtos.VisitDTO;
import com.tecsup.petclinic.entities.Visit;
import com.tecsup.petclinic.exceptions.VisitNotFoundException;
import com.tecsup.petclinic.mappers.VisitMapper;
import com.tecsup.petclinic.repositories.VisitRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Implementacion de VisitService con consultas por relacion, fecha y calculo de costos.
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class VisitServiceImpl implements VisitService {

    private final VisitRepository visitRepository;
    private final VisitMapper visitMapper;

    @Override
    public VisitDTO create(VisitDTO visitDTO) {
        Visit saved = visitRepository.save(visitMapper.mapToEntity(visitDTO));
        return visitMapper.mapToDto(saved);
    }

    @Override
    public VisitDTO update(VisitDTO visitDTO) {
        Visit updated = visitRepository.save(visitMapper.mapToEntity(visitDTO));
        return visitMapper.mapToDto(updated);
    }

    @Override
    public void delete(Integer id) throws VisitNotFoundException {
        VisitDTO visit = findById(id);
        visitRepository.delete(visitMapper.mapToEntity(visit));
    }

    @Override
    public VisitDTO findById(Integer id) throws VisitNotFoundException {
        Visit visit = visitRepository.findById(id)
                .orElseThrow(() -> new VisitNotFoundException("Visit not found with id: " + id));
        return visitMapper.mapToDto(visit);
    }

    @Override
    public List<VisitDTO> findByPetId(Integer petId) {
        return mapList(visitRepository.findByPetId(petId));
    }

    @Override
    public List<VisitDTO> findByVetId(Integer vetId) {
        return mapList(visitRepository.findByVetId(vetId));
    }

    @Override
    public List<VisitDTO> findByDateBetween(LocalDate startDate, LocalDate endDate) {
        return mapList(visitRepository.findByVisitDateBetween(startDate, endDate));
    }

    @Override
    public List<VisitDTO> findByVetIdAndDateBetween(Integer vetId, LocalDate startDate, LocalDate endDate) {
        return mapList(visitRepository.findByVetIdAndVisitDateBetween(vetId, startDate, endDate));
    }

    @Override
    public BigDecimal calculateTotalCostByPet(Integer petId) {
        return visitRepository.findByPetId(petId).stream()
                .map(Visit::getCost)
                .filter(Objects::nonNull)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @Override
    public long countVisitsByPet(Integer petId) {
        return visitRepository.countByPetId(petId);
    }

    @Override
    public List<Visit> findAll() {
        return visitRepository.findAll();
    }

    private List<VisitDTO> mapList(List<Visit> visits) {
        visits.forEach(v -> log.info("{}", v));
        return visits.stream().map(visitMapper::mapToDto).collect(Collectors.toList());
    }
}
