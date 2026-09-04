package com.evolutionerp.dtos;

import jakarta.validation.constraints.*;
import jakarta.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;

public class MmRequisCabDTO {
  @NotBlank
  @Size(max = 10)
  private String codSociedad;
  @Size(max = 12)
  private String nroDoc;
  @NotNull
  private LocalDateTime fecDoc;
  @NotNull
  private LocalDateTime fecReq;
  @NotBlank
  @Size(max = 10)
  private String ccodCencos;
  @Size(max = 12)
  private String ccodPerson;
  @Size(max = 200)
  private String lugarEntr;
  @Size(max = 12)
  private String ccodProveedor;
  @Size(max = 10)
  private String tipPrio;
  @Size(max = 400)
  private String observ;
  @Size(max = 12)
  private String estado;
  @Size(max = 4)
  private String notaEntrada;
  @Size(max = 1)
  private String condic;
  @Valid
  @NotEmpty
  private List<MmRequisDetDTO> detalles;
  @Size(max = 12)
  private String opcMant;
  @Size(max = 4)
  private String app;
  @Size(max = 12)
  private String userSis;
  @Size(max = 12)
  private String userMod;
  private LocalDateTime userSisDate;

  public MmRequisCabDTO() {
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

  public LocalDateTime getFecDoc() {
    return fecDoc;
  }

  public void setFecDoc(LocalDateTime fecDoc) {
    this.fecDoc = fecDoc;
  }

  public LocalDateTime getFecReq() {
    return fecReq;
  }

  public void setFecReq(LocalDateTime fecReq) {
    this.fecReq = fecReq;
  }

  public String getCcodCencos() {
    return ccodCencos;
  }

  public void setCcodCencos(String ccodCencos) {
    this.ccodCencos = ccodCencos;
  }

  public String getCcodPerson() {
    return ccodPerson;
  }

  public void setCcodPerson(String ccodPerson) {
    this.ccodPerson = ccodPerson;
  }

  public String getLugarEntr() {
    return lugarEntr;
  }

  public void setLugarEntr(String lugarEntr) {
    this.lugarEntr = lugarEntr;
  }

  public String getCcodProveedor() {
    return ccodProveedor;
  }

  public void setCcodProveedor(String ccodProveedor) {
    this.ccodProveedor = ccodProveedor;
  }

  public String getTipPrio() {
    return tipPrio;
  }

  public void setTipPrio(String tipPrio) {
    this.tipPrio = tipPrio;
  }

  public String getObserv() {
    return observ;
  }

  public void setObserv(String observ) {
    this.observ = observ;
  }

  public String getEstado() {
    return estado;
  }

  public void setEstado(String estado) {
    this.estado = estado;
  }

  public String getNotaEntrada() {
    return notaEntrada;
  }

  public void setNotaEntrada(String notaEntrada) {
    this.notaEntrada = notaEntrada;
  }

  public String getCondic() {
    return condic;
  }

  public void setCondic(String condic) {
    this.condic = condic;
  }

  public List<MmRequisDetDTO> getDetalles() {
    return detalles;
  }

  public void setDetalles(List<MmRequisDetDTO> detalles) {
    this.detalles = detalles;
  }

  public String getOpcMant() {
    return opcMant;
  }

  public void setOpcMant(String opcMant) {
    this.opcMant = opcMant;
  }

  public String getApp() {
    return app;
  }

  public void setApp(String app) {
    this.app = app;
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
