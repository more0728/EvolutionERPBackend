package com.evolutionerp.entities;

import jakarta.persistence.*;

@Entity
@Table(schema = "evo", name = "mmaterial")
public class Mmaterial {
  @Id
  @Column(name = "cod_material", length = 42)
  private String codMaterial;
  @Column(name = "nom_material", nullable = false, length = 200)
  private String nomMaterial;
  @Column(name = "c_unidad", nullable = false, length = 10)
  private String cUnidad;
  @Column(length = 12)
  private String opcMant = "ACTIVO";

  public Mmaterial() {
  }

  public Mmaterial(String codMaterial, String nomMaterial, String cUnidad, String opcMant) {
    this.codMaterial = codMaterial;
    this.nomMaterial = nomMaterial;
    this.cUnidad = cUnidad;
    this.opcMant = opcMant;
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

  @PrePersist
  @PreUpdate
  void upper() {
    if (nomMaterial != null)
      nomMaterial = nomMaterial.toUpperCase();
    if (cUnidad != null)
      cUnidad = cUnidad.toUpperCase();
  }
}
