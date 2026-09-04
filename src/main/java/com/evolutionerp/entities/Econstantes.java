package com.evolutionerp.entities;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(schema = "evo", name = "econstantes")
@IdClass(Econstantes.EconstantesId.class)
public class Econstantes {
  @Id
  @Column(name = "cod_sociedad", length = 10)
  private String codSociedad;
  @Id
  @Column(name = "cvalor", length = 10)
  private String cvalor;
  @Id
  @Column(name = "app", length = 4)
  private String app;
  @Column(name = "cnom_valor", nullable = false, length = 100)
  private String cnomValor;
  @Column(length = 12)
  private String opcMant = "ACTIVO";
  @Column(name = "user_sis", nullable = false, length = 12)
  private String userSis;
  @Column(name = "user_mod", nullable = false, length = 12)
  private String userMod;
  @Column(name = "user_sis_date", nullable = false)
  private LocalDateTime userSisDate;

  public Econstantes() {
  }

  public Econstantes(String codSociedad, String cvalor, String app, String cnomValor, String opcMant, String userSis,
      String userMod, LocalDateTime userSisDate) {
    this.codSociedad = codSociedad;
    this.cvalor = cvalor;
    this.app = app;
    this.cnomValor = cnomValor;
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

  public String getCvalor() {
    return cvalor;
  }

  public void setCvalor(String cvalor) {
    this.cvalor = cvalor;
  }

  public String getApp() {
    return app;
  }

  public void setApp(String app) {
    this.app = app;
  }

  public String getCnomValor() {
    return cnomValor;
  }

  public void setCnomValor(String cnomValor) {
    this.cnomValor = cnomValor;
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
    if (cnomValor != null)
      cnomValor = cnomValor.toUpperCase();
    if (app != null)
      app = app.toUpperCase();
  }

  // Estilo KitchenHack: el Id vive en el mismo entity (antes EconstantesId.java).
  public static class EconstantesId implements Serializable {
    private String codSociedad;
    private String cvalor;
    private String app;

    public EconstantesId() {
    }

    public EconstantesId(String codSociedad, String cvalor, String app) {
      this.codSociedad = codSociedad;
      this.cvalor = cvalor;
      this.app = app;
    }

    public String getCodSociedad() {
      return codSociedad;
    }

    public void setCodSociedad(String codSociedad) {
      this.codSociedad = codSociedad;
    }

    public String getCvalor() {
      return cvalor;
    }

    public void setCvalor(String cvalor) {
      this.cvalor = cvalor;
    }

    public String getApp() {
      return app;
    }

    public void setApp(String app) {
      this.app = app;
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) {
        return true;
      }
      if (o == null || getClass() != o.getClass()) {
        return false;
      }
      EconstantesId that = (EconstantesId) o;
      return Objects.equals(codSociedad, that.codSociedad)
          && Objects.equals(cvalor, that.cvalor)
          && Objects.equals(app, that.app);
    }

    @Override
    public int hashCode() {
      return Objects.hash(codSociedad, cvalor, app);
    }
  }
}
