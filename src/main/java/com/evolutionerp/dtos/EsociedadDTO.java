package com.evolutionerp.dtos;

import java.time.LocalDateTime;

// Estilo KitchenHack: DTO plano con getters/setters manuales (sin Lombok).
// Completo: incluye país/idioma/nombre comercial/opcMant/auditoría que faltaban.
public class EsociedadDTO {
    private String codSociedad;
    private String nomSociedad;
    private String nitSociedad;
    private String idPais;
    private String idIdioma;
    private String nomComercial;
    private String opcMant;
    private String userSis;
    private String userMod;
    private LocalDateTime userSisDate;

    public EsociedadDTO() {
    }

    public String getCodSociedad() {
        return codSociedad;
    }

    public void setCodSociedad(String codSociedad) {
        this.codSociedad = codSociedad;
    }

    public String getNomSociedad() {
        return nomSociedad;
    }

    public void setNomSociedad(String nomSociedad) {
        this.nomSociedad = nomSociedad;
    }

    public String getNitSociedad() {
        return nitSociedad;
    }

    public void setNitSociedad(String nitSociedad) {
        this.nitSociedad = nitSociedad;
    }

    public String getIdPais() {
        return idPais;
    }

    public void setIdPais(String idPais) {
        this.idPais = idPais;
    }

    public String getIdIdioma() {
        return idIdioma;
    }

    public void setIdIdioma(String idIdioma) {
        this.idIdioma = idIdioma;
    }

    public String getNomComercial() {
        return nomComercial;
    }

    public void setNomComercial(String nomComercial) {
        this.nomComercial = nomComercial;
    }

    public String getOpcMant() {
        return opcMant;
    }

    public void setOpcMant(String opcMant) {
        this.opcMant = opcMant;
    }

    public String getUserSis() {
        return userSis;
    }

    public void setUserSis(String userSis) {
        this.userSis = userSis;
    }

    public String getUserMod() {
        return userMod;
    }

    public void setUserMod(String userMod) {
        this.userMod = userMod;
    }

    public LocalDateTime getUserSisDate() {
        return userSisDate;
    }

    public void setUserSisDate(LocalDateTime userSisDate) {
        this.userSisDate = userSisDate;
    }
}
