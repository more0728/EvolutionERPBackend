package com.evolutionerp.dtos;

import jakarta.validation.constraints.*;

// Estilo KitchenHack: DTO plano, FK como Long (tipo de PK vigente en users).
public class RoleDTO {
  private Long id;
  @Size(max = 50)
  private String rol;
  private Long userId;
  private String username;

  public RoleDTO() {
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getRol() {
    return rol;
  }

  public void setRol(String rol) {
    this.rol = rol;
  }

  public Long getUserId() {
    return userId;
  }

  public void setUserId(Long userId) {
    this.userId = userId;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }
}
