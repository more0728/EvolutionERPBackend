
package com.evolutionerp.model;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
@Entity @Table(schema="evo", name="econstantes") @IdClass(EconstantesId.class)
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Econstantes {
  @Id @Column(name="cod_sociedad", length=10) private String codSociedad;
  @Id @Column(name="cvalor", length=10) private String cvalor;
  @Id @Column(name="app", length=4) private String app;
  @Column(name="cnom_valor", nullable=false, length=100) private String cnomValor;
  @Column(length=12) private String opcMant="ACTIVO";
  @Column(name="user_sis", nullable=false, length=12) private String userSis;
  @Column(name="user_mod", nullable=false, length=12) private String userMod;
  @Column(name="user_sis_date", nullable=false) private LocalDateTime userSisDate;
  @PrePersist @PreUpdate void upper(){ if(cnomValor!=null) cnomValor=cnomValor.toUpperCase(); if(app!=null) app=app.toUpperCase(); }
}
