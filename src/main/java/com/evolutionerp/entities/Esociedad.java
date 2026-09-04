package com.evolutionerp.entities;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(schema = "evo", name = "esociedad")
public class Esociedad {
  @Id
  @Column(name = "cod_sociedad", length = 10)
  private String codSociedad;
  @Column(name = "nom_sociedad", nullable = false, length = 100)
  private String nomSociedad;
  @Column(name = "nit_sociedad", nullable = false, length = 11)
  private String nitSociedad;
  @Column(length = 2)
  private String idPais = "PE";
  @Column(length = 2)
  private String idIdioma = "ES";
  @Column(name = "nom_comercial", length = 100)
  private String nomComercial;
  @Column(length = 12)
  private String opcMant = "ACTIVO";
  @Column(name = "user_sis", nullable = false, length = 12)
  private String userSis;
  @Column(name = "user_mod", nullable = false, length = 12)
  private String userMod;
  @Column(name = "user_sis_date", nullable = false)
  private LocalDateTime userSisDate;

  public Esociedad() {
  }

  public Esociedad(String codSociedad, String nomSociedad, String nitSociedad, String idPais, String idIdioma,
      String nomComercial, String opcMant, String userSis, String userMod, LocalDateTime userSisDate) {
    this.codSociedad = codSociedad;
    this.nomSociedad = nomSociedad;
    this.nitSociedad = nitSociedad;
    this.idPais = idPais;
    this.idIdioma = idIdioma;
    this.nomComercial = nomComercial;
    this.opcMant = opcMant;
    this.userSis = userSis;
    this.userMod = userMod;
    this.userSisDate = userSisDate;
  }

  public String getCodSociedad() {
    return codSociedad;
  }

  public void setCodSociedad(String codSociedad) {
    this.codSociedad = codSociedad;
  }

  public String getNomSociedad() {
    return nomSociedad;
  }

  public void setNomSociedad(String nomSociedad) {
    this.nomSociedad = nomSociedad;
  }

  public String getNitSociedad() {
    return nitSociedad;
  }

  public void setNitSociedad(String nitSociedad) {
    this.nitSociedad = nitSociedad;
  }

  public String getIdPais() {
    return idPais;
  }

  public void setIdPais(String idPais) {
    this.idPais = idPais;
  }

  public String getIdIdioma() {
    return idIdioma;
  }

  public void setIdIdioma(String idIdioma) {
    this.idIdioma = idIdioma;
  }

  public String getNomComercial() {
    return nomComercial;
  }

  public void setNomComercial(String nomComercial) {
    this.nomComercial = nomComercial;
  }

  public String getOpcMant() {
    return opcMant;
  }

  public void setOpcMant(String opcMant) {
    this.opcMant = opcMant;
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

  @PrePersist
  @PreUpdate
  void upper() {
    if (nomSociedad != null)
      nomSociedad = nomSociedad.toUpperCase();
    if (nomComercial != null)
      nomComercial = nomComercial.toUpperCase();
  }
}
