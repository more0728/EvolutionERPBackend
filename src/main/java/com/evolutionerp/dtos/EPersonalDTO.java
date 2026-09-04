package com.evolutionerp.dtos;

public class EPersonalDTO {
    private String ccodPerson;
    private String codSociedad;
    private String nomPerson;
    private String opcMant;

  public EPersonalDTO() {
  }

  public String getCcodPerson() {
        return ccodPerson;
    }

    public void setCcodPerson(String ccodPerson) {
        this.ccodPerson = ccodPerson;
    }

    public String getCodSociedad() {
        return codSociedad;
    }

    public void setCodSociedad(String codSociedad) {
        this.codSociedad = codSociedad;
    }

    public String getNomPerson() {
        return nomPerson;
    }

    public void setNomPerson(String nomPerson) {
        this.nomPerson = nomPerson;
    }

    public String getOpcMant() {
        return opcMant;
    }

    public void setOpcMant(String opcMant) {
        this.opcMant = opcMant;
    }
}
