package com.evolutionerp.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Table(schema = "evo", name = "mmrequis_det")
@IdClass(MmRequisDet.MmRequisDetId.class)
public class MmRequisDet {
  @Id
  @Column(name = "cod_sociedad", length = 10)
  private String codSociedad;
  @Id
  @Column(name = "nro_item", columnDefinition = "numeric(18,0)")
  private Long nroItem;
  @Id
  @Column(name = "nro_doc", length = 12)
  private String nroDoc;
  @Column(name = "cod_material", length = 42)
  private String codMaterial;
  @Column(name = "c_unidad", nullable = false, length = 10)
  private String cUnidad;
  @Column(nullable = false, precision = 18, scale = 4)
  private BigDecimal cantid;
  @Column(name = "ncantidad_recibida", nullable = false, precision = 18, scale = 4)
  private BigDecimal ncantidadRecibida = BigDecimal.ZERO;
  @Column(length = 400)
  private String observ;
  @Column(name = "ccod_proveedor", length = 12)
  private String ccodProveedor;
  @Column(length = 12)
  private String estado = "PENDIENTE";
  @Column(name = "opc_mant", length = 12)
  private String opcMant = "ACTIVO";
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumns({
      @JoinColumn(name = "cod_sociedad", referencedColumnName = "cod_sociedad", insertable = false, updatable = false),
      @JoinColumn(name = "nro_doc", referencedColumnName = "nro_doc", insertable = false, updatable = false) })
  @JsonIgnore
  private MmRequisCab cab;

  public MmRequisDet() {
  }

  public MmRequisDet(String codSociedad, Long nroItem, String nroDoc, String codMaterial, String cUnidad,
      BigDecimal cantid, BigDecimal ncantidadRecibida, String observ, String ccodProveedor, String estado,
      String opcMant, MmRequisCab cab) {
    this.codSociedad = codSociedad;
    this.nroItem = nroItem;
    this.nroDoc = nroDoc;
    this.codMaterial = codMaterial;
    this.cUnidad = cUnidad;
    this.cantid = cantid;
    this.ncantidadRecibida = ncantidadRecibida;
    this.observ = observ;
    this.ccodProveedor = ccodProveedor;
    this.estado = estado;
    this.opcMant = opcMant;
    this.cab = cab;
  }

  public String getCodSociedad() {
    return codSociedad;
  }

  public void setCodSociedad(String codSociedad) {
    this.codSociedad = codSociedad;
  }

  public Long getNroItem() {
    return nroItem;
  }

  public void setNroItem(Long nroItem) {
    this.nroItem = nroItem;
  }

  public String getNroDoc() {
    return nroDoc;
  }

  public void setNroDoc(String nroDoc) {
    this.nroDoc = nroDoc;
  }

  public String getCodMaterial() {
    return codMaterial;
  }

  public void setCodMaterial(String codMaterial) {
    this.codMaterial = codMaterial;
  }

  public String getCUnidad() {
    return cUnidad;
  }

  public void setCUnidad(String cUnidad) {
    this.cUnidad = cUnidad;
  }

  public BigDecimal getCantid() {
    return cantid;
  }

  public void setCantid(BigDecimal cantid) {
    this.cantid = cantid;
  }

  public BigDecimal getNcantidadRecibida() {
    return ncantidadRecibida;
  }

  public void setNcantidadRecibida(BigDecimal ncantidadRecibida) {
    this.ncantidadRecibida = ncantidadRecibida;
  }

  public String getObserv() {
    return observ;
  }

  public void setObserv(String observ) {
    this.observ = observ;
  }

  public String getCcodProveedor() {
    return ccodProveedor;
  }

  public void setCcodProveedor(String ccodProveedor) {
    this.ccodProveedor = ccodProveedor;
  }

  public String getEstado() {
    return estado;
  }

  public void setEstado(String estado) {
    this.estado = estado;
  }

  public String getOpcMant() {
    return opcMant;
  }

  public void setOpcMant(String opcMant) {
    this.opcMant = opcMant;
  }

  public MmRequisCab getCab() {
    return cab;
  }

  public void setCab(MmRequisCab cab) {
    this.cab = cab;
  }

  @PrePersist
  @PreUpdate
  void upper() {
    if (observ != null)
      observ = observ.toUpperCase();
    if (estado != null)
      estado = estado.toUpperCase();
    if (cUnidad != null)
      cUnidad = cUnidad.toUpperCase();
  }

  // Estilo KitchenHack: el Id vive en el mismo entity (antes MmRequisDetId.java).
  public static class MmRequisDetId implements Serializable {
    private String codSociedad;
    private String nroDoc;
    private Long nroItem;

    public MmRequisDetId() {
    }

    public MmRequisDetId(String codSociedad, String nroDoc, Long nroItem) {
      this.codSociedad = codSociedad;
      this.nroDoc = nroDoc;
      this.nroItem = nroItem;
    }

    public String getCodSociedad() {
      return codSociedad;
    }

    public void setCodSociedad(String codSociedad) {
      this.codSociedad = codSociedad;
    }

    public String getNroDoc() {
      return nroDoc;
    }

    public void setNroDoc(String nroDoc) {
      this.nroDoc = nroDoc;
    }

    public Long getNroItem() {
      return nroItem;
    }

    public void setNroItem(Long nroItem) {
      this.nroItem = nroItem;
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) {
        return true;
      }
      if (o == null || getClass() != o.getClass()) {
        return false;
      }
      MmRequisDetId that = (MmRequisDetId) o;
      return Objects.equals(codSociedad, that.codSociedad)
          && Objects.equals(nroDoc, that.nroDoc)
          && Objects.equals(nroItem, that.nroItem);
    }

    @Override
    public int hashCode() {
      return Objects.hash(codSociedad, nroDoc, nroItem);
    }
  }
}
