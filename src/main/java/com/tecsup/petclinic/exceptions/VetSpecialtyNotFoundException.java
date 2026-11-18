package com.tecsup.petclinic.exceptions;

/**
 * Excepcion lanzada cuando no se encuentra la relacion veterinario-especialidad.
 */
public class VetSpecialtyNotFoundException extends Exception {

    private static final long serialVersionUID = 1L;

    public VetSpecialtyNotFoundException(String message) {
        super(message);
    }
}
