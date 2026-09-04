package com.evolutionerp.entities;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(schema = "evo", name = "enumrangos")
@IdClass(EnumRangos.EnumRangosId.class)
public class EnumRangos {
  @Id
  @Column(name = "cod_sociedad", length = 10)
  private String codSociedad;
  @Id
  @Column(length = 4)
  private String app;
  @Column(name = "ultimo_num", nullable = false)
  private Integer ultimoNum;
  @Column(name = "nro_doc", insertable = false, updatable = false, length = 12)
  private String nroDoc;

  public EnumRangos() {
  }

  public EnumRangos(String codSociedad, String app, Integer ultimoNum, String nroDoc) {
    this.codSociedad = codSociedad;
    this.app = app;
    this.ultimoNum = ultimoNum;
    this.nroDoc = nroDoc;
  }

  public String getCodSociedad() {
    return codSociedad;
  }

  public void setCodSociedad(String codSociedad) {
    this.codSociedad = codSociedad;
  }

  public String getApp() {
    return app;
  }

  public void setApp(String app) {
    this.app = app;
  }

  public Integer getUltimoNum() {
    return ultimoNum;
  }

  public void setUltimoNum(Integer ultimoNum) {
    this.ultimoNum = ultimoNum;
  }

  public String getNroDoc() {
    return nroDoc;
  }

  public void setNroDoc(String nroDoc) {
    this.nroDoc = nroDoc;
  }

  // Estilo KitchenHack: el Id vive en el mismo entity (antes EnumRangosId.java).
  public static class EnumRangosId implements Serializable {
    private String codSociedad;
    private String app;

    public EnumRangosId() {
    }

    public EnumRangosId(String codSociedad, String app) {
      this.codSociedad = codSociedad;
      this.app = app;
    }

    public String getCodSociedad() {
      return codSociedad;
    }

    public void setCodSociedad(String codSociedad) {
      this.codSociedad = codSociedad;
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
      EnumRangosId that = (EnumRangosId) o;
      return Objects.equals(codSociedad, that.codSociedad)
          && Objects.equals(app, that.app);
    }

    @Override
    public int hashCode() {
      return Objects.hash(codSociedad, app);
    }
  }
}
