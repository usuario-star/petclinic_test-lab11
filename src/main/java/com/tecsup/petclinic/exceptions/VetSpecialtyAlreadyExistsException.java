package com.tecsup.petclinic.exceptions;

/**
 * Excepcion lanzada al intentar asignar una especialidad ya asignada al veterinario.
 */
public class VetSpecialtyAlreadyExistsException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public VetSpecialtyAlreadyExistsException(String message) {
        super(message);
    }
}
