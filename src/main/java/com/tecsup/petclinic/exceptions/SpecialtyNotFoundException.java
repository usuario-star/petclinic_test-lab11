package com.tecsup.petclinic.exceptions;

/**
 * Excepcion lanzada cuando no se encuentra una especialidad.
 */
public class SpecialtyNotFoundException extends Exception {

    private static final long serialVersionUID = 1L;

    public SpecialtyNotFoundException(String message) {
        super(message);
    }
}
