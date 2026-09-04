package com.evolutionerp.dtos;

// Estilo KitchenHack: DTO plano sin Lombok, getters/setters manuales.
public class MmaterialDTO {
    private String codMaterial;
    private String nomMaterial;
    private String cUnidad;
    private String opcMant;

  public MmaterialDTO() {
  }

  public String getCodMaterial() {
        return codMaterial;
    }

    public void setCodMaterial(String codMaterial) {
        this.codMaterial = codMaterial;
    }

    public String getNomMaterial() {
        return nomMaterial;
    }

    public void setNomMaterial(String nomMaterial) {
        this.nomMaterial = nomMaterial;
    }

    public String getCUnidad() {
        return cUnidad;
    }

    public void setCUnidad(String cUnidad) {
        this.cUnidad = cUnidad;
    }

    public String getOpcMant() {
        return opcMant;
    }

    public void setOpcMant(String opcMant) {
        this.opcMant = opcMant;
    }
}
