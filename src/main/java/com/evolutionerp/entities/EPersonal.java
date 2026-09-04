package com.evolutionerp.entities;

import jakarta.persistence.*;

@Entity
@Table(schema = "evo", name = "epersonal")
public class EPersonal {
  @Id
  @Column(name = "ccod_person", length = 12)
  private String ccodPerson;
  @Column(name = "cod_sociedad", length = 10)
  private String codSociedad;
  @Column(name = "nom_person", nullable = false, length = 100)
  private String nomPerson;
  @Column(length = 12)
  private String opcMant = "ACTIVO";

  public EPersonal() {
  }

  public EPersonal(String ccodPerson, String codSociedad, String nomPerson, String opcMant) {
    this.ccodPerson = ccodPerson;
    this.codSociedad = codSociedad;
    this.nomPerson = nomPerson;
    this.opcMant = opcMant;
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

  @PrePersist
  @PreUpdate
  void upper() {
    if (nomPerson != null)
      nomPerson = nomPerson.toUpperCase();
  }
}
