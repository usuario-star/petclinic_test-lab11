package com.tecsup.petclinic.exceptions;

/**
 * Excepcion lanzada cuando el horario de atencion de una especialidad es invalido.
 */
public class InvalidScheduleException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public InvalidScheduleException(String message) {
        super(message);
    }
}
