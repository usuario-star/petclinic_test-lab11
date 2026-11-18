package com.tecsup.petclinic.repositories;

import com.tecsup.petclinic.entities.Type;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repositorio JPA para la entidad Type.
 */
@Repository
public interface TypeRepository extends JpaRepository<Type, Integer> {

    List<Type> findByName(String name);

    List<Type> findByActive(Boolean active);

    @Override
    List<Type> findAll();
}
