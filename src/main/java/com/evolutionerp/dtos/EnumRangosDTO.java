package com.evolutionerp.dtos;

import jakarta.validation.constraints.*;

public class EnumRangosDTO {
  @NotBlank
  @Size(max = 10)
  private String codSociedad;
  @NotBlank
  @Size(max = 4)
  private String app;
  @NotNull
  @Min(0)
  private Integer ultimoNum;
  private String nroDoc;

  public EnumRangosDTO() {
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
}
