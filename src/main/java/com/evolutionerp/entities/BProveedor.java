package com.evolutionerp.entities;

import jakarta.persistence.*;

@Entity
@Table(schema = "evo", name = "bproveedor")
public class BProveedor {
  @Id
  @Column(name = "ccod_proveedor", length = 12)
  private String ccodProveedor;
  @Column(name = "nom_prov", nullable = false, length = 150)
  private String nomProv;
  @Column(length = 11)
  private String ruc;
  @Column(length = 12)
  private String opcMant = "ACTIVO";

  public BProveedor() {
  }

  public BProveedor(String ccodProveedor, String nomProv, String ruc, String opcMant) {
    this.ccodProveedor = ccodProveedor;
    this.nomProv = nomProv;
    this.ruc = ruc;
    this.opcMant = opcMant;
  }

  public String getCcodProveedor() {
    return ccodProveedor;
  }

  public void setCcodProveedor(String ccodProveedor) {
    this.ccodProveedor = ccodProveedor;
  }

  public String getNomProv() {
    return nomProv;
  }

  public void setNomProv(String nomProv) {
    this.nomProv = nomProv;
  }

  public String getRuc() {
    return ruc;
  }

  public void setRuc(String ruc) {
    this.ruc = ruc;
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
    if (nomProv != null)
      nomProv = nomProv.toUpperCase();
  }
}
