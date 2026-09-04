package com.evolutionerp.entities;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(schema = "evo", name = "eccosto")
@IdClass(EcCosto.EcCostoId.class)
public class EcCosto {
  @Id
  @Column(name = "cod_sociedad", length = 10)
  private String codSociedad;
  @Id
  @Column(name = "ccod_cencos", length = 10)
  private String ccodCencos;
  @Column(name = "nom_cencos", nullable = false, length = 100)
  private String nomCencos;
  @Column(length = 12)
  private String opcMant = "ACTIVO";

  public EcCosto() {
  }

  public EcCosto(String codSociedad, String ccodCencos, String nomCencos, String opcMant) {
    this.codSociedad = codSociedad;
    this.ccodCencos = ccodCencos;
    this.nomCencos = nomCencos;
    this.opcMant = opcMant;
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

  @PrePersist
  @PreUpdate
  void upper() {
    if (nomCencos != null)
      nomCencos = nomCencos.toUpperCase();
  }

  // Estilo KitchenHack: el Id vive en el mismo entity (antes EcCostoId.java).
  public static class EcCostoId implements Serializable {
    private String codSociedad;
    private String ccodCencos;

    public EcCostoId() {
    }

    public EcCostoId(String codSociedad, String ccodCencos) {
      this.codSociedad = codSociedad;
      this.ccodCencos = ccodCencos;
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

    @Override
    public boolean equals(Object o) {
      if (this == o) {
        return true;
      }
      if (o == null || getClass() != o.getClass()) {
        return false;
      }
      EcCostoId that = (EcCostoId) o;
      return Objects.equals(codSociedad, that.codSociedad)
          && Objects.equals(ccodCencos, that.ccodCencos);
    }

    @Override
    public int hashCode() {
      return Objects.hash(codSociedad, ccodCencos);
    }
  }
}
