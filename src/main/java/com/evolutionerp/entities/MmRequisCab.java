package com.evolutionerp.entities;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(schema = "evo", name = "mmrequis_cab")
@IdClass(MmRequisCab.MmRequisCabId.class)
public class MmRequisCab {
  @Id
  @Column(name = "cod_sociedad", length = 10)
  private String codSociedad;
  @Id
  @Column(name = "nro_doc", length = 12)
  private String nroDoc;
  @Column(name = "fec_doc", nullable = false)
  private LocalDateTime fecDoc;
  @Column(name = "fec_req", nullable = false)
  private LocalDateTime fecReq;
  @Column(name = "ccod_cencos", nullable = false, length = 10)
  private String ccodCencos;
  @Column(name = "ccod_person", length = 12)
  private String ccodPerson;
  @Column(name = "lugar_entr", length = 200)
  private String lugarEntr;
  @Column(name = "ccod_proveedor", length = 12)
  private String ccodProveedor;
  @Column(name = "tip_prio", length = 10)
  private String tipPrio;
  @Column(length = 400)
  private String observ;
  @Column(length = 12)
  private String estado = "PENDIENTE";
  @Column(name = "nota_entrada", length = 4)
  private String notaEntrada;
  @Column(columnDefinition = "bpchar", length = 1)
  private String condic = "A";
  @Column(name = "opc_mant", length = 12)
  private String opcMant = "ACTIVO";
  @Column(length = 4, nullable = false)
  private String app = "COM";
  @Column(name = "user_sis", nullable = false, length = 12)
  private String userSis;
  @Column(name = "user_mod", nullable = false, length = 12)
  private String userMod;
  @Column(name = "user_sis_date", nullable = false)
  private LocalDateTime userSisDate;
  @OneToMany(mappedBy = "cab", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<MmRequisDet> detalles = new ArrayList<>();

  public MmRequisCab() {
  }

  public MmRequisCab(String codSociedad, String nroDoc, LocalDateTime fecDoc, LocalDateTime fecReq, String ccodCencos,
      String ccodPerson, String lugarEntr, String ccodProveedor, String tipPrio, String observ, String estado,
      String notaEntrada, String condic, String opcMant, String app, String userSis, String userMod,
      LocalDateTime userSisDate, List<MmRequisDet> detalles) {
    this.codSociedad = codSociedad;
    this.nroDoc = nroDoc;
    this.fecDoc = fecDoc;
    this.fecReq = fecReq;
    this.ccodCencos = ccodCencos;
    this.ccodPerson = ccodPerson;
    this.lugarEntr = lugarEntr;
    this.ccodProveedor = ccodProveedor;
    this.tipPrio = tipPrio;
    this.observ = observ;
    this.estado = estado;
    this.notaEntrada = notaEntrada;
    this.condic = condic;
    this.opcMant = opcMant;
    this.app = app;
    this.userSis = userSis;
    this.userMod = userMod;
    this.userSisDate = userSisDate;
    this.detalles = detalles;
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

  public List<MmRequisDet> getDetalles() {
    return detalles;
  }

  public void setDetalles(List<MmRequisDet> detalles) {
    this.detalles = detalles;
  }

  @PrePersist
  @PreUpdate
  void upper() {
    if (lugarEntr != null)
      lugarEntr = lugarEntr.toUpperCase();
    if (observ != null)
      observ = observ.toUpperCase();
    if (estado != null)
      estado = estado.toUpperCase();
  }

  // Estilo KitchenHack: el Id vive en el mismo entity (antes MmRequisCabId.java).
  public static class MmRequisCabId implements Serializable {
    private String codSociedad;
    private String nroDoc;

    public MmRequisCabId() {
    }

    public MmRequisCabId(String codSociedad, String nroDoc) {
      this.codSociedad = codSociedad;
      this.nroDoc = nroDoc;
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

    @Override
    public boolean equals(Object o) {
      if (this == o) {
        return true;
      }
      if (o == null || getClass() != o.getClass()) {
        return false;
      }
      MmRequisCabId that = (MmRequisCabId) o;
      return Objects.equals(codSociedad, that.codSociedad)
          && Objects.equals(nroDoc, that.nroDoc);
    }

    @Override
    public int hashCode() {
      return Objects.hash(codSociedad, nroDoc);
    }
  }
}
