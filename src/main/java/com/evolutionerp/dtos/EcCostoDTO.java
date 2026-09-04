package com.evolutionerp.dtos;

public class EcCostoDTO {
    private String codSociedad;
    private String ccodCencos;
    private String nomCencos;
    private String opcMant;

    public EcCostoDTO() {
    }

    public String getCodSociedad() {
        return codSociedad;
    }

    public void setCodSociedad(String codSociedad) {
        this.codSociedad = codSociedad;
    }

    public String getCcodCencos() {
        return ccodCencos;
    }

    public void setCcodCencos(String ccodCencos) {
        this.ccodCencos = ccodCencos;
    }

    public String getNomCencos() {
        return nomCencos;
    }

    public void setNomCencos(String nomCencos) {
        this.nomCencos = nomCencos;
    }

    public String getOpcMant() {
        return opcMant;
    }

    public void setOpcMant(String opcMant) {
        this.opcMant = opcMant;
    }
}
