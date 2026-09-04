package com.evolutionerp.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;


public class MmRequisDetDTO {
  private Long nroItem;
  @Size(max = 42)
  private String codMaterial;
  @NotBlank
  @Size(max = 10)
  private String cUnidad;
  @NotNull
  @DecimalMin(value = "0.0", inclusive = true)
  private BigDecimal cantid;
  private BigDecimal ncantidadRecibida;
  @Size(max = 400)
  private String observ;
  @Size(max = 12)
  private String ccodProveedor;
  @Size(max = 12)
  private String estado;
  @Size(max = 10)
  private String codSociedad;
  @Size(max = 12)
  private String nroDoc;
  @Size(max = 12)
  private String opcMant;

  public MmRequisDetDTO() {
  }

  public Long getNroItem() {
    return nroItem;
  }

  public void setNroItem(Long nroItem) {
    this.nroItem = nroItem;
  }

  public String getCodMaterial() {
    return codMaterial;
  }

  public void setCodMaterial(String codMaterial) {
    this.codMaterial = codMaterial;
  }

  // JavaBeans: getCUnidad() serializaría como "CUnidad"/"cunidad".
  // Se fija el nombre JSON del contrato front-backend.
  @JsonProperty("cUnidad")
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

  public String getOpcMant() {
    return opcMant;
  }

  public void setOpcMant(String opcMant) {
    this.opcMant = opcMant;
  }
}
