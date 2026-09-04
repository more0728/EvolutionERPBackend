package com.evolutionerp.dtos;

// Estilo KitchenHack: DTO plano con getters/setters manuales (sin Lombok, sin constructores con args).
public class AuthSociedadRequest {
    private String codSociedad;

    public AuthSociedadRequest() {
    }

    public String getCodSociedad() {
        return codSociedad;
    }

    public void setCodSociedad(String codSociedad) {
        this.codSociedad = codSociedad;
    }
}
