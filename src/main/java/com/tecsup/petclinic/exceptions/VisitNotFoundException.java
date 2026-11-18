package com.tecsup.petclinic.exceptions;

/**
 * Excepcion lanzada cuando no se encuentra una visita veterinaria.
 */
public class VisitNotFoundException extends Exception {

    private static final long serialVersionUID = 1L;

    public VisitNotFoundException(String message) {
        super(message);
    }
}
