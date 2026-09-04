package com.evolutionerp.dtos;

import java.util.List;

// Estilo KitchenHack: DTO plano con getters/setters manuales (sin Lombok, sin constructores con args).
public class JwtResponseSociedadDTO {
    private String token;
    private String username;
    private List<String> sociedades;
    private String sociedadActual;

    public JwtResponseSociedadDTO() {
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<String> getSociedades() {
        return sociedades;
    }

    public void setSociedades(List<String> sociedades) {
        this.sociedades = sociedades;
    }

    public String getSociedadActual() {
        return sociedadActual;
    }

    public void setSociedadActual(String sociedadActual) {
        this.sociedadActual = sociedadActual;
    }
}
