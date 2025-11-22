package com.arc.lince.exceptions;

public class SistemaJaExistenteException extends RuntimeException {

    public SistemaJaExistenteException(String mensagem) {
        super(mensagem);
    }
}