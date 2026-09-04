package com.evolutionerp.dtos;

import java.time.LocalDateTime;

// Estilo KitchenHack: DTO plano con getters/setters manuales (sin Lombok).
// Completo: incluye opcMant/auditoría que faltaban respecto a Econstantes.
public class EconstantesDTO {
    private String codSociedad;
    private String cvalor;
    private String cnomValor;
    private String app;
    private String opcMant;
    private String userSis;
    private String userMod;
    private LocalDateTime userSisDate;

    public EconstantesDTO() {
    }

    public String getCodSociedad() {
        return codSociedad;
    }

    public void setCodSociedad(String codSociedad) {
        this.codSociedad = codSociedad;
    }

    public String getCvalor() {
        return cvalor;
    }

    public void setCvalor(String cvalor) {
        this.cvalor = cvalor;
    }

    public String getCnomValor() {
        return cnomValor;
    }

    public void setCnomValor(String cnomValor) {
        this.cnomValor = cnomValor;
    }

    public String getApp() {
        return app;
    }

    public void setApp(String app) {
        this.app = app;
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
