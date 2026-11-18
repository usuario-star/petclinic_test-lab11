package com.tecsup.petclinic.exceptions;

/**
 * Excepcion lanzada cuando no se encuentra un tipo de mascota.
 */
public class TypeNotFoundException extends Exception {

    private static final long serialVersionUID = 1L;

    public TypeNotFoundException(String message) {
        super(message);
    }
}
