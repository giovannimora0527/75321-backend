package com.uniminuto.clinica.exception;
//Manejo de expecion clase

public class EspecializacionNotFoundException extends RuntimeException{
    public EspecializacionNotFoundException(String mensaje){
        super(mensaje);
    }

}
