package com.evolutionerp.dtos;

// Estilo KitchenHack: DTO plano sin Lombok, getters/setters manuales.
public class BProveedorDTO {
    private String ccodProveedor;
    private String nomProv;
    private String ruc;
    private String opcMant;

  public BProveedorDTO() {
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
}
